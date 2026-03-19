package pract

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Created by Rajendhiran Easu on 13/02/24.
 * Description: 
 */

suspend fun main() {//= runBlocking {

    /*launch {
        println("\n Collect: \n")
        provideData().collect {
            println("Receivable $it")
            delay(1800)
            println("Done $it")
        }
        println("\n Collect Latest: \n")
        provideData().collectLatest {
            println("Receivable $it")
            delay(1800)
            println("Done $it")
        }
    }.join()*/

    runBlocking {
        launch {
            provideData()
                //.filter { it % 2 == 0 }
                .collect {
                    println("Receivable $it")
                    delay(1800)
                    println("Done $it")
                }
        }.join()
        launch {
            provideData().collectLatest {
                println("Receivable $it")
                delay(1800)
                println("Done $it")
            }
        }
    }
    /*println("\n Collect: \n")
    coroutineScope {
        launch {
            provideData().collect {
                println("Receivable $it")
                delay(1800)
                println("Done $it")
            }
        }
    }

    println("\n Collect Latest: \n")

    coroutineScope {
        launch {
            provideData().collectLatest {
                println("Receivable $it")
                delay(1800)
                println("Done $it")
            }
        }
    }*/
    print("Test Completed")
}

fun provideData(): Flow<Int> = flow {
    for (index in 1..5) {
        delay(1500)
        emit(index)
    }
}
