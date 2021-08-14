package sudoku.model;

import java.util.List;

public class SudokuBlueprint {
  private List<List<Integer>> sudokuField;
  private boolean success;
  
  public SudokuBlueprint(List<List<Integer>> sudokuField) {
    this.sudokuField = sudokuField;
    this.success = false;
  }
    
  public List<List<Integer>> getSudokuField() {
    return this.sudokuField;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public void setSudokuField(List<List<Integer>> sudokuField) {
    this.sudokuField = sudokuField;
  }
  
}
