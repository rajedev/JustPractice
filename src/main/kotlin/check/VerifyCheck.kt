package check

fun main() {
//  val inputNum = 5621
//  reverseInput1(inputNum)
//  reverseInput2(inputNum)
//  sumOfEvenNumbers(inputNum)

  val word = "tester"
  val candidate = "reTest"
//  val x = word.lowercase().filter { it.isLetter() }.toCharArray().sorted().joinToString("")
//  val y = word.toCharArray().sorted()
//  val z = candidate.toCharArray().sorted()
//  val res = y == z
//  println(res)
//  println(y)
//  println(z)
//  println("Signature for $word: ${anagramSignature(word)}")
//  println("Is '$candidate' an anagram of '$word'? ${isAnagram(word, candidate)}")
  //println(isAnagramCount(word, candidate))
  /*"14731".forEach { ch->
    val digit = ch - '0'
    println(digit)
  }*/

  /*val g = word.groupBy { it }//.mapValues { it.value.size }
  println(g)
  val g2 = word.groupBy { it }//.mapValues { it.value.size }
  println(g2)
  val g1 = word.groupBy { it }.map { it.key to it.value.size }.toMap()
  println(g1)
  val x = word.groupingBy { it }.eachCount()
  println(x)
  val countMap = mutableMapOf<Char, Int>()
  word.forEach {
    countMap[it] = countMap.getOrElse(it) { 0 } + 1
  }
  println(countMap)*/
  val countMap = mutableMapOf<Char, Int>()
  word.forEach {
    countMap[it] = countMap.getOrElse(it) { 0 } + 1
  }
  println(countMap)
  //println(countMap.maxByOrNull { it.value })
  val max = countMap.maxByOrNull { it.value }?.value ?:""
  println(max)
  println(countMap.filter { it.value==max }.keys.joinToString(""))
  //println(findFrequencyOfChar("testrrreer",'e'))
}

fun findFrequencyOfChar(word: String, char: Char): Int {
  var frequency = 0
  word.forEach {
      if(char == it) frequency++
  }
  //return frequency
  return word.count { it==char }
}

fun String.normalizeStr(): String = lowercase().filter { it.isLetter() }

fun isAnagramCount(s1: String, s2: String): Boolean {
  val normalized1 = s1.normalizeStr()
  val normalized2 = s2.normalizeStr()
  if (normalized1.length != normalized2.length) return false

  val count = IntArray(26) // assuming lowercase a-z
  val x = Array<String>(size = 10) {
    "nan"
  }

  s1.indices.forEach {
    val i = it
//  }
//
//  for (i in s1.indices) {
    println("i is $i")
    println(count.joinToString())
    println(normalized1[i] - 'a')
    println(normalized2[i] - 'a')
    count[normalized1[i] - 'a']++
    println("---->${count.joinToString()}")
    count[normalized2[i] - 'a']--
    println("---->${count.joinToString()} \n")
    println(count.joinToString())
  }
  return count.all { it == 0 }
}


/*fun isAnagramCount(s1: String, s2: String): Boolean {
  val normalizedLeft = s1.lowercase().filter { it.isLetter() }
  val normalizedRight = s2.lowercase().filter { it.isLetter() }
  if (normalizedLeft.length != normalizedRight.length) return false

  val count = IntArray(26)
  for (i in normalizedLeft.indices) {
    count[normalizedLeft[i] - 'a']++
    count[normalizedRight[i] - 'a']--
  }
  return count.all { it == 0 }
}*/
fun reverseInput(input: Int): String {
  var rev = ""
  var tempInput = input
  while (tempInput > 0) {
    val rValue = tempInput % 10
    rev = "$rev$rValue"
    tempInput /= 10
  }
  println("rev: $rev")
  return rev
}

fun reverseInput1(input: Int): String {
  val rev = StringBuilder()
  var tempInput = input
  while (tempInput > 0) {
    rev.append(tempInput % 10)
    tempInput /= 10
  }
  println("rev: $rev")
  return rev.toString()
}

fun reverseInput2(input: Int): Int {
  var rev = 0
  var tempInput = input
  while (tempInput > 0) {
    rev = rev * 10 + tempInput % 10
    tempInput /= 10
  }
  println("rev: $rev")
  return rev
}


fun sumOfEvenDigits(n: Int): Int {
  var sum = 0
  var num = n
  while (num > 0) {
    val digit = num % 10
    if (digit % 2 == 0) {
      sum += digit
    }
    num /= 10
  }
  println("rev: $sum")
  return sum
}

fun anagramSignature(text: String): String {
  return text.lowercase()
    .filter { it.isLetter() }
    .toCharArray()
    .sorted()
    .joinToString("")
}

fun isAnagram(base: String, candidate: String): Boolean {
  return anagramSignature(base) == anagramSignature(candidate)
}
