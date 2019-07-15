package lru;

public class ArrayListLru<E> {

    Object[] elementData;

    class Node {

        private Node pre;

        private Node next;

        private int size;

    }
}
