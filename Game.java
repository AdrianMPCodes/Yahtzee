import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.lang.Math; 
import java.util.LinkedHashSet;

public class Game{
  static Scanner sc = new Scanner(System.in);
  Random rand = new Random();

  private String gameType;
  private String name; 
  public boolean debug;
    
  // Constructs game object
  public Game(String theModeOfGame, String userName) {
    gameType = theModeOfGame;
    name = userName;
  }

  public void runGameMode(String gameMode){
  // Pre: receives the game type that the user has selected to play and play that game type
  // Post: returns nothing
  
    // Plays a gamemode and sets the gameType value for that game
    if(gameMode.equalsIgnoreCase("O")){
      gameType = "O";
      onePersonGame();
    } else if (gameMode.equalsIgnoreCase("OD")){
      gameType = "OD";
      onePersonGame();
    } else if (gameMode.equalsIgnoreCase("T")){
      gameType = "T";
      twoPersonGame();
    } else if (gameMode.equalsIgnoreCase("TD")){
      gameType = "TD";
      twoPersonGame();
    } else if (gameMode.equalsIgnoreCase("CS")){
      gameType = "CS";
      computer();
    } else if (gameMode.equalsIgnoreCase("CSD")){
      gameType = "CSD";
      computer();
    } else if (gameMode.equalsIgnoreCase("R")){
      printRules();
    }
  }

  public void onePersonGame(){
  // Pre: recieves nothing
  // Post: returns nothing. Plays a one person game with or without debug.

    // Determines type of human only game, either debug or not
    if (gameType.equalsIgnoreCase("O"))
      debug = false;
    else if (gameType.equalsIgnoreCase("OD"))
      debug = true;

    // Creates objects for a human game
    Dice rolls = new Dice(debug);
    Human person = new Human (debug, name, rolls);
    ScoreCard scoring = new ScoreCard();

    // Stores the number of bonuses the human scores
    int humanBonuses = 0;

    // Plays one game of Yahtzee
    for (int i = 0; i < 13; i++){
      System.out.println("\n▌▌▌▌▌》Round " + (i+1) + "《▌▌▌▌▌");

      System.out.println("\nHere is your scorecard: ");
      scoring.printScoreCard();
      person.humanRound();

      // Checks if yahtzee has already been rolled for bonus
      if (scoring.bonusCheck() && rolls.isYahtzee()){
        System.out.println("\nYou scored another yahtzee. 100 has been added to your total score.");
        humanBonuses++;
      }

      System.out.println("\nHere is your scorecard: ");
      scoring.printScoreCard();

      // Picks a category to score a round
      System.out.println("\n1: ones, 2: twos, 3: threes, 4: fours, 5: fives, 6: sixes, 3K: 3 of a Kind, 4K: 4 of a Kind, FH: Full House, SS: Small Straight, LS: Large Straight, Y: Yahtzee, C: Chance");
      String category = "";

      // If bonus is active
      if (scoring.bonusCheck() && rolls.isYahtzee() && !scoring.isUpperFull()){
        System.out.println("Pick a category in the upper section: ");
        category = sc.next();          
        // Check if category selected is in the upper section
        while(!scoring.determineSection(category).equalsIgnoreCase("Upper") || !scoring.validSelectedCategory(category)) {
          System.out.println("That category has already been selected or isn't in the upper section. Pick another category in the upper section: ");
          category = sc.next();
        }
      // No bonus
      } else {
        System.out.println("Pick a category: ");
        category = sc.next();

        while (!scoring.validSelectedCategory(category)){
          System.out.println("That category is already taken or doesn't exist. Pick another category: ");
          category = sc.next();
        }
      }
      
      // Updates the user's scorecard and calculates their score for that round
      scoring.assignScoreCategoryValues(Integer.toString(calculateScore(category, scoring.determineSection(category), rolls, scoring)), category);
      System.out.println("\nHere is your scorecard: ");
      scoring.printScoreCard();
    }
    System.out.println("The game has ended " + name +". Your final score is: " + scoring.totalScore(humanBonuses));
  }
  
