import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;

public class DeluxeBFS {

    private static final int INFINITY = Integer.MAX_VALUE;
    private boolean[] marked;
    private int[] distTo;
    private int[] edgeTo;

    public DeluxeBFS(Digraph G, int v) {
        marked = new boolean[G.V()];
        distTo = new int[G.V()];
        edgeTo = new int[G.V()];
        for (int i = 0; i < G.V(); i++)
            distTo[i] = INFINITY;
        bfs(G, v);
    }

    public DeluxeBFS(Digraph G, Iterable<Integer> source) {
        marked = new boolean[G.V()];
        distTo = new int[G.V()];
        edgeTo = new int[G.V()];
        for (int i = 0; i < G.V(); i++)
            distTo[i] = INFINITY;
        bfs(G, source);
    }

    // breadth first search from a single source
    private void bfs(Digraph G, int v) {
        Queue<Integer> queue = new Queue<Integer>();
        queue.enqueue(v);
        marked[v] = true;
        distTo[v] = 0;

        while (!queue.isEmpty()) {
            int curr = queue.dequeue();
            for (int w : G.adj(curr)) {
                if (!marked[w]) {
                    marked[w] = true;
                    edgeTo[w] = curr;
                    distTo[w] = distTo[curr] + 1;
                    queue.enqueue(w);
                }
            }
        }
    }

    // breadth first search from multiple sources
    private void bfs(Digraph G, Iterable<Integer> source) {
        Queue<Integer> queue = new Queue<Integer>();
        for (int w : source) {
            queue.enqueue(w);
            marked[w] = true;
            distTo[w] = 0;
        }

        while (!queue.isEmpty()) {
            int curr = queue.dequeue();
            for (int w : G.adj(curr)) {
                if (!marked[w]) {
                    marked[w] = true;
                    distTo[w] = distTo[curr] + 1;
                    edgeTo[w] = curr;
                    queue.enqueue(w);
                }
            }
        }
    }

    // distance from source (or sources) to v
    public int distanceTo(int v) {
        return distTo[v];
    }

    // return a defensive copy of boolean array marked[]
    public boolean[] getMarked() {
        int len = marked.length;
        boolean[] res = new boolean[len];
        for (int i = 0; i < len; i++)
            res[i] = marked[i];
        return res;
    }
}
