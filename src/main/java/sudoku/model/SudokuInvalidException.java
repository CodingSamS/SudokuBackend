package sudoku.model;

public class SudokuInvalidException extends Exception{
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public SudokuInvalidException(String message) {
    super(message);
  }
}
