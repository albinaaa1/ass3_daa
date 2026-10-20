package mst;

import dsu.DisjointSetUnion;
import graph.Edge;
import graph.Graph;

import java.util.*;

public class Kruskal {
    public static class Result {
        public List<Edge> mstEdges = new ArrayList<>();
        public double totalWeight = 0;
        public boolean isConnected;
        public long operations = 0;
    }

    public static Result findMST(Graph graph) {
        Result result = new Result();
        List<Edge> edges = new ArrayList<>(graph.getAllEdges());

        // Count edge comparisons for sorting
        Collections.sort(edges);
        result.operations += (long) (edges.size() * (Math.log(edges.size()) / Math.log(2)));

        DisjointSetUnion dsu = new DisjointSetUnion(graph.getVertices());

        for (Edge edge : edges) {
            result.operations++;
            if (dsu.union(edge.src, edge.dest)) {
                result.mstEdges.add(edge);
                result.totalWeight += edge.weight;
                if (result.mstEdges.size() == graph.getVertices() - 1) {
                    break;
                }
            }
        }

        result.operations += dsu.getOperationsCount();
        result.isConnected = (graph.getVertices() == 1) || (result.mstEdges.size() == graph.getVertices() - 1);
        return result;
    }
}