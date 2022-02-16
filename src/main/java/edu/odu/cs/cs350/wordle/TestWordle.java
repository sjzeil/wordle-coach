package edu.odu.cs.cs350.wordle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.*;



public class TestWordle {


  
  /**
   * @throws java.lang.Exception
   */
  @BeforeEach
  public void setUp() throws Exception {
  }

  /**
   * Test method for Wordle constructor
   */
  @Test
  public final void testWordleConstructor() {
    //setup: empty
    //call the mutator
    Wordle w = new Wordle();
    //examine the accessors
    assertThat (w.numWords(), is(0));
    assertThat (w.iterator().hasNext(), is(false));
    // w.getSuggestion()   undefined
    // w.toString(): undefined

  }

  /**
   * Test method for Wordle constructor
   */
  @Test
  public final void testWordleAddWord() {
    //setup:
    String word1 = "STARE";
    Wordle w = new Wordle();
    //call the mutator
    w.addWord(word1);
    //examine the accessors
    assertThat (w.numWords(), is(1));
    String[] expected = {word1};
    assertThat (w, contains(expected));
    assertThat (w.getSuggestion(), is(word1.toUpperCase()));
    assertThat (w.toString(), containsString(word1.toUpperCase()));

  }
 
  @Test
  public final void testWordleAddWord3() {
    //setup:
    String[] words = {"STARE", "STAIR", "STEER", "STARE"};
    Wordle w = new Wordle();
    //call the mutator
    for (String word: words) {
      w.addWord(word);
    }
    //examine the accessors
    assertThat (w.numWords(), is(3));
    String[] expected = {"STAIR", "STARE", "STEER"};
    assertThat (w, containsInAnyOrder(expected));
    assertThat (w.getSuggestion(),isOneOf(expected));
    for (String word: expected) {
      assertThat(w.toString(), containsString(word));
    }
  }
  
  @Test
  public final void testWordleGuessCorrectPosition() {
    //setup:
    String[] words = {"STARE", "STAIR", "STEER", "STARE"};
    Wordle w = new Wordle();
    for (String word: words) {
      w.addWord(word);
    }
    //call the mutator
    w.processAGuess("QQAQQ", "XXGXX");
    //examine the accessors
    assertThat (w.numWords(), is(2));
    String[] expected = {"STAIR", "STARE"};
    assertThat (w, containsInAnyOrder(expected));
    assertThat (w.getSuggestion(),isOneOf(expected));
    for (String word: expected) {
      assertThat(w.toString(), containsString(word));
    }
  }
  

  @Test
  public final void testWordleGuessOutOfPosition() {
    //setup:
    String[] words = {"STARE", "STAIR", "STEER", "STARE"};
    Wordle w = new Wordle();
    for (String word: words) {
      w.addWord(word);
    }
    //call the mutator
    w.processAGuess("AQQQQ", "YXXXX");
    //examine the accessors
    assertThat (w.numWords(), is(2));
    String[] expected = {"STAIR", "STARE"};
    assertThat (w, containsInAnyOrder(expected));
    assertThat (w.getSuggestion(),isOneOf(expected));
    for (String word: expected) {
      assertThat(w.toString(), containsString(word));
    }
  }
  

  @Test
  public final void testWordleGuessNowhereInWord() {
    //setup:
    String[] words = {"STARE", "STAIR", "STEER", "STARE"};
    Wordle w = new Wordle();
    for (String word: words) {
      w.addWord(word);
    }
    //call the mutator
    w.processAGuess("EQQQQ", "XXXXX");
    //examine the accessors
    assertThat (w.numWords(), is(1));
    String[] expected = {"STAIR"};
    assertThat (w, containsInAnyOrder(expected));
    assertThat (w.getSuggestion(),isOneOf(expected));
    for (String word: expected) {
      assertThat(w.toString(), containsString(word));
    }
  }
  
  @Test
  public final void testWordleClone() {
    //setup:
    String[] words = {"STARE", "STAIR", "STEER", "STARE"};
    Wordle w0 = new Wordle();
    for (String word: words) {
      w0.addWord(word);
    }
    //call the mutator
    Wordle w = w0.clone();
    //examine the accessors
    assertThat(w, is(w0));
    assertThat (w.numWords(), is(3));
    String[] expected = {"STARE", "STAIR", "STEER"};
    assertThat (w, containsInAnyOrder(expected));
    assertThat (w.getSuggestion(),isOneOf(expected));
    for (String word: expected) {
      assertThat(w.toString(), containsString(word));
    }

    w.addWord("Bogus");
    assertThat(w, not(equalTo(w0)));


  }


}
