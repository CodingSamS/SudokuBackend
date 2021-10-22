## Sudoku Backend:
- Rest Endpoint for accepting sudokus from the toolbox-website
- highly parallel execution
- uses spring + java

### Usage

Define /src/main/resources/application.properties containing:

**Required**: The allowed origin for the cors definition in SudokuController. By default one is used:  
_SudokuBackendApplication.allowedOrigins_  
Example definition:
_SudokuBackendApplication.allowedOrigins=http://localhost_

**Optional**: The server port (if you want to change it): server.port=11111

