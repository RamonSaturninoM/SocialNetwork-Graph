
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
        if (!adjacencyList.containsKey(source)) {
            System.err.println("Error: User '" + source + "' not found in the social network.");
            return Collections.emptyMap();
        }
        
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
    
    public List<String> getFriendsAtDistance(String source, int k) {
        if (k < 0) {
            System.err.println("Error: Distance k must be non-negative.");
            return Collections.emptyList();
        }

        Map<Integer, List<String>> levels = bfsLevels(source);
        return levels.getOrDefault(k, new ArrayList<>());
    }
    
    public List<String> shortestPath(String source, String target) {
        if (!adjacencyList.containsKey(source) || !adjacencyList.containsKey(target)) {
            System.err.println("Error: One or both users not found in the network.");
            return Collections.emptyList();
        }

        Map<String, String> parent = new HashMap<>();
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        queue.add(source);
        visited.add(source);
        parent.put(source, null);

        // BFS loop
        while (!queue.isEmpty()) {
            String current = queue.poll();

            if (current.equals(target)) {
                break;
            }

            for (String neighbor : adjacencyList.get(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parent.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }

        // Reconstruct path
        if (!parent.containsKey(target)) {
            System.out.println("No path found between " + source + " and " + target);
            return Collections.emptyList();
        }

        List<String> path = new LinkedList<>();
        for (String at = target; at != null; at = parent.get(at)) {
            path.add(0, at); // prepend to path
        }

        return path;
    }

    public boolean isGraphConnected() {
        if (adjacencyList.isEmpty()) {
            return true;
        }

        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        // Start BFS from any user
        String startUser = adjacencyList.keySet().iterator().next();
        queue.add(startUser);
        visited.add(startUser);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            for (String neighbor : adjacencyList.get(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        // Check if all users were visited
        return visited.size() == adjacencyList.size();
    }
    
    public int countFriendGroups() {
        Set<String> visited = new HashSet<>();
        int groupCount = 0;

        for (String user : adjacencyList.keySet()) {
            if (!visited.contains(user)) {
                bfsMarkVisited(user, visited);
                groupCount++;
            }
        }

        return groupCount;
    }

    // Helper method to mark all users in a group as visited
    private void bfsMarkVisited(String startUser, Set<String> visited) {
        Queue<String> queue = new LinkedList<>();
        queue.add(startUser);
        visited.add(startUser);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            for (String neighbor : adjacencyList.get(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
    }




}