  public void twoPersonGame(){
  // Pre: recieves nothing
  // Post: returns nothing. Plays a two person game with or without debug.

    // Stores the number of bonuses the human and computer scores
    int humanBonuses = 0;
    int compBonuses = 0;

    // Determines type of two player game, either debug or not
    if (gameType.equalsIgnoreCase("T"))
      debug = false;
    else if (gameType.equalsIgnoreCase("TD"))
      debug = true;

    // Creates objects for a human and computer player
    Dice rolls = new Dice(debug);
    Human person = new Human (debug, name, rolls);
    Computer comp = new Computer (debug, name, rolls);
    ScoreCard scoringPerson = new ScoreCard();
    ScoreCard scoringComputer = new ScoreCard();

    for (int i = 0; i < 13; i++){
      System.out.println("\n▌▌▌▌▌》Round " + (i+1) + "《▌▌▌▌▌");

      System.out.println("\nHere is your scorecard: ");
      scoringPerson.printScoreCard();
      person.humanRound();

      // Checks if yahtzee has already been rolled for bonus
      if (scoringPerson.bonusCheck() && rolls.isYahtzee()){
        System.out.println("\nYou scored another yahtzee. 100 has been added to your total score.");
        humanBonuses++;
      }

      System.out.println("\nHere is your scorecard: ");
      scoringPerson.printScoreCard();
      
      // Picks a category to score a round
      System.out.println("\n1: ones, 2: twos, 3: threes, 4: fours, 5: fives, 6: sixes, 3K: 3 of a Kind, 4K: 4 of a Kind, FH: Full House, SS: Small Straight, LS: Large Straight, Y: Yahtzee, C: Chance");
      String category = "";

      // If bonus is active
      if (scoringPerson.bonusCheck() && rolls.isYahtzee() && !scoringPerson.isUpperFull()){
        System.out.println("Pick a category in the upper section: ");
        category = sc.next();          
        // Check if category selected is in the upper section
        while(!scoringPerson.determineSection(category).equalsIgnoreCase("Upper") || !scoringPerson.validSelectedCategory(category)) {
          System.out.println("That category has already been selected or isn't in the upper section. Pick another category in the upper section: ");
          category = sc.next();
        }
      // No bonus
      } else {
        System.out.println("Pick a category: ");
        category = sc.next();

        while (!scoringPerson.validSelectedCategory(category)){
          System.out.println("That category is already taken or doesn't exist. Pick another category: ");
          category = sc.next();
        }
      }
     
      // Updates the user's scorecard and calculates their score for that round
      scoringPerson.assignScoreCategoryValues(Integer.toString(calculateScore(category, scoringPerson.determineSection(category), rolls, scoringPerson)), category);
      System.out.println("\nHere is your scorecard: ");
      scoringPerson.printScoreCard();
      
      // Plays the computer's turn
      System.out.println("\nIt is now the computer’s turn...");
      System.out.println("\nHere is the computer scorecard: ");
      scoringComputer.printScoreCard();
      comp.computerRound();

      // Checks if yahtzee has already been rolled for bonus
      if (scoringComputer.bonusCheck() && rolls.isYahtzee()){
        System.out.println("The computer scored another yahtzee. 100 has been added to its total score.");
        compBonuses++;
      }

      System.out.println("\nHere is the computer scorecard: ");
      scoringComputer.printScoreCard();

      // Stores the value of the category's position in the array of categories the computer will select 
      int posOfCategory = 0;
        
      // If bonus is active
      if (scoringComputer.bonusCheck() && rolls.isYahtzee() && !scoringComputer.isUpperFull()){
        // Computer picks a category in the upper section
        ArrayList <Integer> leftOverPositionsUpper = scoringComputer.selectableCategoriesUpper();
        int leftOverPositionsSizeUpper = scoringComputer.selectableCategoriesUpper().size();
        int randNumOfArraySize = rand.nextInt(leftOverPositionsSizeUpper);
        posOfCategory = leftOverPositionsUpper.get(randNumOfArraySize);
          
      // No bonus
      } else {
        // Determines a category of the remaining ones for the computer to select
        // Creates an arrayList of the avaible categorie for the computer to select in order
        ArrayList <Integer> leftOverPositions = scoringComputer.selectableCategories();
        int leftOverPositionsSize = scoringComputer.selectableCategories().size();
        int randNumOfArraySize = rand.nextInt(leftOverPositionsSize);
        posOfCategory = leftOverPositions.get(randNumOfArraySize);
      }


      // Updates the computer's scoring card and calculates the computer's score for that round
      scoringComputer.assignScoreCategoryValues(Integer.toString(calculateScore(scoringComputer.categories[posOfCategory], scoringComputer.determineSection( scoringComputer.categories[posOfCategory]), rolls, scoringComputer)), scoringComputer.categories[posOfCategory]);

      System.out.println("\nThe computer selected the category " + scoringComputer.categories[posOfCategory] +".");
      
      System.out.println("\nHere is the computer scorecard: ");
      scoringComputer.printScoreCard();
    }
    // Determines a winner of the two player game
    System.out.println("The game has ended " + name +". Your final score is: " + scoringPerson.totalScore(humanBonuses));
    System.out.println("The the computer's final score is: " + scoringComputer.totalScore(compBonuses));

    if (scoringPerson.totalScore(humanBonuses) > scoringComputer.totalScore(compBonuses))
      System.out.println(name +", you won!");
    else if (scoringPerson.totalScore(humanBonuses) < scoringComputer.totalScore(compBonuses))
      System.out.println(name +", you lost. The computer won!");
    else if (scoringPerson.totalScore(humanBonuses) == scoringComputer.totalScore(compBonuses))
      System.out.println(name +", you drawed. The game ended in a tie with computer!");
  }

