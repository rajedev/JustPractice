package threading

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Created by Rajendhiran Easu on 11/02/24.
 * Description: Demonstrates the use of SupervisorJob in Kotlin Coroutines.
 */
fun main() = runBlocking  {

    val job = launch {
        coroutineScope {
            println("Started")
            delay(5000)
            println("Ended")

            val job1 = launch {
                println("J1 Started")
                delay(8000)
                println("J1 Ended")
            }

            val job2 = launch {
                println("J2 Started")
                delay(3000)
                println("J2 Ended")
            }
        }
    }

    //val scope = CoroutineScope(Dispatchers.IO + CoroutineName("Work Request"))
    val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO + CoroutineName("Work Request"))
    val mainScopeName = "${scope.coroutineContext[CoroutineName]?.name}"
    println("Starting $mainScopeName")

    try {
        println("Job Request 1 Initiated")
        val job1 = scope.launch(CoroutineName("SOW 1")) {
            val name = "${coroutineContext[CoroutineName]?.name}"
            println("$name - Analysis - Begin")
            delay(5_000)
            println("$name - Execution - Begin")
            throw IllegalArgumentException("Need to re-run the analysis for $name")
            delay(7_000)
            println("$name - Report - Submitted")
        }

        println("Job Request 2 Initiated")

        val job2 = scope.launch(CoroutineName("SOW 2")) {
            val name = "${coroutineContext[CoroutineName]?.name}"
            try {
                println("$name - Analysis - Begin")
                delay(3_000)
                println("$name - Execution - Begin")
                delay(3_000)
                println("$name - Report - Submitted")
            } catch (e: Exception) {
                println("Exception caught in $name: ${e.message}")
            }
        }
        joinAll(job, job1, job2)
    } catch (e: Exception) {
        println("Exception: ${e.message}")
    }
    println("Ending $mainScopeName")
}
