package my.tests.max_finder_with_swagger.service.alg;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class SortedSetOfNMaxTest {

    private int[] numbers = new int[]{2, 3, 4, 5, 6, 3, 4, 5, 6, 6, 7, 8, 8, 8, 9, 113, 56, 33};

    @Test
    void positiveCaseWithIntegerTwo() {
        SortedSetOfNMax<Integer> set = new SortedSetOfNMax<>(1, Integer::compare);
        set.addItems(IntStream.of(new int[]{2, 3}).iterator());
        assertEquals(3, set.getResult());
    }

    @Test
    void positiveCaseWithInteger5() {
        SortedSetOfNMax<Integer> set = new SortedSetOfNMax<>(5, Integer::compare);
        set.addItems(IntStream.of(numbers).iterator());
        assertEquals(8, set.getResult());
    }


    @Test
    void positiveCaseWithInteger() {
        SortedSetOfNMax<Integer> set = new SortedSetOfNMax<>(2, Integer::compare);
        set.addItems(IntStream.of(numbers).iterator());
        assertEquals(56, set.getResult());
    }

    @Test
    void positiveCaseWithInteger2() {
        SortedSetOfNMax<Integer> set = new SortedSetOfNMax<>(1, Integer::compare);
        set.addItems(IntStream.of(numbers).iterator());
        assertEquals(113, set.getResult());
    }

    @Test
    void positiveCaseWithInteger3() {
        SortedSetOfNMax<Integer> set = new SortedSetOfNMax<>(3, Integer::compare);
        set.addItems(IntStream.of(numbers).iterator());
        assertEquals(33, set.getResult());
    }

    @Test
    void positiveCaseWithInteger4() {

        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> {
                    SortedSetOfNMax<Integer> set = new SortedSetOfNMax<>(0, Integer::compare);
                    set.addItems(IntStream.of(numbers).iterator());
                }
        );

        assertTrue(thrown.getMessage().contains("N is less than 1"));
    }

    @Test
    void positiveCaseWithStrings() {
        SortedSetOfNMax<String> set = new SortedSetOfNMax<>(3, String::compareTo);
        set.addItems(Stream.of("aaa", "bbb", "ccc", "ddd", "aaa", "a").sequential().iterator());
        assertEquals("bbb", set.getResult());
    }

    @Test
    void positiveCaseWithOnlyOneElement() {
        SortedSetOfNMax<String> set = new SortedSetOfNMax<>(1, String::compareTo);
        set.addItems(Stream.of("aaa").sequential().iterator());
        assertEquals("aaa", set.getResult());
    }

    @Test
    void positiveCaseWithLessElement() {
        SortedSetOfNMax<String> set = new SortedSetOfNMax<>(2, String::compareTo);
        set.addItems(Stream.of("aaa").sequential().iterator());
        assertNull(set.getResult());
    }

    @Test
    void positiveCaseWithLessMaxElements() {
        SortedSetOfNMax<String> set = new SortedSetOfNMax<>(2, String::compareTo);
        set.addItems(Stream.of("aaa", "aaa", "aaa").sequential().iterator());
        assertNull(set.getResult());
    }

}