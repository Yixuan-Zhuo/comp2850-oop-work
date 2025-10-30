import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.shouldBe
import java.io.File

@Suppress("unused")
class WordleTest : StringSpec({
    // Write your tests here
    // isValid
    "isValid returns true for 5-letter word" {
        Wordle.isValid("apple") shouldBe true
        Wordle.isValid("grape") shouldBe true
    }

    "isValid returns false for non-5-letter word" {
        Wordle.isValid("app") shouldBe false
        Wordle.isValid("apples") shouldBe false
        Wordle.isValid("") shouldBe false
    }

    // readWordList
    "readWordList filters non-5-letter words" {
        val testFile = File.createTempFile("test_words", ".txt")
        testFile.writeText("apple\nbanana\ngrape\ndate\nlemon")
        val result = Wordle.readWordList(testFile.path)
        result shouldContainExactly listOf("APPLE", "GRAPE", "LEMON")
        testFile.delete()
    }

    "readWordList returns empty list for no valid words" {
        val testFile = File.createTempFile("empty_words", ".txt")
        testFile.writeText("banana\ndate\nkiwi")
        Wordle.readWordList(testFile.path) shouldBe emptyList()
        testFile.delete()
    }

    // pickRandomWord
    "pickRandomWord removes and returns a word from list" {
        val words = mutableListOf("cat", "dog", "bat")
        val picked = Wordle.pickRandomWord(words)
        listOf("cat", "dog", "bat").contains(picked) shouldBe true
        words shouldNotContain picked
    }

    "pickRandomWord throws error for empty list" {
        val emptyList = mutableListOf<String>()
        shouldThrow<IllegalArgumentException> {
            Wordle.pickRandomWord(emptyList)
        }.message shouldBe "Word list cannot be empty"
    }

    // evaluateGuess
    "evaluateGuess returns 1 for exact matches, 0 otherwise" {
        Wordle.evaluateGuess("apple", "apple") shouldContainExactly listOf(1, 1, 1, 1, 1)
        Wordle.evaluateGuess("apric", "apple") shouldContainExactly listOf(1, 1, 0, 0, 0)
        Wordle.evaluateGuess("xyzzy", "apple") shouldContainExactly listOf(0, 0, 0, 0, 0)
    }
})
