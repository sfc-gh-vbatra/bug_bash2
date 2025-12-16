package com.bugbash;

import java.util.List;

/**
 * Represents a factory droid with movement capabilities.
 */
public class FactoryDroid {
    private final String id;
    private Position position;
    private Grid grid;
    private DroidState state;
    private List<Position> currentPath;

    public FactoryDroid(String id, Position startPosition, Grid grid) {
        this.id = id;
        this.position = startPosition;
        this.grid = grid;
        this.state = DroidState.IDLE;
        this.currentPath = null;
    }

    public String getId() {
        return id;
    }

    public Position getPosition() {
        return position;
    }

    public DroidState getState() {
        return state;
    }

    public Grid getGrid() {
        return grid;
    }

    public void setState(DroidState newState) {
        this.state = newState;
    }

    public boolean moveTo(Position targetPosition) {
        if (state == DroidState.ERROR) {
            return false;
        }

        // Vacate current position
        grid.vacate(position);

        position = targetPosition;
        setState(DroidState.MOVING);

        // Try to occupy new position
        boolean occupied = grid.occupy(position);
        if (occupied) {
            setState(DroidState.IDLE);
            return true;
        } else {
            setState(DroidState.BLOCKED);
            return false;
        }
    }

    /**
     * Move to an adjacent position (one step).
     */
    public boolean moveOneStep(Position nextPosition) {
        if (nextPosition == null) {
            setState(DroidState.ERROR);
            return false;
        }

        // Check if positions are adjacent (Manhattan distance of 1)
        if (position.manhattanDistance(nextPosition) != 1) {
            setState(DroidState.ERROR);
            return false;
        }

        return moveTo(nextPosition);
    }

    public boolean followPath(List<Position> path) {
        if (path == null) {
            setState(DroidState.ERROR);
            return false;
        }

        this.currentPath = path;
        setState(DroidState.MOVING);

        for (Position nextPos : path) {
            if (!moveOneStep(nextPos)) {
                setState(DroidState.BLOCKED);
                return false;
            }
        }

        setState(DroidState.IDLE);
        this.currentPath = null;
        return true;
    }

    /**
     * Resets droid from ERROR state.
     */
    public void reset() {
        if (state == DroidState.ERROR) {
            setState(DroidState.IDLE);
            currentPath = null;
        }
    }

    @Override
    public String toString() {
        return "FactoryDroid{id='" + id + "', position=" + position +
               ", state=" + state + "}";
    }
}
