/* Developed By: Kevin Wong
 * Revised Date: Oct 24, 2021
 * Version     : 1.1 */

/* Manages the game and all of its attributes */
public class GameSystem {
  public final static int DEFAULT_GRID_HEIGHT =    4;
  public final static int DEFAULT_GRID_WIDTH  =    4;
  public final static int DEFAULT_WINNING_VAL = 2048;
  public final static int DEFAULT_WINNING_BLK = 2048;
  
  private Block[][] grid;                              // The grid of the game
  private Player[] allPlayer;                          // To keep track of the two players
  
  private int winningBlk;                              // Block that must be reached to win the game.
  private int winningVal;                              // Value that must be reached to win the game.
  private Player currPlayer;                           // Keeps track of the current player
  
  // Final Static Variables
  // String Direction Identifiers (AWDS)
  public final static String MOVE_LF = "a";
  public final static String MOVE_UP = "w";
  public final static String MOVE_RG = "d";
  public final static String MOVE_DW = "s";
  // Integer Direction Identifiers (A-W-D-S | Left-Up-Right-Down) --> For Move() Method
  public final static int    LEFT    = 0;
  public final static int    UP      = 1;
  public final static int    RIGHT   = 2;
  public final static int    DOWN    = 3;
  
  //=================== CONSTRUCTOR =====================//
  /* The constructor to initialize the grid. */
  public GameSystem() {
    this.grid = new Block[DEFAULT_GRID_HEIGHT][DEFAULT_GRID_WIDTH]; // Making grid with default (4x4) dimensions
    this.winningBlk = DEFAULT_WINNING_BLK; // Using default winning values for Blocks
    this.winningVal = DEFAULT_WINNING_VAL; // and user Points
    this.allPlayer = new Player[2]; // Initializing the allPlayer array
    this.allPlayer[0] = new Player(0); // Using the following Player Ids: 0
    this.allPlayer[1] = new Player(1); // and 1
  }
  
  /* The constructor to customize the winning value.
   * @param winningBlk - The block that the player must reach 
   *                     to win the game.
   * @param winningVal - The value that the player must reach
   *                     to win the game. */
  public GameSystem(int winningBlk, int winningVal, int height, int width) {
    this.grid = new Block[height][width]; // Making grid with custom (mxn) dimensions
    this.winningBlk = winningBlk; // Using custom winning values for Blocks
    this.winningVal = winningVal; // and user Points (set by the user)
    this.allPlayer = new Player[2]; // Initializing the allPlayer array using IDS 0 & 1
    this.allPlayer[0] = new Player(0);
    this.allPlayer[1] = new Player(1);
  }
  
  //================== PRIVATE METHODS ====================//
  /* Place your private methods here, so that I can 
   * find them more easily! */
  
  /* Shifts all the blocks to the left.
   * @return    - Returns points earned from making this move. 
   *              Returns 0 if either: no moves possible (grid full) or no combinations were done from this direction */
  private int moveLeft() {
    int prevPoints = 0; // The previous points (before making a move)
    int currPoints = 0; // The points currently earned from moving
    int currVal = 0; // Value of the block to find a combinator for
    int currPos = 0; // Position of the block to find a combinator for
    boolean exit = false; // Sentinel value
    do {
      for (int i = 0; i < this.grid.length; i++) { // Looping through every row
        for (int j = 0; j < this.grid[i].length; j++) { // and looping through every column in every row
          if (this.grid[i][j] != null) { // If the block Reference Variable contains an actual object
            // Then it will be selected and the next loop will try to find matches (in terms of value) for it
            currVal = this.grid[i][j].GetVal(); // Storing this block's value
            currPos = j; // and position in the row
            for (int k = j+1; k < this.grid[i].length; k++) { // Looking at blocks after this one (j+1)
              if (this.grid[i][k] != null) { // If we find a block that is not null, we will check if it matches
                int val = this.grid[i][k].GetVal(); // Storing the (possibly) matching block's value
                if (val == currVal) { // Checkin if their values match
                  currPoints += val; // Adding points earned
                  this.grid[i][currPos] = new Block(val + currVal); // Changing the value of the original block (it has been added)
                  this.grid[i][k] = null; // making the match block a null (it has been added to the poriginal)
                  currVal = this.grid[i][currPos].GetVal(); // changing the current value of the original block (it has been increased)
                } else { // If the block exists but is NOT a match for the current chosen block
                  currVal = val; // search will be done for the matches of the new block, it's value
                  currPos = k; // and position are stored
                }
              }
            }
          }
          // Cleanup mechanism (shifting blocks in the left direction)
          int currI = j; // starting from column j of the row
          while (currI > 0 && j < this.grid[i].length) { // preventing ArrayIndexOutOfBoundsException, this loop moves backwards
            if (this.grid[i][currI-1] == null) { // if the previous block is a null
              currI--; // continue searching for more null blocks
            } else break; // once a block that is not null is reached, stop searching
          }
          if (currI != j) { // only swap if there are empty blocks available
            this.grid[i][currI] = this.grid[i][j]; // Assigning the current block to the latest position possible
            this.grid[i][j] = null; // Emptying the current block's reference variable
          } // example: | y |  | x |, middle block is empty, so it will move | y | x |  |
          
        } 
      }
      if (prevPoints == currPoints) { // The loop will only continue until points can be earned
        exit = true;
      } else {
        prevPoints = currPoints; // Update Previous points for next loop
      } 
    } while (!exit);
    return currPoints; // return the points earned from moving left
  }
  
