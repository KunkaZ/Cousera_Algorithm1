import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private Node first, last;
    private int sizeN;

    // And lastly, an inner class is absolutely unnecessary,
    // a static nested class would do the job, saving 8 bytes of memory per item.
    private class Node {
        Item item;
        Node pre;
        Node next;

        private Node() {
            pre = null;
            next = null;
        }

        private Node(Item it) {
            item = it;
            pre = null;
            next = null;
        }
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        sizeN = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return sizeN;
    }

    // add the item to the front
    public void addFirst(Item item) {

        if (item == null) {
            throw new IllegalArgumentException("Can't add first last with Null item!");
        }

        if (isEmpty()) {
            first = new Node(item);
            last = first;
        } else {
            Node oldfirst = first;
            first = new Node(item);
            oldfirst.pre = first;
            first.next = oldfirst;
        }
        sizeN++;
    }

    // add the item to the back
    public void addLast(Item item) {

        if (item == null) {
            throw new IllegalArgumentException("Can't add last with Null item!");
        }

        if (isEmpty()) {
            first = new Node(item);
            last = first;
        } else {
            Node oldlast = last;
            last = new Node(item);
            oldlast.next = last;
            last.pre = oldlast;
        }
        sizeN++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }

        Item retItem = first.item;
        first = first.next;
        if (first != null) {
            // if sizeN = 1, first will be null
            first.pre = null;
        }
        if (--sizeN == 0) last = null;
        return retItem;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("Can't remove last for empty deque");
        }

        Item retItem = last.item;
        last = last.pre;

        if (last != null) {
            last.next = null;
        }

        if (--sizeN == 0) first = null;
        return retItem;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {

        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException("Unsupported method:remove");
        }

        // return current item
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {

        Deque<Integer> deque = new Deque<>();
        deque.addFirst(1);
        deque.removeLast();
        deque.addFirst(3);
        deque.removeLast();

        // test removeFirst when sizeN = 1
        Integer ret;
        System.out.println("Test isEmpty:");
        Deque<Integer> testDeque = new Deque<Integer>();
        if (testDeque.isEmpty()) System.out.println("Empty deque");

        System.out.println("Test addFirst removeFirst");
        testDeque.addFirst(5);
        ret = testDeque.removeFirst();
        if (testDeque.isEmpty()) System.out.println("Empty deque  2" + ret.toString());

        System.out.println("Test addLast removeFirst");
        testDeque.addLast(5);
        ret = testDeque.removeFirst();
        if (testDeque.isEmpty()) System.out.println("Empty deque  3" + ret.toString());


        System.out.println("Test iterator:");
        for (int i = 0; i < 3; i++) {
            testDeque.addLast(i);
            testDeque.addFirst(100 + i);
            System.out.println("Print all elements:");
            System.out.println("size " + Integer.toString(testDeque.size()));
            Iterator<Integer> it = testDeque.iterator();


            while (it.hasNext()) {
                System.out.println(it.next());
            }
        }

//        System.out.println("Test addLast null removeFirst, throw expection");
//        testDeque.addLast(null);
//        testDeque.removeFirst();
//        if (testDeque.isEmpty()) System.out.println("Empty deque  4");
//
//        System.out.println("Test addLast null removeFirst");
//        testDeque.removeFirst();
//        testDeque.removeLast();
//        if (testDeque.isEmpty()) System.out.println("Empty deque  4");
    }

}
