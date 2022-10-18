import java.util.Scanner;
import java.util.Random;

public class Dice {
  static Scanner sc = new Scanner(System.in);
  
  public int fiveDice[];
  boolean debug;
  Random rand = new Random();
  
  // Constructs dice object
  public Dice(boolean isBug){
    debug = isBug;
    fiveDice = new int[5];
  }

  public void rollDice(){
  // Pre: nothing.
  // Post:returns nothing. Assigns the value of the 5 die for debug or no debug.

    System.out.println("");
    // User inputs dice values for debug.
    if (debug){
      for (int i = 0; i < 5; i++){
        boolean validEntry = false;
        int selectRoll = 0;
        String selectRollString;
        
        do {
          System.out.print("Enter die " + (i+1) + ": ");
          selectRollString = sc.next();

          try {
            selectRoll = Integer.parseInt(selectRollString); 
            // Check if value is valid
            if (selectRoll < 1 || selectRoll > 6) {
              System.out.println("Invalid entry. Try again.");
            } else {
              validEntry = true;
            }
          }
          catch (NumberFormatException e) {
            System.out.println("Input is not valid. Try again.");
          }
        } while (!validEntry);
        fiveDice[i] = selectRoll;
      }
    // Random generated die otherwise.
    }else {
      for (int i = 0; i < fiveDice.length; i++){
        fiveDice[i] = rand.nextInt(6) + 1;
      }
    }
  }

  public void reRoll(int diePos){
  // Pre: receives the position of the dice that needs to be rerolled.
  // Post: returns nothing. The user either enters a number or a new random number is selected for the die being rerolled (Depending on if debug is on or off).
    if (debug){
      boolean validEntry = false;
      int selectRoll = 0;
      String selectRollString;

      // Gets valid input for a dice roll from the user
      do {
        System.out.print("Enter die " + (diePos+1) + ": ");
        selectRollString = sc.next();

        try {
          selectRoll = Integer.parseInt(selectRollString); 
          // Check if value is valid
          if (selectRoll < 1 || selectRoll > 6) {
            System.out.println("Invalid entry. Try again.");
          } else {
            validEntry = true;
          }
        }
        catch (NumberFormatException e) {
          System.out.println("Input is not valid. Try again.");
        }
      } while (!validEntry);
      fiveDice[diePos] = selectRoll;
    } else {
      fiveDice[diePos] = rand.nextInt(6) + 1;
    }
  }

  public void sortDice(){
  // Pre: receives nothing.
  // Post: returns nothing. Input array is sorted using insertion sort in ascending order such that dice[i] ≤ dice[i+1] for 0 ≤ i ≤ 5

    // Insertion sort via class time where we learned it.
    // Start at index 1 since index 0 is already sorted.
    for (int i = 1; i < fiveDice.length; i++) {
      for (int j = i - 1; j >= 0; j--) {
        if (fiveDice[j+1] < fiveDice[j]) {
          int temp = fiveDice[j+1];
          fiveDice[j+1] = fiveDice[j];
          fiveDice[j] = temp;
        }
        else
          break;
      }
    }
  }

  public int sumDice(){
  // Pre: receives nothing.
  // Post: returns the sum of all five die in the array of fiveDice.

    int totalSum = 0;  

    for (int i = 0; i < fiveDice.length; i++)
      totalSum += fiveDice[i];

    return totalSum;
  }

  public boolean isYahtzee(){
    // Determiness if all 5 dice are the same number
    for (int i = 0; i < 5; i++){
      int sameDieCounter = 0;
      for (int j = 0; j < 5; j++){
        if (fiveDice[j] == fiveDice[i]) 
          sameDieCounter++;
      } 
      if (sameDieCounter == 5)
        return true;
    }
    return false;
  }

  public void printDice(){
  // Pre: receives nothing.
  // Post: returns nothing. prints the five dice.
    System.out.print("Here are the dice:");
      
    for (int i = 0; i < fiveDice.length; i++)
      System.out.print(" " + fiveDice[i]);
    System.out.println("");
  }
}