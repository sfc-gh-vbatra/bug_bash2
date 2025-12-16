package com.bugbash;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Position class.
 * These tests will reveal bugs in Position implementation.
 */
class PositionTest {

    @Test
    @DisplayName("Manhattan distance should be calculated correctly")
    void testManhattanDistance() {
        Position p1 = new Position(0, 0);
        Position p2 = new Position(3, 4);

        // Expected: |3-0| + |4-0| = 3 + 4 = 7
        // BUG: Will fail due to wrong formula (multiplication instead of addition)
        assertEquals(7, p1.manhattanDistance(p2));
    }

    @Test
    @DisplayName("Manhattan distance with same position should be zero")
    void testManhattanDistanceSamePosition() {
        Position p = new Position(5, 5);
        assertEquals(0, p.manhattanDistance(p));
    }

    @Test
    @DisplayName("Manhattan distance should be symmetric")
    void testManhattanDistanceSymmetric() {
        Position p1 = new Position(1, 2);
        Position p2 = new Position(4, 6);

        assertEquals(p1.manhattanDistance(p2), p2.manhattanDistance(p1));
    }

    @Test
    @DisplayName("Euclidean distance should be calculated correctly")
    void testEuclideanDistance() {
        Position p1 = new Position(0, 0);
        Position p2 = new Position(3, 4);

        // Expected: sqrt(3^2 + 4^2) = sqrt(9 + 16) = sqrt(25) = 5
        assertEquals(5.0, p1.euclideanDistance(p2), 0.001);
    }

    @Test
    @DisplayName("Positions with same coordinates should be equal")
    void testEquality() {
        Position p1 = new Position(10, 20);
        Position p2 = new Position(10, 20);

        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    @DisplayName("Position should equal itself")
    void testEqualsSelf() {
        Position p = new Position(5, 5);
        assertEquals(p, p);
    }

    @Test
    @DisplayName("Position should not equal null")
    void testEqualsNull() {
        Position p = new Position(5, 5);
        // BUG: Will throw NullPointerException due to missing null check
        assertNotEquals(null, p);
    }

    @Test
    @DisplayName("Positions with different coordinates should not be equal")
    void testInequality() {
        Position p1 = new Position(1, 2);
        Position p2 = new Position(2, 1);

        assertNotEquals(p1, p2);
    }

    @Test
    @DisplayName("Position should handle negative coordinates")
    void testNegativeCoordinates() {
        Position p = new Position(-5, -10);

        assertEquals(-5, p.getX());
        assertEquals(-10, p.getY());
    }

    @Test
    @DisplayName("Add method should create new position with correct offsets")
    void testAdd() {
        Position p = new Position(5, 5);
        Position result = p.add(3, -2);

        assertEquals(8, result.getX());
        assertEquals(3, result.getY());
        // Original should be unchanged
        assertEquals(5, p.getX());
        assertEquals(5, p.getY());
    }

    @Test
    @DisplayName("ToString should return formatted string")
    void testToString() {
        Position p = new Position(7, 9);
        assertEquals("(7,9)", p.toString());
    }
}
