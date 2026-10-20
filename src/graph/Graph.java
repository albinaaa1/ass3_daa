package graph;

import java.util.*;

public class Graph {
    private final int vertices;
    private final boolean directed;
    private final List<List<Edge>> adjList;
    private final List<Edge> allEdges;

    public Graph(int vertices, boolean directed) {
        this.vertices = vertices;
        this.directed = directed;
        this.adjList = new ArrayList<>(vertices);
        this.allEdges = new ArrayList<>();
        for (int i = 0; i < vertices; i++) {
            adjList.add(new ArrayList<>());
        }
    }

    public void addEdge(int src, int dest, double weight) {
        Edge edge = new Edge(src, dest, weight);
        adjList.get(src).add(edge);
        allEdges.add(edge);
        if (!directed) {
            adjList.get(dest).add(new Edge(dest, src, weight));
        }
    }

    public int getVertices() { return vertices; }
    public boolean isDirected() { return directed; }
    public List<List<Edge>> getAdjList() { return adjList; }
    public List<Edge> getAllEdges() { return allEdges; }
}