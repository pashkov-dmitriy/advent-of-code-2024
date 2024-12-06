package ru.myproj

val solutions = listOf(::day1, ::day2)

fun main() {
    println("Advent of Code 2024")

    solutions.forEachIndexed { i, solution ->
        val day = i + 1

        println("---------- Day $day ----------")
        day1("C:\\AoC2024\\DAY$day.txt").onFailure { ex ->
            println("Execution failed")
            println(ex)
        }
            .onSuccess { res ->
                println("Day $day result is $res")
            }
    }
}