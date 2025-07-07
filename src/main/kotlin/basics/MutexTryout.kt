package basics

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex

/**
 * Created by Rajendhiran Easu on 07/07/25.
 * Description:
 */

private val resourceMutex = Mutex()
private val skippedJobs = mutableListOf<String>()
private val completedJobs = mutableListOf<String>()

fun main() = runBlocking {
    val workers = listOf("Ganesh", "Sundar", "Bharathi", "Divya", "Murugan")
    val jobs = workers.map { user ->
        delay(1200)
        println("\nRequest for Job by: $user")
        launchJob(user)
    }
    jobs.joinAll()
    println("\nJobs Status")
    println("Skipped: ${skippedJobs.size} | $skippedJobs")
    println("Completed: ${completedJobs.size} | $completedJobs")
}

fun CoroutineScope.launchJob(user: String) = launch {
    if (!resourceMutex.tryLock()) {
        println("Resource unavailable, skipping: $user")
        skippedJobs += user
        return@launch
    }
    try {
        println("Job started by $user (processing 2000ms)")
        delay(2000)
        println("Job completed by $user")
        completedJobs += user
    } finally {
        resourceMutex.unlock()
        println("$user released the resource")
    }
}
