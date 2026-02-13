package Day8;

import java.util.List;
import java.util.function.Predicate;

public class Stream {
    public static void main(String[] args) {

        List<String> names = List.of("Akash", "Ananth", "Joylan", "Niranjan");

        Predicate<String> startsWithA = name -> name.startsWith("A");

        names.stream()
                .filter(startsWithA)
                .map(String::toUpperCase)
                .forEach(System.out::println);
    }
}
