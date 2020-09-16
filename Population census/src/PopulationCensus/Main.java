package PopulationCensus;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }

        System.out.println("Вычисление в один поток");
        calculate(persons);

        System.out.println("Вычисление в несколько потоков");
        calculateParallel(persons);

    }

    private static void calculate(Collection<Person> persons) {
        Stream<Person> stream1 = persons.stream();
        long startTime = System.nanoTime();
        long count = stream1
                .filter(x -> x.getAge() < 18)
                .count();
        long stopTime = System.nanoTime();
        double processTime = (double) (stopTime - startTime) / 1_000_000_000.0;
        System.out.printf("%nКоличество несовершеннолетних в списке = %d%n", count);
        System.out.println("Process time 1: " + processTime + " s");//0.0600824 s

        long startTime2 = System.nanoTime();
        Stream<Person> stream2 = persons.stream();
        stream2.filter(x -> x.getSex().equals(Sex.MAN))
                .filter(x -> x.getAge() >= 18 && x.getAge() <= 27)
                .map(Person::getFamily)
                .collect(Collectors.toList());
        long stopTime2 = System.nanoTime();
        double processTime2 = (double) (stopTime2 - startTime2) / 1_000_000_000.0;
        System.out.println("Process time 2: " + processTime2 + " s");//0.1805579

        long startTime3 = System.nanoTime();
        Stream<Person> stream3 = persons.stream();

        // x -> x.getAge() >= 18 && x -> x.getAge() <= (x.getSex().equals(Sex.MAN)? 65: 60 )   (x.getSex().equals(Sex.MAN) && x.getAge() <= 65) ||
        //                        (x.getSex().equals(Sex.WOMEN) && x.getAge() <= 60)

        stream3.filter(x -> x.getEducation().equals(Education.HIGHER))
                .filter(x -> x.getAge() >= 18 && x.getAge() <= (x.getSex().equals(Sex.MAN)? 65: 60 ) )
                .sorted(Comparator.comparing(Person::getFamily))
                .collect(Collectors.toList());
        long stopTime3 = System.nanoTime();
        double processTime3 = (double) (stopTime3 - startTime3) / 1_000_000_000.0;
        System.out.println("Process time 3: " + processTime3 + " s");//0.6192456
    }

    private static void calculateParallel(Collection<Person> persons) {
        Stream<Person> stream1 = persons.parallelStream();
        long startTime = System.nanoTime();
        long count = stream1
                .filter(x -> x.getAge() < 18)
                .count();
        long stopTime = System.nanoTime();
        double processTime = (double) (stopTime - startTime) / 1_000_000_000.0;
        System.out.printf("%nКоличество несовершеннолетних в списке = %d%n", count);
        System.out.println("Process time 1: " + processTime + " s");//0.062206 s

        long startTime2 = System.nanoTime();
        Stream<Person> stream2 = persons.parallelStream();
        stream2.filter(x -> x.getSex().equals(Sex.MAN))
                .filter(x -> x.getAge() >= 18 && x.getAge() <= 27)
                .map(Person::getFamily)
                .collect(Collectors.toList());
        long stopTime2 = System.nanoTime();
        double processTime2 = (double) (stopTime2 - startTime2) / 1_000_000_000.0;
        System.out.println("Process time 2: " + processTime2 + " s");//0.0597141 s

        long startTime3 = System.nanoTime();
        Stream<Person> stream3 = persons.parallelStream();
        stream3.filter(x -> x.getEducation().equals(Education.HIGHER))
                .filter(x -> x.getAge() >= 18 && x.getAge() <= (x.getSex().equals(Sex.MAN)? 65: 60 ) )
                .sorted(Comparator.comparing(Person::getFamily))
                .collect(Collectors.toList());
        long stopTime3 = System.nanoTime();
        double processTime3 = (double) (stopTime3 - startTime3) / 1_000_000_000.0;
        System.out.println("Process time 3: " + processTime3 + " s");//0.4415157

    }
}

