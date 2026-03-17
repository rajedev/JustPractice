package threading

import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeoutOrNull

/**
 * Created by Rajendhiran Easu on 10/02/24.
 * Description: Demonstrates the use of withTimeoutOrNull in Kotlin Coroutines to handle timeouts gracefully.
 * The withTimeoutOrNull function allows you to specify a timeout for a block of code.
 */

suspend fun main() {
    val x = withTimeoutOrNull(3000) {
        println("Task Started...")
        delay(5000)
        println("Task Completed...")
        "Success"
    }
    print(x)
}
