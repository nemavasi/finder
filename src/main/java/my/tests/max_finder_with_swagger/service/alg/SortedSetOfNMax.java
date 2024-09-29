package my.tests.max_finder_with_swagger.service.alg;

import java.util.Comparator;
import java.util.Iterator;

public class SortedSetOfNMax<T> implements SetOfNMax<T> {

    private Comparator<T> comparator;
    private int maxSize = 0;
    private int currentSize = 0;

    private SortedSetOfNMaxItem<T> root;

    public SortedSetOfNMax(int N, Comparator<T> comparator) {
        this.maxSize = N;
        this.comparator = comparator;
    }

    @Override
    public void addItems(Iterator<T> itemValuesIterator) {
        while (itemValuesIterator.hasNext()) {
            addItem(itemValuesIterator.next());
        }
    }

    @Override
    public void addItem(T itemValue) {
        SortedSetOfNMaxItem<T> item = new SortedSetOfNMaxItem<>(itemValue);
        if (maxSize < 1) {
            throw new RuntimeException("N is less than 1");
        }
        if (root == null) {
            root = item;
            currentSize = 1;
        } else {
            root = insertNew(root, item);
            if (currentSize > maxSize) {
                //removeMinItem();
                root = deleteMinItem(root, null);
            }
        }
    }

    private SortedSetOfNMaxItem<T> insertNew(SortedSetOfNMaxItem<T> localRoot, SortedSetOfNMaxItem<T> item) {
        if (localRoot == null) {
            currentSize++;
            return item;
        }
        int comp = comparator.compare(localRoot.getValue(), item.getValue());
        if (comp > 0) {
            localRoot.setLeft(insertNew(localRoot.getLeft(), item));
        } else if (comp < 0) {
            localRoot.setRight(insertNew(localRoot.getRight(), item));
        }
        return balanceNode(localRoot);
    }


    private SortedSetOfNMaxItem<T> deleteMinItem(SortedSetOfNMaxItem<T> currentNode, SortedSetOfNMaxItem<T> parentNode) {
        if (currentNode.getLeft() != null) {
            currentNode.setLeft(deleteMinItem(currentNode.getLeft(), currentNode));
            return balanceNode(currentNode);
        } else {
            if (currentNode.getRight() == null) {
                currentSize--;
                return null;
            } else {
                currentSize--;
                return currentNode.getRight();
            }

        }
    }

    @Override
    public T getResult() {
        if (currentSize == 0 || currentSize < maxSize) {
            return null;
        } else {
            return min().getValue();
        }
    }

    private SortedSetOfNMaxItem<T> balanceNode(SortedSetOfNMaxItem<T> localRoot) {
        fixHeight(localRoot);
        int balance = bFactor(localRoot);
        //Left Left
        if (balance > 1 && bFactor(localRoot.getLeft()) >= 0) {
            return rotateRight(localRoot);
        }

        //Left Right
        if (balance > 1 && bFactor(localRoot.getLeft()) < 0) {
            localRoot.setLeft(rotateLeft(localRoot.getLeft()));
            return rotateRight(localRoot);
        }

        //Right Right
        if (balance < -1 && bFactor(localRoot.getRight()) <= 0) {
            return rotateLeft(localRoot);
        }

        //Right Left
        if (balance < -1 && bFactor(localRoot.getRight()) > 0) {
            localRoot.setRight(rotateRight(localRoot.getRight()));
            return rotateLeft(localRoot);
        }

        return localRoot;
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

    private int height(SortedSetOfNMaxItem<T> item) {
        return item != null ? item.getHeight() : 0;
    }

    private int bFactor(SortedSetOfNMaxItem<T> item) {
        return height(item.getLeft()) - height(item.getRight());
    }

    private void fixHeight(SortedSetOfNMaxItem<T> item) {
        if (item != null) {
            int hl = height(item.getLeft());
            int hr = height(item.getRight());
            item.setHeight((Math.max(hl, hr)) + 1);
        }
    }

    private SortedSetOfNMaxItem<T> rotateLeft(SortedSetOfNMaxItem<T> item) {
        SortedSetOfNMaxItem<T> oldRight = item.getRight();
        item.setRight(oldRight.getLeft());
        oldRight.setLeft(item);
        fixHeight(item);
        fixHeight(oldRight);
        return oldRight;
    }

    private SortedSetOfNMaxItem<T> rotateRight(SortedSetOfNMaxItem<T> item) {
        SortedSetOfNMaxItem<T> oldLeft = item.getLeft();
        item.setLeft(oldLeft.getRight());
        oldLeft.setRight(item);
        fixHeight(item);
        fixHeight(oldLeft);
        return oldLeft;
    }

}
