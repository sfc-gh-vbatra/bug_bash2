package com.bugbash;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * Integration tests for factory droid system.
 * Tests complete scenarios involving multiple components.
 */
class IntegrationTest {

    private Grid grid;
    private DroidFactory factory;
    private PathFinder pathFinder;

    @BeforeEach
    void setUp() {
        grid = new Grid(15, 15);
        factory = new DroidFactory(grid);
        pathFinder = new PathFinder(grid);
    }

    @Test
    @DisplayName("Complete scenario: Create droid, find path, execute movement")
    void testCompleteMovementScenario() {
        Position start = new Position(0, 0);
        Position goal = new Position(5, 5);

        // Create droid using factory
        FactoryDroid droid = factory.createWorkerDroid(start);
        assertNotNull(droid);
        grid.occupy(start);

        // Find path
        List<Position> path = pathFinder.findPath(start, goal);
        assertNotNull(path);

        // Execute movement
        boolean success = droid.followPath(path);
        assertTrue(success);
        assertEquals(goal, droid.getPosition());
        assertEquals(DroidState.IDLE, droid.getState());
    }

    @Test
    @DisplayName("Multiple droids should navigate independently")
    void testMultipleDroids() {
        FactoryDroid droid1 = factory.createWorkerDroid(new Position(0, 0));
        FactoryDroid droid2 = factory.createScoutDroid(new Position(10, 10));

        assertNotNull(droid1);
        assertNotNull(droid2);
        assertNotEquals(droid1.getId(), droid2.getId());

        grid.occupy(new Position(0, 0));
        grid.occupy(new Position(10, 10));

        Position goal1 = new Position(3, 3);
        Position goal2 = new Position(12, 12);

        List<Position> path1 = pathFinder.findPath(droid1.getPosition(), goal1);
        List<Position> path2 = pathFinder.findPath(droid2.getPosition(), goal2);

        assertTrue(droid1.followPath(path1));
        assertTrue(droid2.followPath(path2));

        assertEquals(goal1, droid1.getPosition());
        assertEquals(goal2, droid2.getPosition());
    }

    @Test
    @DisplayName("Droid should navigate around obstacles")
    void testNavigateAroundObstacles() {
        // Create obstacle wall
        for (int y = 2; y < 8; y++) {
            grid.addObstacle(new Position(5, y));
        }

        Position start = new Position(3, 5);
        Position goal = new Position(7, 5);

        FactoryDroid droid = factory.createWorkerDroid(start);
        grid.occupy(start);

        List<Position> path = pathFinder.findPath(start, goal);
        assertNotNull(path);

        boolean success = droid.followPath(path);
        assertTrue(success);
        assertEquals(goal, droid.getPosition());
    }

    @Test
    @DisplayName("Factory should create different types of droids")
    void testFactoryDroidTypes() {
        FactoryDroid worker = factory.createWorkerDroid(new Position(0, 0));
        FactoryDroid scout = factory.createScoutDroid(new Position(1, 1));
        FactoryDroid builder = factory.createBuilderDroid(new Position(2, 2));

        assertNotNull(worker);
        assertNotNull(scout);
        assertNotNull(builder);

        assertTrue(worker.getId().startsWith("WORKER"));
        assertTrue(scout.getId().startsWith("SCOUT"));
        assertTrue(builder.getId().startsWith("BUILDER"));

        assertEquals(3, factory.getDroidCount());
    }

    @Test
    @DisplayName("Factory should handle unknown droid type")
    void testFactoryUnknownType() {
        // BUG: Returns null instead of throwing exception
        FactoryDroid droid = factory.createDroid("UNKNOWN", new Position(0, 0));

        assertNull(droid);
    }

    @Test
    @DisplayName("Factory should handle null droid type")
    void testFactoryNullType() {
        // BUG: Returns null instead of throwing exception
        FactoryDroid droid = factory.createDroid(null, new Position(0, 0));

        assertNull(droid);
    }

    @Test
    @DisplayName("Droid should handle blocked path and recover")
    void testBlockedPathRecovery() {
        Position start = new Position(5, 5);
        FactoryDroid droid = factory.createWorkerDroid(start);
        grid.occupy(start);

        // Add obstacle blocking direct path
        grid.addObstacle(new Position(5, 6));

        Position blockedGoal = new Position(5, 6);
        boolean result = droid.moveTo(blockedGoal);

        assertFalse(result);
        assertEquals(DroidState.BLOCKED, droid.getState());

        // Reset and try different path
        droid.reset();
        // Note: reset only works for ERROR state, not BLOCKED
        // This demonstrates state management complexity
    }

    @Test
    @DisplayName("Complex pathfinding with multiple obstacles")
    void testComplexPathfinding() {
        // Create maze-like structure
        for (int i = 3; i < 12; i++) {
            if (i != 7) {
                grid.addObstacle(new Position(i, 5));
            }
        }
        for (int i = 3; i < 10; i++) {
            if (i != 6) {
                grid.addObstacle(new Position(i, 9));
            }
        }

        Position start = new Position(0, 0);
        Position goal = new Position(14, 14);

        FactoryDroid droid = factory.createWorkerDroid(start);
        grid.occupy(start);

        List<Position> path = pathFinder.findPath(start, goal);
        assertNotNull(path);

        boolean success = droid.followPath(path);
        assertTrue(success);
        assertEquals(goal, droid.getPosition());
    }

    @Test
    @DisplayName("Droids should not collide")
    void testDroidCollisionAvoidance() {
        Position pos1 = new Position(5, 5);
        Position pos2 = new Position(5, 6);

        FactoryDroid droid1 = factory.createWorkerDroid(pos1);
        FactoryDroid droid2 = factory.createScoutDroid(pos2);

        grid.occupy(pos1);
        grid.occupy(pos2);

        // Droid2 tries to move to droid1's position
        boolean result = droid2.moveTo(pos1);

        // Should fail because position is occupied
        assertFalse(result);
        assertEquals(DroidState.BLOCKED, droid2.getState());
    }

    @Test
    @DisplayName("Pathfinder should handle no path scenario gracefully")
    void testNoPathScenario() {
        // Completely surround an area
        for (int x = 7; x <= 9; x++) {
            grid.addObstacle(new Position(x, 7));
            grid.addObstacle(new Position(x, 9));
        }
        grid.addObstacle(new Position(7, 8));
        grid.addObstacle(new Position(9, 8));

        Position start = new Position(0, 0);
        Position unreachableGoal = new Position(8, 8);

        FactoryDroid droid = factory.createWorkerDroid(start);
        grid.occupy(start);

        List<Position> path = pathFinder.findPath(start, unreachableGoal);

        assertNull(path);
    }
}
