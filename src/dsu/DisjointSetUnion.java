package dsu;

public class DisjointSetUnion {
    private final int[] parent;
    private final int[] rank;
    private long operationsCount;

    public DisjointSetUnion(int size) {
        parent = new int[size];
        rank = new int[size];
        operationsCount = 0;
        for (int i = 0; i < size; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    public int find(int i) {
        operationsCount++;
        if (parent[i] == i) {
            return i;
        }
        // Path compression
        return parent[i] = find(parent[i]);
    }

    public boolean union(int i, int j) {
        operationsCount++;
        int rootI = find(i);
        int rootJ = find(j);

        if (rootI != rootJ) {
            // Union by rank
            if (rank[rootI] < rank[rootJ]) {
                parent[rootI] = rootJ;
            } else if (rank[rootI] > rank[rootJ]) {
                parent[rootJ] = rootI;
            } else {
                parent[rootJ] = rootI;
                rank[rootI]++;
            }
            return true;
        }
        return false;
    }

    public long getOperationsCount() { return operationsCount; }
}