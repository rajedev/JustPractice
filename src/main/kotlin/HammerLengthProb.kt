/*
Hammer Length:

 [-] Setup Board
        [-] Dynamic Board and pass hammer length
        [-] Pass Hammer length as param
 [-] Create a method that to which to find the logic of max.
      hammer value while splitting into hammer length
        [-] Applying Sliding Window Technique to identify the max. hammer value
*/
/**
 * Created by Rajendhiran Easu on 01/05/24.
 * Description: Hammer length Logics - Sliding Window Approach
 */
fun findMaxHammerValue(board: IntArray, hammerLength: Int): Int {
    var maxHammerCount = 0
    var currentCount = 0

    // If Hammer length is higher than board size
    if (hammerLength > board.size) {
        println("Invalid Hammer Length.. Board Size is : ${board.size}")
    }

    // As per Sliding Window Technique
    // Need to count the max. hammer on the initial window of size `hammer length`
    for (value in 0 until hammerLength) {
        if (board[value] == 1) {
            currentCount++
        }
    }
    maxHammerCount = currentCount
    // Need to update the hammer count on the remaining windows by removing
    // the first value of the previous window and by adding the new element to the last
    for (value in hammerLength until board.size) {
        if (board[value] == 1) {
            currentCount++
        }
        if (board[value - hammerLength] == 1) {
            currentCount--
        }
        maxHammerCount = maxOf(maxHammerCount, currentCount)
    }
    return maxHammerCount;
}

fun main() {
    val board = intArrayOf(1, 0, 1, 0, 1, 0, 1, 1, 0)
    val max = findMaxHammerValue(board, 3)
    println(max)
}
