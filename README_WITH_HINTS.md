# Factory Droid Bug Bash

A Java-based factory droid game with **intentional bugs** designed for testing practice and bug-fixing exercises.

## Overview

This project contains a simple factory droid simulation where droids can navigate a grid using pathfinding algorithms. The code has intentional bugs across different categories, and your goal is to find and fix them using the comprehensive test suite provided.

## Project Structure

```
bug_bash/
├── pom.xml                          # Maven configuration
├── src/
│   ├── main/java/com/bugbash/
│   │   ├── Position.java            # 2D coordinate class (HAS BUGS)
│   │   ├── Grid.java                # Game grid/map (HAS BUGS)
│   │   ├── FactoryDroid.java        # Main droid class (HAS BUGS)
│   │   ├── PathFinder.java          # A* pathfinding (HAS BUGS)
│   │   ├── DroidState.java          # Enum for droid states
│   │   └── DroidFactory.java        # Factory pattern (HAS BUGS)
│   └── test/java/com/bugbash/
│       ├── PositionTest.java        # Tests for Position
│       ├── GridTest.java            # Tests for Grid
│       ├── FactoryDroidTest.java    # Tests for FactoryDroid
│       ├── PathFinderTest.java      # Tests for PathFinder
│       └── IntegrationTest.java     # End-to-end tests
└── README.md                        # This file
```

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Running the Tests

```bash
cd /Users/vbatra/demos/bug_bash
mvn clean test
```

**Expected result**: 10 out of 64 tests will fail due to intentional bugs.

### Viewing Test Results

After running tests, detailed reports are available in:
```
target/surefire-reports/
```

## Bug Categories

This project contains bugs in four categories:

### 1. Null Pointer Issues ☠️
Missing null checks causing NullPointerExceptions.

**Affected files:**
- `Position.java` - equals() method
- `Grid.java` - addObstacle() method
- `FactoryDroid.java` - moveTo() method
- `PathFinder.java` - constructor and findPath() method
- `DroidFactory.java` - createDroid() method

### 2. Boundary/Edge Cases 📏
Off-by-one errors and boundary validation issues.

**Affected files:**
- `Grid.java` - isValidPosition() method
- `FactoryDroid.java` - movement outside grid boundaries
- `PathFinder.java` - start/end position validation

### 3. State Management 🔄
Incorrect state transitions and state pollution.

**Affected files:**
- `Grid.java` - Can add obstacles on occupied cells
- `FactoryDroid.java` - Invalid state transitions
- `PathFinder.java` - Not clearing previous search state
- `DroidFactory.java` - Improper initialization

### 4. Logic Errors 🧮
Wrong algorithms or calculations.

**Affected files:**
- `Position.java` - Manhattan distance calculation
- `FactoryDroid.java` - Empty path handling
- `PathFinder.java` - Heuristic function

## Bug Fixing Exercise

### Step 1: Run Tests and Identify Failures

```bash
mvn test
```

Look for failing tests and read the assertion messages carefully.

### Step 2: Analyze Each Bug

For each failing test:
1. Read the test name and understand what it's testing
2. Look at the assertion that failed
3. Find the corresponding source code
4. Identify the bug (comments in code provide hints)

### Step 3: Fix the Bugs

#### Hints by File:

**Position.java:**
- Line ~50: equals() method missing null check - will crash with NullPointerException
- Line ~32: manhattanDistance() uses `*` instead of `+` in calculation

**Grid.java:**
- Line ~37: isValidPosition() uses `<=` when it should use `<` for width/height
- Line ~46: addObstacle() doesn't check for null position
- Line ~48: addObstacle() doesn't verify position isn't already occupied

**FactoryDroid.java:**
- Line ~44: setState() doesn't validate state transitions
- Line ~52: moveTo() doesn't check if targetPosition is null
- Line ~54: moveTo() doesn't validate target is within grid before moving
- Line ~90: followPath() doesn't handle empty list properly

**PathFinder.java:**
- Line ~22: Constructor doesn't check if grid is null
- Line ~28: heuristic() uses Euclidean distance instead of Manhattan
- Line ~41: findPath() doesn't validate start/goal positions
- Line ~42: findPath() doesn't clear cameFrom/gScore from previous searches

**DroidFactory.java:**
- Line ~26: createDroid() returns null instead of throwing exception for unknown types
- Line ~36: Droids not occupying starting position on grid
- Line ~40: Scout droid should maybe start in different state

### Step 4: Verify Your Fixes

After fixing each bug, run the tests again:

```bash
mvn test
```

The number of passing tests should increase. Repeat until all 64 tests pass!

## Test Categories

### Unit Tests
- **PositionTest**: 11 tests covering coordinate operations and distance calculations
- **GridTest**: 16 tests covering grid boundaries, obstacles, and state
- **FactoryDroidTest**: 14 tests covering droid movement and state management
- **PathFinderTest**: 14 tests covering pathfinding algorithm

### Integration Tests
- **IntegrationTest**: 9 tests covering complete scenarios with multiple components

## Success Criteria

You've successfully completed the bug bash when:
- ✅ All 64 tests pass
- ✅ No NullPointerExceptions occur
- ✅ Boundary validation works correctly
- ✅ State management is consistent
- ✅ Algorithms produce correct results

## Learning Objectives

By completing this exercise, you will practice:
1. **Reading test output** - Understanding JUnit assertions and failure messages
2. **Debugging** - Tracing code execution to find root causes
3. **Null safety** - Recognizing and fixing null pointer vulnerabilities
4. **Boundary conditions** - Handling edge cases in algorithms
5. **State management** - Ensuring consistent object state
6. **Algorithm correctness** - Verifying mathematical and logical operations

## Running Specific Tests

To run a single test class:
```bash
mvn test -Dtest=PositionTest
```

To run a single test method:
```bash
mvn test -Dtest=PositionTest#testManhattanDistance
```

## Tips

1. **Start with unit tests** - Fix Position and Grid bugs first before tackling complex integration tests
2. **Read the bug comments** - Each buggy class has comments indicating where bugs are
3. **One bug at a time** - Fix one bug, run tests, verify, then move to the next
4. **Understand the fix** - Don't just make tests pass; understand why the bug occurred
5. **Check test assertions** - The expected vs actual values give clues about what's wrong

## Advanced Challenge

Once all tests pass:
1. Add more test cases for edge conditions
2. Implement additional droid capabilities (resource gathering, building)
3. Create a visualization of droids moving on the grid
4. Add performance tests for pathfinding on large grids

## License

This is an educational project for testing and debugging practice.

---

**Current Status**: 10 failing tests out of 64 total tests

**Your Goal**: Get all tests to pass! 🎯
