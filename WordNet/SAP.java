import edu.princeton.cs.algs4.Digraph;

public class SAP {

    private final Digraph graph;

    public SAP(Digraph G) {
        if (G == null)
            throw new IllegalArgumentException("Null graph.");
        graph = new Digraph(G); // deep copy of the graph
    }

    public int length(int v, int w) {
        validateVertex(v);
        validateVertex(w);

        DeluxeBFS bfsV = new DeluxeBFS(graph, v);
        DeluxeBFS bfsW = new DeluxeBFS(graph, w);

        boolean[] markedV = bfsV.getMarked();
        boolean[] markedW = bfsW.getMarked();

        int length = Integer.MAX_VALUE;
        for (int i = 0; i < graph.V(); i++) {
            if (markedV[i] && markedW[i]) {
                length = Math.min(bfsV.distanceTo(i) + bfsW.distanceTo(i), length);
            }
        }

        if (length == Integer.MAX_VALUE)
            length = -1;
        return length;
    }

    // shortest ancestor of two vertices
    public int ancestor(int v, int w) {
        validateVertex(v);
        validateVertex(w);

        DeluxeBFS bfsV = new DeluxeBFS(graph, v);
        DeluxeBFS bfsW = new DeluxeBFS(graph, w);

        boolean[] markedV = bfsV.getMarked();
        boolean[] markedW = bfsW.getMarked();

        int ancestor = -1;
        int length = Integer.MAX_VALUE;

        for (int i = 0; i < graph.V(); i++) {
            if (markedV[i] && markedW[i]) {
                if (bfsV.distanceTo(i) + bfsW.distanceTo(i) < length) {
                    length = bfsV.distanceTo(i) + bfsW.distanceTo(i);
                    ancestor = i;
                }
            }
        }
        return ancestor;
    }

    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null)
            throw new IllegalArgumentException("no vertex");
        for (int i : v)
            validateVertex(i);
        for (int i : w)
            validateVertex(i);

        DeluxeBFS bfsV = new DeluxeBFS(graph, v);
        DeluxeBFS bfsW = new DeluxeBFS(graph, w);

        boolean[] markedV = bfsV.getMarked();
        boolean[] markedW = bfsW.getMarked();

        int length = Integer.MAX_VALUE;
        for (int i = 0; i < graph.V(); i++) {
            if (markedV[i] && markedW[i]) {
                length = Math.min(bfsV.distanceTo(i) + bfsW.distanceTo(i), length);
            }
        }

        if (length == Integer.MAX_VALUE)
            length = -1;
        return length;
    }

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null)
            throw new IllegalArgumentException("no vertex");
        for (int i : v)
            validateVertex(i);
        for (int i : w)
            validateVertex(i);

        DeluxeBFS bfsV = new DeluxeBFS(graph, v);
        DeluxeBFS bfsW = new DeluxeBFS(graph, w);

        boolean[] markedV = bfsV.getMarked();
        boolean[] markedW = bfsW.getMarked();

        int ancestor = -1;
        int length = Integer.MAX_VALUE;

        for (int i = 0; i < graph.V(); i++) {
            if (markedV[i] && markedW[i]) {
                if (bfsV.distanceTo(i) + bfsW.distanceTo(i) < length) {
                    length = bfsV.distanceTo(i) + bfsW.distanceTo(i);
                    ancestor = i;
                }
            }
        }
        return ancestor;
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= graph.V()) {
            throw new IllegalArgumentException("index out of bound");
        }
    }
}
