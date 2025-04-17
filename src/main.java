import java.util.*;
import java.io.*;

public class main {
    public static void main(String[] args) {
        SocialGraph graph = new SocialGraph();
        Scanner scanner = new Scanner(System.in);

        // Load graph
        graph.loadFromFile("src/social network.txt");
        System.out.println("Social graph loaded!");

        while (true) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. View friends at distance k");
            System.out.println("2. Find shortest path between two users");
            System.out.println("3. Check if graph is connected");
            System.out.println("4. Count friend groups");
            System.out.println("5. Exit");
            System.out.print("Choose an option (1-5): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // clear newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter user name: ");
                    String user = scanner.nextLine();
                    System.out.print("Enter distance k: ");
                    int k = scanner.nextInt();
                    scanner.nextLine(); // clear newline
                    List<String> friends = graph.getFriendsAtDistance(user, k);
                    System.out.println("Friends at distance " + k + ": " + friends);
                }
                case 2 -> {
                    System.out.print("Enter source user: ");
                    String src = scanner.nextLine();
                    System.out.print("Enter target user: ");
                    String tgt = scanner.nextLine();
                    List<String> path = graph.shortestPath(src, tgt);
                    System.out.println("Shortest path: " + path);
                }
                case 3 -> {
                    boolean connected = graph.isGraphConnected();
                    System.out.println("Graph is connected? " + connected);
                }
                case 4 -> {
                    int groups = graph.countFriendGroups();
                    System.out.println("Number of friend groups: " + groups);
                }
                case 5 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default ->
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
