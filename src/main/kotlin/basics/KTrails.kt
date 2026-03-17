package basics

import kotlin.random.Random

/**
 * Created by Rajendhiran Easu on 02/07/25.
 * Description:
 */

fun main() {

    // Index Operator Overloading

    /*val jTrails = JTrails()

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
    println("Setter Details: Name: ${jTrails.name} ; Age: ${jTrails.age}")*/

    /*println(dataMap)
    println(addData())
    println(dataMap)*/
    //val inputStr = "Tester"
    //val outputStr = inputStr.reversed()

    //var revStr = ""
    /* for (ch in inputStr) {
         revStr = ch + revStr
     }*/

    /* for( index in inputStr.length -1 downTo 0) {
         revStr += inputStr[index]
     }

     //println(outputStr)
     println(revStr)*/

    /*   val data = "https://asd.bgx.com/api"
       println("data pre removeSuffix = $data")
       val test = data.removeSuffix("/")
       println("data post removeSuffix = $data")
       println("test = $test")*/

    /* println("Enter a number to check palindrome:")
     var x = readln().toInt()
     val actual = x
     var rev = 0
     var revStr = ""
     while (x > 0) {
         val digit = x % 10
         rev = rev * 10 + digit
         revStr += digit.toString()
         x /= 10
     }
     println(rev)
     println("str: $revStr")
     print("Staus $actual is ${if (rev == actual) "Palindrome" else "Not a Palindrome"}")*/

    /* var x ="Tester info "
     x = x.plus("Data")
     println(x)*/
    val randomList = listOf("xx", "xxye", "sad", "tst")
    println(randomList.size)
    val ran = Random.nextInt(from = 0, until = randomList.size)
    println(ran)
    val sourceList = randomList.slice(0..ran)
    println(sourceList)

    /*try {
        test0()
    } catch (ex: Exception) {
        println("main catch - ${ex.message}")
    }*/

//    val mylist: List<String> = listOf("A", "B", "C", "D", "E")
//   // print(mylist.joinToString())
//    val randomItem = mylist.shuffled().take(3)
//    println(randomItem)

}

fun test0() {
    try {
        test()
        throw IllegalArgumentException("Test0 method Exception")
    } catch (ex: Exception) {
        println("main - ${ex.message}")
    }
}

fun test() {

    try {
        throw IllegalArgumentException("Test method Exception")
    } catch (ex: Exception) {
        println("test - ${ex.message}")
    }
}

val dataMap = mutableMapOf("test" to "tester", "test1" to "tester1", "test2" to "tester2")

fun addData(): Map<String, String> {
    return dataMap + mapOf("test3" to "tester3", "test4" to "tester4", "test5" to "tester5")
}

interface Car {
    val name: String
    fun drive(): String
}

