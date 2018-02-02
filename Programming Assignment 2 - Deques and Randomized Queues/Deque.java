/******************************************************************************
 *  Name:    Yao Jiqian
 *  NetID:   JQ
 *  Precept: P02
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description:  implement elementary data structures using arrays and linked 
 *                lists, and to introduce you to generics and iterators.
 ******************************************************************************/

import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;


public class Deque<Item> implements Iterable<Item> {
    
    private Node first, last;
    private int sz;
    
    private class Node {
        
        Node pre;
        Node next;
        Item item;
        // Node() {
        //    item = null;
        //    pre = null;
        //    next = null;
        //}
        
        Node(Item it) {
            item = it;
            pre = null;
            next = null;
        }
        
    }
    
    private class DequeIterator implements Iterator<Item> {
        
        private Node current = first;
        public boolean hasNext() {
            
            return (current != null);
        }
        public Item next() {
            if (current == null) {
                throw new java.util.NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    
    
    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        sz = 0;
    }
    
    // is the deque empty?
    public boolean isEmpty() {
        
        return sz == 0;
    }
    
    // return the number of items on the deque
    public int size() {
        return sz;
    }
    
    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException();
        }
        Node nd = new Node(item);
        if (sz == 0) {
            first = nd;
            last = nd;
            nd.next = null;
            nd.pre = null;
        } else {
            nd.next = first;
            first.pre = nd;
            nd.pre = null;
            first = nd;
        }
        sz++;
    }
    
    // add the item to the end
    public void addLast(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException();
        }
        Node nd = new Node(item);
        if (sz == 0) {
            first = nd;
            last = nd;
            nd.next = null;
            nd.pre = null;
        } else {
            nd.pre = last;
            last.next = nd;
            nd.next = null;
            last = nd;
        }
        sz++;
    }
    
    // remove and return the item from the front
    public Item removeFirst() {
        if (sz == 0) {
            throw new java.util.NoSuchElementException();
        }
        Item it = first.item;
        first = first.next;
        
        sz--;
        if (sz == 0) {
            last = null;
            first = null;
        } else {
            first.pre = null;
        }
        return it;
    }
    
    // remove and return the item from the end
    public Item removeLast() {
        if (sz == 0) {
            throw new java.util.NoSuchElementException();
        }
        Item it = last.item;
        last = last.pre;
        
        sz--;
        if (sz == 0) {
            first = null;
            last = null;
        } else {
            last.next = null;
        }
        return it;
    }
    
    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        
        return new DequeIterator();
    }
    
    // unit testing (optional)
    public static void main(String[] args) {
        
        Deque<Integer> deque = new Deque<Integer>();
        deque.addLast(1);
        deque.addFirst(2);
        deque.removeFirst(); // ==> 2 
        deque.removeFirst(); // ==> 1 
        deque.addFirst(5);
        deque.addFirst(6);
        deque.removeFirst(); // ==> 6
        deque.addLast(8);
        deque.addLast(9);
        deque.addFirst(10);
        deque.removeLast(); // ==> 9
        StdOut.println(deque.size());
        Iterator<Integer> it = deque.iterator();
        while (it.hasNext()) {
            StdOut.println(it.next());
            // it.next();
        }
        it.next();
    }
}
