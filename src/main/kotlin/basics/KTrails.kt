package basics

/**
 * Created by Rajendhiran Easu on 02/07/25.
 * Description:
 */

fun main() {

    // Index Operator Overloading

    val jTrails = JTrails()

    // getter
    println(jTrails["Tester"])
    println(jTrails["Rajendhiran"])
    jTrails[20]
    jTrails["Rajendhiran", 20]
    jTrails[JData().apply {
        name = "Rajendhiran"
        age = 20
    }]

    // setter
    jTrails["Rajendhiran"] = 20
    println("Setter Details: Name: ${jTrails.name} ; Age: ${jTrails.age}")
}
