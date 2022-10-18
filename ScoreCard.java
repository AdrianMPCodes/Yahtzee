import java.util.ArrayList;

public class ScoreCard{
  // Solely for printing
  String scoringOptions[] = {"ones", "twos", "threes", "fours", "fives", "sixes", "3 of a kind", "4 of a kind", "Full House", "Small Straight", "Large Straight", "Yahtzee", "Chance"};
  // For using in methods beyond printing
  String categories[] = {"1", "2", "3", "4", "5", "6", "3K", "4K", "FH", "SS", "LS", "Y", "C"};

  String scores[] = new String [categories.length];

  // ScoreCard constructor
  public ScoreCard(){
    for (int i = 0; i < scores.length; i++)
      scores[i] = "x";
  }

  public void assignScoreCategoryValues(String scoreValue, String scoringCategory){
  // Pre: receives a score value for a round and a scoring category.
  // Post: returns nothing. Assigns the score value to the selected scoring category.

    for (int i = 0; i < scores.length; i++){
      if (scoringCategory.equalsIgnoreCase(categories[i])){
        scores[i] = scoreValue;
      }
    }
  }

  public boolean validSelectedCategory(String selectedCategory){
  // Pre: receives a category to score.
  // Post: returns true or false depending on if the selected category has already been scored.

    for (int i = 0; i < categories.length; i++){
      if(categories[i].equalsIgnoreCase(selectedCategory)){
        if (scores[i].equalsIgnoreCase("x"))
          return true;
      }
    }
    return false;
  }

  public String determineSection(String category){
  // Pre: receives scoring category.
  // Post: determines whether the scoring category is in the upper of lower section.

    String section = "";
    for (int i = 0; i < categories.length; i++){
      if (categories[i].equalsIgnoreCase(category)){
        if (i < 6)
          section = "Upper";  
        else
          section = "Lower";  
      }
    }
    return section;
  }

  public ArrayList <Integer> selectableCategories(){
  // Pre: receives nothing.
  // Post: returns an arrayList (b/c size changes) of ints correlating to avaible positions of categories in the scorecard.
    ArrayList <Integer> selectablePositions = new ArrayList<Integer>();
    
    for (int i = 0; i < categories.length; i++){
      if(scores[i].equalsIgnoreCase("x"))
        selectablePositions.add(i);    
    }
    return selectablePositions;
  }

  public ArrayList <Integer> selectableCategoriesUpper(){
  // Pre: receives nothing.
  // Post: returns an arrayList (b/c size changes) of ints correlating to avaible positions of categories in the scorecard for the upper section.
    ArrayList <Integer> selectablePositions = new ArrayList<Integer>();

    for (int i = 0; i < 6; i++){
      if(scores[i].equalsIgnoreCase("x"))
        selectablePositions.add(i);    
    }
    return selectablePositions;
  }

  public boolean bonusCheck(){
  // Pre: receives nothing.
  // Post: returns true if user is eligible for a bonus and false otherwise.

    if (scores[11].equalsIgnoreCase("50"))
        return true;
    return false;
  }

  public boolean isUpperFull(){
  // Pre: receives nothing
  // Post: returns false if the upper section has a playable category and true otherwise

    for (int i = 0; i < 6; i++){
      if (scores[i].equalsIgnoreCase("x"))
        return false;
    }
    return true;    
  }

  public int totalScore(int totalBonuses){
  // Pre: receives the number of bonuses scored in one game (user or computer)
  // Post: returns the player's score by summing their scorecard and adding 100 points per bonus
    int finalScore = 0;

    for (int i = 0; i < scores.length; i++)
      finalScore += Integer.valueOf(scores[i]);

    for (int i = 0; i < totalBonuses; i++)
      finalScore += 100;

    return finalScore;
  }

  public void printScoreCard(){
  // Pre: receives nothing.
  // Post: returns nothing. Prints the scorecard.

    System.out.println("");
    for (int i = 0; i < scores.length; i++)
      System.out.printf("%-20s%8s\n", scoringOptions[i], scores[i]);
  }
}