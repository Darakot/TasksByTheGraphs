# Java Spring Project

This test covers Spring basics, basic version control with git as well as some
algortihm solving skills.

## General

* All tasks features will start from the `develop` branch and you have to create a branch for your tasks, e.g., `task/01-algorithm`
* When you are done with a task, please create a pull request to branch `develop`.
* You are allowed to merge between task/feature branches. 
* When you finished a task please create a work in progress pull request back to `develop` and assign it to your supervisor or @expertsieve.
* If you can not finish a task or have an issue during implementation try to explain it in the pull request description and/or `README` file
* Please use a built system, e.g., maven or gradle and leave some documentation about how to built your solution.
* `built.sh` and `start.sh` scripts are welcome.

# Tasks 

## Task 1 - The Algorithm

Suppose we have some input data describing relationships between nodes over 
multiple generations. The input data is formatted as a list of 
(parent, child) pairs, where each individual is assigned a unique integer 
identifier.

For example, in this diagram, 3 is a child of 10 and 2, and 5 is a child of 4:

```            
10  2   4
 \ /   / \
  3   5   8
   \ / \   \
    6   17   9
```

Sample graph as input data

```java
// Java 
int[][] parentChildPairs = new int[][] {
    {10, 3}, {2, 3}, {3, 6}, {5, 6}, {5, 17},
    {4, 5}, {4, 8}, {8, 9}
};
```

Write a function that takes this data as input and returns two collections:

* one containing all individuals with zero known parents, and 
* one containing all individuals with exactly one known parent.

Sample output for the sample graph

```
Zero parents: 10, 2, 4
One parent: 5, 7, 8, 9
```

Test your solution.

### Clarifications

* Please do not implement your solution in the `main` function.
* Output order is irrelevant.
* The IDs are not guaranteed to be contiguous.
* The input is not necessarily a connected graph. There may be >3 generations.
* No node in the input set will have more than two parents, nor will there be duplicate entries.
* No node in the input is their own parent. 
* There are no cycles in the input.
* No node may appear twice via different ancestry paths from the same descendant. That is, individual A may not be descended from individual B through both of the separate individuals C and D.


## Task 2 - Complex relationships

Based on Task 1, write a function that, for two given individuals in our dataset, returns `true` if and only if they share at least one known ancestor.


Example based on the sample graph of Task 1 two nodes input:
```
[3, 8] => false
[5, 8] => true
[6, 8] => true
```

Test your solution.

## Task 3 - REST with Spring

Please implement a **Spring Boot** and **Spring Framework based** REST service that provides Task 1 and Task 2 via an API. Thus, your solution shall accept an input graph and provide the result for Task 1 and Task 2 via API. For Task 2 it shall accept user input (for the node pair).
Test your solution.

### Clarifications

* The final version of your service must accept a graph as input and store it 
(in memory is ok). 
* The calculation results shall be available on request.
* API documentation is mandatory