package connectivity;

import graph.Edge;
import graph.Graph;

import java.util.*;

public class TarjanSCC {
    public static class Result {
        public List<List<Integer>> sccList = new ArrayList<>();
        public long operations = 0;
    }

    private static int id;
    private static int[] ids, low;
    private static boolean[] onStack;
    private static Deque<Integer> stack;

    public static Result findSCCs(Graph graph) {
        Result result = new Result();
        int n = graph.getVertices();
        id = 0;
        ids = new int[n];
        Arrays.fill(ids, -1);
        low = new int[n];
        onStack = new boolean[n];
        stack = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            if (ids[i] == -1) {
                dfs(i, graph, result);
            }
        }
        return result;
    }

    private static void dfs(int u, Graph graph, Result result) {
        ids[u] = low[u] = id++;
        stack.push(u);
        onStack[u] = true;
        result.operations++;

        for (Edge e : graph.getAdjList().get(u)) {
            result.operations++;
            int v = e.dest;
            if (ids[v] == -1) {
                dfs(v, graph, result);
                low[u] = Math.min(low[u], low[v]);
            } else if (onStack[v]) {
                low[u] = Math.min(low[u], ids[v]);
            }
        }

        if (ids[u] == low[u]) {
            List<Integer> scc = new ArrayList<>();
            while (true) {
                int node = stack.pop();
                onStack[node] = false;
                scc.add(node);
                if (node == u) break;
            }
            result.sccList.add(scc);
        }
    }
}