package pract

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Created by Rajendhiran Easu on 11/02/24.
 * Description: 
 */

fun main() = runBlocking {
    //  val job1 = launch(Dispatchers.IO) {
    val job1 = launch {
        delay(3000)
        println("A - ${Thread.currentThread().name}")
    }
    val job2 = launch(Dispatchers.IO) {
        println("B - ${Thread.currentThread().name}")
         delay(2000)
    }
    joinAll(job1,job2)
    println("Completed")
}
