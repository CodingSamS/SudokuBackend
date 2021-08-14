package sudoku.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import sudoku.utils.IntegerTuple;

public class SudokuSolver {

  private Stack<Sudoku> sudokuStack;
  private boolean valuesFound;
  private boolean currentSudokuIsValid;

  public SudokuSolver(Sudoku sudoku) {
    this.sudokuStack = new Stack<Sudoku>();
    this.sudokuStack.push(sudoku);
  }

  public Sudoku solveSudoku() throws SudokuInvalidException {
    while (true) {
      if (0 < this.sudokuStack.size()) {
        Sudoku sudokuToSolve = this.sudokuStack.pop();

        this.currentSudokuIsValid = true;
        this.valuesFound = true;

        while (this.valuesFound && this.currentSudokuIsValid) {
          this.valuesFound = false;
          this.sudokuSolveRoutine(sudokuToSolve);
        }
        
        if (!this.currentSudokuIsValid) {
          continue;
        }

        if (this.calculateNumberOfFinishedFields(sudokuToSolve) == 81) {
          return sudokuToSolve;
        } else {
          this.addPossibleSolutionsToStack(sudokuToSolve);
        }
      } else {
        throw new SudokuInvalidException("This is not a valid Sudoku");
      }
    }

  }

  public void sudokuSolveRoutine(Sudoku sudokuToSolve) {
    sudokuToSolve.getSudokuBoxes().parallelStream().forEach(item -> applySudokuConstraint(item));
    sudokuToSolve.getSudokuRows().parallelStream().forEach(item -> applySudokuConstraint(item));
    sudokuToSolve.getSudokuColumns().parallelStream().forEach(item -> applySudokuConstraint(item));
  }

  public void applySudokuConstraint(ArrayList<LinkedList<Integer>> sudokuValues) {
    if (!this.currentSudokuIsValid) {
      return;
    }
    ConcurrentHashMap<Integer, Boolean> singleValues = new ConcurrentHashMap<Integer, Boolean>();
    ConcurrentLinkedQueue<LinkedList<Integer>> multiValueList = new ConcurrentLinkedQueue<LinkedList<Integer>>();
    sudokuValues.parallelStream().forEach(item -> {
      if (item.size() == 1) {
        if (singleValues.containsKey(item.getFirst())) {
          this.currentSudokuIsValid = false;
          return;
        } else {
          singleValues.put(item.getFirst(), true);
        }
      } else {
        multiValueList.add(item);
      }
    });

    multiValueList.parallelStream().forEach(item -> {
      for(Integer value : singleValues.keySet()) {
        if (item.remove(value)) {
          this.valuesFound = true;
        }
      }
      if (item.size() == 0) {
        this.currentSudokuIsValid = false;
      }
    });
  }
  
  public int calculateNumberOfFinishedFields(Sudoku sudokuToSolve) {
    AtomicInteger numberOfFinishedFields = new AtomicInteger(0);
    sudokuToSolve.getSudokuField().values().parallelStream().forEach(item -> {
      if (item.size() == 1) {
        numberOfFinishedFields.getAndIncrement();
      }
    });
    return numberOfFinishedFields.get();
  }
  
  public void addPossibleSolutionsToStack(Sudoku startingSudoku) {
    int x = 0;
    int y = 0;
    int smallestSize = 10;
    
    for(int r=0; r<9; r++) {
      for(int c=0; c<9; c++) {
        int listSize = startingSudoku.getSudokuRows().get(r).get(c).size();
        if(1 < listSize && listSize < smallestSize) {
          x = r;
          y = c;
          smallestSize = listSize;
        }
      }
    }
    
    LinkedList<Integer> smallestList = new LinkedList<Integer>();
    smallestList.addAll(startingSudoku.getSudokuField().get(new IntegerTuple(x, y)));
    startingSudoku.getSudokuField().get(new IntegerTuple(x, y)).clear();
    
    for(int i=1; i<smallestSize; i++) {
      Sudoku newSudoku = startingSudoku.cloneSudoku();
      newSudoku.getSudokuField().get(new IntegerTuple(x, y)).add(smallestList.get(i));
      this.sudokuStack.push(newSudoku);
    }
    
    startingSudoku.getSudokuField().get(new IntegerTuple(x, y)).add(smallestList.getFirst());
    this.sudokuStack.push(startingSudoku);    
  }
}
