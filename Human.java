import java.util.Scanner;

public class Human extends Player {
  static Scanner sc = new Scanner(System.in);

  String name;
  boolean debug;
  Dice rolls;

  public Human(boolean isBug, String userName, Dice fiveDice){
    debug = isBug;
    name = userName;
    rolls = fiveDice;
  }

  public void humanRound(){
  // Pre: receives nothing.
  // Post: returns nothing. Plays a human round of Yahtzee.
    
    String keptDie [] = new String [5];
    
    // Plays the user's next 3 rolls and breaks once the user keeps all die
    for (int i = 1; i <= 3; i++){
      System.out.println("\n" + name + "'s turn. Roll " + i +".");      

      // Rolls all die for user's first turn
      if (i == 1){
        rolls.rollDice();
        rolls.sortDice();
        rolls.printDice();
        // Creates array of dice being kept and rerolled
        keptDie = chooseDiceToKeep(rolls);
      } else {
        // Re-rolls any dice the user doesn't keep for turns 2 and 3
        for (int j = 0; j < keptDie.length; j++){
          if (keptDie[j].equalsIgnoreCase("R"))
            rolls.reRoll(j);
        }      
        System.out.println("\nAfter rerolling...");
        rolls.sortDice();
        rolls.printDice();
        // You can't reroll on last turn. You are stuck with your dice.
        if (i < 3)
          keptDie = chooseDiceToKeep(rolls);
      }
      
      // Determines if all die are kept
      if(Player.keepAllDie(keptDie))
        break;
    }
  }

  public String [] chooseDiceToKeep(Dice rolls){
  // Pre: receives dice object.
  // Post: returns an array of dice the user has selected to keep.

    String diceBeingKept [] = new String [5];
    
    System.out.println("\nFor each die, enter K (keep) or R (reroll):");

    for (int i = 1; i <= diceBeingKept.length; i++){
      System.out.print("die " + i + "("+ rolls.fiveDice[i-1] +"): ");
      String userDecision = sc.nextLine();

      while (!userDecision.equalsIgnoreCase("K") && !userDecision.equalsIgnoreCase("R")){
        System.out.print("die " + i + "("+ rolls.fiveDice[i-1] +"): ");
        userDecision = sc.nextLine();
      }
      diceBeingKept[i-1] = userDecision;      
    }
    return diceBeingKept;
  }
}