import java.util.Comparator;

public class Token implements Comparable<Token> {

    /*
     * Shared across all token objects; belongs to the class, not the
     * instance.
     */
    public static int countUniqueWords = 0;

    private int count = 1;
    private String str;

    public Token(String s) {
        ++countUniqueWords;
        this.str = s;
    }

    public int getCount() {
        return this.count;
    }

    public void incrementCount() {
        ++this.count;
    }

    @Override
    public boolean equals(Object obj) {
        /*
         * A non-empty, non-null, unequal String object should return false.
         * Default false
         */
        if (obj == null) {
            return false;
        } else if (this == obj) {
            /*
             * Reflexive x.equals(x) := true; ie, an object is equal to
             * itself.
             */
            return true;
        } else if (this.toString().equals(obj.toString())) {
            // Content comparison := aString.equals(anotherString).
            return true;
        } else {
            return false;
        }
    }

    /*
     * Tokens are not very special, so a standard String comparison is
     * sufficient. Call the String compareTo method.
     */
    @Override
    public int compareTo(Token t) {
        return this.str.compareTo(t.toString());
    }

    public static Comparator<Token> Alphabetical = (Token tokenOne, Token tokenTwo) -> {
        return tokenOne.compareTo(tokenTwo);
    }; // End of assigned lambda expression.

    // This comparator will cause Collections.Sort to sort less frequent words
    // first.
    public static Comparator<Token> IncreasingFrequency = (Token tokenOne, Token tokenTwo) -> {
        int difference = tokenOne.getCount() - tokenTwo.getCount();
        return ((difference == 0) ? tokenOne.compareTo(tokenTwo) : difference);
    }; // End of assigned lambda expression.

    // This comparator will cause Collections.Sort to sort more frequent words first.
    /**
     * Because more frequent words should be sorted /before/ less
     * frequent words, the difference should be negative. A word
     * with a higher frequency will return a positive difference, so
     * to be sorted first the magnitude needs to be inverted.
     */
    public static Comparator<Token> DecreasingFrequency = (Token tokenOne, Token tokenTwo) -> {
        int difference = tokenOne.getCount() - tokenTwo.getCount();
        return ((difference == 0) ? tokenOne.compareTo(tokenTwo) : -difference);
    }; // End of assigned lambda expression.

    @Override
    public String toString() {
        return this.str;
    }
}