  /* Shifts all the blocks to the right.
   * @return    - Returns points earned from making this move. 
   *              Returns 0 if either: no moves possible (grid full) or no combinations were done from this direction */
  private int moveRight() {
    int prevPoints = 0; // The previous points (before making a move)
    int currPoints = 0; // The points currently earned from moving
    int currVal = 0; // Value of the block to find a combinator for
    int currPos = 0; // Position of the block to find a combinator for
    boolean exit = false; // Sentinel value
    do { // Move is attempted at least once
      for (int i = this.grid.length - 1; i >= 0; i--) { // Moving from right - left, so indexes are changed accordingly
        for (int j = this.grid[i].length - 1; j >= 0; j--) { // looping from right - left through the columns of every row
          if (grid[i][j] != null) { // Same mechanism as before (refer to moveLeft()), but this time it searches for matches in the reverse direction
            currVal = grid[i][j].GetVal();
            currPos = j;
            for (int k = j-1; k >= 0; k--) { // Search starts from the index of one block before the current block 
              if (grid[i][k] != null) {
                int val = grid[i][k].GetVal();
                if (val == currVal) {
                  currPoints += val;
                  grid[i][currPos] = new Block(val + currVal);
                  grid[i][k] = null;
                  currVal = grid[i][currPos].GetVal();
                } else {
                  currVal = val;
                  currPos = k;
                }
              }
            }
          }
          int currI = j; // Same cleanup mechanism as before except it moves backwards
          while (currI < this.grid[i].length - 1 && j >= 0) { // Preventing ArrayIndexOutOfBoundsException
            if (grid[i][currI+1] == null) { // This time, since the movement is to the right, cleanup is done to the right as well
              currI++; // Looking for more null blocks on the RIGHT of the row
            } else break;
          }
          if (currI != j) { // only s
            grid[i][currI] = grid[i][j];
            grid[i][j] = null;
          }
        } 
      }
      if (prevPoints == currPoints) { // Checking if more points can be earned
        exit = true;
      } else {
        prevPoints = currPoints;
      } 
    } while (!exit);
    return currPoints; // Returning points earned from moving right
  }
  
