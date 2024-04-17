package com.college.student.cache.lru_dll;

import java.util.LinkedList;
import java.util.List;

public class LRU_DoubleLinkedList<T> {
    private Node<T> head;
    private Node<T> tail;

    public void addLast(T key) {
        Node<T> node = new Node<>(key);
        if (head == null) {
            head = tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
    }

    public void addFirst(T key) {
        Node<T> node = new Node<>(key);
        if (head == null) {
            head = tail = node;
        } else {
            node.next = head;
            head.prev = node;
            head = node;
        }
    }

    public T removeLast() {
        Node<T> temp = null;
        if (tail != null) {
            temp = tail;
            tail = tail.prev;
            if (tail != null) {
                tail.next = null;
            }
        }
        assert temp != null;
        return temp.key;
    }

    public void remove(T key) {
        if (head.key == key) {
            head = head.next;
            if (head != null) {
                head.prev = null;
            } else tail = null;
        }
        assert tail != null;
        if (tail.key == key) {
            tail = tail.prev;
            if (tail != null) tail.prev = null;
            else head = null;
        }
        Node<T> temp = head;
        while (temp != null) {
            if (temp.key == key) {
                assert temp.prev != null;
                temp.prev.next = temp.next;
                temp.next.prev = temp.prev;
            }
            temp = temp.next;
        }
    }

    public T get(T key) {
        if (head == null) return null;
        if (head.key == key) return key;
        if (tail.key == key) {
            tail.prev.next = null;
            addFirst(key);
            tail = tail.prev;
            return key;
        }
        Node<T> temp = head;
        while (temp != null) {
            if (temp.key == key) {
                temp.next.prev = temp.prev;
                temp.prev.next = temp.next;
                temp.next = head;
                head.prev = temp;
                head = temp;
                return temp.key;
            }
            temp = temp.next;
        }
        return null;
    }

    public List<T> getAll() {
        List<T> keySet = new LinkedList<>();
        Node<T> temp = head;
        while (temp != null) {
            keySet.add(temp.key);
            temp = temp.next;
        }
        return keySet;
    }

    class Node<E> {
        E key;
        Node<T> next;
        Node<T> prev;

        Node(E key) {
            this.key = key;
            next = prev = null;
        }
    }
}
