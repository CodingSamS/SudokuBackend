package sudoku.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import sudoku.utils.sudokuValidityChecker;
import sudoku.utils.sudokuFormatConverter;
import sudoku.model.Sudoku;
import sudoku.model.SudokuSolver;
import sudoku.model.SudokuBlueprint;
import sudoku.model.SudokuNotSolvedException;
import sudoku.model.SudokuInvalidException;

@RestController
public class SudokuController {
  @CrossOrigin(origins = "${SudokuBackendApplication.allowedOrigins}")
  @PostMapping(path = "/sudoku")
  public SudokuBlueprint solveSudoku(@RequestBody SudokuVO sudokuVO) {

    SudokuBlueprint sudokuBlueprint = new SudokuBlueprint(sudokuVO.getSudoku());

    System.out.println("Received Blueprint");

    if(!sudokuValidityChecker.isValid(sudokuBlueprint)) {
      return sudokuBlueprint;
    }

    System.out.println("Blueprint is valid - converting to Sudoku");

    Sudoku sudokuToSolve = sudokuFormatConverter.convertSudokuBlueprintToSudoku(sudokuBlueprint);

    System.out.println("Conversion finished - Checking Sudoku for validity");

    if(!sudokuToSolve.isValid()) {
      System.out.println("Invalid Sudoku from user input");
      return sudokuBlueprint;
    }

    System.out.println("Sudoku is valid - start solving");

    SudokuSolver sudokuSolver = new SudokuSolver(sudokuToSolve);
    try {
      Sudoku solvedSudoku = sudokuSolver.solveSudoku();
      solvedSudoku.printHashMap();
      sudokuBlueprint = sudokuFormatConverter.convertSudokuToSudokuBlueprint(solvedSudoku);
      sudokuBlueprint.setSuccess(true);
    } catch(SudokuInvalidException | SudokuNotSolvedException e) {
      e.printStackTrace(System.out);
    }

    System.out.println("Sudoku solved successfully - returning the result");

    return sudokuBlueprint;
  }
}
