package my.tests.max_finder_with_swagger.service.alg;

import java.util.Comparator;
import java.util.Iterator;

public class SortedSetOfNMax<T> {

    private Comparator<T> comparator;
    private int maxSize = 0;
    private int currentSize = 0;

    private SortedSetOfNMaxItem<T> root;

    public SortedSetOfNMax(int N, Comparator<T> comparator) {
        this.maxSize = N;
        this.comparator = comparator;
    }

    public void addItems(Iterator<T> itemValuesIterator) {
        while (itemValuesIterator.hasNext()) {
            addItem(itemValuesIterator.next());
        }
    }

    public void addItem(T itemValue) {
        SortedSetOfNMaxItem<T> item = new SortedSetOfNMaxItem<>(itemValue);
        if (maxSize < 1) {
            throw new RuntimeException("N is less than 1");
        }
        if (root == null) {
            root = item;
            currentSize = 1;
        } else {
            insert(root, item);
            if (currentSize > maxSize) {
                removeMinItem();
            }
        }
    }

    private void removeMinItem() {
        SortedSetOfNMaxItem<T> currentItem = root;
        SortedSetOfNMaxItem<T> itemBefore = null;
        while (currentItem.getLeft() != null) {
            itemBefore = currentItem;
            currentItem = currentItem.getLeft();
        }
        if (currentItem == root) {
            root = root.getRight();
        } else {
            itemBefore.setLeft(null);
        }
        currentSize--;
    }

    private void insert(SortedSetOfNMaxItem<T> localRoot, SortedSetOfNMaxItem<T> item) {
        int comp = comparator.compare(localRoot.getValue(), item.getValue());
        if (comp > 0) {
            if (localRoot.getLeft() == null) {
                localRoot.setLeft(item);
                currentSize++;
            } else {
                insert(localRoot.getLeft(), item);
            }

        } else if (comp < 0) {
            if (localRoot.getRight() == null) {
                localRoot.setRight(item);
                currentSize++;
            } else {
                insert(localRoot.getRight(), item);
            }
        }
    }

    public T getResult() {
        if (currentSize == 0 || currentSize < maxSize) {
            return null;
        } else {
            return min().getValue();
        }
    }

    private SortedSetOfNMaxItem<T> max() {
        var currentItem = root;
        while (currentItem.getRight() != null) {
            currentItem = currentItem.getRight();
        }
        return currentItem;
    }

    private SortedSetOfNMaxItem<T> min() {
        var currentItem = root;
        while (currentItem.getLeft() != null) {
            currentItem = currentItem.getLeft();
        }
        return currentItem;
    }

}
