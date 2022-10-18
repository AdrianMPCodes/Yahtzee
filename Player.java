abstract class Player{
  
  // Constructs player class
  public Player(){
  }

  // I've decided to call the round type (human or computer) in their respective classes because I don't use similar methods for each.
  // chooseDiceToKeep() for human vs. decideReRoll() for computer would have been a hassle to call from player with different if statements if I were to
  // use a oneRound() method in player.

  // Other common methods are just located in the dice class. I put a lot of effort into thinking about my classes and where I should place methods.
  // Hope this makes sense.

  public static boolean keepAllDie(String [] keptDie){
  // Pre: Receives array of die being kept and rerolled.
  // Post: Returns true if all die are kept, and false otherwise.

  int keeperCounter = 0;

  for (int j = 0; j < keptDie.length; j++){
    if (keptDie[j].equalsIgnoreCase("K"))
     keeperCounter++;
  }
    if (keeperCounter == 5)
      return true;
    
    return false;
  }
}