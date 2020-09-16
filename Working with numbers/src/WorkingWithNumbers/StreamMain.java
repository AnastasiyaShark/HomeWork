package WorkingWithNumbers;


import java.util.Arrays;
import java.util.List;
import java.util.stream.*;

public class StreamMain {

    public void main() {
        List<Integer> intList = Arrays.asList(1, 2, 5, 16, -1, -2, 0, 32, 3, 5, 8, 23, 4);
        Stream<Integer> stream = intList.stream();
        stream.filter(x -> x > 0)
                .filter(x -> x % 2 == 0)
                .sorted()
                .forEach(System.out::println);
    }


}
