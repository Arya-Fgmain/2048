/* A class that handles all the printing of
 * the game. */
public class Displayer {
  public Displayer() {
  }
  
  /* Prints the grid and its content on the screen
   * @param grid - The Block object 2D array. */
  public void PrintGrid( Block[][] grid ) {
    String line = "-"; // Single line, will be changed later
    String header = line;
    for (int i = 0; i < grid[0].length; i++) {
      for (int j = 0; j < 7; j++) { // Every block has 7 lines, so 7 lines are added to the header variable
        header += line;
      }
    }
    System.out.println(header); // Header is printed
    for (int i = 0; i < grid.length; i++) {
      System.out.print("|"); // First | is printed manually
      for (int j = 0; j < grid[i].length; j++) { // Looping through the grid
        if (grid[i][j] != null) { // Only use the following specifications if the block is not null
          if (grid[i][j].GetIsNew()) { // If the block is new (random block)
            System.out.printf("%4s} |", "{" + grid[i][j].GetVal()); // use {} to surround it
            grid[i][j].SetIsNew(false); // Change its isNew attribute to false to prevent it from being printed with curly brackets again
          } else {
            System.out.printf("%5d |", grid[i][j].GetVal()); // Else, just print with 5 spaces
          }
        } else {
          System.out.printf("%5s |", " "); // If block is null, do not print any values
        }
        
      }
      System.out.println("\n" + header); // Print header at the end of every row
    }
  }
  
  /* Prints the scores of the two players
   * @param p1 - First player's Player object
   * @param p2 - Second player's Player object */
  public void PrintScores( Player p1, Player p2 ) { // Sysem.out.printf is used to print the player's scores, printing has been reduced to one line. 
    // Player.GetScore() Method is used to get the Payers' scores
    System.out.printf("=============== SCORES ================\n|PLAYER 0:%6d  |  PLAYER 1:%6d  |\n=======================================\n", p1.GetScore(), p2.GetScore());
  }
}