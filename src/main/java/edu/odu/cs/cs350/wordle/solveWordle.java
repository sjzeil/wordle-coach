package edu.odu.cs.cs350.wordle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class solveWordle {

    /**
     * Wordle solution coach.
     */
    public static void main(String[] args) throws IOException {
        Wordle wordle = new Wordle();
        fillFromDictionary(new File("/usr/share/dict/words"), wordle);
        while (wordle.numWords() > 1) {
            processAGuess(wordle);
            System.out.println("There are " + wordle.numWords() + " possible solutions remaining.");
            if (wordle.numWords() > 1) {
                System.out.println("Suggestion: " + wordle.getSuggestion());
            }
        }
        if (wordle.numWords() == 1) {
            System.out.println("The only possible solution is: " + wordle.iterator().next());
        } else if (wordle.numWords() == 0) {
            System.out.println("No solution is possible using words in the dictionary.");
        }
    }

    private static void processAGuess(Wordle wordle) throws IOException {
        System.out.println("What word has been guessed?");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String guess = in.readLine();
        String responses = "";
        while (responses.length() != 5) {
            System.out.println(
                    "What colors were shown? B=black G=green Y=yellow X=grey");
            responses = in.readLine().toUpperCase();
            if (responses.length() != 5 || responses.matches(".*[^BGYX].*")) {
                responses = "";
                continue;
            }
            wordle.processAGuess(guess, responses);
        }

    }

    private static void fillFromDictionary(File file, Wordle wordle) {
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            String line = "";
            while (true) {
                line = in.readLine();
                if (line == null)
                    break;
                else if (line.length() == 5) {
                    wordle.addWord(line);
                }
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Could not find " + file);
        } catch (IOException ex2) {
            System.err.println("Could not read " + file);
        }
    }
}
