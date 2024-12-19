package days

class day7 : Iday {
    override fun part1(inputLines: List<String>) {
        println("Day 7 part 1")
        var total = 0L

        for (l in inputLines.indices){
            println("Line: ${inputLines[l]}")
            val answer = inputLines[l].split(":")[0].toLong()
            //println("answer: $answer")
            //println("split0: ${inputLines[l].split(":")[0]}")
            //println("split1: ${inputLines[l].split(":")[1].trim().split(" ")}")
            val formulaNumbers = inputLines[l].split(":")[1].trim().split(" ").map() { it.toLong() }
            //println("formulaNumbers: $formulaNumbers")
            var stack = ArrayDeque(formulaNumbers)
            var binaryNum = 0
            var calculatedValue = caculateWithBinary(binaryNum, stack, answer)
            if (calculatedValue != 0L) {
                println("Total: ${total}, CalculatedValue: $calculatedValue")
                total += calculatedValue
            }
        }

        println("Total: $total")
    }


    private fun caculateWithBinary(binaryNumber: Int, stack: ArrayDeque<Long>, expectedAnswer: Long) : Long {
        var actualAnswer = 0L
        var newBinaryNumber = binaryNumber

        for(n in 0 until Math.pow(2.toDouble(), stack.size.toDouble()).toInt()) {
            actualAnswer = calculateStringWithBinaryRepresentationForOperators(stack, newBinaryNumber)
            if (actualAnswer == expectedAnswer) {
                //println("We found an answer!")
                return expectedAnswer
            }
            newBinaryNumber += 1
        }
        return 0L
    }

    private fun calculateStringWithBinaryRepresentationForOperators(stack: ArrayDeque<Long>, binaryNumber: Int) : Long {
        var actualAnswer = 0L
        var binaryAsString = Integer.toBinaryString(binaryNumber).padStart(Int.SIZE_BITS, '0')
        var positionInBinary = binaryAsString.length - 1
        //println("actualAnswer: $actualAnswer, binaryAsString: $binaryAsString")
        //println("len(positionInBinary): ${binaryAsString.length}")
        for(n in stack.indices) {
            //println("Stack[$n]: ${stack[n]}")
            if (n == 0) {
                actualAnswer = stack[n]
            }
            else {
                if (binaryAsString.elementAt(positionInBinary) == '0') {
                    actualAnswer += stack[n]
                }
                else {
                    actualAnswer *= stack[n]
                }
                positionInBinary -= 1
            }

        }
        //println("Returning: $actualAnswer")
        return actualAnswer
    }


    override fun part2(inputLines: List<String>) {
        println("Day 7 part 2")
        var total = 0L

        for (l in inputLines.indices){
            println("Line: ${inputLines[l]}")
            val answer = inputLines[l].split(":")[0].toLong()
            //println("answer: $answer")
            //println("split0: ${inputLines[l].split(":")[0]}")
            //println("split1: ${inputLines[l].split(":")[1].trim().split(" ")}")
            val formulaNumbers = inputLines[l].split(":")[1].trim().split(" ").map() { it.toLong() }
            //println("formulaNumbers: $formulaNumbers")
            var stack = ArrayDeque(formulaNumbers)
            var binaryNum = 0
            var calculatedValue = caculateWithTernary(binaryNum, stack, answer)
            if (calculatedValue != 0L) {
                println("Total: ${total}, CalculatedValue: $calculatedValue")
                total += calculatedValue
            }
        }

        println("Total: $total")
    }
    private fun caculateWithTernary(binaryNumber: Int, stack: ArrayDeque<Long>, expectedAnswer: Long) : Long {
        var actualAnswer = 0L
        var newBinaryNumber = binaryNumber

        for(n in 0 until Math.pow(3.toDouble(), stack.size.toDouble()).toInt()) {
            actualAnswer = calculateStringWithTernaryRepresentationForOperators(stack, newBinaryNumber)
            if (actualAnswer == expectedAnswer) {
                println("We found an answer!")
                return expectedAnswer
            }
            newBinaryNumber += 1
        }
        return 0L
    }
    private fun calculateStringWithTernaryRepresentationForOperators(stack: ArrayDeque<Long>, binaryNumber: Int) : Long {
        var actualAnswer = 0L
        var binaryAsString = decimalToTernary(binaryNumber)
        var positionInBinary = binaryAsString.length - 1
        //println("actualAnswer: $actualAnswer, binaryAsString: $binaryAsString")
        //println("len(positionInBinary): ${binaryAsString.length}")
        for(n in stack.indices) {
            //println("Stack[$n]: ${stack[n]}")
            if (n == 0) {
                actualAnswer = stack[n]
            }
            else {
                if (binaryAsString.elementAt(positionInBinary) == '0') {
                    actualAnswer += stack[n]
                }
                else if (binaryAsString.elementAt(positionInBinary) == '1') {
                    actualAnswer *= stack[n]
                }
                else {
                    // binaryAsString = '2'
                    actualAnswer = (actualAnswer.toString() + stack[n].toString()).toLong()
                }
                positionInBinary -= 1
            }

        }
            //println("Returning: $actualAnswer")
        return actualAnswer
    }

    fun decimalToTernary(n: Int): String {
        var num = n
        val ternary = StringBuilder()
        while (num > 0) {
            ternary.append(num % 3)
            num /= 3
        }
        return ternary.reverse().toString().padStart(Int.SIZE_BITS, '0')
    }
}