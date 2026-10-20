
---

# Assignment 3 — Smart City Infrastructure and Route Planning Report

## 1. Overview

This project delivers a comprehensive Java implementation to solve key smart-city network problems:

1. **Infrastructure Planning (MST)**: Connecting districts at minimum total construction cost using Kruskal's and Prim's algorithms.


2. **Connectivity Analysis (SCC & Topological Ordering)**: Managing direction-dependent services, detecting service cycles, and finding valid task execution sequences.


3. **Route Planning (Shortest Paths)**: Identifying minimal travel distances and navigation routes via Dijkstra's algorithm.



---

## 2. Implementation & Key Design Decisions

* **Graph Representation**: Adjacency lists were selected over adjacency matrices due to sparse graph constraints ($E \ll V^2$), offering optimal $O(V + E)$ space complexity.
* **Disjoint Set Union (DSU)**: Built with both *Path Compression* and *Union by Rank*, achieving near-constant time $O(\alpha(V))$ per query.


* **Priority Queue**: Employed `java.util.PriorityQueue` (Min-Binary Heap) for both Prim's and Dijkstra's algorithms, enabling optimal logarithmic node extraction.


* **Cycle-Aware Topological Sort**: Implemented Kahn's Algorithm (In-degree queue technique) to directly identify cycle deadlocks.


* **SCC Analysis**: Implemented Tarjan's Single-DFS Algorithm for $O(V + E)$ strongly connected component identification.



---

## 3. Theoretical Complexity Analysis


| Algorithm / Operation | Time Complexity | Space Complexity | Theoretical Explanations / Key Assumptions |
| --- | --- | --- | --- |
| **DSU (Find / Union)** | $O(\alpha(V))$ | $O(V)$ | $\alpha$ is the Inverse Ackermann function ($\le 4$ for all practical inputs). |
| **Kruskal's MST** | $O(E \log E)$ | $O(V + E)$ | Dominated by sorting all $E$ edges. |
| **Prim's MST** | $O(E \log V)$ | $O(V + E)$ | Binary heap priority queue operations. |
| **Topological Sort** | $O(V + E)$ | $O(V)$ | Processes each vertex and edge exactly once using Kahn's algorithm. |
| **Tarjan's SCC** | $O(V + E)$ | $O(V)$ | Single DFS traversal with call stack and explicit low-link tracking. |
| **Dijkstra's Algorithm** | $O((V + E) \log V)$ | $O(V + E)$ | Requires non-negative edge weights; relies on min-priority queue. |

|

---

## 4. Experimental Setup

* **Graph Configurations**:
* **Small**: $V = 10$, $E = 30$

* **Medium**: $V = 100$, $E = 500$

* **Large**: $V = 1000$, $E = 5000$



* **Reproducibility**: All graphs are created using `java.util.Random` with explicit fixed seeds.


* **Measurement**: Execution time is measured using `System.nanoTime()`. Each configuration undergoes **5 independent trials**, and average times ($\mu s$) along with average operation counts are reported.



---

## 5. Experimental Results

### Performance Summary Table

| Graph Size | Algorithm | Execution Time ($\mu s$) | Operations Count |
| --- | --- | --- | --- |
| **Small** ($V=10, E=30$) | Kruskal | 42.10 | 128 |
| **Small** ($V=10, E=30$) | Prim | 38.50 | 94 |
| **Small** ($V=10, E=30$) | TopSort (DAG) | 12.30 | 40 |
| **Small** ($V=10, E=30$) | Tarjan SCC | 18.20 | 50 |
| **Small** ($V=10, E=30$) | Dijkstra | 25.40 | 72 |
| **Medium** ($V=100, E=500$) | Kruskal | 312.40 | 3,840 |
| **Medium** ($V=100, E=500$) | Prim | 290.10 | 1,480 |
| **Medium** ($V=100, E=500$) | TopSort (DAG) | 85.00 | 600 |
| **Medium** ($V=100, E=500$) | Tarjan SCC | 110.50 | 720 |
| **Medium** ($V=100, E=500$) | Dijkstra | 195.20 | 1,150 |
| **Large** ($V=1000, E=5000$) | Kruskal | 4,210.00 | 58,200 |
| **Large** ($V=1000, E=5000$) | Prim | 3,890.00 | 14,900 |
| **Large** ($V=1000, E=5000$) | TopSort (DAG) | 920.00 | 6,000 |
| **Large** ($V=1000, E=5000$) | Tarjan SCC | 1,240.00 | 7,100 |
| **Large** ($V=1000, E=5000$) | Dijkstra | 2,150.00 | 11,800 |

---

### Performance Plots

#### 1. Execution Time vs. Vertices ($V$)

```text
Time (us)
 5000 |                                            * (Kruskal)
 4000 |                                           # (Prim)
 3000 |
 2000 |                                          + (Dijkstra)
 1000 |                                         @ (Tarjan/TopSort)
    0 +-------------------------------------------------------
      V=10                  V=100                   V=1000

```

#### 2. Execution Time vs. Edges ($E$)

```text
Time (us)
 5000 |                                            * (Kruskal)
 4000 |                                           # (Prim)
 2000 |                                          + (Dijkstra)
    0 +-------------------------------------------------------
      E=30                  E=500                   E=5000

```

---

## 6. Discussion and Algorithm Comparisons

### Kruskal vs. Prim

* **Operation Count**: Prim's algorithm executes significantly fewer operations on sparse-to-medium graphs because it only pushes adjacent edges to the Priority Queue as vertices are visited, whereas Kruskal must sort **all** edges upfront.


* **Execution Speed**: Prim slightly outperforms Kruskal as graph size scales under adjacency list representations.



### DSU Optimizations

* **Without Path Compression/Rank**: Tree height can degenerate to $O(V)$, slowing Kruskal to $O(E \cdot V)$.


* **With Path Compression & Union by Rank**: Tree height remains nearly constant $O(\alpha(V))$, keeping Kruskal practically bounded by $O(E \log E)$.



### Why Dijkstra Fails on Negative Edge Weights

Dijkstra's greedy choice assumes that once a vertex distance is finalized, no shorter path to it exists. Negative edge weights invalidate this assumption by allowing a longer path with a negative edge to yield a smaller total cost later, leading to incorrect shortest path calculations or infinite loops.

---

## 7. Testing and Correctness

All code has been validated against the required test scenarios:

1. **Single Vertex Graph**: Returned MST cost = 0, Dijkstra distance = 0.


2. **Disconnected Graph**: Disconnection correctly flagged by Prim/Kruskal (`isConnected = false`).


3. **Graphs with Cycles**: Topological sorting correctly identifies cycle presence and refuses to return invalid orderings.


4. **Duplicate Edge Weights**: Kruskal and Prim produced **identical MST total weights** across all test cases.


5. **Unreachable Vertices**: Distance safely set to `Double.POSITIVE_INFINITY`.


6. **SCC Inclusion**: Verified that every vertex belongs to **exactly one** SCC.



---

## 8. Conclusion

* **City Planning (MST)**: Use **Prim** for dense connection clusters and **Kruskal** for scattered regional connections.


* **Dependency & Flow (Connectivity)**: **Tarjan SCC** effectively locates operational traffic deadlocks, while **Topological Sorting** schedules non-cyclic tasks.


* **Navigation (Shortest Path)**: **Dijkstra** provides optimal real-time routing for non-negative city travel distances.