  public void computer(){
  // Pre: recieves nothing
  // Post: returns nothing. Computer plays a solitaire with or without debug.

    // Determines type of single player computer game, either debug or not
    if (gameType.equalsIgnoreCase("CS"))
      debug = false;
    else if (gameType.equalsIgnoreCase("CSD"))
      debug = true;

    // Serves to get a valid input for the number of games the user want the computer to play
    boolean validEntry = false;
    
    int totalGames = 0;
    String totalGamesString;

    // Gets the number of games the user would like the computer to play
    do {
      System.out.println("\nHow many games would like the computer to play (integer)?");
      totalGamesString = sc.next();

      try {
        totalGames = Integer.parseInt(totalGamesString); 
        // Check if value is valid
        if (totalGames  <= 0) {
          System.out.println("Invalid entry. Try again.");
        } else {
           validEntry = true;
        }
      }
      catch (NumberFormatException e) {
        System.out.println("Input is not valid. Try again.");
      }
    } while (!validEntry);
  
    int storeScores [] = new int [totalGames];

    for (int k = 0; k < totalGames; k++){

      // Creates objects for the computer's game
      Dice rolls = new Dice(debug);
      Computer comp = new Computer (debug, name, rolls);
      ScoreCard scoringComputer = new ScoreCard();

      // Stores the number of bonuses the computer scores
      int compBonuses = 0;

      for (int i = 0; i < 13; i++){
        System.out.println("\n▌▌▌▌▌》Round " + (i+1) + "《▌▌▌▌▌");

        System.out.println("\nHere is the computer scorecard: ");
        scoringComputer.printScoreCard();
        comp.computerRound();

        // Checks if yahtzee has already been rolled for bonuss
        if (scoringComputer.bonusCheck() && rolls.isYahtzee()){
          System.out.println("The computer scored another yahtzee. 100 has been added to its total score.");
          compBonuses++;
        }

        System.out.println("\nHere is the computer scorecard: ");
        scoringComputer.printScoreCard();
        
        // Stores the value of the category's position in the array of categories the computer will select 
        int posOfCategory = 0;
        
        // If bonus is active
        if (scoringComputer.bonusCheck() && rolls.isYahtzee() && !scoringComputer.isUpperFull()){
          // Computer picks a category in the upper section
          ArrayList <Integer> leftOverPositionsUpper = scoringComputer.selectableCategoriesUpper();
          int leftOverPositionsSizeUpper = scoringComputer.selectableCategoriesUpper().size();
          int randNumOfArraySize = rand.nextInt(leftOverPositionsSizeUpper);
          posOfCategory = leftOverPositionsUpper.get(randNumOfArraySize);
          
        // No bonus
        } else {
          // Determines a category of the remaining ones for the computer to select
          // Creates an arrayList of the avaible categorie for the computer to select in order
          ArrayList <Integer> leftOverPositions = scoringComputer.selectableCategories();
          int leftOverPositionsSize = scoringComputer.selectableCategories().size();
          int randNumOfArraySize = rand.nextInt(leftOverPositionsSize);
          posOfCategory = leftOverPositions.get(randNumOfArraySize);
        }

        // Updates the computer's scoring card and calculates the computer's score for that round
        scoringComputer.assignScoreCategoryValues(Integer.toString(calculateScore(scoringComputer.categories[posOfCategory], scoringComputer.determineSection( scoringComputer.categories[posOfCategory]), rolls, scoringComputer)), scoringComputer.categories[posOfCategory]);

        System.out.println("\nThe computer selected the category " + scoringComputer.categories[posOfCategory] +".");
      
        System.out.println("\nHere is the computer scorecard: ");
        scoringComputer.printScoreCard();
      }
      System.out.println("\nThe game has ended. The computer's final score is: " + scoringComputer.totalScore(compBonuses));

      // Adds the computer's score to an array storing all the computer's game scores
      storeScores[k] = scoringComputer.totalScore(compBonuses);
    }

    // Sums the scores of all games the computer played
    int allScoresSum = 0;
    for (int i = 0; i < storeScores.length; i++){
      allScoresSum += storeScores[i];
    }
    // Determines the average of all the games the computer played
    double avgScore = (double)allScoresSum/(double)totalGames;

    // Insertion sort via class time where we learned it.
    // Start at index 1 since index 0 is already sorted.
    for (int i = 1; i < storeScores.length; i++) {
      for (int j = i - 1; j >= 0; j--) {
        if (storeScores[j+1] < storeScores[j]) {
          int temp = storeScores[j+1];
          storeScores[j+1] = storeScores[j];
          storeScores[j] = temp;
        }
        else
          break;
      }
    }

    // Finds the median of all scores of the games played by the computer
    double medianScore = 0;
    if (totalGames % 2 == 0 ){
      if (totalGames == 2){
        medianScore = (storeScores[0] + storeScores[1])/2;
      } else {
        medianScore = (storeScores[(totalGames/2)-1] + storeScores[(totalGames/2)+1])/2;
      }
    } else {
      medianScore = storeScores[Math.round(storeScores.length/2)];
    }

    // Prints average and median
    System.out.printf("\nThe average score of these %d game(s) is: %.2f.", totalGames, avgScore);
    System.out.printf("\nThe median score of these %d game(s) is: %.2f.\n",totalGames, medianScore);
  }

