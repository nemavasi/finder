package my.tests.max_finder_with_swagger.service.alg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SortedSetOfNMaxItem<T> {

    public SortedSetOfNMaxItem(T value) {
        this.value = value;
    }

    private T value;
    private SortedSetOfNMaxItem<T> left;
    private SortedSetOfNMaxItem<T> right;
}
