package com.bugbash;

/**
 * Factory class for creating factory droids.
 */
public class DroidFactory {
    private Grid grid;
    private int droidCounter = 0;

    public DroidFactory(Grid grid) {
        this.grid = grid;
    }

    /**
     * Creates a droid of the specified type.
     */
    public FactoryDroid createDroid(String type, Position startPosition) {
        String droidId = type + "-" + (++droidCounter);

        if (type == null) {
            return null;
        }

        FactoryDroid droid;
        switch (type.toUpperCase()) {
            case "WORKER":
                droid = new FactoryDroid(droidId, startPosition, grid);
                break;
            case "SCOUT":
                droid = new FactoryDroid(droidId, startPosition, grid);
                break;
            case "BUILDER":
                droid = new FactoryDroid(droidId, startPosition, grid);
                break;
            default:
                return null;
        }

        return droid;
    }

    /**
     * Creates a worker droid (convenience method).
     */
    public FactoryDroid createWorkerDroid(Position startPosition) {
        return createDroid("WORKER", startPosition);
    }

    /**
     * Creates a scout droid (convenience method).
     */
    public FactoryDroid createScoutDroid(Position startPosition) {
        return createDroid("SCOUT", startPosition);
    }

    /**
     * Creates a builder droid (convenience method).
     */
    public FactoryDroid createBuilderDroid(Position startPosition) {
        return createDroid("BUILDER", startPosition);
    }

    /**
     * Gets the total number of droids created by this factory.
     */
    public int getDroidCount() {
        return droidCounter;
    }
}
