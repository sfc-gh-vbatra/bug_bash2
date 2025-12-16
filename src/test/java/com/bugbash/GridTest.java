package com.bugbash;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

/**
 * Tests for Grid class.
 * These tests will reveal boundary and null pointer bugs.
 */
class GridTest {

    private Grid grid;

    @BeforeEach
    void setUp() {
        grid = new Grid(10, 10);
    }

    @Test
    @DisplayName("Grid should validate dimensions")
    void testGridCreation() {
        assertThrows(IllegalArgumentException.class, () -> new Grid(0, 10));
        assertThrows(IllegalArgumentException.class, () -> new Grid(10, 0));
        assertThrows(IllegalArgumentException.class, () -> new Grid(-5, 10));
    }

    @Test
    @DisplayName("Valid positions should be within grid boundaries")
    void testValidPosition() {
        assertTrue(grid.isValidPosition(new Position(0, 0)));
        assertTrue(grid.isValidPosition(new Position(5, 5)));
        assertTrue(grid.isValidPosition(new Position(9, 9)));
    }

    @Test
    @DisplayName("Positions at grid boundaries should be validated correctly")
    void testBoundaryPositions() {
        // BUG: Will fail - off-by-one error allows position(10,10) to be valid
        assertFalse(grid.isValidPosition(new Position(10, 10)));
        assertFalse(grid.isValidPosition(new Position(10, 0)));
        assertFalse(grid.isValidPosition(new Position(0, 10)));
    }

    @Test
    @DisplayName("Negative positions should be invalid")
    void testNegativePositions() {
        assertFalse(grid.isValidPosition(new Position(-1, 0)));
        assertFalse(grid.isValidPosition(new Position(0, -1)));
        assertFalse(grid.isValidPosition(new Position(-5, -5)));
    }

    @Test
    @DisplayName("Should add obstacle at valid position")
    void testAddObstacle() {
        Position pos = new Position(5, 5);
        assertTrue(grid.addObstacle(pos));
        assertTrue(grid.isObstacle(pos));
    }

    @Test
    @DisplayName("Should not add obstacle at invalid position")
    void testAddObstacleInvalidPosition() {
        Position pos = new Position(20, 20);
        assertFalse(grid.addObstacle(pos));
        assertFalse(grid.isObstacle(pos));
    }

    @Test
    @DisplayName("Should handle null position when adding obstacle")
    void testAddObstacleNull() {
        // BUG: Will throw NullPointerException instead of handling gracefully
        assertThrows(NullPointerException.class, () -> grid.addObstacle(null));
    }

    @Test
    @DisplayName("Should remove obstacle")
    void testRemoveObstacle() {
        Position pos = new Position(3, 3);
        grid.addObstacle(pos);
        assertTrue(grid.isObstacle(pos));

        assertTrue(grid.removeObstacle(pos));
        assertFalse(grid.isObstacle(pos));
    }

    @Test
    @DisplayName("Should occupy and vacate positions")
    void testOccupyPosition() {
        Position pos = new Position(2, 2);
        assertFalse(grid.isOccupied(pos));

        assertTrue(grid.occupy(pos));
        assertTrue(grid.isOccupied(pos));

        assertTrue(grid.vacate(pos));
        assertFalse(grid.isOccupied(pos));
    }

    @Test
    @DisplayName("Should not occupy position with obstacle")
    void testOccupyObstaclePosition() {
        Position pos = new Position(4, 4);
        grid.addObstacle(pos);

        assertFalse(grid.occupy(pos));
    }

    @Test
    @DisplayName("Walkable position should be valid, not obstacle, not occupied")
    void testWalkable() {
        Position pos = new Position(6, 6);
        assertTrue(grid.isWalkable(pos));

        grid.addObstacle(pos);
        assertFalse(grid.isWalkable(pos));

        grid.removeObstacle(pos);
        grid.occupy(pos);
        assertFalse(grid.isWalkable(pos));
    }

    @Test
    @DisplayName("Should get correct neighbors for middle position")
    void testGetNeighborsMiddle() {
        Position center = new Position(5, 5);
        Set<Position> neighbors = grid.getNeighbors(center);

        assertEquals(4, neighbors.size());
        assertTrue(neighbors.contains(new Position(5, 6))); // up
        assertTrue(neighbors.contains(new Position(5, 4))); // down
        assertTrue(neighbors.contains(new Position(6, 5))); // right
        assertTrue(neighbors.contains(new Position(4, 5))); // left
    }

    @Test
    @DisplayName("Should get correct neighbors for corner position")
    void testGetNeighborsCorner() {
        Position corner = new Position(0, 0);
        Set<Position> neighbors = grid.getNeighbors(corner);

        // BUG: May include invalid positions due to boundary error
        assertEquals(2, neighbors.size());
        assertTrue(neighbors.contains(new Position(0, 1)));
        assertTrue(neighbors.contains(new Position(1, 0)));
    }

    @Test
    @DisplayName("Should handle null position for neighbors")
    void testGetNeighborsNull() {
        Set<Position> neighbors = grid.getNeighbors(null);
        assertNotNull(neighbors);
        assertTrue(neighbors.isEmpty());
    }

    @Test
    @DisplayName("Should not add obstacle on occupied position")
    void testAddObstacleOnOccupiedPosition() {
        Position pos = new Position(7, 7);
        grid.occupy(pos);

        // BUG: Will succeed when it should fail - doesn't check occupation
        grid.addObstacle(pos);

        // This should still be occupied, not an obstacle
        assertTrue(grid.isOccupied(pos));
    }
}
