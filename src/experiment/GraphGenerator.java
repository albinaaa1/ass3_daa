package experiment;

import graph.Graph;
import java.util.*;

public class GraphGenerator {
    public static Graph generateConnectedUndirected(int vertices, int edges, long seed) {
        Random rand = new Random(seed);
        Graph graph = new Graph(vertices, false);

        // Ensure connectivity via simple backbone tree
        for (int i = 1; i < vertices; i++) {
            int parent = rand.nextInt(i);
            double weight = 1 + rand.nextDouble() * 99;
            graph.addEdge(i, parent, weight);
        }

        // Add remaining edges
        int currentEdges = vertices - 1;
        while (currentEdges < edges) {
            int u = rand.nextInt(vertices);
            int v = rand.nextInt(vertices);
            if (u != v) {
                double weight = 1 + rand.nextDouble() * 99;
                graph.addEdge(u, v, weight);
                currentEdges++;
            }
        }
        return graph;
    }

    public static Graph generateDirected(int vertices, int edges, boolean makeDAG, long seed) {
        Random rand = new Random(seed);
        Graph graph = new Graph(vertices, true);
        int added = 0;

        while (added < edges) {
            int u = rand.nextInt(vertices);
            int v = rand.nextInt(vertices);
            if (u == v) continue;

            if (makeDAG) {
                if (u < v) { // Force directed order for DAG
                    graph.addEdge(u, v, 1 + rand.nextDouble() * 99);
                    added++;
                }
            } else {
                graph.addEdge(u, v, 1 + rand.nextDouble() * 99);
                added++;
            }
        }
        return graph;
    }
}