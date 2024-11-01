
/**
 * Created by Rajendhiran Easu on 07/10/22.
 * Description: Delegation Trails
 */

fun main() {
    println(PaymentProcess(CashPayment()).processPayment())
    PaymentProcess.isUPIDown = true
    println(PaymentProcess(UPIPayment("Tez")).processPayment())
    PaymentProcess.isUPIDown = false
    println(PaymentProcess(UPIPayment("Paytm")).processPayment())
    PaymentProcess.isCardDown = true
    println(PaymentProcess(CardPayment("HDFC", "CC")).processPayment())
    PaymentProcess.isCardDown = false
    println(PaymentProcess(CardPayment("Citi")).processPayment())
    PaymentProcess(CashPayment()).paymentMode()
}

interface IPayment {
    fun paymentMode(): String
}

class CashPayment : IPayment {
    override fun paymentMode(): String {
        return "Cash"
    }
}

class UPIPayment(val upiName: String) : IPayment {
    override fun paymentMode(): String {
        return "UPI - $upiName"
    }
}

class CardPayment(val providerName: String, val cardType: String = "DC") : IPayment {
    override fun paymentMode(): String {
        return "Card-$providerName-$cardType"
    }
}

const val PROCESSING_ERROR_MSG = "Sorry we can't process it for now with "
const val PROCESSING_MSG = "Process Initiated for "

class PaymentProcess(private val iPayment: IPayment) : IPayment by iPayment {
    companion object {
        var isCardDown: Boolean = false
        var isUPIDown: Boolean = false
    }

    fun processPayment(): String {
        val paymentMode = iPayment.paymentMode()
        return if (iPayment !is CashPayment && (isUPIDown || isCardDown)) {
            "$PROCESSING_ERROR_MSG $paymentMode"
        } else {
            "$PROCESSING_MSG $paymentMode"
        }
    }
}
