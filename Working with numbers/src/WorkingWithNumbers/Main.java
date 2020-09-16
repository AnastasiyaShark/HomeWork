package WorkingWithNumbers;

import java.util.*;
public class Main {

    public static void main(String[] args) {
        List<Integer> collection = new ArrayList<>(Arrays.asList(1, 2, 5, 16, -1, -2, 0, 32, 3, 5, 8, 23, 4));
        Iterator<Integer> iterator = collection.iterator();
        while (iterator.hasNext()) {
            int nextInt = iterator.next();
            if (nextInt <= 0) {
                iterator.remove();
            }
        }
        iterator = collection.iterator();
        while (iterator.hasNext()) {
            int nextInt2 = iterator.next();
            if (nextInt2 % 2 != 0) {
                iterator.remove();
            }
        }
        Collections.sort(collection);
        System.out.println(collection);
        StreamMain streamMain = new StreamMain();
        streamMain.main();
    }

}



