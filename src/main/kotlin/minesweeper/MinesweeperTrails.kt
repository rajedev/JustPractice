package minesweeper

/*
  Design Understanding
   [X] Setup Puzzle Board with no. of rows, columns & mines
        [x] Place mines to the blocks i.e. cells ([row][column]) random selection:
        [x] Lets collect the coordinates and shuffle to randomize and add mines to those cells
        [x] Update the neighbor cells with the numbers indicating the mines next to it
   [X] Reveal Cells
        [x] Need to handle the game first click - if it is mine - replace the mine and neighbors cells with updated numbers
        [x] Need to boom i.e. game over on clicking the mines
        [x] Need to reveal the info. on subsequent clicks
        [x] Game Won and reveal all - Result as - remaining mines = (row * columns) - Mines
   [X] Flag the cell for the future reference to identify it
   [X] Show Puzzle Board -- Output
        [x] Debug Board Show -- Only Development - which reveals with the data's
        [x] Puzzle Board to show
 */
/**
 * Created by Rajendhiran Easu on 29/04/24.
 * Description: SnowBowling Game Logic
 */
const val DEBUG_MODE = true

class MinesweeperBoard(private val rows: Int = 4, private val columns: Int = 4, private val totalMines: Int = 4) {

    private val directions = arrayOf(
        Pair(-1, -1), // top-left
        Pair(-1, 0), // top
        Pair(-1, 1), // top-right
        Pair(0, -1), // Left
        Pair(0, 0),
        Pair(0, 1), // right
        Pair(1, -1), // bottom-left
        Pair(1, 0), // bottom
        Pair(1, 1) // bottom-right
    )

    private val puzzleBoard = Array(rows) { Array(columns) { Cell() } }
    private var remainingMines = (rows * columns) - totalMines
    private var isFirstClick = true

    init {
        placeMines()
        updateNeighborMinesWithCount()
    }

    private fun placeMines() {
        val randomCoordinates = List(rows * columns) {
            it / columns to it % columns
        }.toMutableList().apply {
            shuffle()
        }
        repeat(totalMines) {
            val (rowPos, colPos) = randomCoordinates.removeAt(randomCoordinates.size - 1)
            puzzleBoard[rowPos][colPos].isMine = true
        }
    }

    private fun updateNeighborMinesWithCount() {
        puzzleBoard.forEachIndexed { rowIndex, cells ->
            cells.forEachIndexed { columnIndex, cell ->
                if (!cell.isMine) {
                    puzzleBoard[rowIndex][columnIndex].neighborMines = numberOfNeighborMines(rowIndex, columnIndex)
                }
            }
        }
    }

    private fun numberOfNeighborMines(rowPos: Int, colPos: Int): Int {
        var minesCount = 0
       /* for ((directionX, directionY) in directions) {
            val neighborX = rowPos + directionX
            val neighborY = colPos + directionY
            if(neighborX in 0 until rows && neighborY in 1 until columns &&
                !puzzleBoard[neighborX][neighborY].isMine){
                    minesCount++
            }
        }
        return minesCount
        }*/
        for (directionX in -1..1) {
            for (directionY in -1..1) {
                val neighborX = rowPos + directionX
                val neighborY = colPos + directionY
                if (neighborX in 0 until rows && neighborY in 0 until columns && puzzleBoard[neighborX][neighborY].isMine) {
                    minesCount++
                }
            }
        }
        return minesCount
    }

    fun debugBoardShow() {
        if (DEBUG_MODE) {
            println("\n----------------------------")
            println("Actual Board -- For Reference")
            puzzleBoard.forEachIndexed { rIndex, cells ->
                cells.forEachIndexed { cIndex, cell ->
                    if (cell.isMine) {
                        print(" * ")
                    } else {
                        if (cell.neighborMines == 0) {
                            print(" - ")
                        } else {
                            print(" ${cell.neighborMines} ")
                        }
                    }
                }
                println()
            }
            println("----------------------------")
            println()
        }
    }

