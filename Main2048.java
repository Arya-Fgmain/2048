import java.util.Scanner; // Importing Scanner class from java.util package
public class Main2048 {
  public static void main(String[] args){
    // Provided code. Do not modify
    GameSystem gs;
    Displayer disp = new Displayer();
    // My code
    Scanner input = new Scanner(System.in); // Taking input from the keyboard
    boolean exit = false; // Sentinel Value
    String userMove = ""; // dummy value, used to store the user's chosen move
    int userOption; // to store the user's choice of game
    int[] customSettings = new int[4]; // to store custom settings (if user chooses)
    Block[][] grid; // To store the grid, so that it can be passed to Displayer.PrintGrid()
    Player currP; // To store the current Player
    int currId; // and his/her id
    int prevScore = 0; // dummy value, will be used to store the user's previous score
    
    // Asking user for their choice of game
    System.out.print("Which game to play?\n1. Default Game (2048 winning value and 4 x 4 grid)\n2. Custom Game\nYour Option: ");
    userOption = input.nextInt(); // Storing user's choice
    if (userOption == 2) { // If they choose a custom game, store the specified:
      System.out.print("Winning Block: "); 
      customSettings[0] = input.nextInt(); // Winning block value,
      System.out.print("Winning Val: ");
      customSettings[1] = input.nextInt(); // Winning User Score Value,
      System.out.print("Grid Height: ");
      customSettings[2] = input.nextInt(); // Height of Grid,
      System.out.print("Grid Width : ");
      customSettings[3] = input.nextInt(); // And Width of Grid
      gs = new GameSystem(customSettings[0], customSettings[1], customSettings[2], customSettings[3]); // Specifications are passed from customSettings to GameSystem constructor in order
    } else {
      gs = new GameSystem(); // Default GameSystem constructor is called for default game
    }
    System.out.println("DIRECTION GUIDE:\n  a - left\n  w - up\n  d - right\n  s - down\n"); // Direction guide AWDS Left-Up-Right-Down for the user
    grid = gs.GetGrid(); // Storing the grid in the grid Block reference variable
    gs.SetCurrPlayer(0); // Setting the current player to 0 (initially)
    while (!exit) { // Looping the game
      currP = gs.GetCurrPlayer(); // Initializing the currP variable using the gs.GetCurrPlayer() Method
      currId = currP.GetId(); // Using the Player.GetId() Method to store the current player's id
      prevScore = currP.GetScore(); // Using the Player.GetScore() Method to store the current player's score (might possibly change)
      gs.RandBlock(); // Creating a random block every time
      disp.PrintGrid(grid); // and printing the grid
      disp.PrintScores(gs.GetPlayer(0), gs.GetPlayer(1)); // showing the players' scores
      while (true) {
        System.out.print("Player " + currId + " turn: "); // Showing  which player's turn it is
        userMove = "" + input.next().charAt(0); // Storing their chosen direction
        // Constant Global variables from GameSystem.java are used
        if (!userMove.equals(GameSystem.MOVE_UP) && !userMove.equals(GameSystem.MOVE_LF) && !userMove.equals(GameSystem.MOVE_DW) && !userMove.equals(GameSystem.MOVE_RG)) {
          System.out.println("WARNING! Invalid direction"); // Warn them if they don't choose a valid direction
        } else {
          break; // Break out of this loop and continue the outer loop if their direction is valid
        }
      }
      if (userMove.equals(GameSystem.MOVE_UP)) { 
        currP.SetScore( prevScore + gs.Move(GameSystem.UP) ); // Calling the GameSystem.Move() Function according to the chosen direction
      } else if (userMove.equals(GameSystem.MOVE_LF)) { 
        currP.SetScore( prevScore + gs.Move(GameSystem.LEFT) ); // Consant Global variables from GameSystem.java are used
      } else if (userMove.equals(GameSystem.MOVE_DW)) { 
        currP.SetScore( prevScore + gs.Move(GameSystem.DOWN) );
      } else if (userMove.equals(GameSystem.MOVE_RG)) { 
        currP.SetScore( prevScore + gs.Move(GameSystem.RIGHT) ); 
      }
      System.out.println("Player " + currId + " got " + (currP.GetScore() - prevScore) + " in this round.\n");
      if (gs.IsGridFull() && prevScore == currP.GetScore()) { // Checking if grid is full
        disp.PrintGrid(grid); // Showing the full grid and exclaiming the loser
        System.out.println("The grid is full! Player " + currId + " lose.");
        exit = true; // Terminating the game loop
      } else if (gs.CheckWinner()) { // Checking if the GameSystem.winningBlk or GameSystem.winningVal's values are reached
        disp.PrintGrid(grid); // Printing the grid
        if (currP.GetScore() >= customSettings[1]) {
          System.out.println("The winning points (" + customSettings[1] + ") have been reached! Player " + currId + " wins."); // Display if a player has reached the winning score
        } else {
          System.out.println("The winning block (" + customSettings[0] + ") has been reached! Player " + currId + " wins."); // Display if a winning block has been reached
        }
        break; // Terminating the Game Loop
      }
      gs.SwitchPlayer(); // Switching to the next Player
    }
    
  }
}