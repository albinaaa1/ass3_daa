package test;

import connectivity.*;
import graph.Graph;
import mst.*;
import shortestpath.Dijkstra;

public class TestRunner {
    public static void main(String[] args) {
        System.out.println("=== RUNNING UNIT & INTEGRATION TESTS ===");

        // 1. Single vertex
        Graph single = new Graph(1, false);
        assert Kruskal.findMST(single).totalWeight == 0;
        assert Prim.findMST(single).totalWeight == 0;
        assert Dijkstra.findShortestPaths(single, 0).dist[0] == 0;
        System.out.println("[PASS] 1. Single vertex graph");

        // 2. Disconnected graph
        Graph dis = new Graph(4, false);
        dis.addEdge(0, 1, 5);
        assert !Kruskal.findMST(dis).isConnected;
        assert !Prim.findMST(dis).isConnected;
        System.out.println("[PASS] 2. Disconnected graph detection");

        // 3. Duplicate weights & Multiple valid MSTs
        Graph dup = new Graph(3, false);
        dup.addEdge(0, 1, 2.0);
        dup.addEdge(1, 2, 2.0);
        dup.addEdge(0, 2, 2.0);
        double kWeight = Kruskal.findMST(dup).totalWeight;
        double pWeight = Prim.findMST(dup).totalWeight;
        assert Math.abs(kWeight - pWeight) < 1e-9 && kWeight == 4.0;
        System.out.println("[PASS] 3. Duplicate weights & MST match");

        // 4. Cycle Detection in Topological Sort
        Graph cycle = new Graph(3, true);
        cycle.addEdge(0, 1, 1);
        cycle.addEdge(1, 2, 1);
        cycle.addEdge(2, 0, 1);
        assert TopologicalSort.sort(cycle).hasCycle;
        System.out.println("[PASS] 4. Cycle detection in TopSort");

        // 5. Unreachable Vertices in Dijkstra
        Graph unreach = new Graph(3, true);
        unreach.addEdge(0, 1, 10);
        Dijkstra.Result dRes = Dijkstra.findShortestPaths(unreach, 0);
        assert dRes.dist[2] == Double.POSITIVE_INFINITY;
        System.out.println("[PASS] 5. Unreachable vertex handling");

        // 6. SCC Verification (Every vertex in exactly one SCC)
        Graph sccG = new Graph(4, true);
        sccG.addEdge(0, 1, 1);
        sccG.addEdge(1, 0, 1);
        sccG.addEdge(2, 3, 1);
        TarjanSCC.Result sccRes = TarjanSCC.findSCCs(sccG);
        int totalNodesInSCC = 0;
        for (var scc : sccRes.sccList) totalNodesInSCC += scc.size();
        assert totalNodesInSCC == 4;
        System.out.println("[PASS] 6. SCC completeness test");

        System.out.println("\nALL TESTS PASSED SUCCESSFULLY!");
    }
}