# My Personal Project: *Minesweeper*

## Introduction

### What is *Minesweeper*?

Minesweeper is a single-player puzzle game that requires strategy, and sometimes a bit of luck in order to win. The objective of the game is to locate all mines in a rectangular grid without detonating any of them. The game is relatively simple on small boards, but gets increasingly more difficult as the size and number of mines increase.

### Personal Project

For my project, I plan on designing a *Minesweeper* replica that has all the features of the most common versions of *Minesweeper*, as well as a few more to make the game more interesting and fun. Once this project is complete, anyone should be able to start the application and learn how to play the infamous game of *Minesweeper*.

### Why *Minesweeper*?

I chose to make *Minesweeper* for my project because of my personal interest in the game. Just like other single-player puzzle games that require strategy and logic to beat, such as sudoku, *Minesweeper* appeals to me because of the complex strategy and problem-solving that are required to beat the game. Another reason why I chose *Minesweeper* is because it sounds like a fun challenge for me to make that will test both my knowledge of the Java language and my ability to represent real-world ideas in terms of code.

## User Stories

As a user, I want to be able to ...
- [x] create a board and specify its width, height, and number of bombs
- [x] add various tiles to a board (list of tiles)
- [x] input a move to open or flag a tile on the board
- [x] view the number of bombs remaining
- [x] make the first move always safe
- [x] select from different preset boards (beginner, intermediate, expert, etc.)
- [x] add the moves that I make to a list stored in a player
- [x] view the list of moves that I have made so far
- [x] view an instructions screen that tells me how to play the game
- [x] save the current player state
- [x] load a stored player state
- [x] add a timer to track how long a game has been going
- add a difficulty rating for the board
- create a leaderboard based on time duration or board difficulty

## Instructions for Grader

- You can generate the first required event by either left or right-clicking on any tile on the board. This will cause the board to be updated resulting in different tiles to be added to a board
- You can generate the second required event by navigating to the settings menu, clicking the new game button, and finally clicking any of the game options (beginner, intermediate, expert, custom). This will cause a new board to be generated resulting in tiles to be added to a new board
- You can locate my visual component by opening the application, as it opens to a new board every time with images on the tiles
- You can save the state of my application by playing a game and closing the application, as the application automatically saves after every move
- You can reload the state of my application by navigating to the settings menu, clicking the new game button, and finally clicking the load game button. If the last played board was not completed (the user won or lost), the board will be reloaded, otherwise nothing will happen.

## Phase 4: Task 2

Tue Aug 09 21:16:12 PDT 2022

Created a new player board

Tue Aug 09 21:16:12 PDT 2022

Created a new player board

Tue Aug 09 21:16:13 PDT 2022

Created a new master board

Tue Aug 09 21:16:13 PDT 2022

Added a new Move: 3 3 0

Tue Aug 09 21:16:14 PDT 2022

Added a new Move: 6 4 1

Tue Aug 09 21:16:15 PDT 2022

Added a new Move: 9 1 1

Tue Aug 09 21:16:15 PDT 2022

Added a new Move: 9 0 0

## Phase 4: Task 3

If I had more time to work on the project, I would ...
- refactor mainPanel and settingsPanel to use the singleton design pattern, as they are only created once and it would reduce the amount of bi-direction associations between classes
- refactor most of the panel classes to use an observer design pattern on Player, as they all currently have an association with Player, when they can instead just observe Player 
- add an abstract class or interface for the panel classes that contains the shared behaviour of the classes such as initialize, instantiateFields, cleanup, mouseListener, etc


## TODO
- [x] fix the timer when load board is clicked
  - [x] timer continues counting after load board is clicked
  - [x] currently the timer only tracks when the last move was made then it saves. If I want a more accurate timer I have to save the player every tick of the timer
- fix the "invalid board" popup so that it disappears after a few seconds
  - change font color to red
- add a leaderboard
  - should display the top 10 times for all the preset boards as well as one for difficulty ratings
  - prompt the user to input a name if they get a high score
  - new Json file to store the leaderboard
- add difficulty ratings to boards 
  - research how they are calculated
  - add an indicator somewhere on the mainPanel that displays the rating