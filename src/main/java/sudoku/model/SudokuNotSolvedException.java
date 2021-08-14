package sudoku.model;

public class SudokuNotSolvedException extends Exception{
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public SudokuNotSolvedException(String message) {
    super(message);
  }
  
}
