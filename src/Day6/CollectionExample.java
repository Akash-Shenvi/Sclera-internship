package Day6;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CollectionExample {
    public static void main(String[] args) {

        List<String> names = new ArrayList<>();
        names.add("Akash");
        names.add("Vighnesh");
        names.add("Anantha");

        Iterator<String> iterator = names.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}

