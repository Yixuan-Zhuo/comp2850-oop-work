// Implement the six required functions here

import java.io.File
import kotlin.random.Random

object Wordle {
    const val MAX_ATTEMPTS = 10
    const val WORD_LENGTH = 5

    // 1. Determine whether the word has 5 letters
    fun isValid(word: String): Boolean = word.length == WORD_LENGTH

    // 2. Read word list from file
    fun readWordList(filename: String): MutableList<String> = File(filename)
        .readLines()
        .filter { isValid(it.trim()) }
        .map { it.trim().uppercase() }
        .toMutableList()

    // 3. Randomly select a word and remove it from the list
    fun pickRandomWord(words: MutableList<String>): String {
        require(words.isNotEmpty()) { "Word list cannot be empty" }
        val randomIndex = Random.nextInt(words.size)
        return words.removeAt(randomIndex)
    }

    // 4. Loop until the input is valid
    fun obtainGuess(attempt: Int): String {
        while (true) {
            print("Attempt $attempt: ")
            val guess = readln().trim().uppercase()
            if (isValid(guess)) {
                return guess
            } else {
                println("Invalid guess! Please enter a $WORD_LENGTH-letter word.")
            }
        }
    }

    // 5. 0 indicates no match, 1 indicates a match
    fun evaluateGuess(guess: String, target: String): List<Int> =
        guess.zip(target).map { (g, t) -> if (g == t) 1 else 0 }

    // 6. If it matches, it will display letters, if it doesn't match, it will display ?
    fun displayGuess(guess: String, matches: List<Int>) {
        if (guess.length != WORD_LENGTH || matches.size != WORD_LENGTH) {
            println("Invalid guess or match result")
            return
        }
        var display = ""
        for (i in 0 until WORD_LENGTH) {
            val char = guess[i]
            val matchResult = matches[i]
            display += if (matchResult == 1) char else '?'
        }
        println(display)
    }
}
