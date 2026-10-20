package shortestpath;

import graph.Edge;
import graph.Graph;

import java.util.*;

public class Dijkstra {
    public static class Node implements Comparable<Node> {
        public int id;
        public double dist;

        public Node(int id, double dist) {
            this.id = id;
            this.dist = dist;
        }

        @Override
        public int compareTo(Node o) {
            return Double.compare(this.dist, o.dist);
        }
    }

    public static class Result {
        public double[] dist;
        public int[] parent;
        public long operations = 0;

        public Result(int n) {
            dist = new double[n];
            parent = new int[n];
            Arrays.fill(dist, Double.POSITIVE_INFINITY);
            Arrays.fill(parent, -1);
        }

        public List<Integer> getPathTo(int dest) {
            List<Integer> path = new ArrayList<>();
            if (dist[dest] == Double.POSITIVE_INFINITY) return path;
            for (int at = dest; at != -1; at = parent[at]) {
                path.add(at);
            }
            Collections.reverse(path);
            return path;
        }
    }

    public static Result findShortestPaths(Graph graph, int src) {
        int n = graph.getVertices();
        Result result = new Result(n);
        result.dist[src] = 0;

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(src, 0.0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            result.operations++;
            int u = current.id;

            if (current.dist > result.dist[u]) continue;

            for (Edge e : graph.getAdjList().get(u)) {
                result.operations++;
                if (e.weight < 0) {
                    throw new IllegalArgumentException("Dijkstra does not support negative edge weights.");
                }

                if (result.dist[u] + e.weight < result.dist[e.dest]) {
                    result.dist[e.dest] = result.dist[u] + e.weight;
                    result.parent[e.dest] = u;
                    pq.add(new Node(e.dest, result.dist[e.dest]));
                }
            }
        }
        return result;
    }
}