    fun reveal(rRow: Int, rCol: Int): Boolean {
        if (rRow < 0 || rRow >= rows || rCol < 0 || rCol > columns || puzzleBoard[rRow][rCol].isRevealed) {
            return false
        }
        if (!isFirstClick) {
            puzzleBoard[rRow][rCol].isRevealed = true
            remainingMines--
            if (puzzleBoard[rRow][rCol].isMine) {
                println("Game Over! You hit a mine!")
                revealAll()
                return true
            }
            if (puzzleBoard[rRow][rCol].neighborMines == 0) {
                /*for (di in -1..1) {
                    for (dj in -1..1) {
                        reveal(rRow + di, rCol + dj)
                    }
                }*/
                for ((directionX, directionY) in directions) {
                    reveal(rRow + directionX, rCol + directionY)
                }
            } //else {
            //puzzleBoard[rRow][rCol].isRevealed = true
            //}
            if (isWon()) {
                println("You Won the Game!")
                revealAll()
                return true
            }
        } else {
            isFirstClick = false
            placeFirstClick(rRow, rCol)
        }
        return false
    }

    private fun placeFirstClick(row: Int, col: Int) {
        //Find the first available cell without a mine and move the mine there and update the neighbor count
        if (puzzleBoard[row][col].isMine) {
            run done@{
                repeat(rows) { r ->
                    repeat(columns) { c ->
                        if (!(r == row && c == col) && !puzzleBoard[r][c].isMine) {
                            puzzleBoard[r][c].isMine = true
                            puzzleBoard[row][col].isMine = false
                            return@done
                        }
                    }
                }
            }
            updateNeighborMinesWithCount()
            debugBoardShow()
        }
        reveal(row, col)
    }

    fun toggleFlag(row: Int, col: Int) {
        if (row in 0 until rows && col in 0 until columns) {
            val cell = puzzleBoard[row][col]
            if (!cell.isRevealed) {
                cell.isFlagged = !cell.isFlagged
            }
        }
    }

    private fun revealAll() {
        puzzleBoard.forEach {
            it.forEach { blocks ->
                if (!blocks.isRevealed) {
                    blocks.isRevealed = true
                }
            }
        }
    }

    fun isWon() = remainingMines == 0

    fun showPuzzleBoard() {
        for (i in 0 until rows) {
            for (j in 0 until columns) {
                val cell = puzzleBoard[i][j]
                print(
                    if (cell.isRevealed) {
                        if (cell.isMine) "*" else cell.neighborMines.toString()
                    } else {
                        if (cell.isFlagged) "F" else "-"
                    }
                )
                print(" ")
            }
            println()
        }
    }
}

data class Cell(
    var isMine: Boolean = false,
    var isFlagged: Boolean = false,
    var neighborMines: Int = 0,
    var isRevealed: Boolean = false,
)

fun main() {
    var playAgain = true
    while (playAgain) {
        val rows = 3
        val cols = 3
        val minesCount = 3
        val minesweeper = MinesweeperBoard(rows, cols, minesCount)

        minesweeper.showPuzzleBoard()
        var gameOver = false
        while (!gameOver) {

            minesweeper.debugBoardShow()

            println("Enter 'r' to reveal, 'f' to flag, or 'q' to quit: ")
            val input = readlnOrNull()
            when (input) {
                "q" -> {
                    gameOver = true
                    playAgain = false
                }

                "r" -> {
                    println("Enter row and column to reveal (zero-indexed): ")
                    val row = readln().toInt()
                    val col = readln().toInt()
                    gameOver = minesweeper.reveal(row, col)
                    minesweeper.showPuzzleBoard()
                }

                "f" -> {
                    println("Enter row and column to flag (zero-indexed): ")
                    val row = readln().toInt()
                    val col = readln().toInt()
                    minesweeper.toggleFlag(row, col)
                    minesweeper.showPuzzleBoard()
                    if (minesweeper.isWon()) {
                        println("Congratulations! You've won!")
                        gameOver = true
                    }
                }

                else -> println("Invalid input! Please enter 'r', 'f', or 'q'.")
            }
        }
    }
}