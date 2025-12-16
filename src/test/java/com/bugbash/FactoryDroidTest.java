package com.bugbash;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;

/**
 * Tests for FactoryDroid class.
 * These tests will reveal state management, boundary, and null pointer bugs.
 */
class FactoryDroidTest {

    private Grid grid;
    private FactoryDroid droid;

    @BeforeEach
    void setUp() {
        grid = new Grid(10, 10);
        Position startPos = new Position(5, 5);
        grid.occupy(startPos);
        droid = new FactoryDroid("TEST-1", startPos, grid);
    }

    @Test
    @DisplayName("Droid should initialize with correct state")
    void testInitialState() {
        assertEquals("TEST-1", droid.getId());
        assertEquals(new Position(5, 5), droid.getPosition());
        assertEquals(DroidState.IDLE, droid.getState());
    }

    @Test
    @DisplayName("Droid should move to valid adjacent position")
    void testMoveToAdjacentPosition() {
        Position target = new Position(5, 6);

        assertTrue(droid.moveOneStep(target));
        assertEquals(target, droid.getPosition());
    }

    @Test
    @DisplayName("Droid should not move to non-adjacent position in one step")
    void testMoveToNonAdjacentPosition() {
        Position target = new Position(7, 7);

        // BUG: May succeed due to lack of validation
        assertFalse(droid.moveOneStep(target));
        assertEquals(DroidState.ERROR, droid.getState());
    }

    @Test
    @DisplayName("Droid should handle null target position")
    void testMoveToNullPosition() {
        // BUG: Will throw NullPointerException in moveTo()
        assertThrows(NullPointerException.class, () -> droid.moveTo(null));
    }

    @Test
    @DisplayName("Droid should not move outside grid boundaries")
    void testMoveOutsideGrid() {
        // Move to edge
        droid = new FactoryDroid("EDGE-1", new Position(9, 9), grid);
        grid.occupy(new Position(9, 9));

        Position outside = new Position(10, 10);

        // BUG: May allow movement outside grid
        droid.moveTo(outside);
        assertNotEquals(outside, droid.getPosition());
    }

    @Test
    @DisplayName("State transitions should be validated")
    void testInvalidStateTransition() {
        droid.setState(DroidState.ERROR);

        // BUG: Allows invalid state transitions
        droid.setState(DroidState.MOVING);

        // Should not allow ERROR -> MOVING transition directly
        assertEquals(DroidState.MOVING, droid.getState());
    }

    @Test
    @DisplayName("Droid should follow a valid path")
    void testFollowPath() {
        Position p1 = new Position(5, 6);
        Position p2 = new Position(5, 7);
        Position p3 = new Position(6, 7);

        assertTrue(droid.followPath(Arrays.asList(p1, p2, p3)));
        assertEquals(new Position(6, 7), droid.getPosition());
        assertEquals(DroidState.IDLE, droid.getState());
    }

    @Test
    @DisplayName("Droid should handle empty path")
    void testFollowEmptyPath() {
        Position originalPos = droid.getPosition();

        // BUG: May not handle empty path correctly
        droid.followPath(Collections.emptyList());

        assertEquals(originalPos, droid.getPosition());
    }

    @Test
    @DisplayName("Droid should handle null path")
    void testFollowNullPath() {
        droid.followPath(null);
        assertEquals(DroidState.ERROR, droid.getState());
    }

    @Test
    @DisplayName("Droid should stop if path is blocked")
    void testFollowBlockedPath() {
        // Add obstacle in path
        grid.addObstacle(new Position(5, 6));

        Position p1 = new Position(5, 6);
        Position p2 = new Position(5, 7);

        assertFalse(droid.followPath(Arrays.asList(p1, p2)));
        assertEquals(DroidState.BLOCKED, droid.getState());
    }

    @Test
    @DisplayName("Droid should not move when in ERROR state")
    void testMoveInErrorState() {
        droid.setState(DroidState.ERROR);

        Position target = new Position(5, 6);
        assertFalse(droid.moveTo(target));
    }

    @Test
    @DisplayName("Reset should clear ERROR state")
    void testReset() {
        droid.setState(DroidState.ERROR);
        assertEquals(DroidState.ERROR, droid.getState());

        droid.reset();
        assertEquals(DroidState.IDLE, droid.getState());
    }

    @Test
    @DisplayName("Reset should not affect non-ERROR states")
    void testResetNonErrorState() {
        droid.setState(DroidState.MOVING);
        droid.reset();

        assertEquals(DroidState.MOVING, droid.getState());
    }

    @Test
    @DisplayName("ToString should return formatted string")
    void testToString() {
        String str = droid.toString();
        assertTrue(str.contains("TEST-1"));
        assertTrue(str.contains("IDLE"));
    }
}
