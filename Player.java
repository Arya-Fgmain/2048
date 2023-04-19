/* To keep track of a player's information */
public class Player {
  private int playerId;      // The player's ID
  private int score;         // The player's score
  
  /* Constructing a Player object with an assigned 
   * player ID
   * @param id - The ID given to the player */
  public Player( int id ) {
    this.playerId = id; // Initializing Player Objects using their id
  }
  
  /* Get the player's value 
   * @return - The ID that belongs to this 
   *           Player object */
  public int GetId() {
    return this.playerId; // Returning the Player Object's ID
  }
  
  /* Set the player's score 
   * @param score - The new score that the player get */
  public void SetScore(int score) {
    this.score = score; // Setting the Player Object's Score
  }
  
  /* Get the player's score
   * @return - Get the player's current score */
  public int GetScore() {
    return this.score; // Returning the Player Object's Score
  }
}