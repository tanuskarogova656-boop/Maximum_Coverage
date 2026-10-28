package main

fun main() {
    println(commission(10000.0,"Мир"))
    println(commission(5000.0,"Mastercard"))
}

fun commission(
    amountTransfers: Double,
    cardTypeAndAccount: String,
    amountOfPreviousTransfers: Double = 0.0
): Double {
    val dailyLimitCard = 150_000.0
    val monthlyLimitCard = 600_000.0
    val perOperationLimitVKPay = 15_000.0
    val monthlyLimitVKPay = 40_000.0

    when (cardTypeAndAccount) {
        "Mastercard", "Maestro", "Visa", "Мир" -> {
            if (amountTransfers > dailyLimitCard) return -1.0
            if (amountOfPreviousTransfers + amountTransfers > monthlyLimitCard) return -1.0
        }
        "VK Play" -> {
            if (amountTransfers > perOperationLimitVKPay) return -1.0
            if (amountOfPreviousTransfers + amountTransfers > monthlyLimitVKPay) return -1.0
        }
        else -> return -1.0
    }

    val result: Double = when (cardTypeAndAccount) {
        "Mastercard", "Maestro" -> {
            val totalAmount = amountOfPreviousTransfers + amountTransfers
            val freeLimit = 75_000.0
            if (totalAmount in 300.0..freeLimit) {
                0.0
            } else {
                amountTransfers * 0.006 + 20.0
            }
        }
        "Visa", "Мир" -> maxOf(amountTransfers * 0.0075, 35.0)
        "VK Play" -> 0.0
        else -> -1.0
    }
    return result
}