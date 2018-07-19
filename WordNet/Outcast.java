import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {

    private final WordNet wordnet;

    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    public String outcast(String[] nouns) {
        int n = nouns.length;
        int id = 0;
        int maxDistance = 0;
        for (int i = 0; i < n; i++) {
            if (distance(nouns, i) > maxDistance) {
                id = i;
                maxDistance = distance(nouns, i);
            }
        }
        return nouns[id];
    }

    private int distance(String[] nouns, int target) {
        int n = nouns.length;
        int distance = 0;
        for (int i = 0; i < n; i++) {
            if (i == target)
                continue;
            distance += wordnet.distance(nouns[i], nouns[target]);
        }
        return distance;
    }

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
