package connectivity;

import graph.Edge;
import graph.Graph;

import java.util.*;

public class TopologicalSort {
    public static class Result {
        public List<Integer> order = new ArrayList<>();
        public boolean hasCycle = false;
        public long operations = 0;
    }

    public static Result sort(Graph graph) {
        Result result = new Result();
        int n = graph.getVertices();
        int[] inDegree = new int[n];

        for (int u = 0; u < n; u++) {
            for (Edge e : graph.getAdjList().get(u)) {
                inDegree[e.dest]++;
                result.operations++;
            }
        }

        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) {
                q.add(i);
            }
        }

        while (!q.isEmpty()) {
            int u = q.poll();
            result.order.add(u);
            result.operations++;

            for (Edge e : graph.getAdjList().get(u)) {
                result.operations++;
                inDegree[e.dest]--;
                if (inDegree[e.dest] == 0) {
                    q.add(e.dest);
                }
            }
        }

        if (result.order.size() != n) {
            result.hasCycle = true;
            result.order.clear();
        }

        return result;
    }
}