package DataAnalysis;


import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        List<Person> persons = Arrays.asList(
                new Person("Jack", "Evans", 16, Sex.MAN, Education.SECONDARY),
                new Person("Connor", "Young", 23, Sex.MAN, Education.FURTHER),
                new Person("Emily", "Harris", 24, Sex.WOMEN, Education.HIGHER),
                new Person("Harry", "Wilson", 18, Sex.MAN, Education.HIGHER),
                new Person("George", "Davies", 35, Sex.MAN, Education.FURTHER),
                new Person("Samuel", "Adamson", 40, Sex.MAN, Education.HIGHER),
                new Person("John", "Brown", 44, Sex.MAN, Education.HIGHER)
        );

        Stream<Person> stream1 = persons.stream();
        long count = stream1
                .filter(x -> x.getAge() < 18)
                .count();
        System.out.printf("Количество несовершеннолетних в списке = %d%n%n", count);


        Stream<Person> stream2 = persons.stream();
        List<String> surnames = stream2
                .filter(x -> x.getSex().equals(Sex.MAN))
                .filter(x -> x.getAge() >= 18 && x.getAge() <= 27)
                .map(Person::getFamily)
                .collect(Collectors.toList());
        System.out.println("Список фамилий призывников ( мужчины от 18 и до 27 лет): ");
        for (String surname : surnames) {
            System.out.printf("- %s%n", surname);
        }


        Stream<Person> stream3 = persons.stream();
        List<Person> people = stream3.filter(x -> x.getEducation().equals(Education.HIGHER))
                .filter(x -> x.getAge() >= 18 && (x.getSex().equals(Sex.MAN) && x.getAge() <= 65) ||
                        (x.getSex().equals(Sex.WOMEN) && x.getAge() <= 60))
                .sorted(Comparator.comparing(Person::getFamily))
                .collect(Collectors.toList());
        System.out.println("Список потенциально работоспособных людей с высшим образованием: ");
        for (Person person:people) {
            System.out.println(person);
        }
    }
}








