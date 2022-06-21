package ru.javawebinar.topjava;

import org.assertj.core.api.Assertions;

public class TestUtil {
    public static <T> void assertMatch(Iterable<T> actual, Iterable<T> expected) {
        Assertions.assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}
