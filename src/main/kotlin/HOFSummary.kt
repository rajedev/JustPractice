
class Operators {
    fun add(a: Int, b: Int) = a + b
    fun inc(a: Int) = a + 1
}

fun calculate(a: Int, b: Int, opr: (Int, Int) -> Int) = opr(a, b)
fun calculate(a: Int, opr: (Int) -> Int) = opr(a)

val addition = { a: Int, b: Int -> Operators().add(a, b) }
fun addition(a: Int, b: Int) = Operators().add(a, b)

val increment = { a: Int -> Operators().inc(a) }
fun increment(a: Int) = Operators().inc(a)

/**
 * Created by Rajendhiran Easu on 26/10/2019
 * Description: HOF Practice
 */

fun main() {
    println("Output: " + calculate(1, 2) { a, b -> Operators().add(a, b) }) // Lambda as param
    println("Output: " + calculate(1, 2, Operators()::add)) // Function as param - reference through its object
    println("Output: " + calculate(1, 2, addition)) // Lambda Expression as param
    println("Output: " + calculate(1, 2, ::addition)) // Function as param
    println("")
    println("Output: " + calculate(1) { a -> Operators().inc(a) }) // Lambda as param
    println("Output: " + calculate(1) { Operators().inc(it) })  //Lambda as param Since it is the only param so,`it` referenced here
    println("Output: " + calculate(1, Operators()::inc)) // Function as param - reference through its object
    println("Output: " + calculate(1, increment)) // Lambda Expression as param
    println("Output: " + calculate(1, ::increment)) // Function as param
}
