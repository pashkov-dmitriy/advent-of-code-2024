package ru.myproj

import day2

val solutions = listOf(::day1, ::day2)

fun main() {
    println("Advent of Code 2024")

    solutions.forEachIndexed { i, solution ->
        val day = i + 1

        println("---------- Day $day ----------")
        solutions[i]("C:\\AoC2024\\DAY$day.txt").onFailure { ex ->
            println("Execution failed")
            println(ex)
        }
            .onSuccess { res ->
                println("Day $day result is $res")
            }
    }
}