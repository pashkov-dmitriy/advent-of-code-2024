package ru.myproj

import java.io.File

/*
    Arguments:
        filename    - text file to parse
        parsingRule - function, which takes file and produce result
    Function to parse input from file.

    This should be parsed according to parsingRule function.
    Returns parsed input in expected form, wrapped in Result.
 */
fun<E> parseInput(filename: String, parsingRule: (file: File) -> E): Result<E> {
    val result = runCatching {
        val file = File(filename)

        if (!file.isFile and !file.canRead()) throw IllegalStateException()
        else file
    }
    return result.map { file -> parsingRule(file) }
}