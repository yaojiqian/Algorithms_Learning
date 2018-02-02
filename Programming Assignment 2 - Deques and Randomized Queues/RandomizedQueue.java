/******************************************************************************
 *  Name:    Yao Jiqian
 *  NetID:   JQ
 *  Precept: P02
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description:  A randomized queue is similar to a stack or queue, 
 *                except that the item removed is chosen uniformly at random 
 *                from items in the data structure.
 ******************************************************************************/

import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

public class RandomizedQueue<Item> implements Iterable<Item> {
    
    private NodeBlock firstBlock;
    // private int blockCount;
    // private Node first;
    private int sz;
    
    private class Node {
        Item item;
        Node next;
    }
    
    private class NodeBlock implements Iterable<Item> {
        private static final int BLOCK_SIZE = 32;
        private Node first;
        private int nodeCount = 0;
        private NodeBlock next;
        
        public boolean isFull() {
            return nodeCount >= BLOCK_SIZE;
        }
        
        public int count() {
            return nodeCount;
        }
        
        public void addNode(Item item) {
            if (nodeCount >= BLOCK_SIZE) {
                throw new java.util.NoSuchElementException();
            }
            Node nd = new Node();
            nd.item = item;
            nd.next = first;
            first = nd;
            nodeCount++;
        }
        
        public Item removeNode(int idx) {
            Node current = first;
            Node pre = first;
            while (idx > 0 && current != null) {
                pre = current;
                current = current.next;
                idx--;
            }
            
            if (current == null) {
                throw new java.util.NoSuchElementException();
            }
            
            pre.next = current.next;
            Item it = current.item;
            nodeCount--;
            return it;
        }
        
        public Item getNode(int idx) {
            Node current = first;
            while (idx > 0 && current != null) {
                current = current.next;
                idx--;
            }
            
            if (current == null) {
                throw new java.util.NoSuchElementException();
            }
            
            Item it = current.item;
            return it;
        }
        
        public Iterator<Item> iterator() {
            return new NodeBlockIterator();
        }
        
        private class NodeBlockIterator implements Iterator<Item> {
            
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
    }
    
    private class RandomizedQueueIterator implements Iterator<Item> {
        
        private int current = 0;
        private final int[] index;
        private final Object[] itemsInRandom;
        
        public RandomizedQueueIterator() {
            index = StdRandom.permutation(sz);
            itemsInRandom = new Object[sz];
            NodeBlock currentBlock = firstBlock;
            int indexInCurrentBlock = 0;
            Iterator<Item> it = firstBlock.iterator();
            for (int i = 0; i < sz; i++) {
                if (indexInCurrentBlock < currentBlock.count()) {
                    itemsInRandom[index[i]] = it.next();
                    indexInCurrentBlock++;
                } else {
                    currentBlock = currentBlock.next;
                    it = currentBlock.iterator();
                    itemsInRandom[index[i]] = it.next();
                    indexInCurrentBlock = 1;
                }
            }
        }
        
        public boolean hasNext() {
            return current < sz;
        }
        
        public Item next() {
            if (sz == 0 || index == null || current >= index.length) {
                throw new java.util.NoSuchElementException();
            }
            // int itIdx = index[current++];

            return (Item) itemsInRandom[current++];
        }
        
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }
    
    // construct an empty randomized queue
    public RandomizedQueue() {
        firstBlock = new NodeBlock();
        // first = null;
        sz = 0;
    }
    
    // is the randomized queue empty?
    public boolean isEmpty() {
        return sz == 0;
    }
    
    // return the number of items on the randomized queue
    public int size() {
        return sz;
    }
    
    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException();
        }
        
        NodeBlock currentBlock = firstBlock;
        NodeBlock preBlock = firstBlock;
        while (currentBlock != null && currentBlock.isFull()) {
            preBlock = currentBlock;
            currentBlock = currentBlock.next;
        }
        
        if (currentBlock == null) {
            currentBlock = new NodeBlock();
            currentBlock.addNode(item);
            preBlock.next = currentBlock;
            currentBlock.next = null;
        } else {
            currentBlock.addNode(item);
        }

        sz++;
    }
    
    // remove and return a random item
    public Item dequeue() {
        if (sz == 0) {
            throw new java.util.NoSuchElementException();
        }
        int itIdx = StdRandom.uniform(sz);
        // StdOut.printf("itIdx = %d \n", itIdx);
        NodeBlock currentBlock = firstBlock;
        NodeBlock preBlock = firstBlock;
        while (currentBlock.count() <= itIdx) {
            itIdx -= currentBlock.count();
            preBlock = currentBlock;
            currentBlock = currentBlock.next;
        }
        
        Item it = currentBlock.removeNode(itIdx);
        if (currentBlock.count() == 0) {
            preBlock.next = currentBlock.next;
            // currentBlock = null;
        }
        
        sz--;
        return it;
    }
    // return a random item (but do not remove it)
    public Item sample() {
        if (sz == 0) {
            throw new java.util.NoSuchElementException();
        }
        int itIdx = StdRandom.uniform(sz);
        NodeBlock currentBlock = firstBlock;
        while (currentBlock.count() <= itIdx) {
            itIdx -= currentBlock.count();
            currentBlock = currentBlock.next;
        }
        
        Item it = currentBlock.getNode(itIdx);
        return it;
    }
    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }
    // unit testing (optional)
    public static void main(String[] args) {
        
        RandomizedQueue<Integer> q = new RandomizedQueue<Integer>();
        for (int i = 0; i < 10000; i++) {
            q.enqueue(i);
        }
        /*
        q.enqueue("two");
        q.enqueue("one");
        q.enqueue("three");
        q.enqueue("four");
        q.enqueue("six");
        q.enqueue("eight");
        q.enqueue("five");
        q.enqueue("seven");
        
        String s = q.dequeue();
        StdOut.println(s);
        s = q.dequeue();
        StdOut.println(s);
        s = q.dequeue();
        StdOut.println(s);
        s = q.dequeue();
        StdOut.println(s);
        */
        /*
        Iterator<Integer> it = q.iterator();
        StdOut.println(q.size());
        while (it.hasNext()) {
            Integer i = it.next();
            StdOut.println(i);
        }
        */
        
        for (int i = 0; i < 1000; i++) {
            int k = q.sample();
            StdOut.printf("i = %d, k = %d\n", i, k);
        }
        StdOut.println("++++++++++++++++++++++++++++++++++");
        
        Iterator<Integer> it2 = q.iterator();
        StdOut.println(q.size());
        while (it2.hasNext()) {
            Integer i = it2.next();
            StdOut.println(i);
        }    
        
    }
}