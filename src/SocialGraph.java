
import java.io.*;
import java.util.*;

public class SocialGraph {

    private Map<String, List<String>> adjacencyList = new HashMap<>();

    // Load graph from file
    public void loadFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] users = line.split(",\\s*");
                if (users.length == 2) {
                    String user1 = users[0];
                    String user2 = users[1];
                    if (!user1.equals(user2)) { // Check for self-loop
                        adjacencyList.putIfAbsent(user1, new ArrayList<>());
                        adjacencyList.putIfAbsent(user2, new ArrayList<>());
                        if (!adjacencyList.get(user1).contains(user2)) { // Check for duplicate
                            adjacencyList.get(user1).add(user2);
                            adjacencyList.get(user2).add(user1); // Add both directions
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    // Print the adjacency list (for testing purposes)
    public void printGraph() {
        for (String user : adjacencyList.keySet()) {
            System.out.println(user + " -> " + adjacencyList.get(user));
        }
    }
    
    public Map<Integer, List<String>> bfsLevels(String source) {
        Map<Integer, List<String>> levels = new HashMap<>();
        Queue<String> queue = new LinkedList<>();
        Map<String, Boolean> visited = new HashMap<>();

        // Initialize BFS
        queue.add(source);
        visited.put(source, true);
        levels.put(0, Arrays.asList(source));

        int level = 1;
        while (!queue.isEmpty()) {
            List<String> currentLevel = new ArrayList<>();
            int size = queue.size(); // Nodes in the current level

            for (int i = 0; i < size; i++) {
                String current = queue.poll(); // Current user being processed

                // Explore neighbors
                for (String neighbor : adjacencyList.get(current)) {
                    if (!visited.containsKey(neighbor)) { // Check if already visited
                        visited.put(neighbor, true);
                        currentLevel.add(neighbor);
                        queue.add(neighbor); // Add neighbor to the queue
                    }
                }
            }

            // Add the current level to the result map
            if (!currentLevel.isEmpty()) {
                levels.put(level, currentLevel);
                level++;
            }
        }

        return levels; // Return distance levels
    }
}
