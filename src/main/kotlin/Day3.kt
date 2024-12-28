import ru.myproj.parseInput
import java.io.Reader


private fun Int.toCharStrict(): Char {
    return if (this in Char.MIN_VALUE.code..Char.MAX_VALUE.code)
               this.toChar()
           else throw IllegalStateException()
}

private interface Executable<T> {
    fun execute(): Result<T>
}

private sealed class Command<T>() : Executable<T> {
    data class Mul(private var _params: List<Number> = listOf()) : Command<Number>() {
        var params = _params
            set(value) {
                _params = value
            }

        companion object {
            val name = "mul"
        }

        private fun multiply(a: Number, b: Number): Number {
            return when {
                a is Double || b is Double -> a.toDouble() * b.toDouble()
                a is Float || b is Float -> a.toFloat() * b.toFloat()
                a is Long || b is Long -> a.toLong() * b.toLong()
                else -> a.toInt() * b.toInt()
            }
        }

        override fun execute(): Result<Number>
        {
            return runCatching {
                params.reduce { acc, num ->
                    multiply(acc, num)
                }
            }
        }
    }
    object Invalid : Command<Nothing>() {
        override fun execute(): Result<Nothing> {
            return runCatching { throw IllegalStateException() }
        }
    }
}

fun day3(filename: String): Result<Int> {
    return parseInput(filename) { file ->
        val reader = file.reader()

        while (reader.ready()) {
            val command = readCommand(reader)
            when (command) {
                is Command.Mul-> {
                    runCatching {
                        val num1 = parseNumber(reader).getOrThrow()
                        val num2 = parseNumber(reader).getOrThrow()
                        var paramList = listOf<Int>(num1, num2)
                        command.params = paramList
                        command.execute()
                    }

                }
                is Command.Invalid -> command.execute()
            }
        }

    }
        .map {

        }
}

private fun readCommand(reader: Reader): Command<*> {
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

private fun parseCommand(str: String): Command<*> {
    return when (str) {
        Command.Mul.name -> Command.Mul()
        else             -> Command.Invalid
    }
}

private fun parseNumber(reader: Reader): Result<Int> {
    val numberBuilder = StringBuilder()
    var charCode: Int = -1

    runCatching {
        while (reader.read().also { charCode = it } != -1) {
            val char = charCode.toCharStrict()

            if (char.isDigit()) {
                numberBuilder.append(char)
                if (numberBuilder.length > 3)
                    return Result.failure(IllegalStateException("Max length reached"))
            }
            else {
                return if (numberBuilder.isNotEmpty())
                           return Result.success(numberBuilder.toString().toInt())
                       else return Result.failure(IllegalStateException("No digits in the stream"))
            }
        }
    }
    return Result.failure(IllegalStateException("Max length reached"))
}