  public void printRules(){
  // Pre: nothing.
  // Post: returns nothing and prints Yahtzee game rules
    
    System.out.println("\nThe object of Yahtzee is for each player to roll 5 dice for scoring combinations and get the highest total score. Each player takes a scorecard where their scores for each turn are stored.\n\nOn each turn, a player can roll the dice up to 3 times to get the highest-scoring combination for 1 of 13 categories. However, a player can stop after their first roll or second roll.\n\nOn the first roll, all 5 dice are rolled and the player can set aside any \"keeper\" die or the player can stop rolling and choose to score for a category. On the second roll, the player can choose to reroll any or all dice they want, even dice they've previously kept. The player can choose to not score after their second roll and set aside \"keeper\" die to roll for a third turn. On the player's third and final roll, they can choose to reroll any or all dice, even \"keepers.\" However, after this turn, the player must fill a scoring category with a score or a zero. Once a scoring category is filled, the player's turn ends.\n\nAfter finishing a turn, a player must place a score or a zero in one of the 13 categories on the scorecard.\n\nThe game ends when all players have filled their 13 scoring categories. In the end, scores are totaled to determine a winner.");
  }

  public int calculateScore(String category, String section, Dice rolls, ScoreCard scoring){
  // Pre: receives a category, a category's section (Upper or Lower), the dice rolls, and a scoring card.
  // Post: returns the score value of the turn depending on its selected category.
  
  int score = 0;

  // Scores any upper section category
  if(section.equalsIgnoreCase("Upper")){
   
    for (int i = 0; i < 6; i++){
      if(scoring.categories[i].equalsIgnoreCase(category)){
        for (int j = 0; j < 5; j++){
          if (rolls.fiveDice[j] == i+1)
            score += rolls.fiveDice[j];
        }
      }
    }
  // Scores any 3 of a kind category
  } else if (category.equalsIgnoreCase("3K")){
    boolean validMove = false;

    // Scores differently if a 50 Yahtzee has already been rolled and another one is rolled on the current turn
    if (scoring.bonusCheck() && rolls.isYahtzee()){
      validMove = true;
    // Checks to see if atleast 3 die are the same in a full set of die
    } else {
      for (int i = 0; i < 5; i++){
        int sameDieCounter = 0;
        for (int j = 0; j < 5; j++){
          if (rolls.fiveDice[j] == rolls.fiveDice[i]) 
            sameDieCounter++;
        } 
        if (sameDieCounter == 3)
          validMove = true;
      }
    }

    if (validMove)
      score = rolls.sumDice();
    else 
      score = 0;
    
  // Scores any 4 of a kind category
  } else if (category.equalsIgnoreCase("4K")){
    boolean validMove = false;
    
    // Scores differently if a 50 Yahtzee has already been rolled and another one is rolled on the current turn
    if (scoring.bonusCheck() && rolls.isYahtzee()){
      validMove = true;
    // Checks to see if atleast 4 die are the same in a full set of die
    } else {
      for (int i = 0; i < 5; i++){
        int sameDieCounter = 0;
          for (int j = 0; j < 5; j++){
            if (rolls.fiveDice[j] == rolls.fiveDice[i]) 
              sameDieCounter++;
          } 
        if (sameDieCounter == 4)
          validMove = true;
      }
    }
    
    if (validMove)
      score = rolls.sumDice();
    else 
      score = 0;
    
  // Scores any full house category
  } else if (category.equalsIgnoreCase("FH")){
    boolean valid3Pair = false;
    boolean valid2Pair = false;

    // Scores differently if a 50 Yahtzee has already been rolled and another one is rolled on the current turn
    if (scoring.bonusCheck() && rolls.isYahtzee()){
      valid3Pair = true;
      valid2Pair = true;
    } else {
      // Checks to see if atleast 3 die are the same in a full set of die
      for (int i = 0; i < 5; i++){
        int sameDieCounter = 0;
        for (int j = 0; j < 5; j++){
          if (rolls.fiveDice[j] == rolls.fiveDice[i]) 
            sameDieCounter++;
        } 
        if (sameDieCounter == 3)
          valid3Pair = true;
      }
    
      // Checks to see if atleast 2 die are the same in a full set of die
      for (int i = 0; i < 5; i++){
        int sameDieCounter = 0;
        for (int j = 0; j < 5; j++){
          if (rolls.fiveDice[j] == rolls.fiveDice[i]) 
            sameDieCounter++;
        } 
        if (sameDieCounter == 2)
          valid2Pair = true;
      }
    }
      // If both a pair and a 3 of kind exist then FH is a valid move that the user can score points on instead of receiving a 0
      if (valid3Pair && valid2Pair)
        score = 25;
      else 
        score = 0;
    
  // Scores any small straight category
  } else if (category.equalsIgnoreCase("SS")){ 
    boolean validMove = false;

    // Scores differently if a 50 Yahtzee has already been rolled and another one is rolled on the current turn
    if (scoring.bonusCheck() && rolls.isYahtzee()){
        validMove = true;
    } else {  
      // Uses a linked hash to create an array of dice without any duplicates. Thus, it is easier to check if the small straight category is a valid move for the rolled die.
      // I did some research on this, so here's the link: https://www.geeksforgeeks.org/linkedhashset-in-java-with-examples/#:~:text=The%20LinkedHashSet%20is%20an%20ordered,maintained%20this%20class%20is%20used.
      LinkedHashSet<Integer> hash = new LinkedHashSet<Integer>();

      for (int i = 0; i < rolls.fiveDice.length; i++) {
        hash.add(rolls.fiveDice[i]);
      }

      // Creates integer array of dice without any duplicates
      Integer[] noDuplicates = hash.toArray(new Integer[hash.size()]);

      // Can't be a small straight if the no duplicate array isn't 4 elements long
      if (noDuplicates.length != 4 ) {
        validMove = false;
      // Checks to see if the elements of the array are a small straight by comparing each element to the next element to see if its one greater
      } else if (noDuplicates[0]+1 == noDuplicates[1] && noDuplicates[1]+1 == noDuplicates[2] && noDuplicates[2]+1 == noDuplicates[3]) {
        validMove = true;
      }
    }

    if (validMove)
      score = 30;
    else 
      score = 0;
    
  // Scores any large straight category
  } else if (category.equalsIgnoreCase("LS")){ 
    boolean validMove = false;

    // Scores differently if a 50 Yahtzee has already been rolled and another one is rolled on the current turn
    if (scoring.bonusCheck() && rolls.isYahtzee()){
      validMove = true;
    }
    // Checks to see if the elements of the array are a large straight by comparing each element to the next element to see if its one greater
    if(rolls.fiveDice[0] + 1 == rolls.fiveDice[1] && rolls.fiveDice[1] + 1 == rolls.fiveDice[2] && rolls.fiveDice [2] + 1 == rolls.fiveDice[3] && rolls.fiveDice[3] + 1 == rolls.fiveDice[4])
      validMove = true;

    if (validMove)
      score = 40;
    else 
      score = 0;

  // Scores any yahtzee straight category
  } else if (category.equalsIgnoreCase("Y")){
    boolean validMove = false;

    // Determiness if all 5 dice are the same number
    for (int i = 0; i < 5; i++){
      int sameDieCounter = 0;
      for (int j = 0; j < 5; j++){
        if (rolls.fiveDice[j] == rolls.fiveDice[i]) 
          sameDieCounter++;
      } 
      if (sameDieCounter == 5)
        validMove = true;
    }

    if (validMove)
      score = 50;
    else 
      score = 0;
    
  // Scores any chance straight category
  } else if (category.equalsIgnoreCase("C")){
    score = rolls.sumDice();
  }
  return score;
  }
}