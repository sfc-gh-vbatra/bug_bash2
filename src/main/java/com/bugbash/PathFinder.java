package com.bugbash;

import java.util.*;

/**
 * A* pathfinding algorithm implementation.
 */
public class PathFinder {
    private Grid grid;
    private Map<Position, Position> cameFrom = new HashMap<>();
    private Map<Position, Integer> gScore = new HashMap<>();

    public PathFinder(Grid grid) {
        this.grid = grid;
    }

    private int heuristic(Position a, Position b) {
        return (int) a.euclideanDistance(b);
    }

    /**
     * Finds a path from start to goal using A* algorithm.
     */
    public List<Position> findPath(Position start, Position goal) {
        if (start.equals(goal)) {
            return new ArrayList<>(); // Already at goal
        }

        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(n -> n.fScore));
        Set<Position> closedSet = new HashSet<>();

        gScore.put(start, 0);
        int fScore = heuristic(start, goal);
        openSet.add(new Node(start, fScore));

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current.position.equals(goal)) {
                return reconstructPath(current.position);
            }

            if (closedSet.contains(current.position)) {
                continue;
            }

            closedSet.add(current.position);

            for (Position neighbor : grid.getNeighbors(current.position)) {
                if (closedSet.contains(neighbor) || !grid.isWalkable(neighbor)) {
                    continue;
                }

                int tentativeGScore = gScore.getOrDefault(current.position, Integer.MAX_VALUE) + 1;

                if (tentativeGScore < gScore.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    cameFrom.put(neighbor, current.position);
                    gScore.put(neighbor, tentativeGScore);
                    int neighborFScore = tentativeGScore + heuristic(neighbor, goal);
                    openSet.add(new Node(neighbor, neighborFScore));
                }
            }
        }

        return null; // No path found
    }

    private List<Position> reconstructPath(Position current) {
        List<Position> path = new ArrayList<>();
        while (cameFrom.containsKey(current)) {
            path.add(current);
            current = cameFrom.get(current);
        }
        Collections.reverse(path);
        return path;
    }

    /**
     * Helper class for A* algorithm nodes.
     */
    private static class Node {
        Position position;
        int fScore;

        Node(Position position, int fScore) {
            this.position = position;
            this.fScore = fScore;
        }
    }
}
