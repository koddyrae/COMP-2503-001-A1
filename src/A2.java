import java.util.Scanner;

/**
 * @author Bryce Carson
 */
public class A2 {
    // FIELDS
    private static int countOfStopWords = 0;
    private static int totalWords = 0;
    // Create the three lists that will be used to complete this assignment.
    private static SLL<Token>
        tokensAlphabetical = new SLL<>(),
        tokensHighestFrequency = new SLL<>(),
        tokensLowestFrequency = new SLL<>();

    // "Stop Words" provided by the instructor.
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
    public A2() {
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

            Token maybeNewToken = new Token(maybeNewWord);
            /*
             * When the size of the tokensAlphabetical array list (the dictionary) is zero,
             * unconditionally add the word to the dictionary; thereafter, all
             * tokens must be compared against the growing dictionary to
             * determine if they are already present or not.
             */
            if (tokensAlphabetical.size() == 0) {
                // An exception to the rule that we need to add the tokens in order. There is no ordering a single element.
                tokensAlphabetical.addHead(maybeNewToken);
                continue Tokenize;
            }

            /*
             * Iterate over the list's current size, incrementing the
             * count of each unique word if it is already found in the
             * dictionary.
             */
            for (int tokenIndex = 0; tokenIndex < tokensAlphabetical.size(); tokenIndex++) {
                Token existingToken = tokensAlphabetical.get(tokenIndex).getData();

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
                    continue Tokenize;
                }
            }

            // Finally, if we should add the word to the dictionary we do so.
            tokensAlphabetical.addInOrder(new Node<Token>(new Token(maybeNewWord)), Token.Alphabetical);
        }

        // Get the first node.
        System.out.println("Populating the other two lists.");
        Node<Token> currentNode = tokensAlphabetical.get(0);

        // Create the other two lists in order of increasing and decreasing frequency.
        while(currentNode != null) {
            tokensLowestFrequency.addInOrder(currentNode, Token.IncreasingFrequency);
            tokensHighestFrequency.addInOrder(currentNode, Token.DecreasingFrequency);

            currentNode = currentNode.getNext();
        }

        s.close();
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
         * word_n : #frequency
         */
        System.out.println("Total Words: " + totalWords);
        System.out.println("Unique Words: " + Token.countUniqueWords);
        System.out.println("Stop Words: " + countOfStopWords);

        if (tokensAlphabetical.size() > 0) {
            System.out.println("\n10 Most Frequent");
            for (int i = 0; i < 10 && i < tokensHighestFrequency.size(); i++) {
                Token t = tokensHighestFrequency.get(i).getData(); System.out.println(t + " : " + t.getCount());
            }

            System.out.println("\n10 Least Frequent");
            for (int i = 0; i < 10 && i < tokensLowestFrequency.size(); i++) {
                Token t = tokensLowestFrequency.get(i).getData(); System.out.println(t + " : " + t.getCount());
            }

            System.out.println("\nAll");
            Token t;
            for (int i = 0; i < tokensAlphabetical.size(); i++) {
                t = tokensAlphabetical.get(i).getData();
                System.out.println(t + " : " + t.getCount());
            }
        }
    }
}
