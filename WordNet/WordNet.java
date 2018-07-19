import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

public class WordNet {

    private Digraph graph;
    private final SAP sap;
    private final Map<Integer, String> idToSynset;
    private final Map<String, Set<Integer>> nounToID;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException("null input");

        idToSynset = new HashMap<Integer, String>();
        nounToID = new HashMap<String, Set<Integer>>();

        initSynset(synsets);
        graph = initHypernyms(hypernyms);
        DirectedCycle cycle = new DirectedCycle(graph);

        // check if the graph has a cycle
        if (cycle.hasCycle())
            throw new IllegalArgumentException("graph contains cycle");

        // check if the graph is rooted
        int root = 0;
        for (int i = 0; i < graph.V(); i++) {
            if (graph.outdegree(i) == 0)
                root++;
        }
        if (root != 1)
            throw new IllegalArgumentException("invalid input");

        sap = new SAP(graph);
    }

    private void initSynset(String synset) {
        In file = new In(synset);
        while (file.hasNextLine()) {
            String[] line = file.readLine().split(",");
            int id = Integer.valueOf(line[0]);
            String item = line[1];
            idToSynset.put(id, item);

            String[] nouns = item.split(" ");
            for (int i = 0; i < nouns.length; i++) {
                Set<Integer> ids = nounToID.get(nouns[i]);
                if (ids == null)
                    ids = new HashSet<Integer>();
                ids.add(id);
                nounToID.put(nouns[i], ids);
            }
        }
    }

    private Digraph initHypernyms(String hypernyms) {
        graph = new Digraph(idToSynset.size());
        In file = new In(hypernyms);
        while (file.hasNextLine()) {
            String[] line = file.readLine().split(",");
            int id = Integer.parseInt(line[0]);
            for (int i = 1; i < line.length; i++) {
                graph.addEdge(id, Integer.parseInt(line[i]));
            }
        }
        return graph;
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nounToID.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null)
            throw new IllegalArgumentException("null input");
        return nounToID.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        validateNoun(nounA);
        validateNoun(nounB);
        Set<Integer> idA = nounToID.get(nounA);
        Set<Integer> idB = nounToID.get(nounB);

        return sap.length(idA, idB);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA
    // and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        validateNoun(nounA);
        validateNoun(nounB);

        Set<Integer> idA = nounToID.get(nounA);
        Set<Integer> idB = nounToID.get(nounB);

        int ancestor = sap.ancestor(idA, idB);
        return idToSynset.get(ancestor);
    }

    private void validateNoun(String noun) {
        if (!isNoun(noun))
            throw new IllegalArgumentException();
    }

    // do unit testing of this class
    /*
     * public static void main(String[] args) {
     * 
     * }
     */
}
