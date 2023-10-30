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

    // NOTE: now that I've learned the lambda syntax, use that next time.
    private static Comparator<Token> MostFrequentEnglishWords = new Comparator<>() {
        @Override
        public int compare(Token tokenOne, Token tokenTwo) {
            int difference = tokenOne.getCount() - tokenTwo.getCount();
            if (difference == 0) {
                return tokenOne.compareTo(tokenTwo);
            } else {
                /*
                 * Because more frequent words should be sorted /before/ less
                 * frequent words, the difference should be negative. A word
                 * with a higher frequency will return a positive difference, so
                 * to be sorted first the magnitude needs to be inverted.
                 */
                return -difference;
            }
        }
    };

    private static Comparator<Token> LeastFrequentEnglishWords = new Comparator<>() {
        @Override
        public int compare(Token tokenOne, Token tokenTwo) {
            int difference = tokenOne.getCount() - tokenTwo.getCount();
            if (difference == 0) {
                return tokenOne.compareTo(tokenTwo);
            } else {
                /*
                 * Words with a higher frequency will return a positive
                 * difference and be sorted after less frequent, so no change to
                 * the difference variable's value before returning it is
                 * necessary, as it was for the MostFrequentEnglishWords
                 * comparator.
                 */
                return difference;
            }
        }
    };

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
        new A1();
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
         *
         * All
         * word2 : #frequency
         */
        System.out.println("Total Words: " + totalWords);
        System.out.println("Unique Words: " + Token.countUniqueWords);
        System.out.println("Stop Words: " + countStopWords);

        if (tokens.size() > 0) {
            System.out.println();
            System.out.println("10 Most Frequent");
            Collections.sort(tokens, MostFrequentEnglishWords);
            for (int i = 0; i < 10 && i < tokens.size(); i++) {
                if (tokens.get(i) != null) {
                    System.out.println(tokens.get(i).toString() + " : " + tokens.get(i).getCount());
                } else {
                    break;
                }
            }

            System.out.println();
            System.out.println("10 Least Frequent");
            Collections.sort(tokens, LeastFrequentEnglishWords);
            for (int i = 0; i < 10 && i < tokens.size(); i++) {
                if (tokens.get(i) != null) {
                    System.out.println(tokens.get(i).toString() + " : " + tokens.get(i).getCount());
                } else {
                    break;
                }
            }

            System.out.println();
            System.out.println("All");
            Collections.sort(tokens);
            for (Token token : tokens) {
                if (token != null) {
                    System.out.println(token + " : " + token.getCount());
                } else {
                    break;
                }
            }
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
            return this.toString().compareTo(t.toString());
        }

        @Override
        public String toString() {
            return this.str;
        }
    }
}
