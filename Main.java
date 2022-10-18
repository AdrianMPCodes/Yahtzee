import java.util.Scanner;

class Main {
  public static void main(String[] args) {
  Scanner sc = new Scanner(System.in);

  String name;
  String gameType;

  System.out.println("What is your name?");
  name = sc.nextLine();

  do{
    // Gets input for game format from user
    System.out.println("\nHello, " + name + ". Choose an option below:");

    System.out.println("O: play a ONE PERSON game\nOD: play a ONE PERSON game with DEBUGGING\nT: play a TWO PERSON game against the computer\nTD: play a TWO PERSON game against the computer with DEBUGGING\nCS: watch the COMPUTER play SOLITAIRE\nCSD: watch the COMPUTER play SOLITAIRE with DEBUGGING\nR: print Rules\nQ: quit");

    System.out.println("\nOption: ");
    gameType = sc.next();
    
    // Creates game object
    Game fullGame = new Game(gameType, name);

    // Runs a game mode
    fullGame.runGameMode(gameType);

    } while (!gameType.equalsIgnoreCase("Q"));
  }
}