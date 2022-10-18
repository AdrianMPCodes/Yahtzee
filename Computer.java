public class Computer extends Player {

  String name; 
  boolean debug;
  Dice rolls;

  // Computer constructor
  public Computer(boolean isBug, String userName, Dice fiveDice){
    debug = isBug;
    name = userName;
    rolls = fiveDice;
  }

  public void computerRound(){
  // Pre: receives nothing.
  // Post: returns nothing. Plays one computer round of Yahtzee.

    // Creates an array of dice being kept or rerolled.
    String keptDie [] = new String [5];

    for (int i = 1; i <= 3; i++){
      System.out.println("\nComputer's turn. Roll " + i +".");

      // Just warning the user that they will have to fill in dice rolls for the computer on debug.
      if (debug){
        System.out.println("\n" + name + ", you are choosing the computer’s dice rolls.");
      } 

      // Rolls all die for computer's first turn
      if (i == 1){
        rolls.rollDice();
        rolls.sortDice();
        rolls.printDice();
        // Computer decides which die are being kept
        keptDie = decideReRoll(keptDie);
      } else {
        // Re-rolls any dice that the computer doesn't keep for turns 2 and 3
        for (int j = 0; j < keptDie.length; j++){
          if (keptDie[j].equalsIgnoreCase("R"))
            rolls.reRoll(j);
        } 
        // Re-rolls dice and prints them sorted
        System.out.println("\nAfter rerolling...");
        rolls.sortDice();
        rolls.printDice();
        // You can't reroll on last turn. You are stuck with your dice.
        if (i < 3)
          keptDie = decideReRoll(keptDie);
        }

        // Determines if all die are kept, if so, the round is ended
        if(Player.keepAllDie(keptDie))
          break;
    }
  }

  public String [] decideReRoll (String [] dieBeingKept){
  // Pre: receives an empty array.
  // Post: returns an array dice rolls being kept or rerolled. 

    System.out.println("");
    // Prints the die the computer is rerolling
    for (int i = 0; i < 5; i++){
      // Rerolls any die that aren't a 5 or a 6
      if (rolls.fiveDice[i] == 6 || rolls.fiveDice[i] == 5){
        dieBeingKept[i] = "K";
      } else {
        System.out.println("The computer rerolls die " + (i+1)+ ".");
        dieBeingKept[i] = "R";
      }
    }
    return dieBeingKept;
  }
}