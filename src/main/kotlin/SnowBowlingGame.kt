/*
Bowling Game - Design Understanding

[X] Setup the Frame
    [X] 10 Frames with 2 Chances i.e. rolls
[X] Scoring Logic - Deriving Total Score & Each Frame Score
    [X] Open Frame - 1 Points for each pin
    [X] Bonus Points
        [X] OnStrike
            [X] Must: All the pins are knocked at the very
                1st roll of the frame
            [X] 10 points + No. of pins dropped on the next
                Frame i.e. count 2 rolls
        [X] OnSpare
            [X] Knocking all the pins fewer in first 1st roll and
                remaining in the 2nd roll
            [X] 10 points + No. of pins dropped on the 1st roll of the
               next frame i.e. count of 1st roll alone.
[X] ShowScoreCard
 */

/**
 * Created by Rajendhiran Easu on 05/02/24.
 * Description: SnowBowling Game Logic
 */

data class Frame(
    val roll1Hits: Int = 0,
    val roll2Hits: Int = 0,
) {
    var hasSpare: Boolean = false
    var hasStrike: Boolean = false
    var gameTotalScoreOnEachFrame: Int = 0
}

class Bowling {
    private val frames = Array(10) { Frame() }

    fun setup(frames: Array<Frame>) {
        frames.copyInto(this.frames)
    }

    fun score(): Int {
        var totalScore = 0
        for ((fIndex, frame) in frames.withIndex()) {
            totalScore += frame.roll1Hits + frame.roll2Hits
            when {
                isStrike(frame.roll1Hits) -> {
                    frame.hasStrike = true
                    totalScore += strikeBonus(fIndex)
                }

                isSpare(frame.roll1Hits, frame.roll2Hits) -> {
                    frame.hasSpare = true
                    totalScore += spareBonus(fIndex)
                }
            }
            frame.gameTotalScoreOnEachFrame = totalScore
        }
        return totalScore
    }

    // Identify the frame ended with strike or spare
    private fun isStrike(roll1Hit: Int): Boolean = roll1Hit == 10

    private fun isSpare(roll1: Int, roll2: Int) = roll1 != 10 && (roll1 + roll2) == 10

    // Calculate Bonus Points for strike
    private fun strikeBonus(frameIndex: Int) = if (frameIndex + 1 >= frames.size) 0 else {
        val nextFrame = frames[frameIndex + 1]
        nextFrame.roll1Hits + nextFrame.roll2Hits
    }

    private fun spareBonus(frameIndex: Int) = if (frameIndex + 1 >= frames.size) 0 else {
        val nextFrame = frames[frameIndex + 1]
        nextFrame.roll1Hits
    }

    fun showScoreCard() {
        frames.forEachIndexed { index, frame ->
            println(buildString {
                append("Frame ${index + 1} - Score: ${frame.gameTotalScoreOnEachFrame} ")
                append(
                    if (frame.hasSpare) "[${frame.roll1Hits} || / ]"
                    else if (frame.hasStrike) "[ X ]"
                    else "[${frame.roll1Hits} || ${frame.roll2Hits} ]"
                )
            })
        }
        println()
    }
}

fun main() {
    val game = Bowling()
    // Construct frames directly
    val frames = arrayOf(Frame(10, 0), Frame(7, 3),
        Frame(9, 0), Frame(10, 0),
        Frame(0, 8), Frame(8, 2),
        Frame(0, 6), Frame(10, 0),
        Frame(10, 0), Frame(10, 8)
    )
    game.setup(frames)
    println(game.score())
    game.showScoreCard()
}