  /* Shifts all the blocks up.
   * @return    - Returns points earned from making this move. 
   *              Returns 0 if either: no moves possible (grid full) or no combinations were done from this direction */
  private int moveUp() {
    int prevPoints = 0; // The previous points (before making a move)
    int currPoints = 0; // The points currently earned from moving
    int currVal = 0; // Value of the block to find a combinator for
    int currPos = 0; // Position of the block to find a combinator for
    boolean exit = false; // Sentinel value
    do {
      for (int i = 0; i < this.grid[0].length; i++) { // Outer loop goes through the columns now
        for (int j = 0; j < this.grid.length; j++) { // and inner loop goes through the rows
          if (this.grid[j][i] != null) { // Selecting a non-null block to find a match for in other rows
            currVal = this.grid[j][i].GetVal(); // Storing its value
            currPos = j; // and position
            for (int k = j+1; k < this.grid.length; k++) { // Looking from top to bottom for matches (starting from the index of the block in the row below)
              if (this.grid[k][i] != null) { // If a non-null block is found in another row, it will be checked for matching
                int val = this.grid[k][i].GetVal(); // It's value is stored for checking
                if (val == currVal) { // If values match, adding will be performed (same as before)
                  currPoints += val;
                  this.grid[currPos][i] = new Block(val + currVal); // block at original row's value will be added
                  this.grid[k][i] = null; // and this block will be deleted
                  currVal = this.grid[currPos][i].GetVal(); // the new value is assigned to the original block for further searching
                } else {
                  currVal = val; // If this block does not match the original, the search for matches will be done for this block instead, so its value
                  currPos = k; // and position are stored for searching
                }
              }
            }
            int currI = j; // Cleanup is done bottom-top (through rows)
            while (currI > 0 && j < this.grid.length) { // Preventing ArrayIndexOutOfBoundsException
              if (this.grid[currI-1][i] == null) { // If blocks in upper rows are null
                currI--; // continue searching for more null blocks
              } else break; // stop if a non-null block is found
            }
            if (currI != j) { // Only swap if null blocks were found
              this.grid[currI][i] = this.grid[j][i];
              this.grid[j][i] = null;
            }
          }
        } 
      }
      if (prevPoints == currPoints) { // Check if more points can be earned (and loop should not continue)
        exit = true;
      } else { // Else update previous points earned to current points, so as to prepare for the next loop
        prevPoints = currPoints;
      } 
    } while (!exit);
    return currPoints; // Return points earned from moving up
  }
  
  /* Shifts all the blocks down.
   * @return    - Returns points earned from making this move. 
   *              Returns 0 if either: no moves possible (grid full) or no combinations were done from this direction */
  private int moveDown() {
    int prevPoints = 0; // The previous points (before making a move)
    int currPoints = 0; // The points currently earned from moving
    int currVal = 0; // Value of the block to find a combinator for
    int currPos = 0; // Position of the block to find a combinator for
    boolean exit = false; // Sentinel value
    do {
      for (int i = this.grid[0].length - 1; i >= 0; i--) { // Looping backwards through the rows (right-left)
        for (int j = this.grid.length - 1; j >= 0; j--) { // Looping from bottom-top
          if (this.grid[j][i] != null) { // Selecting a block to find a match for
            currVal = this.grid[j][i].GetVal();
            currPos = j;
            for (int k = j-1; k >= 0; k--) { // Looping through the rows above for a match
              if (this.grid[k][i] != null) { // If a non-null block is found
                int val = this.grid[k][i].GetVal(); // its value is stored
                if (val == currVal) { // If values match, the matching is performed and the found block is made into null
                  currPoints += val;
                  this.grid[currPos][i] = new Block(val + currVal);
                  this.grid[k][i] = null;
                  currVal = this.grid[currPos][i].GetVal();
                } else { // If these two blocks did not match, then the new block will become the subject of the search
                  currVal = val;
                  currPos = k;
                }
              }
            }
            int currI = j; // Cleanup is done from top-bottom
            while (currI < this.grid.length - 1 && j >= 0) { // Searching for null blocks after the current row
              if (this.grid[currI+1][i] == null) {
                currI++; // If a null block is found, currI is incremented to continue the search
              } else break; // If the next block is not null, stop the search and perform the shifting
            }
            if (currI != j) { // Only shift if empty blocks were found
              this.grid[currI][i] = this.grid[j][i];
              this.grid[j][i] = null;
            }
          }
        } 
      }
      if (prevPoints == currPoints) { // Checking if the loop should terminate (no points have been earned)
        exit = true;
      } else {
        prevPoints = currPoints; // Updating previous points and continuing the loop
      } 
    } while (!exit);
    return currPoints; // returning the points earned from moving down
  }
  
  //================== PUBLIC METHODS ====================//
  /* Get the whole grid of the game
   * @return - The Block object 2D array */
  public Block[][] GetGrid() {
    return this.grid; // returning the grid
  }
  
