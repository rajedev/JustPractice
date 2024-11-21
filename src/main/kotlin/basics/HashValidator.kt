package basics

/**
 * Created by Rajendhiran Easu on 21/11/24.
 * Description:
 */

fun main() {
// Test cases
    val sha1 = "2ef7bde608ce5404e97d5f042f95f89f1c232871" // valid SHA-1
    val sha256 = "9d5e3d2a1f31a37673f0cd27b6256e42d44f98860b9f8b72456fa5f09fd94d97" // valid SHA-256
    val md5 = "098f6bcd4621d373cade4e832627b4f6" // valid MD5

    // Case 1: All hashes valid
    println()
    validateHashValues(sha1, sha256, md5).apply {
        println(this.isSuccess)
        println(this.getOrNull())
        println(this.exceptionOrNull()?.message)
    }

    // Case 2: Missing all hashes
    println()
    validateHashValues(null, null, null).apply {
        println(this.isSuccess)
        println(this.getOrNull())
        println(this.exceptionOrNull()?.message)
    }

    // Case 3: Invalid SHA-1 hash
    println()
    validateHashValues("invalidsha1hash", sha256, md5).apply {
        println(this.isSuccess)
        println(this.getOrNull())
        println(this.exceptionOrNull()?.message)
    }

    // Case 4: Invalid MD5 hash
    println()
    validateHashValues(sha1, sha256, "invalidmd5hash").apply {
        println(this.isSuccess)
        println(this.getOrNull())
        println(this.exceptionOrNull()?.message)
    }

    // Case 5: Invalid SHA-256 hash
    println()
    validateHashValues(sha1, "invalidsha256hash", md5).apply {
        println(this.isSuccess)
        println(this.getOrNull())
        println(this.exceptionOrNull()?.message)
    }

    // Case 6: Multiple invalid hashes
    println()
    validateHashValues("invalidsha1hash", "invalidsha256hash", "invalidmd5hash").apply {
        println(this.isSuccess)
        println(this.getOrNull())
        println(this.exceptionOrNull()?.message)
    }

    // Case 7: Invalid SHA-256 & 1 hash
    println()
    validateHashValues("invalidsha1hash", "invalidsha256hash", md5).apply {
        println(this.isSuccess)
        println(this.getOrNull())
        println(this.exceptionOrNull()?.message)
    }

    // Case 8: Invalid SHA-1 & MD5 hash
    println()
    validateHashValues("invalidsha1hash", sha256, "invalidshamd5hash").apply {
        println(this.isSuccess)
        println(this.getOrNull())
        println(this.exceptionOrNull()?.message)
    }
}

fun isValidHexadecimal(input: String): Boolean =
    input.matches(Regex("^[0-9a-fA-F]+$"))

fun validateHash(input: String, algorithm: String): Boolean = when (algorithm.uppercase()) {
    "SHA-1" -> input.length == 40 && isValidHexadecimal(input)
    "SHA-256" -> input.length == 64 && isValidHexadecimal(input)
    "MD5" -> input.length == 32 && isValidHexadecimal(input)
    else -> false
}

const val SHA1 = "SHA-1"
const val SHA256 = "SHA-256"
const val MD5 = "MD5"

internal fun hasValidHashValues(sha1: String?, sha256: String?, md5: String?): Result<String> {
    val hashValues = mapOf(
        SHA1 to sha1,
        SHA256 to sha256,
        MD5 to md5
    )

    // Verifying least one hash is provided
    if (hashValues.values.all { it.isNullOrEmpty() }) {
        return Result.failure(Exception("At least one hash value (SHA-1, SHA-256, or MD5) must be provided."))
    }

    // Collect invalid hashes key
    val invalidHashes = hashValues
        .filter { it.value?.let { value -> !validateHash(value, it.key) } == true }
        .map { "${it.key} (${it.value})" }

    // If there are no invalid hashes, return success
    if (invalidHashes.isEmpty()) {
        return Result.success("All hash values are valid.")
    }

    // Construct the error message on failure
    val errorMessage = buildString {
        append("Invalid ")

        append(invalidHashes.takeIf { it.size <= 2 }
            ?.joinToString(" & ")  // Handles both 1 and 2 invalid hashes
            ?: (invalidHashes.dropLast(1)
                .joinToString(", ") + " and ${invalidHashes.last()}") // Handles more than 2
        )
        append(" hash values.")
    }

    return Result.failure(Exception(errorMessage))
}

fun validateHashValues(sha1: String?, sha256: String?, md5: String?): Result<String> {
    val hashValues = mapOf(
        "SHA-1" to sha1,
        "SHA-256" to sha256,
        "MD5" to md5
    )

    // Ensure at least one hash is provided
    if (hashValues.values.all { it.isNullOrEmpty() }) {
        return Result.failure(Exception("At least one hash value (SHA-1, SHA-256, or MD5) must be provided."))
    }

    // Collect invalid hashes without using null assertion
    val invalidHashes = hashValues
        .filter { it.value?.let { value -> !validateHash(value, it.key) } == true }
        .map { "${it.key} (${it.value})" }
    //.map { it.key to it.value }

    // If there are no invalid hashes, return success
    if (invalidHashes.isEmpty()) {
        return Result.success("Success: All hash values are valid.")
    }

    // Construct the error message
    val errorMessage = buildString {
        append("Invalid ")
        // Handle the case for 1, 2, or more invalid hashes
        append(
            invalidHashes.takeIf { it.size <= 2 }
                ?.joinToString(" & ")
                ?: (invalidHashes.dropLast(1).joinToString(", ") +
                        " and ${invalidHashes.last()}") // Handles more than 2
        )

        /*append(
            invalidHashes.takeIf { it.size <= 2 }
                ?.joinToString(" & ") { (hAlgorith, hValue) ->
                    "$hAlgorith ($hValue)"
                } // Handles both 1 and 2 invalid hashes
                ?: (invalidHashes.dropLast(1)
                    .joinToString(", ") { (hAlgorith, hValue) ->
                        "$hAlgorith ($hValue)"
                    } + " and ${
                    invalidHashes.last().let { (hAlgorith, hValue) ->
                        "$hAlgorith ($hValue)"
                    }
                }") // Handles more than 2
        )*/
        /*append(
            when (invalidHashes.size) {
                1 -> invalidHashes.first() // Single item
               // 2 -> invalidHashes.joinToString(" & ") // Two items
                else -> invalidHashes.dropLast(1)
                    .joinToString(", ") + " and ${invalidHashes.last()}" // More than two items
            }
        )*/
        append(" hash values.")
    }

    /*// Construct the error message based on the number of invalid hashes
    val errorMessage = when (invalidHashes.size) {
        1 -> "Invalid ${invalidHashes.first()} hash value."
        2 -> "Invalid ${invalidHashes.joinToString(" & ")} hash values."
        else -> "Invalid ${
            invalidHashes.dropLast(1).joinToString(", ")
        } and ${invalidHashes.last()} hash values."
    }*/

    return Result.failure(Exception(errorMessage))
}
