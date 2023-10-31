import java.util.Scanner;
// import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;

import ca.cyberscientist.SLL;

/**
 * @author Bryce Carson
 */
public class A2 {
    // FIELDS
    private static int countOfStopWords = 0;
    private static int totalWords = 0;
    private static SLL<Token> tokens = new SLL<>();

    // This comparitor will cause Collections.Sort to sort less frequent words first.
    private static Comparator<Token> LeastFrequentEnglishWords = (Token tokenOne, Token tokenTwo) -> {
        int difference = tokenOne.getCount() - tokenTwo.getCount();
        return ((difference == 0) ? token.compareTwo(tokenTwo) : difference);
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
        return ((difference == 0) ? tokenOne.compareTo(tokenTwo) : -difference);
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

    // METHODS
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
                    ++countOfStopWords;
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
                tokens.addInOrder(new Token(maybeNewWord));
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
            tokens.addInOrder(new Token(maybeNewWord));
        }
    }

    public static void main(String[] args) {
        // Initialize totalWords, Token.countUniqueWords, and countOfStopWords.
        new A2();

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
        System.out.println("Stop Words: " + countOfStopWords);

        if (tokens.size() > 0) {
            System.out.println("\n10 Most Frequent");
            Collections.sort(tokens, MostFrequentEnglishWords);
            for (int i = 0; i < 10 && i < tokens.size(); i++) {
                Token t = tokens.get(i); System.out.println(t + " : " + t.getCount());
            }

            System.out.println("\n10 Least Frequent");
            Collections.sort(tokens, LeastFrequentEnglishWords);
            for (int i = 0; i < 10 && i < tokens.size(); i++) {
                Token t = tokens.get(i); System.out.println(t + " : " + t.getCount());
            }

            System.out.println("\nAll");
            Collections.sort(tokens); // Natural sort
            for (Token t : tokens) System.out.println(t + " : " + t.getCount());
        }
    }
}
