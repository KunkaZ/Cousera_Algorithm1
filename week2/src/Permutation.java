import edu.princeton.cs.algs4.StdIn;

import java.util.Iterator;


public class Permutation {

    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> randQueue = new RandomizedQueue<String>();
//        int i = 0;
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
//            if (StdRandom.uniform(1, k + 1) > k / 2 && (i++ < k)) {
            randQueue.enqueue(s);
//            }
//            System.out.println("Start: " + Arrays.toString(randQueue.items));
        }
        Iterator<String> randQueItr = randQueue.iterator();

        while (randQueItr.hasNext() && k > 0) {
            System.out.println(randQueItr.next());
            k--;
        }
    }
}

