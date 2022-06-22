package ru.javawebinar.topjava;

import static org.assertj.core.api.Assertions.assertThat;

public class TestUtil {

    public static <T> void assertMatch(T actual, T expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static <T> void assertMatchIgnoringFields(T actual, T expected, String... fields) {
        assertThat(actual).usingRecursiveComparison().ignoringFields(fields).isEqualTo(expected);
    }

    public static <T> void assertMatch(Iterable<T> actual, Iterable<T> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }

    public static <T> void assertMatchIgnoringFields(Iterable<T> actual, Iterable<T> expected, String... fields) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparatorIgnoringFields(fields).isEqualTo(expected);
    }
}
