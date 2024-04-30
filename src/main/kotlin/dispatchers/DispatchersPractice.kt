package dispatchers

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.security.MessageDigest
import java.util.concurrent.ConcurrentHashMap
import kotlin.random.Random
import kotlin.reflect.full.memberProperties

/**
 * Created by Rajendhiran Easu on 30/04/24.
 * Description: Dispatchers Practice
 */

fun main(): Unit = runBlocking {
    CoroutineScope(Dispatchers.IO).launch {
        val list = listOf("Test", "Test1", "Test", "Test2", "Test", "Test3", "Test", "Test4")
        list.forEachIndexed { index, s ->
            launch(Dispatchers.IO) {
                ConCur1().process(TestObj(input = s), object : TestObjCallback {
                    override fun invoke(msg: String) {
                        println(msg)
                    }
                })
            }
        }
    }
    delay(60000)
}

class TestObj(
    val input: String,
    val aID: String = "TESTERRTWERTEWREW ERWRES RTF",
    val uID: String = "dgdfg dfht uytry tyrty t"
)

interface TestObjCallback {
    fun invoke(msg: String)
}

fun hash(input: String, algorithm: MessageDigest): String {
    val bytes = algorithm.digest(input.trim().toByteArray())
    return bytes.joinToString("") { "%02x".format(it) }
}

class ConCur1 {

    companion object {
        private val requests = ConcurrentHashMap<String, ArrayList<TestObjCallback>>()
    }

    suspend fun process(dataObj: TestObj, callback: TestObjCallback) {
        if (isRequestInProgress(dataObj, callback)) return
        delay(Random.nextLong(from = 2500, until = 6500))
        dispatchResult("${dataObj.input} ${System.currentTimeMillis()}", dataObj)
    }

    // Test Object
    private fun dispatchResult(
        msg: String,
        data: TestObj
    ) {
        synchronized(requests) {
            val key = serializeToKey(data)
            println("Dispatch - ${data.input}")
            println(key)
            val tempRequest = requests[key]
            requests.remove(key)
            tempRequest?.let {
                for (callback in tempRequest) {
                    callback.invoke(msg)
                }
            }
        }
    }

    private fun isRequestInProgress(data: TestObj, callback: TestObjCallback): Boolean {
        synchronized(requests) {
            val key = serializeToKey(data)
            requests.keys.find { it == key }?.let {
                requests[it]?.add(callback)
                println(key)
                println("${data.input} Already In Progress")
                return true
            }
            requests[key] = arrayListOf(callback)
            return false
        }
    }

    private fun serializeToKey(testObj: TestObj): String {
        // Create a string representation of the object, e.g., "input:Test,id:1"
        val d = testObj::class.memberProperties.joinToString(",") { prop ->
            "${prop.name}:${prop.getter.call(testObj)}"
        }
        return hash(d, MessageDigest.getInstance("MD5"))
    }
}
