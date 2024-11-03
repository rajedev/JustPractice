package slidingwindow

/**
 * Created by Rajendhiran Easu on 04/05/24.
 * Description: Sliding Window logic to find the max. sum of sub-array of size K
 */

fun main() {
    val (windowSum, maxSum) = findMaxSumOfSubArray(intArrayOf(1, 2, 3, 4, 5), 3)
    println("Window Detail: $windowSum")
    println("Max Sum: $maxSum")

}
fun findMaxSumOfSubArray(data: IntArray, subArraySize: Int): Pair<List<Int>, Int> {
    val windowData = mutableListOf<Int>()
    var maxSum: Int = 0
    var windowSum = 0

    // Need to check subArraySize should not above data size
    if (subArraySize > data.size) {
        println("Invalid sub array size!")
        return Pair(emptyList(), 0)
    }

    for (value in 0 until subArraySize) {
        windowSum += data[value]
    }
    windowData.add(windowSum)
    maxSum = windowSum

    for (value in subArraySize until data.size) {
        windowSum += data[value] - data[value - subArraySize]
        windowData.add(windowSum)
        maxSum = maxOf(maxSum, windowSum)
    }
    return Pair(windowData, maxSum)
}
