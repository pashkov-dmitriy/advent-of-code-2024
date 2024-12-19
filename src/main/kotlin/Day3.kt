import ru.myproj.parseInput
import java.io.Reader


private fun Int.toCharStrict(): Char {
    return if (this in Char.MIN_VALUE.code..Char.MAX_VALUE.code)
               this.toChar()
           else throw IllegalStateException()
}

private enum class Command(string: String) {
    MUL("mul"),
    INVALID("")
}

fun day3(filename: String): Result<Int> {
    return parseInput(filename) { file ->
        val correctSymbols = listOf<Char>(
            '0', '1', '2', '3', '4',
            '5', '6', '7', '8', '9',
            'l', 'm', 'u', '(', ')',
            ','
        )
        val reader = file.reader()

        while (reader.ready()) {
            when (readCommand(reader)) {
                Command.MUL -> {

                }
                Command.INVALID -> TODO()
            }
        }

    }
        .map {

        }
}

private fun readCommand(reader: Reader): Command {
    var ch = reader.read()
    if (ch == -1) throw IllegalArgumentException("End of stream reached unexpectedly.")
    var commandString = ""

    while (ch.toCharStrict() != '(') {
        if (ch == -1) throw IllegalArgumentException("End of stream reached unexpectedly.")
        commandString += ch.toCharStrict()
        ch = reader.read()
    }

    return parseCommand(commandString)
}

private fun parseCommand(str: String): Command {
    return when (str) {
        Command.MUL.name -> Command.MUL
        else             -> Command.INVALID
    }
}

private fun parseNumber(reader: Reader): Int {
    var ch = reader.read()
    if (ch == -1) throw IllegalArgumentException("End of stream reached unexpectedly.")
    var numString = ""


}