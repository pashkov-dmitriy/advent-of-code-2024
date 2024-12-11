import ru.myproj.parseInput


fun day2(filename: String): Result<Int> {
    return parseInput(filename) { file ->
        val lines = file.readLines()
        lines.map { line ->
            line.split(Regex("\\s"))
                .map { it.toInt() }
        }
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

private sealed class SequenceType {
    object Increasing : SequenceType()
    object Decreasing : SequenceType()
    object Unsafe : SequenceType()
}

/*
    Function to check if order is safe according to AoC 2024 Day 2 rules.
 */
private fun checkDataIsSafe(data: List<Int>): Boolean {

    fun checkDecOrInc(left: Int, right: Int) = when (left - right) {
        in -3..-1 -> SequenceType.Decreasing
        in 1..3 -> SequenceType.Increasing
        else -> SequenceType.Unsafe
    }

    tailrec fun checkDataIsSafeInternal(
        data: List<Int>,
        isDec: Boolean,
        isInc: Boolean
    ) : Boolean {
        if (data.size <= 1) return true

        val res = checkDecOrInc(data[0], data[1])

        return when (res) {
            is SequenceType.Decreasing -> {
                if (!isInc) checkDataIsSafeInternal(data.drop(1), true, isInc)
                else false
            }
            is SequenceType.Increasing -> {
                if (!isDec) checkDataIsSafeInternal(data.drop(1), isDec, true)
                else false
            }
            is SequenceType.Unsafe -> false
        }
    }

    return checkDataIsSafeInternal(data, false, false)
}

