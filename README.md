# PathForge

PathForge is an interactive Java application for visualizing pathfinding and graph traversal algorithms on a customizable grid.

## Features

- Interactive grid-based visualization
- Breadth-First Search (BFS)
- Depth-First Search (DFS)
- Dijkstra's shortest-path algorithm
- Step-by-step algorithm visualization
- Custom obstacle placement
- Movable start and target nodes
- Random obstacle generation
- Reset and clear grid controls
- Path and visited-node visualization
- Search statistics

## Technologies

- Java
- Java Swing
- Java AWT
- Object-Oriented Programming
- Queue
- Deque
- PriorityQueue
- HashSet

## Algorithms

### BFS

Breadth-First Search explores nodes level by level using a queue.

For an unweighted grid, BFS finds the shortest path.

**Time Complexity:** `O(V + E)`

**Space Complexity:** `O(V)`

### DFS

Depth-First Search explores one branch as deeply as possible before backtracking.

DFS does not guarantee the shortest path.

**Time Complexity:** `O(V + E)`

**Space Complexity:** `O(V)`

### Dijkstra's Algorithm

Dijkstra's algorithm finds the shortest path by repeatedly processing the node with the smallest known distance.

**Time Complexity:** `O((V + E) log V)`

**Space Complexity:** `O(V)`

## Running the Project

Make sure Java 17 or later is installed.

Compile:

```bash
javac -d out *.java
