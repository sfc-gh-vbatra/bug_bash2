package com.bugbash;

/**
 * Represents the various states a factory droid can be in.
 */
public enum DroidState {
    /**
     * Droid is idle and not performing any action.
     */
    IDLE,

    /**
     * Droid is currently moving to a destination.
     */
    MOVING,

    /**
     * Droid is blocked and cannot proceed.
     */
    BLOCKED,

    /**
     * Droid encountered an error.
     */
    ERROR
}
