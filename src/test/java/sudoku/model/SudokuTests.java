package sudoku.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;


import sudoku.utils.sudokuFormatConverter;

import java.util.ArrayList;
import java.util.List;

class SudokuTests {
  
  static ArrayList<Sudoku> invalidSudokus = new ArrayList<Sudoku>();
  static ArrayList<Sudoku> validSudokus = new ArrayList<Sudoku>();
  
  @BeforeAll
  static void setup() {
    int[][] invalid1 = { 
        {9,9,9,9,9,9,9,9,9},
        {9,9,9,9,9,9,9,9,9},
        {9,9,9,9,9,9,9,9,9},
        {9,9,9,9,9,9,9,9,9},
        {9,9,9,9,9,9,9,9,9},
        {9,9,9,9,9,9,9,9,9},
        {9,9,9,9,9,9,9,9,9},
        {9,9,9,9,9,9,9,9,9},
        {9,9,9,9,9,9,9,9,9}
      };
    invalidSudokus.add(createSudoku(invalid1));
    int[][] invalid2 = { 
        {1,9,9,9,9,9,9,9,9},
        {9,1,9,9,9,9,9,9,9},
        {9,9,9,9,9,9,9,9,9},
        {9,9,9,1,9,9,9,9,9},
        {9,9,9,9,9,9,9,9,9},
        {9,9,9,9,9,9,9,9,9},
        {9,9,9,9,9,9,1,9,9},
        {9,9,9,9,9,9,9,9,9},
        {9,9,9,9,9,9,9,9,9}
      };
    invalidSudokus.add(createSudoku(invalid2));
    int[][] invalid3 = { 
        {1,1,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0}
      };
    invalidSudokus.add(createSudoku(invalid3));
    
    int[][] valid1 = { 
        {0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0}
      };
    validSudokus.add(createSudoku(valid1));
    int[][] valid2 = { 
        {1,3,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,1,0,0},
        {0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0},
        {0,0,0,7,8,0,0,0,0},
        {0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0},
        {3,2,0,0,0,0,9,0,0},
        {0,0,0,0,0,0,0,0,1}
      };
    validSudokus.add(createSudoku(valid2));
  }
  
  static Sudoku createSudoku(int[][] invalid) {
    List<List<Integer>> invalidMatrix = new ArrayList<List<Integer>>();
    for(int i = 0; i < invalid.length; i++) {
      ArrayList<Integer> temp = new ArrayList<Integer>();
      for(int j = 0; j < invalid[i].length; j++) {
        temp.add(invalid[i][j]);
      }
      invalidMatrix.add(temp);
    }
    SudokuBlueprint invalidBlueprint = new SudokuBlueprint(invalidMatrix);
    return sudokuFormatConverter.convertSudokuBlueprintToSudoku(invalidBlueprint);
  }

  @Test
  void testInvalid() {
    for(Sudoku sudoku : SudokuTests.invalidSudokus) {
      assertEquals(sudoku.isValid(), false);
    }
  }
  
  @Test
  void testValid() {
    for(Sudoku sudoku : SudokuTests.validSudokus) {
      assertEquals(sudoku.isValid(), true);
    }
  }

}
