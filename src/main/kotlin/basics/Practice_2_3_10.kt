package basics

/**
 * Created by Rajendhiran Easu on 08/02/26.
 * Description:
 */

sealed interface Process {
    data object Planning : Process
    data class Implementation(val details: String)
    data object Execution : Process
    data object Closure : Process
}

fun main() {

    val process = Process.Closure

    val info = when (process) {
        Process.Planning -> "Blueprint"
        is Process.Implementation -> "Impl"
        Process.Execution -> "Execute"
        Process.Closure -> "Done"
        else -> "on hold"
    }
    print(info)
}