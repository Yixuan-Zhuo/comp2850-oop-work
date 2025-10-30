fun main() {
    val wordFilePath = "data/words.txt"
    var canContinue = true

    // 1. Read the list of words
    val wordList = try {
        Wordle.readWordList(wordFilePath)
    } catch (e: java.io.IOException) {
        println("Error reading word file: ${e.message}")
        canContinue = false
        mutableListOf()
    }

    // 2. Check if the list is empty
    if (canContinue && wordList.isEmpty()) {
        println("No valid ${Wordle.WORD_LENGTH}-letter words found in $wordFilePath")
        canContinue = false
    }

    // Reduce the total number of returns (first return)
    if (!canContinue) {
        return
    }

    // 3. Select target word and game configuration
    val targetWord = Wordle.pickRandomWord(wordList)
    val maxAttempts = Wordle.MAX_ATTEMPTS

    // 4. Game Main Loop
    println("Welcome to Wordle! You have $maxAttempts attempts to guess the ${Wordle.WORD_LENGTH}-letter word.")
    for (attempt in 1..maxAttempts) {
        val guess = Wordle.obtainGuess(attempt)
        val matches = Wordle.evaluateGuess(guess, targetWord)
        Wordle.displayGuess(guess, matches)

        // Return if you guess correctly (second return)
        if (matches.all { it == 1 }) {
            println("\nCongratulations! You guessed the word in $attempt attempts!")
            return
        }
    }

    // Game Over
    println("\nGame Over! The word was: $targetWord")
}
