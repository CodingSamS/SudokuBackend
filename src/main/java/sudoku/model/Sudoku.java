package sudoku.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

import sudoku.utils.IntegerTuple;

public class Sudoku {
  
  private ConcurrentHashMap<IntegerTuple, LinkedList<Integer>> sudokuField;
  private ArrayList<ArrayList<LinkedList<Integer>>> sudokuRows;
  private ArrayList<ArrayList<LinkedList<Integer>>> sudokuColumns;
  private ArrayList<ArrayList<LinkedList<Integer>>> sudokuBoxes;
  
  public Sudoku(ConcurrentHashMap<IntegerTuple, LinkedList<Integer>> sudokuField) {
    this.sudokuField = sudokuField;
    
    this.sudokuRows = new ArrayList<ArrayList<LinkedList<Integer>>>();
    for(int r=0; r<9; r++) {
      ArrayList<LinkedList<Integer>> tempList = new ArrayList<LinkedList<Integer>>();
      for(int c=0; c<9; c++) {
        tempList.add(this.sudokuField.get(new IntegerTuple(r, c)));
      }
      this.sudokuRows.add(tempList);
    }
    
    this.sudokuColumns = new ArrayList<ArrayList<LinkedList<Integer>>>();
    for(int c=0; c<9; c++) {
      ArrayList<LinkedList<Integer>> tempList = new ArrayList<LinkedList<Integer>>();
      for(int r=0; r<9; r++) {
        tempList.add(this.sudokuField.get(new IntegerTuple(r, c)));
      }
      this.sudokuColumns.add(tempList);
    }
    
    this.sudokuBoxes = new ArrayList<ArrayList<LinkedList<Integer>>>();
    for(int rowIncrement=0; rowIncrement<9; rowIncrement += 3) {
      for(int columnIncrement=0; columnIncrement<9; columnIncrement +=3) {
        ArrayList<LinkedList<Integer>> tempList = new ArrayList<LinkedList<Integer>>();
        for(int r=0; r<3; r++) {
          for(int c=0; c<3; c++) {
            int row = r + rowIncrement;
            int column = c + columnIncrement;
            tempList.add(this.sudokuField.get(new IntegerTuple(row, column)));
          }
        }
        this.sudokuBoxes.add(tempList);
      }
    }
  }
  
  public Sudoku cloneSudoku() {
    ConcurrentHashMap<IntegerTuple, LinkedList<Integer>> sudokuFieldClone = new ConcurrentHashMap<IntegerTuple, LinkedList<Integer>>();
    
    this.sudokuField.entrySet().parallelStream().forEach(entry -> {
      LinkedList<Integer> tempList = new LinkedList<Integer>();
      for(int value : this.sudokuField.get(entry.getKey())) {
        tempList.add(value);
      }
      sudokuFieldClone.put(new IntegerTuple(entry.getKey().getX(), entry.getKey().getY()), tempList);
    });
    
    Sudoku sudokuClone = new Sudoku(sudokuFieldClone);
    return sudokuClone;
  }
  
  public boolean isValid() {
    for(ArrayList<LinkedList<Integer>> sudokuRow : this.sudokuRows) {
      ConcurrentHashMap<Integer, Integer> duplicateTesterMap = new ConcurrentHashMap<Integer, Integer>();
      int uniqueId = 10;
      for(LinkedList<Integer> element : sudokuRow) {
        if(element.size() == 1) {
          duplicateTesterMap.put(element.getFirst(), 1);
        } else {
          duplicateTesterMap.put(uniqueId, 1);
          uniqueId++;
        }
      }
      if(duplicateTesterMap.size() < 9) {
        return false;
      }
    }
    for(ArrayList<LinkedList<Integer>> sudokuColumn : this.sudokuColumns) {
      ConcurrentHashMap<Integer, Integer> duplicateTesterMap = new ConcurrentHashMap<Integer, Integer>();
      int uniqueId = 10;
      for(LinkedList<Integer> element : sudokuColumn) {
        if(element.size() == 1) {
          duplicateTesterMap.put(element.getFirst(), 1);
        } else {
          duplicateTesterMap.put(uniqueId, 1);
          uniqueId++;
        }
      }
      if(duplicateTesterMap.size() < 9) {
        return false;
      }
    }
    for(ArrayList<LinkedList<Integer>> sudokuBox : this.sudokuBoxes) {
      ConcurrentHashMap<Integer, Integer> duplicateTesterMap = new ConcurrentHashMap<Integer, Integer>();
      int uniqueId = 10;
      for(LinkedList<Integer> element : sudokuBox) {
        if(element.size() == 1) {
          duplicateTesterMap.put(element.getFirst(), 1);
        } else {
          duplicateTesterMap.put(uniqueId, 1);
          uniqueId++;
        }
      }
      if(duplicateTesterMap.size() < 9) {
        return false;
      }
    }
    return true;
  }
  
  public ConcurrentHashMap<IntegerTuple, LinkedList<Integer>> getSudokuField() {
    return this.sudokuField;
  }

  public ArrayList<ArrayList<LinkedList<Integer>>> getSudokuRows() {
    return this.sudokuRows;
  }

  public ArrayList<ArrayList<LinkedList<Integer>>> getSudokuColumns() {
    return this.sudokuColumns;
  }

  public ArrayList<ArrayList<LinkedList<Integer>>> getSudokuBoxes() {
    return this.sudokuBoxes;
  }

  
  public void printHashMap() {
    System.out.println("Printing HashMap");
    for(int r=0; r<9; r++) {
      for(int c=0; c<9; c++) {
        System.out.print(this.sudokuField.get(new IntegerTuple(r, c)));
      }
      System.out.println();
    }
  }
  
  public void printRows() {
    System.out.println("Printing Rows");
    for(int r=0; r<9; r++) {
      System.out.println(this.sudokuRows.get(r).toString());
    }
  }
  
  public void printColumns() {
    System.out.println("Printing Columns");
    System.out.println(this.sudokuColumns.toString());
    for(int c=0; c<9; c++) {
      System.out.println(this.sudokuColumns.get(c).toString());
    }
  }
  
  public void printBoxes() {
    System.out.println("Printing Boxes");
    for(int b=0; b<9; b++) {
      System.out.println(this.sudokuBoxes.get(b).toString());
    }
  }
}
