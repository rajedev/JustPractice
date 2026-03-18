package threading

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

/**
 * Created by Rajendhiran Easu on 11/02/24.
 * Description: This code demonstrates the use of Kotlin Coroutines and
 * Channels to facilitate communication between two coroutines.
 * One coroutine is responsible for sending data (integers) through a channel,
 * while another coroutine receives and processes that data.
 * The example illustrates how to use different types of channels
 * (rendezvous, buffered, unlimited, conflated) and
 * how to handle the closing of the channel gracefully when the sender has finished sending data.
 */

suspend fun main() {
    val dataChannel = Channel<Int>() //Channel.RENDEZVOUS --// Default
    //val dataChannel = Channel<Int>(2) // Buffered with capacity of 2
    //val dataChannel = Channel<Int>(Channel.UNLIMITED) // Unlimited buffer
    //val dataChannel = Channel<Int>(Channel.CONFLATED) // Only keeps the latest value, discards previous ones
    val scope = CoroutineScope(Dispatchers.IO + CoroutineName("Data Transfer"))

    val job1 = scope.launch {
        repeat(3) {
            println("Sending: ${it + 1}")
            dataChannel.send(it + 1)
        }
        dataChannel.close() // Close the channel after sending all data
    }

    val job2 = scope.launch {
        for (data in dataChannel) {
            delay(data * 3000L)
            println("Received: $data")
        }
    }

    joinAll(job1, job2)
    println("Completed Data Transfer...")
}
