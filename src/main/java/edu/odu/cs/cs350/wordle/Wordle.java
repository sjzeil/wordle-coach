package edu.odu.cs.cs350.wordle;

import java.lang.invoke.StringConcatException;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;

/**
 * A Wordle "coach" (state tracker).   Checks words guessed and replies received
 * against a substantial English dictionary.
 */
public class Wordle implements Iterable<String>, Cloneable {

    private Set<String> words;
    private Random rand;

    /**
     * Create a wordle coach with no words and no guesses so far.
     */
    public Wordle() {
        words = new HashSet<>();
        rand = new Random();
    }

    /**
     * Provide access to all words that have been added and that are consistent
     * with the guesses made so far.
     * 
     * @return an iterator over a sequence of words
     */
    public Iterator<String> iterator() {
        return words.iterator();
    }

    /**
     * Indicate the number of words remaining that are consistent with the 
     * guesses made so far.
     * 
     * @return number of words remaining.
     */
    public int numWords() {
        return words.size();
    }

    /**
     * Add a word to the list of candidates.  Must be called before any guesses are made.
     * Added words are converted to all upper-case.  Duplicates are ignored. 
     * 
     * Words that are not of length 5 or that contain any non-alphabetic characters are ignored.
     * 
     * @param word a word to add.
     */
    public void addWord(String word) {
        if (word.length() != 5)
            return;
        boolean OK = true;
        for (int i = 0; i < 5 && OK; ++i) {
            OK = Character.isAlphabetic(word.charAt(i));
        }
        if (OK) {
            words.add(word.toUpperCase());
        }
    }

    /**
     * Remove from the list of candidate words any that are inconsistent with
     * a guess. 
     * 
     * @param guess  a 5-letter word
     * @param codedResponses a string of 5 character representing the Wordle response:
     *           X (grey) means the letter cannot occur anywhere in the target word, Y (yellow) means that
     *           the letter must occur somewhere else in the target word, G (green) means that the letter must be in this exact position
     *           within the target word. 
     */
    public void processAGuess(String guess, String codedResponses) {
        guess = guess.toUpperCase();
        HashSet<String> remaining = new HashSet<>();
        for (String word: words) {
            boolean OK = true;
            for (int i = 0; i < 5 && OK; ++i) {
                char r = codedResponses.charAt(i);
                if (r == 'X') {
                    OK = word.indexOf(guess.charAt(i)) < 0;
                } else if (r == 'Y') {
                    int k = word.indexOf(guess.charAt(i));
                    OK = k > -1 && k != i;
                } else if (r == 'G') {
                    OK = word.charAt(i) == guess.charAt(i);
                }
            }
            if (OK) {
                remaining.add(word);
            }
        }
        words = remaining;
    }

    /**
     * Supply a random guess from among the candidate words.
     * 
     * @return a word consistent with the guesses made so far.
     */
    public String getSuggestion() {
        int k = rand.nextInt(numWords());
        Iterator<String> iter = iterator();
        String word = null;
        for (int i = 0; i < k; ++i) {
            word = iter.next();
        }
        if (word == null) {
            word = iter.next();
        }
        return word;
    }

    public Wordle clone() {
        Wordle theClone = new Wordle();
        /*
        for (String word: words) {
            theClone.addWord(word);
        }*/
        theClone.words = words;
        return theClone;
    }


    public String toString() {
        StringBuffer buf = new StringBuffer();
        for (String w: words) {
            buf.append(w + ", ");
        }
        return buf.toString();
    }


    public boolean equals(Object obj) {
        if (obj instanceof Wordle) {
            Wordle right = (Wordle)obj;
            if (numWords() != right.numWords())
                return false;
            return words.equals(right.words);
        } else {
            return false;
        }
    }

}
