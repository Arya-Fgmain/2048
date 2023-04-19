/* Developed By: Kevin Wong
 * Revised Date: Oct 24, 2021
 * Version     : 1.1 */

/* Represents each block in the grid */
public class Block {
  private int val;        // The value of the block
  private boolean isNew;  // If the block is newly genereated or old
  
  /* Constructing a Block object with a specified
   * value.
   * @param val - The value of the block */
  public Block(int val) {
    this.val = val; // constructing a block object with the given value
  }
  
  /* Get the value of the block
   * @return - The value of the block it contains */
  public int GetVal() {
    return this.val; // returning the value of the Block Object Instance
  }
  
  /* Set the value of the block
   * @param val - The new value that the block will
   *              use. */
  public void SetVal(int val) {
    this.val = val; // Modifying the value of the Block Object
  }
  
  /* Get if the block is newly created or not
   * @return - True if the block is newly generated
   *           False otherwise. */   
  public boolean GetIsNew() {
    return this.isNew; // Checking if the Block Object has been newly generated
  }
  
  /* Set if the block is newly created or not
   * @param val - The value you want the block to be 
   *              Set to true to indicate that the block is new
   *              Set to false to indicate that the block is old */   
  public void SetIsNew(boolean val){
    this.isNew = val; // Setting the Block's status (new - true / old - false)
  }
}