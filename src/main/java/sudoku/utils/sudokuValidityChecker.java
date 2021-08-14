package sudoku.utils;

import sudoku.model.SudokuBlueprint;

public final class sudokuValidityChecker {
  private sudokuValidityChecker() {}
  
  public static boolean isValid(SudokuBlueprint sudokuBlueprint) {
    if(sudokuBlueprint.getSudokuField().size() != 9) {
      return false;
    }
    for(int r=0; r<9; r++) {
      if(sudokuBlueprint.getSudokuField().get(r).size() != 9) {
        return false;
      }
      for(int c=0; c<9; c++) {
        if(sudokuBlueprint.getSudokuField().get(r).get(c) < 0 || 9 < sudokuBlueprint.getSudokuField().get(r).get(c)) {
          return false;
        }
      }
    }
    return true;
  }
}
