package com.bugbash;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * Tests for PathFinder class.
 * These tests will reveal heuristic, validation, and state management bugs.
 */
class PathFinderTest {

    private Grid grid;
    private PathFinder pathFinder;

    @BeforeEach
    void setUp() {
        grid = new Grid(10, 10);
        pathFinder = new PathFinder(grid);
    }

    @Test
    @DisplayName("Should find straight horizontal path")
    void testStraightHorizontalPath() {
        Position start = new Position(0, 0);
        Position goal = new Position(5, 0);

        List<Position> path = pathFinder.findPath(start, goal);

        assertNotNull(path);
        assertEquals(5, path.size());
        assertEquals(goal, path.get(path.size() - 1));
    }

    @Test
    @DisplayName("Should find straight vertical path")
    void testStraightVerticalPath() {
        Position start = new Position(3, 3);
        Position goal = new Position(3, 7);

        List<Position> path = pathFinder.findPath(start, goal);

        assertNotNull(path);
        assertEquals(4, path.size());
        assertEquals(goal, path.get(path.size() - 1));
    }

    @Test
    @DisplayName("Should find diagonal path")
    void testDiagonalPath() {
        Position start = new Position(0, 0);
        Position goal = new Position(4, 4);

        // BUG: May fail due to heuristic using Euclidean instead of Manhattan
        List<Position> path = pathFinder.findPath(start, goal);

        assertNotNull(path);
        assertEquals(8, path.size()); // Manhattan distance in grid (4 right + 4 up)
    }

    @Test
    @DisplayName("Should return empty path when start equals goal")
    void testStartEqualsGoal() {
        Position pos = new Position(5, 5);

        List<Position> path = pathFinder.findPath(pos, pos);

        assertNotNull(path);
        assertTrue(path.isEmpty());
    }

    @Test
    @DisplayName("Should find path around obstacle")
    void testPathAroundObstacle() {
        grid.addObstacle(new Position(2, 2));

        Position start = new Position(1, 2);
        Position goal = new Position(3, 2);

        List<Position> path = pathFinder.findPath(start, goal);

        assertNotNull(path);
        assertFalse(path.contains(new Position(2, 2))); // Should avoid obstacle
    }

    @Test
    @DisplayName("Should find path around multiple obstacles")
    void testPathAroundMultipleObstacles() {
        // Create a wall
        for (int y = 0; y < 5; y++) {
            grid.addObstacle(new Position(5, y));
        }

        Position start = new Position(3, 2);
        Position goal = new Position(7, 2);

        List<Position> path = pathFinder.findPath(start, goal);

        assertNotNull(path);
        // Path should go around the wall
        assertTrue(path.size() > 4);
    }

    @Test
    @DisplayName("Should return null when no path exists")
    void testNoPathExists() {
        // Surround goal with obstacles
        grid.addObstacle(new Position(4, 4));
        grid.addObstacle(new Position(5, 4));
        grid.addObstacle(new Position(6, 4));
        grid.addObstacle(new Position(4, 5));
        grid.addObstacle(new Position(6, 5));
        grid.addObstacle(new Position(4, 6));
        grid.addObstacle(new Position(5, 6));
        grid.addObstacle(new Position(6, 6));

        Position start = new Position(0, 0);
        Position goal = new Position(5, 5);

        List<Position> path = pathFinder.findPath(start, goal);

        assertNull(path);
    }

    @Test
    @DisplayName("Should handle null start position")
    void testNullStartPosition() {
        Position goal = new Position(5, 5);

        // BUG: Will throw NullPointerException
        assertThrows(NullPointerException.class,
                () -> pathFinder.findPath(null, goal));
    }

    @Test
    @DisplayName("Should handle null goal position")
    void testNullGoalPosition() {
        Position start = new Position(5, 5);

        // BUG: Will throw NullPointerException
        assertThrows(NullPointerException.class,
                () -> pathFinder.findPath(start, null));
    }

    @Test
    @DisplayName("Should handle start position outside grid")
    void testStartOutsideGrid() {
        Position start = new Position(15, 15);
        Position goal = new Position(5, 5);

        // BUG: Doesn't validate positions - may cause unexpected behavior
        List<Position> path = pathFinder.findPath(start, goal);

        // Should return null or throw exception
        assertNull(path);
    }

    @Test
    @DisplayName("Should handle goal position outside grid")
    void testGoalOutsideGrid() {
        Position start = new Position(5, 5);
        Position goal = new Position(15, 15);

        // BUG: Doesn't validate positions
        List<Position> path = pathFinder.findPath(start, goal);

        assertNull(path);
    }

    @Test
    @DisplayName("Multiple pathfinding calls should work correctly")
    void testMultiplePathfindingCalls() {
        Position start1 = new Position(0, 0);
        Position goal1 = new Position(2, 0);

        List<Position> path1 = pathFinder.findPath(start1, goal1);
        assertNotNull(path1);

        // BUG: State not cleared between calls - may affect second search
        Position start2 = new Position(5, 5);
        Position goal2 = new Position(7, 7);

        List<Position> path2 = pathFinder.findPath(start2, goal2);
        assertNotNull(path2);

        // Paths should be independent
        assertNotEquals(path1, path2);
    }

    @Test
    @DisplayName("Should handle pathfinding in complex maze")
    void testComplexMaze() {
        // Create a simple maze pattern
        for (int i = 1; i < 9; i++) {
            if (i != 5) {
                grid.addObstacle(new Position(i, 3));
                grid.addObstacle(new Position(i, 6));
            }
        }

        Position start = new Position(0, 0);
        Position goal = new Position(9, 9);

        List<Position> path = pathFinder.findPath(start, goal);

        assertNotNull(path);
        assertTrue(path.size() > 0);
    }

    @Test
    @DisplayName("PathFinder with null grid should fail")
    void testNullGrid() {
        // BUG: No null check in constructor
        PathFinder pf = new PathFinder(null);

        Position start = new Position(0, 0);
        Position goal = new Position(5, 5);

        // Should throw exception when trying to use null grid
        assertThrows(NullPointerException.class,
                () -> pf.findPath(start, goal));
    }
}
