package threading

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

/**
 * Created by Rajendhiran Easu on 10/02/24.
 * Description: This code simulates a scenario of placing orders for different types of chaat at a food stall. It demonstrates the use of Kotlin Coroutines to handle concurrent tasks and manage exceptions using CoroutineExceptionHandler. Each order is processed in a separate coroutine, and if an exception occurs (like an order cancellation), it is handled gracefully without affecting other ongoing orders.
 */

suspend fun main() {

    val exceptionHandler = CoroutineExceptionHandler { ctx, exception ->
        println("*** ${ctx[CoroutineName]?.name} - ${exception.message} ***")
    }
    val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO + CoroutineName("Evening Chaat") + exceptionHandler)

    val order1 = scope.launch(CoroutineName("table-100")) {
        with(PlaceOrder(coroutineContext[CoroutineName]?.name ?: "")) {
            requestFor(Items.MASALA_PURI, noOfItems = 5)
            requestFor(Items.BHEL_PURI)
            requestFor(Items.PANE_PURI, noOfItems = 2)
        }
    }
    val order2 = scope.launch(CoroutineName("table-101")) {
        with(PlaceOrder(coroutineContext[CoroutineName]?.name ?: "")) {
            requestFor(Items.THAI_PURI, noOfItems = 2)
            requestFor(Items.BHEL_PURI, isInterrupt = true)
            requestFor(Items.MASALA_PURI, noOfItems = 2)
            requestFor(Items.PANE_PURI)
        }
    }
    joinAll(order1, order2)
}

class PlaceOrder(val tableName: String) {

    suspend fun requestFor(item: Items, noOfItems: Int = 1, isInterrupt: Boolean = false) {
        println("$tableName - Preparation Started for ${item.name} ")
        delay(item.prepTime * noOfItems)
        if (isInterrupt) {
            throw IllegalArgumentException("Order Cancelled while preparing ${item.name}")
        }
        println("$tableName - Your ordered chaat - ${item.name} is ready")
    }
}

enum class Items(val prepTime: Long) {
    MASALA_PURI(5000),
    PANE_PURI(2000),
    BHEL_PURI(7000),
    THAI_PURI(3000)
}
