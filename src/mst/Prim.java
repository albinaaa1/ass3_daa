package mst;

import graph.Edge;
import graph.Graph;

import java.util.*;

public class Prim {
    public static class Result {
        public List<Edge> mstEdges = new ArrayList<>();
        public double totalWeight = 0;
        public boolean isConnected;
        public long operations = 0;
    }

    public static Result findMST(Graph graph) {
        Result result = new Result();
        int n = graph.getVertices();
        if (n == 0) return result;

        boolean[] visited = new boolean[n];
        PriorityQueue<Edge> pq = new PriorityQueue<>();

        visited[0] = true;
        for (Edge e : graph.getAdjList().get(0)) {
            pq.add(e);
            result.operations++;
        }

        while (!pq.isEmpty() && result.mstEdges.size() < n - 1) {
            Edge edge = pq.poll();
            result.operations++;

            if (visited[edge.dest]) continue;

            visited[edge.dest] = true;
            result.mstEdges.add(edge);
            result.totalWeight += edge.weight;

            for (Edge next : graph.getAdjList().get(edge.dest)) {
                result.operations++;
                if (!visited[next.dest]) {
                    pq.add(next);
                }
            }
        }

        int visitedCount = 0;
        for (boolean v : visited) if (v) visitedCount++;
        result.isConnected = (visitedCount == n);

        return result;
    }
}