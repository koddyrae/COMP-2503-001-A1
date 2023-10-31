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

    @Override
    public String toString() {
        return this.str;
    }
}
