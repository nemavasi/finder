package my.tests.max_finder_with_swagger.service.alg;

import java.util.Iterator;

public interface SetOfNMax<T> {

    void addItems(Iterator<T> itemValuesIterator);

    void addItem(T itemValue);

    T getResult();
}
