import java.util.*;

public class main {

    public static void main(String[] args) {
        SocialGraph graph = new SocialGraph();
        Scanner scanner = new Scanner(System.in);

        // Load the graph
        graph.loadFromFile("src/social network.txt");
        System.out.println("Social graph loaded!");

        // Sample run (assignment expects this style)
        System.out.print("Enter user name: ");
        String user = scanner.nextLine();

        System.out.print("Enter distance: ");
        int k = scanner.nextInt();
        scanner.nextLine(); // consume leftover newline

        // Get and display friends at distance k
        List<String> levelK = graph.getFriendsAtDistance(user, k);
        System.out.println("Friends at distance " + k + ":");
        for (String friend : levelK) {
            System.out.println("- " + friend);
        }

        // Show connectivity status
        System.out.println("Graph is connected? " + graph.isGraphConnected());
    }
}
