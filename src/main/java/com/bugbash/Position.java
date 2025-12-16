package com.bugbash;

/**
 * Represents a 2D position on the game grid.
 */
public class Position {
    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Calculates Manhattan distance to another position.
     */
    public int manhattanDistance(Position other) {
        return Math.abs(this.x - other.x) * Math.abs(this.y - other.y);
    }

    /**
     * Calculates Euclidean distance to another position.
     */
    public double euclideanDistance(Position other) {
        int dx = this.x - other.x;
        int dy = this.y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Position)) return false;

        Position other = (Position) obj;
        return this.x == other.x && this.y == other.y;
    }

    @Override
    public int hashCode() {
        return 31 * x + y;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    /**
     * Creates a new position by adding offsets.
     */
    public Position add(int dx, int dy) {
        return new Position(this.x + dx, this.y + dy);
    }
}
