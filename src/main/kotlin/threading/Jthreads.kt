package threading

/**
 * Created by Rajendhiran Easu on 13/10/25.
 * Description:
 */


fun main(): Unit {
    val x = Thread {
        repeat(3) {
            println("X Repeat: ${it+1}")
            Thread.sleep(1000)
        }
    }

    val t = Thread {
        repeat(3) {
            println("T Repeat: ${it+1}")
            Thread.sleep(3000)
        }
    }
    x.start()
    t.start()
}