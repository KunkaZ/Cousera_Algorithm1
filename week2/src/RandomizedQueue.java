import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int sizeN;
    private Item[] items;
    private int current;

    // construct an empty randomized queue
    public RandomizedQueue() {
        sizeN = 0;
        current = 0;
        items = (Item[]) new Object[1];
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        int j = 0;
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null && j < capacity) {
                copy[j++] = items[i];
            }
        }
        items = copy;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return sizeN == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return sizeN;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Can't add Null item!");
        }
        if (sizeN == items.length) {
            resize(2 * items.length);
            items[sizeN++] = item;
            current = sizeN - 1;
        } else {
            if (items[current] == null) {
                items[current] = item;
                current = 0;  // this only happened after enqueue
                sizeN++;
            } else {
                for (; current < items.length; current++) {
                    if (items[current] == null) {
                        items[current] = item;
                        sizeN++;
                        return;
                    }
                }
            }
        }
    }


    // remove and return a random item
    public Item dequeue() {
        if (sizeN == 0) {
            throw new NoSuchElementException("Empty queue!");
        }
        int ind = StdRandom.uniform(0, items.length);
        while (items[ind] == null) {
            ind = StdRandom.uniform(0, items.length);
        }

        Item retItem = items[ind];
        items[ind] = null;
        current = ind;
        sizeN--;
        if (sizeN > 0 && sizeN <= items.length / 4) {
            resize(items.length / 2);
            current = sizeN;
        }
        return retItem;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (sizeN == 0) {
            throw new NoSuchElementException("Empty queue!");
        }
        int ind = StdRandom.uniform(0, items.length);
        while (items[ind] == null) {
            ind = StdRandom.uniform(0, items.length);
        }
        return items[ind];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {

        StdRandom.shuffle(items);
        current = 0;
        return new RandomizedQueueIterator();

    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private int cur = 0;

        // skip the null item in the array
        public boolean hasNext() {
            if (cur >= items.length) return false;
            if (items[cur] != null) return true;
            else {
                for (; cur < items.length; cur++) {
                    if (items[cur] != null) return true;
                }
                return false;
            }
        }

        public void remove() {
            throw new UnsupportedOperationException("Unsupported method:remove");
        }

        // return current item
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = items[cur++];
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
//        RandomizedQueue<Integer> testItems = new RandomizedQueue<Integer>();
//        int arraySize = Integer.parseInt(args[0]);
//        int[] arrayCpy = new int[arraySize];
//        // test iterator
//        System.out.println("--------------------test sequential enqueue--------------------");
//        for (int i = 1; i <= arraySize; i++) {
//
//            testItems.enqueue(i);
////            System.out.println("****check One:" + i + " Items: " + Arrays.toString(testItems.items));
//        }
//
//        testIterator(testItems, arraySize);
//
//        testIterator(testItems, arraySize);
//
//        System.out.println("--------------------test dequeue and enqueue--------------------");
//
//        System.out.println("Start: " + Arrays.toString(testItems.items));
//        for (int i = 0; i < arraySize; i++) {
//            System.out.println("dequeue test " + i + ": retItem: " + testItems.dequeue());
//            testItems.enqueue(i);
//            testItems.enqueue(i);
//            testIterator(testItems, arraySize);
//        }


    }

//    private static void testIterator(RandomizedQueue<Integer> testItems, int arraySize) {
//
//        int[] arrayCpy = new int[testItems.sizeN];
//        Iterator<Integer> it = testItems.iterator();
////        System.out.println("size:" + testItems.sizeN + " Items: " + Arrays.toString(testItems.items));
//        int i = 0;
//        while (it.hasNext()) {
//            if (i < testItems.sizeN) {
//                arrayCpy[i++] = it.next();
//            }
//        }
//        System.out.println("iterator: " + Arrays.toString(arrayCpy));
//    }


}
