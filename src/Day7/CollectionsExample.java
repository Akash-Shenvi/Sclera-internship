package Day7;

import java.util.*;

public class CollectionsExample {
    public static void main(String[] args) {

        Set<String> uniqueUsers = new HashSet<>();
        uniqueUsers.add("Akash");
        uniqueUsers.add("Rahul");
        uniqueUsers.add("Akash");

        System.out.println("Set (No duplicates): " + uniqueUsers);

        Map<String, Integer> scores = new HashMap<>();
        scores.put("Akash", 90);
        scores.put("Rahul", 85);
        scores.put("Anantha", 95);

        System.out.println("Map (Key-Value): " + scores);
        System.out.println("Akash Score: " + scores.get("Akash"));

        Queue<String> tasks = new LinkedList<>();
        tasks.offer("Task1");
        tasks.offer("Task2");
        tasks.offer("Task3");

        System.out.println("Queue before processing: " + tasks);
        System.out.println("Processing: " + tasks.poll());
        System.out.println("Queue after processing: " + tasks);
    }
}

