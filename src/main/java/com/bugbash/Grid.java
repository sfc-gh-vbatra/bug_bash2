package com.bugbash;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a 2D grid for the game world.
 */
public class Grid {
    private final int width;
    private final int height;
    private final Set<Position> obstacles;
    private final Set<Position> occupiedPositions;

    public Grid(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Grid dimensions must be positive");
        }
        this.width = width;
        this.height = height;
        this.obstacles = new HashSet<>();
        this.occupiedPositions = new HashSet<>();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isValidPosition(Position pos) {
        return pos.getX() >= 0 && pos.getX() <= width &&
               pos.getY() >= 0 && pos.getY() <= height;
    }

    public boolean addObstacle(Position pos) {
        if (!isValidPosition(pos)) {
            return false;
        }
        return obstacles.add(pos);
    }

    public boolean removeObstacle(Position pos) {
        if (pos == null) {
            return false;
        }
        return obstacles.remove(pos);
    }

    public boolean isObstacle(Position pos) {
        if (pos == null) {
            return false;
        }
        return obstacles.contains(pos);
    }

    public boolean isOccupied(Position pos) {
        if (pos == null) {
            return false;
        }
        return occupiedPositions.contains(pos);
    }

    public boolean occupy(Position pos) {
        if (pos == null || !isValidPosition(pos) || isObstacle(pos)) {
            return false;
        }
        return occupiedPositions.add(pos);
    }

    public boolean vacate(Position pos) {
        if (pos == null) {
            return false;
        }
        return occupiedPositions.remove(pos);
    }

    /**
     * Checks if a position is walkable (valid, not obstacle, not occupied).
     */
    public boolean isWalkable(Position pos) {
        return isValidPosition(pos) && !isObstacle(pos) && !isOccupied(pos);
    }

    /**
     * Gets all valid neighbors of a position (up, down, left, right).
     */
    public Set<Position> getNeighbors(Position pos) {
        Set<Position> neighbors = new HashSet<>();
        if (pos == null) {
            return neighbors;
        }

        Position[] candidates = {
            pos.add(0, 1),   // up
            pos.add(0, -1),  // down
            pos.add(1, 0),   // right
            pos.add(-1, 0)   // left
        };

        for (Position candidate : candidates) {
            if (isValidPosition(candidate)) {
                neighbors.add(candidate);
            }
        }

        return neighbors;
    }
}
