package days

class day3 : Iday {
    override fun part1(inputLines: List<String>) {
        var total = 0

        for(l in inputLines) {
            println("Line: $l")

            // regex exe\(\d{1,3},\d{1,3}\)*
            val regex = """mul\(\d{1,3},\d{1,3}\)""".toRegex()
            val matches = regex.findAll(l)
            for (m in matches) {
                println("Match: ${m.value}")
                val regex2 = """\d{1,3}""".toRegex()
                val matches2 = regex2.findAll(m.value)
                val number1 = matches2.elementAt(0).value.toInt()
                val number2 = matches2.elementAt(1).value.toInt()
                total += number1 * number2
            }
        }

        println("Total: $total")
    }
    override fun part2(inputLines: List<String>) {
        // regex /exe\(\d{1,3},\d{1,3}\)|don't\(\)|do\(\)/gm
        var total = 0

        var continueCalculating = true
        for(l in inputLines) {
            println("Line: $l")

            // regex exe\(\d{1,3},\d{1,3}\)*
            val regex = """mul\(\d{1,3},\d{1,3}\)|don't\(\)|do\(\)""".toRegex()
            val matches = regex.findAll(l)

            for (m in matches) {
                println("Match: ${m.value}")
                if (m.value == "don't()") {
                    continueCalculating = false
                } else if (m.value == "do()") {
                    continueCalculating = true
                } else if (continueCalculating) {
                    val regex2 = """\d{1,3}""".toRegex()
                    val matches2 = regex2.findAll(m.value)
                    val number1 = matches2.elementAt(0).value.toInt()
                    val number2 = matches2.elementAt(1).value.toInt()
                    total += number1 * number2
                }

            }
        }

        println("Total: $total")

    }
}