import kotlin.system.exitProcess


/*
* [-] To Setup
*       [-] Dynamic board with size * size
*       [-] Introduce the Player X & O
*       [-] Runtime Input for the Game Play flow with the input
*           respective rows and column selection
* [-] Check for the winner
*       [-] Identify the player has checked and completed the entire
*           Row, Column and
*           Diagonals (Top-Left to Bottom-Right)  & (Top-Right to Bottom Left)
* [-] Show Puzzle Board on player selecting the respective cells
* */

/**
 * Created by Rajendhiran Easu on 05/05/24.
 * Description: TicTacToe - Two Players Impl.
 */

const val PLAYER_X: Char = 'X'
const val PLAYER_O: Char = 'O'
const val EMPTY: Char = ' '
class TicTacToeBoard(private val size: Int) {
    private val puzzleBoard = Array(size) { CharArray(size) { EMPTY } }
    fun letsPlay() {
        var playChances = size * size
        var currentPlayer = PLAYER_X
        while (playChances > 0) {
            showBoard()
            println("$currentPlayer's Turn")
            println("Enter your row & column (0-${size - 1})" +
                    " (zero-indexed), Anytime -1 to quit:")
            val row = readInput("Row")
            val col = readInput("Column")
            if (puzzleBoard[row][col] == EMPTY) {
                puzzleBoard[row][col] = currentPlayer
                if (validateWinning(row, col)) {
                    showBoard()
                    println("$currentPlayer won!")
                    return
                } else {
                    currentPlayer = if (currentPlayer == PLAYER_X) PLAYER_O else PLAYER_X
                }
                playChances--
            } else {
                print("Cell Occupied, Please try other")
            }
        }
        showBoard()
        println("It's a draw!")
    }

    private fun readInput(prompt: String): Int {
        var input: Int
        while (true) {
            println("$prompt 0-${size - 1}:")
            input = readln().toInt()
            when (input) {
                -1 -> {
                    exitProcess(0)
                }

                in 0 until size -> {
                    return input
                }

                else -> {
                    println("Enter the valid input between " +
                            "0-${size - 1} or -1 to quit")
                }
            }
        }
    }

    private fun validateWinning(row: Int, col: Int): Boolean {
        val currentPlayer = puzzleBoard[row][col]
        return verifyRow(row, currentPlayer) ||
                verifyColumn(col, currentPlayer) ||
                verifyDiagonal(row, col, currentPlayer)
    }

    private fun verifyRow(row: Int, cPlayer: Char): Boolean {
        /*for (column in 0 until size) {
            if (puzzleBoard[row][column] != cPlayer) {
                return false
            }
        }
        return true*/
        return (0 until size).all { puzzleBoard[row][it] == cPlayer }
    }

    private fun verifyColumn(column: Int, cPlayer: Char): Boolean {
        /*for (row in 0 until size) {
            if (puzzleBoard[row][column] != cPlayer) {
                return false
            }
        }
        return true*/
        return (0 until size).all { puzzleBoard[it][column] == cPlayer }
    }

    private fun verifyDiagonal(row: Int, col: Int, cPlayer: Char): Boolean {
        /*// (Top-Left to Bottom-Right)
        if (row == col) {
            var isWon = true
            for (diagonal in 0 until size) {
                if (puzzleBoard[diagonal][diagonal] != cPlayer) {
                    isWon = false
                    break
                }
            }
            if (isWon) return true
        }
        // (Top-Right to Bottom Left)
        if (row + col == size - 1) {
            var isWon = true
            for (diagonal in 0 until size) {
                if (puzzleBoard[diagonal][size - 1 - diagonal] != cPlayer) {
                    isWon = false
                    break
                }
            }
            if (isWon) return true
        }
        return false*/
        return ((row==col) && (0 until size)
                .all { puzzleBoard[it][it] == cPlayer }) ||
                ((row + col == size-1) && (0 until  size)
                    .all { puzzleBoard[it][size-1 - it] == cPlayer })
    }

    private fun showBoard() {
        println("  " + (0 until size).joinToString(" "))
        for (position in 0 until size) {
            println("$position ${puzzleBoard[position].joinToString(" ")}")
        }
    }
}

fun main() {
    val game = TicTacToeBoard(3)
    game.letsPlay()
}