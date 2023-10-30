package ca.cyberscientist;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;

/**
 * @author Bryce Carson
 */
public class A1 {
    private static int countStopWords = 0;
    private static int totalWords = 0;
    private static ArrayList<Token> tokens = new ArrayList<>();

    // This comparitor will cause Collections.Sort to sort less frequent words first.
    private static Comparator<Token> LeastFrequentEnglishWords = (Token tokenOne, Token tokenTwo) -> {
        int difference = tokenOne.getCount() - tokenTwo.getCount();
        return ((difference == 0) token.compareTwo(tokenTwo) : difference);
    }; // End of assigned lambda expression.

    // This comparitor will cause Collections.Sort to sort more frequent words first.
    /**
     * Because more frequent words should be sorted /before/ less
     * frequent words, the difference should be negative. A word
     * with a higher frequency will return a positive difference, so
     * to be sorted first the magnitude needs to be inverted.
     */
    private static Comparator<Token> MostFrequentEnglishWords = (Token tokenOne, Token tokenTwo) -> {
        int difference = tokenOne.getCount() - tokenTwo.getCount();
        return ((difference == 0) tokenOne.compareTo(tokenTwo) : -difference);
    }; // End of assigned lambda expression.

    // "Stop Words" provided by the intstructor.
    private static final String[] stopWords = {
            "a",     "about", "all",   "am",
            "an",    "and",   "any",   "are",
            "as",    "at",    "be",    "been",
            "but",   "by",    "can",   "cannot",
            "could", "did",   "do",    "does",
            "else",  "for",   "from",  "get",
            "got",   "had",   "has",   "have",
            "he",    "her",   "hers",  "him",
            "his",   "how",   "i",     "if",
            "in",    "into",  "is",    "it",
            "its",   "like",  "more",  "me",
            "my",    "no",    "now",   "not",
            "of",    "on",    "one",   "or",
            "our",   "out",   "said",  "say",
            "says",  "she",   "so",    "some",
            "than",  "that",  "the",   "their",
            "them",  "then",  "there", "these",
            "they",  "this",  "to",    "too",
            "us",    "upon",  "was",   "we",
            "were",  "what",  "with",  "when",
            "where", "which", "while", "who",
            "whom",  "why",   "will",  "you",
            "your"
    };

    public static void main(String[] args) {
        // Initialize totalWords, Token.countUniqueWords, and countStopWords.
        new A1();

        // Generate a printed listing following this format.
        /*
         * Total Words: #words
         * Unique Words: #words
         * Stop Words: #words
         *
         * 10 Most Frequent
         * word1 : #frequency
         *
         * 10 Least Frequent
         * word2 : #frequency
         *
         * All
         * word1 : #frequency
         * word2 : #frequency
         * word3 : #frequency
         *       …
         * wordn : #frequency
         */
        System.out.println("Total Words: " + totalWords);
        System.out.println("Unique Words: " + Token.countUniqueWords);
        System.out.println("Stop Words: " + countStopWords);

        // If there were any tokens.
        if (tokens.size() > 0) {
            System.out.println("\n10 Most Frequent");
            Collections.sort(tokens, MostFrequentEnglishWords);
            for (int i = 0; i < 10 && i < tokens.size(); i++) {
                Token t = tokens.get(i); // Defensive
                (t != null) ? System.out.println(t + " : " + t.getCount()) : break;
            }

            System.out.println("\n10 Least Frequent");
            Collections.sort(tokens, LeastFrequentEnglishWords);
            for (int i = 0; i < 10 && i < tokens.size(); i++) {
                Token t = tokens.get(i); // Defensive
                (t != null) ? System.out.println(t + " : " + t.getCount()) : break;
            }

            // Sort whatever tokens exist alphabetically and print each with its count.
            System.out.println("\nAll");
            Collections.sort(tokens);
            for (Token t : tokens) (t != null) ? System.out.println(t + " : " + t.getCount()) : break;
        }
    }

    public A1() {
        Scanner s = new Scanner(System.in);

        Tokenize: while (s.hasNext()) {
            /*
             * Replace all non-lowercase alphabetic characters with the empty
             * string, ie remove them (sanitize the input).
             */
            String maybeNewWord = s.next().trim().toLowerCase().replaceAll("[^a-z]", "");
            if (maybeNewWord.length() == 0)
                continue Tokenize;

            ++totalWords; // Count it as a word if it's clearly a word.

            for (String stopWord : stopWords) {
                if (stopWord.equals(maybeNewWord)) {
                    ++countStopWords;
                    continue Tokenize;
                }
            }

            /*
             * When the size of the tokens array list (the dictionary) is zero,
             * unconditionally add the word to the dictionary; thereafter, all
             * tokens must be compared against the growing dictionary to
             * determine if they are already present or not.
             */
            if (tokens.size() == 0) {
                tokens.add(new Token(maybeNewWord));
                continue Tokenize;
            }

            /*
             * Iterate over the dictionary's current size, incrementing the
             * count of each unique word if it is already found in the
             * dictionary.
             */
            for (int i = 0; i < tokens.size(); i++) {
                Token existingToken = tokens.get(i);

                /*
                 * In the event that we encounter an existing token equivalent
                 * to what otherwise maybeNewWord, increment the count of that
                 * token, change the sentinel to false so that we do not add the
                 * word to the dictionary another time (in the next iteration of
                 * the loop), and continue tokenizing because we do not need to
                 * check against the rest of the dictionary.
                 */
                if (existingToken.toString().equals(maybeNewWord)) {
                    existingToken.incrementCount();
                    continue Tokenize; // Avoid the next statement in this case,
                                       // it would be redundant.
                }
            }

            // Finally, if we should add the word to the dictionary we do so.
            tokens.add(new Token(maybeNewWord));
        }
    }

    public class Token implements Comparable<Token> {

        /*
         * Shared across all token objects; belongs to the class, not the
         * instance.
         */
        private static int countUniqueWords = 0;

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
}
