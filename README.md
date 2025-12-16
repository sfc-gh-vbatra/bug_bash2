# Factory Droid Bug Bash - Code Review Challenge

A Java-based factory droid game with **intentional bugs** designed for advanced code review practice.

## Overview

This project contains a simple factory droid simulation where droids can navigate a grid using pathfinding algorithms. The code has intentional bugs across different categories, and your goal is to **find them through code review alone** - no tests provided!

## The Challenge

Unlike typical bug bash exercises with failing tests to guide you, this is a **pure code review challenge**. You must:
- Read and analyze the code
- Identify bugs by understanding what the code *should* do
- Spot logical errors, edge cases, and potential crashes
- Fix bugs based on your own analysis

## Project Structure

```
bug_bash/
├── pom.xml                          # Maven configuration
└── src/
    └── main/java/com/bugbash/
        ├── Position.java            # 2D coordinate class
        ├── Grid.java                # Game grid/map
        ├── FactoryDroid.java        # Main droid class
        ├── PathFinder.java          # A* pathfinding algorithm
        ├── DroidState.java          # Enum for droid states
        └── DroidFactory.java        # Factory pattern for creating droids
```

## Prerequisites
- Java 17 or higher
- Strong understanding of Java programming
- Knowledge of common bug patterns
- Critical thinking and code analysis skills

## Bug Categories

This project contains bugs in four categories. Your challenge is to find them all:

### 1. Null Pointer Issues ☠️
Missing null checks that will cause NullPointerExceptions at runtime.

### 2. Boundary/Edge Cases 📏
Off-by-one errors and incorrect boundary validation.

### 3. State Management 🔄
Incorrect state transitions and state pollution between operations.

### 4. Logic Errors 🧮
Wrong algorithms, incorrect calculations, or flawed implementations.

## Your Mission

### Step 1: Understand the System

Read through each class and understand:
- **Position.java**: How 2D coordinates work, distance calculations
- **Grid.java**: How the game grid manages boundaries, obstacles, and occupied positions
- **DroidState.java**: The possible states a droid can be in
- **FactoryDroid.java**: How droids move and navigate
- **PathFinder.java**: A* pathfinding algorithm implementation
- **DroidFactory.java**: Factory pattern for creating different droid types

### Step 2: Find the Bugs

For each file, look for:
- What happens if null is passed?
- Are boundaries checked correctly?
- Can invalid state transitions occur?
- Are algorithms implemented correctly?
- What edge cases might break?

### Step 3: Document Your Findings

Create a list of bugs you find:
- File name and approximate line number
- Description of the bug
- Why it's a problem
- How to fix it

### Step 4: Fix and Verify

- Fix each bug you identified
- Think through scenarios that would trigger each bug
- Consider writing your own tests to verify fixes (advanced!)

## Expected Bug Count

There are **15+ bugs** hidden in this codebase across the four categories. Can you find them all?

## Difficulty Level

⭐⭐⭐⭐⭐ **Expert Level**

This exercise requires:
- Deep understanding of Java semantics
- Ability to trace code execution mentally
- Knowledge of common pitfalls and edge cases
- Experience with debugging and code review

## Tips for Success

1. **Read carefully** - Don't skim; understand what each method is supposed to do
2. **Think about edge cases** - What if inputs are null, negative, or at boundaries?
3. **Trace execution paths** - Follow the code flow in your mind
4. **Question assumptions** - Does the code handle all cases?
5. **Consider the API contract** - What should methods return/throw in error cases?

## Advanced Challenge

Once you've found and fixed all bugs:
1. Write a comprehensive test suite to prove your fixes work
2. Add defensive programming to prevent similar bugs
3. Implement additional features (resource gathering, building)
4. Create a visualization of droids moving on the grid

## Learning Objectives

By completing this exercise, you will master:
1. **Code Review Skills** - Identifying bugs without running code
2. **Mental Debugging** - Tracing execution paths in your head
3. **Null Safety** - Recognizing missing null checks
4. **Boundary Analysis** - Spotting off-by-one errors
5. **State Management** - Understanding state consistency issues
6. **Algorithm Verification** - Validating algorithmic correctness

## Hints (If You Get Stuck)

<details>
<summary>Click to reveal general areas to focus on</summary>

- Look at methods that accept Position parameters
- Check all boundary comparisons (<=, <, >=, >)
- Examine what happens between multiple method calls
- Review distance calculation formulas
- Check if resources are cleaned up between operations

</details>

## Building the Project

```bash
mvn clean compile
```

If you write your own tests:
```bash
mvn clean test
```

## License

This is an educational project for advanced code review and debugging practice.

---

**Difficulty**: Expert Level 🔥

**Your Goal**: Find all 15+ bugs through code review alone!

**No tests. No hints. Just pure analysis.** 🎯
