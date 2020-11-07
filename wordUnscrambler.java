import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Rebecca Goldberg 
 * class: CSC220-02 
 * project: Project #2: Word Unscrambler Game
 * 
 * This program creates a scrambled word and asks the user to guess the
 * scrambled word by swapping letters until the word is fully unscrambled
 * the game counts the number of swaps that the player used in order to 
 * unscramble the word.
 */

public class wordUnscrambler {
  // list of private class variables
  private static ArrayList<Character> letters = new ArrayList<Character>();
  private static String chosenWord;
  private static String newWord;
  private static int attemptCounter;

  // Default Constructor sets a counter variable to 0
  public wordUnscrambler() {
    attemptCounter = 0;
  }

  /**
   * The scramble method takes a String word, creates an ArrayList with each char
   * in its own index, and scrambles the chars between 5-10 times randomly.
   * 
   * @param word - a String variable taken from the .txt file randomly
   * @return ArrayList<Character> letters - an ArrayList of the scrambled word
   * 
   */
  public static ArrayList<Character> scramble(String word) {

    // adds each letter to a new place in an array
    for (int i = 0; i < word.length(); i++) {
      letters.add(word.charAt(i));
    }

    // scrambles the letters
    // generates a random number between 5-10
    int numOfTimes = (int) (Math.random() * 5) + 5;
    // loops through numOfTimes times and preforms the swap
    for (int i = 0; i < numOfTimes; i++) {
      // creates two random Index numbers smaller than word length
      int randomIndex = (int) (Math.random() * (word.length()));
      int secondIndex = (int) (Math.random() * (word.length()));
      // makes sure there are two unique letter indexes to swap
      while (randomIndex == secondIndex) {
        secondIndex = (int) (Math.random() * (word.length()));
      }
      swap(randomIndex, secondIndex);

    }

    return letters;
  }

  /**
   * This method prints the menu screen
   * 
   * @param newWord - a String of the updated scrambled word
   */
  public static void menu(String newWord) {
    // formats the -------- in the beginning of the menu
    for (int i = 0; i < newWord.length(); i++)
      System.out.print("-");
    System.out.println();
    // prints the index numbers above the word
    for (int i = 0; i < newWord.length(); i++) {
      System.out.print(i);
    }
    System.out.println();
    // prints out the scrambled word
    System.out.println(newWord);
    // formats the -------- at the end of the menu
    for (int i = 0; i < newWord.length(); i++)
      System.out.print("-");
    System.out.println();
    System.out.println();
    // prints out the menu instructions
    System.out.println("Enter 1 to swap letters.");
    System.out.println("Enter 2 to solve.");
    System.out.println("Enter 3 to quit.");
    System.out.println();
  }

  /**
   * This method takes an ArrayList<Character> and converts it to a string
   * 
   * @param list - an ArrayList with the chars of a word in each index
   * @return returnWord - a string version of all the chars in the array
   */
  public static String toWord(ArrayList<Character> list) {
    String returnWord = "";
    // adds each char from the array to a String variable
    for (int i = 0; i < list.size(); i++) {
      returnWord += list.get(i);
    }
    return returnWord;
  }

  /**
   * This method swaps the two letters at the two parameter indeces.
   * 
   * @param indexOne
   * @param indexTwo
   * @return word with swapped letters
   */
  public static void swap(int indexOne, int indexTwo) {
    // swaps the letters at the provided indeces
    // adds each letter to a new place in an array
    Character temp = letters.get(indexOne);
    letters.set(indexOne, letters.get(indexTwo));
    letters.set(indexTwo, temp);
  }

  public static String userSwap(int indexOne, int indexTwo) {
    swap(indexOne,indexTwo);
    newWord = toWord(letters);
    // counts how many times the user preforms a swap
    attemptCounter++;
    return newWord;
  }

  /**
   * This method takes input of numbers 1-3 from the user, for selecting which
   * menu action they would like. If the number is invalid, the program ends
   */
  public static void inputReader() {
    // creates a Scanner and reads the input from the user
    Scanner inputScnr = new Scanner(System.in);
    int userInput = inputScnr.nextInt();
    // executes if the user would like to swap letters
    if (userInput == 1) {
      // asks for the index numbers of the two letters to be swapped
      Scanner indexScnr = new Scanner(System.in);
      System.out.println("Enter the indexes separated by spaces");
      int indexOne = indexScnr.nextInt();
      int indexTwo = indexScnr.nextInt();
      // calls the swap method to complete this task
      // only if the index numbers are valid
      if ((indexOne >= 0 && indexOne < newWord.length()) && (indexTwo >= 0 && indexTwo < newWord.length()))
        newWord = userSwap(indexOne, indexTwo);
      else { // gives the user another chance to try giving valid index numbers
        System.out.println("Invalid index numbers. Try again...");
        try {
          Thread.sleep(1100);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        menu(newWord);
        inputReader();
      }
      // if the word is guessed correctly then the program prints the amount
      // of tries it took and then the program ends.
      if(newWord.equals(chosenWord)){
        System.out.printf("Congrats!! you guessed the word in %d tries. ", attemptCounter);
        System.out.println();
        System.out.println("The word was: " + chosenWord);
        System.exit(0);
      }
      // if the word has not yet been guessed the program loads
      // the menu with the updated scrambled word
      else {
        menu(newWord);
        inputReader();
      }
    }
    // executes if the user wants the solved word
    else if(userInput == 2){
      System.out.println("The word was: " + chosenWord + ". Bye!");
      System.exit(0);
    }
    // executes if the user would like to quit
    else if(userInput == 3)
      System.exit(0);
    // executes if the input was invalid
    else {
      System.out.println("Invalid menu option");
      System.exit(0);
    }

  }
  /**
  * This is the main method
  * @param args
  * @throws FileNotFoundException
  */
  public static void main (String args[]) throws FileNotFoundException  { 
    // counts the number of lines/words in the .txt file
    // this allows for any words.txt file to be used as llom
    int lineCount = 0;
    File file = new File("words.txt");
    Scanner scnrCount = new Scanner(file);
    while(scnrCount.hasNextLine()) {
    lineCount++;
    scnrCount.nextLine();
    }
    
    // chooses a random word from the .txt file
    Scanner scnr = new Scanner(file);
    chosenWord = "";
    int randomWord = (int) (Math.random()*lineCount)+1;
    for(int i = 0; i < randomWord; i++){
      chosenWord = scnr.nextLine();
    }
    // scrambles the chosen word and displays it in a string form
    newWord = toWord(scramble(chosenWord));
    // prints the menu with the scrambled word
    menu(newWord);
    inputReader();
    

    scnrCount.close();
    scnr.close();
    }

}