package experiment;

import connectivity.*;
import graph.Graph;
import mst.*;
import shortestpath.Dijkstra;

public class BenchmarkRunner {
    public static void main(String[] args) {
        int[] V = {10, 100, 1000};
        int[] E = {30, 500, 5000};
        String[] names = {"Small", "Medium", "Large"};

        System.out.println("====== EXPERIMENTAL ANALYSIS RESULTS ======\n");

        for (int i = 0; i < V.length; i++) {
            System.out.println("=== Configuration: " + names[i] + " (V=" + V[i] + ", E=" + E[i] + ") ===");

            // MST Benchmark
            double kruskalTime = 0, primTime = 0;
            long kruskalOps = 0, primOps = 0;

            for (int r = 0; r < 5; r++) {
                Graph undir = GraphGenerator.generateConnectedUndirected(V[i], E[i], 42 + r);

                long start = System.nanoTime();
                Kruskal.Result kRes = Kruskal.findMST(undir);
                kruskalTime += (System.nanoTime() - start);
                kruskalOps += kRes.operations;

                start = System.nanoTime();
                Prim.Result pRes = Prim.findMST(undir);
                primTime += (System.nanoTime() - start);
                primOps += pRes.operations;
            }

            System.out.printf("Kruskal MST : Avg Time = %.2f us, Avg Ops = %d\n", (kruskalTime / 5) / 1000.0, kruskalOps / 5);
            System.out.printf("Prim MST    : Avg Time = %.2f us, Avg Ops = %d\n", (primTime / 5) / 1000.0, primOps / 5);

            // Directed Graph Benchmarks
            double topSortTime = 0, tarjanTime = 0, dijkstraTime = 0;
            long topSortOps = 0, tarjanOps = 0, dijkstraOps = 0;

            for (int r = 0; r < 5; r++) {
                Graph dag = GraphGenerator.generateDirected(V[i], E[i], true, 100 + r);
                Graph dir = GraphGenerator.generateDirected(V[i], E[i], false, 200 + r);

                long start = System.nanoTime();
                TopologicalSort.Result tsRes = TopologicalSort.sort(dag);
                topSortTime += (System.nanoTime() - start);
                topSortOps += tsRes.operations;

                start = System.nanoTime();
                TarjanSCC.Result sccRes = TarjanSCC.findSCCs(dir);
                tarjanTime += (System.nanoTime() - start);
                tarjanOps += sccRes.operations;

                start = System.nanoTime();
                Dijkstra.Result dRes = Dijkstra.findShortestPaths(dag, 0);
                dijkstraTime += (System.nanoTime() - start);
                dijkstraOps += dRes.operations;
            }

            System.out.printf("TopSort (DAG): Avg Time = %.2f us, Avg Ops = %d\n", (topSortTime / 5) / 1000.0, topSortOps / 5);
            System.out.printf("Tarjan SCC   : Avg Time = %.2f us, Avg Ops = %d\n", (tarjanTime / 5) / 1000.0, tarjanOps / 5);
            System.out.printf("Dijkstra     : Avg Time = %.2f us, Avg Ops = %d\n\n", (dijkstraTime / 5) / 1000.0, dijkstraOps / 5);
        }
    }
}