  /* Randomize a block and its value (between 2 or 4) and place it 
   * in the grid. Note: Must not randomize a position that already 
   * has a block. */
  public void RandBlock() {
    boolean isFound = false; // Sentinel value for loop
    int[] possibleValues = {2, 4}; // Array to hold possible values for the random block
    while (!isFound) {
      int height = (int)(Math.random() * this.grid.length); // Searching random values for the height
      int width  = (int)(Math.random() * this.grid[0].length); // and width of the grid array
      if (this.grid[height][width] == null) { // If this Block Object Instance is a null, then the random Block can be put here
        isFound = true; // Loop will terminate after random block has been created
        int temp = possibleValues[(int)(Math.random() * 2)]; // Randomizing the index (0/1) of the possibleValues array ( to make the random block value either 2 or 4)
        this.grid[height][width] = new Block(temp); // New Block is instantiated and assigned a value
        this.grid[height][width].SetIsNew(true); // the Block.isNew attribute is given value 'true' so that it will be printed with frames {} in Displayer.PrintGrid()
      }
    }
  }
  
  /* Move all the blocks to the specified direction. If the direction is
   * invalid, it will not move any blocks.
   * @param dir - The direction that the user wants to move to.
   *              Refer to GameSystem global variables for direction
   *              value.
   * @return    - Returns points earned from this round of move. 
   *              Returns -1 if the direction is invalid, and blocks
   *              will not be moved. */
  public int Move(int dir) {
    int res = -1; // Returning -1 if the direction is invalid
    if (dir == LEFT) { // Using Constant Global Integer Direction Identifiers to determine which move method to call
      res = this.moveLeft(); // If the move is valid, res is assigned the points earned from making that move
    } else if (dir == RIGHT) {
      res = this.moveRight();
    } else if (dir == UP) {
      res = this.moveUp();
    } else if (dir == DOWN) {
      res = this.moveDown();
    } // Grid won't move if direction is invalid
    return res; // returning either the poinsts earned or -1
  }
  
  /* Set who will be the player turn
   * @param playerId - The ID of the player */
  public void SetCurrPlayer(int playerId) {
    for (int i = 0; i < allPlayer.length; i++) { // Looping through the allPlayer array
      if (allPlayer[i].GetId() == playerId) { // Finding the Player Object with the designated ID
        currPlayer = allPlayer[i]; // and making him/her the current Player
      }
    }
  }
  
  /* Get the Player who is currently his/her turn
   * @return - The Player object of the player who is currently
   *           his/her turn */
  public Player GetCurrPlayer() {
    return this.currPlayer; // Returning the Player Object instance of the current player
  }
  
  /* Get the Player with the indicated player ID
   * @param playerId - The ID of the player 
   * @return         - The Player object of the player */
  public Player GetPlayer(int playerId ) {
    Player p = new Player(0); // Dummy player object
    for (int i = 0; i < allPlayer.length; i++) { // Looping through the allPlayer array to find the Player with the given Player ID
      if (allPlayer[i].GetId() == playerId) {
        p = allPlayer[i]; // Re-assigning the value of p after the player is found
      }
    }
    return p; // returning the Player
  }
  
  /* Switch player's turn. If it is player 0 turn, then it will
   * switch to player 1, and vice versa */
  public void SwitchPlayer() {
    if (currPlayer == allPlayer[0]) { // If the current Player is player 0
      currPlayer = allPlayer[1]; // it will become player 1
    } else { // else (if its player 1)
      currPlayer = allPlayer[0];  // it will become player 0
    }
  }
  
  /* Check if the player wins or not by reaching the 
   * specified winning value.
   * @return - True if:
   *             - blocks is greater than or equals to winning block OR
   *             - points is greater than or equals to winning value
   *           False otherwise. */
  public boolean CheckWinner() {
    for (int i = 0; i < allPlayer.length; i++) { // Checking allPlayer to see a player's score is more than or equal to winningVal
      if (allPlayer[i].GetScore() >= winningVal) {
        return true; // Returning true if yes
      }
    }
    for (int i = 0; i < this.grid.length; i++) { // Looping through every row and column of the grid to find a block with winningBlk's value or more
      for (int j = 0; j < this.grid[i].length; j++) {
        if (this.grid[i][j] != null && this.grid[i][j].GetVal() >= winningBlk) { // if found, 
          return true; // return true
        }
      }
    }
    return false; // Return false if neither condition has been satisfied
  }
  
  /* Check if the grid is full or not
   * @return - True if the grid has no more empty blocks
   *           False otherwise. */
  public boolean IsGridFull() {
    for (int i = 0; i < this.grid.length; i++) { // Loop through the grid
      for (int j = 0; j < this.grid[i].length; j++) {
        if (this.grid[i][j] == null) { // If a null block is found
          return false; // return false (grid is not full)
        }
      }
    }
    return true; // Else, return true as the grid is full
  }
}