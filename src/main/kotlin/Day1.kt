package ru.myproj

import kotlin.math.abs

/*
    Advent of Code 2024 Day 1 solution.
    There is input file that contains two columns of numbers like:
            1 5
            7 2
            2 4
    Pair up the smallest number in the left list with the smallest number in the right list,
    then the second-smallest left number with the second-smallest right number, and so on.
    Within each pair, figure out how far apart the two numbers are;
    you'll need to add up all of those distances.
    For example, if you pair up a 3 from the left list with a 7 from the right list, the distance apart is 4;
    if you pair up a 9 with a 3, the distance apart is 6.
 */

fun day1(filename: String): Result<Int> {
    return parseInput(filename){ file ->
        var left = mutableListOf<Int>()
        var right = mutableListOf<Int>()
        file.forEachLine { line ->
            val (leftNum, rightNum) = line.split("\\s+".toRegex()).map { s -> s.toInt() }
            left.add(leftNum)
            right.add(rightNum)
        }
        Pair(left, right)
    }
        .map { pair ->
            sumOfDistances(pair.first, pair.second)
        }
}

private fun sumOfDistances(left: List<Int>, right: List<Int>): Int {
    val sortedLeft = left.sorted()
    val sortedRight = right.sorted()

    return sortedLeft
        .zip(sortedRight)
        .map { (l, r) -> abs(l - r) }
        .sum()
}
