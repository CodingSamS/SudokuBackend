package sudoku.utils;

import sudoku.model.SudokuNotSolvedException;
import sudoku.model.Sudoku;
import sudoku.model.SudokuBlueprint;

import java.util.concurrent.ConcurrentHashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

public final class sudokuFormatConverter {
  private sudokuFormatConverter() {}
  
  public static Sudoku convertSudokuBlueprintToSudoku(SudokuBlueprint sudokuBlueprint) {
    ConcurrentHashMap<IntegerTuple, LinkedList<Integer>> sudokuHashmap = new ConcurrentHashMap<IntegerTuple, LinkedList<Integer>>();
    
    for(int r=0; r<9; r++) {
      for(int c=0; c<9; c++) {
        int value = sudokuBlueprint.getSudokuField().get(r).get(c);
        LinkedList<Integer> newList = new LinkedList<Integer>();
        if(value == 0) {
          for(int i=1; i<10; i++) {
            newList.add(i);
          }
        }
        else {
          newList.add(value);
        }
        sudokuHashmap.put(new IntegerTuple(r, c), newList);
      }
    }
    
    Sudoku sudoku = new Sudoku(sudokuHashmap);
    
    return sudoku;
  }
  
  public static SudokuBlueprint convertSudokuToSudokuBlueprint (Sudoku sudoku) throws SudokuNotSolvedException {
    List<List<Integer>> blueprintList = new ArrayList<List<Integer>>();
    ConcurrentHashMap<IntegerTuple, LinkedList<Integer>> sudokuHashmap = sudoku.getSudokuField();
    
    for(int r=0; r<9; r++) {
      List<Integer> tempList = new ArrayList<Integer>(); 
      for(int c=0; c<9; c++) {
        if(sudokuHashmap.get(new IntegerTuple(r, c)).size() != 1) {
          throw new SudokuNotSolvedException("The Sudoku is not solved correctly - size of field " + String.valueOf(r) + 
              "|" + String.valueOf(c) + " is " + String.valueOf(sudokuHashmap.get(new IntegerTuple(r, c)).size()));
        }
        tempList.add(sudokuHashmap.get(new IntegerTuple(r, c)).getFirst());
      }
      blueprintList.add(tempList);
    }    
    
    return new SudokuBlueprint(blueprintList);
  }
}
