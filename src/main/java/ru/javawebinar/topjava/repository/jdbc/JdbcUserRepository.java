package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.util.*;

@Repository
public class JdbcUserRepository implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private static final ResultSetExtractor<List<User>> resultSetExtractor = rs -> {
        Map<Integer, User> result = new LinkedHashMap<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            User user = result.get(id);
            if (user == null) {
                user = new User(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getInt("calories_per_day"),
                        rs.getBoolean("enabled"),
                        rs.getDate("registered"), new HashSet<>());
                result.put(id, user);
            }
            String role = rs.getString("role");
            if (role != null) {
                user.getRoles().add(Role.valueOf(role));
            }
        }
        return new ArrayList<>(result.values());
    };

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public User save(User user) {
        ValidationUtil.jdbcValidate(user);
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
            batchInsertRoles(user.getRoles(), user.getId());
        } else {
            if (namedParameterJdbcTemplate.update("""
                       UPDATE users SET name=:name, email=:email, password=:password, 
                       registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id
                    """, parameterSource) != 0) {
                batchUpdateRoles(user.getRoles(), user.getId());
            } else {
                return null;
            }
        }
        return user;
    }

    private int[][] batchInsertRoles(Set<Role> roles, int userId) {
        return jdbcTemplate.batchUpdate(
                "INSERT INTO user_roles (user_id, role) VALUES (?,?)",
                roles,
                roles.size(),
                (ps, argument) -> {
                    ps.setInt(1, userId);
                    ps.setString(2, argument.name());
                }
        );
    }

    public int[][] batchUpdateRoles(Set<Role> roles, int userId) {
        return jdbcTemplate.batchUpdate(
                "UPDATE user_roles SET role=? WHERE user_id=?",
                roles,
                roles.size(),
                (ps, argument) -> {
                    ps.setString(1, argument.name());
                    ps.setInt(2, userId);
                }
        );
    }

    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users LEFT JOIN user_roles ur on users.id = ur.user_id WHERE users.id=?",
                resultSetExtractor, id);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query(
                "SELECT * FROM users LEFT JOIN user_roles ur on users.id = ur.user_id WHERE email=?",
                resultSetExtractor,
                email);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query(
                "SELECT * FROM users LEFT JOIN user_roles ur on users.id = ur.user_id ORDER BY name, email",
                resultSetExtractor);
    }
}
