import ru.myproj.parseInput
import java.io.File


private fun parsingRule(file: File): List<List<Int>> {
    val lines = file.readLines()
    return lines.map { line ->
        line.split(Regex("\\s"))
            .map { it.toInt() }
    }
}

/*
    Advent of Code 2024 Day 2 solution.
    There is input file that contains multiple rows of data or reports:
            7 6 4 2 1 //Safe because the levels are all decreasing by 1 or 2.
            1 2 7 8 9 // Unsafe because 2 7 is an increase of 5.
            9 7 6 2 1
            1 3 2 4 5 // Safe by removing the second level, 3
            8 6 4 4 1
            1 3 6 7 9
    This example data contains six reports each containing five levels.
    A report only counts as safe if both of the following are true:
        - The levels are either all increasing or all decreasing.
        - Any two adjacent levels differ by at least one and at most three.
    One bad value can be removed, so some reports can be converted to safe.
 */

fun day2(filename: String): Result<Int> {
    return parseInput(filename) { file ->
        parsingRule(file)
    }
        .map { res ->
            res.fold(0) { acc, value ->
                if (checkDataIsSafe(value)) {
                    acc + 1
                } else {
                    acc + 0
                }
            }
        }
}

/*
    Classes to indicate sequence type
 */
private sealed class SequenceType {
    object Increasing : SequenceType()
    object Decreasing : SequenceType()
    object Unsafe : SequenceType()
}

/*
    Function to check if order is safe according to AoC 2024 Day 2 rules.
    Takes one report and optional damper availability as parameters.
    Return true if the report is safe, otherwise false
 */
private fun checkDataIsSafe(data: List<Int>, damperAvailable: Boolean = false): Boolean {

    // Calculates current sequence type by two values
    fun checkDecOrInc(left: Int, right: Int) = when (left - right) {
        in -3..-1 -> SequenceType.Decreasing
        in 1..3 -> SequenceType.Increasing
        else -> SequenceType.Unsafe
    }

    // Main logic of the function
    tailrec fun checkDataIsSafeInternal(
        data: List<Int>,
        isDec: Boolean,
        isInc: Boolean,
        damperAvailable: Boolean
    ) : Boolean {
        if (data.size <= 1) return true

        val res = checkDecOrInc(data[0], data[1])

        return when (res) {
            is SequenceType.Decreasing -> {
                when {
                    isInc && damperAvailable -> checkDataIsSafeInternal(data.drop(1), true, false, false)
                    !isInc                   -> checkDataIsSafeInternal(data.drop(1), true, false, damperAvailable)
                    else                     -> false
                }
            }
            is SequenceType.Increasing -> {
                when {
                    isDec && damperAvailable -> checkDataIsSafeInternal(data.drop(1), false, true, false)
                    !isDec                   -> checkDataIsSafeInternal(data.drop(1), false, true, damperAvailable)
                    else                     -> false
                }
            }
            is SequenceType.Unsafe -> {
                when (damperAvailable) {
                    true -> checkDataIsSafeInternal(data.drop(1), isDec, isInc, false)
                    false -> false
                }
            }
        }
    }

    return checkDataIsSafeInternal(data, false, false, damperAvailable)
}

// Day 2 Second half solution
fun day2SecondHalf(filename: String): Result<Int> {
    return parseInput(filename) { file ->
        parsingRule(file)
    }
        .map { res ->
            res.fold(0) { acc, value ->
                if (checkDataIsSafe(value, true)) {
                    acc + 1
                } else {
                    acc + 0
                }
            }
        }
}

