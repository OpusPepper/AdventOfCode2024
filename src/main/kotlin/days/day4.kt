package days


class matrixPosition(r: Int, c: Int) {
    var row: Int = r
    var col: Int = c
}


class day4 : Iday {
    override fun part1(inputLines: List<String>) {
        var total = 0
        val matrixInput = Array(inputLines.size) { CharArray(inputLines.elementAt(0).length)}
        val xPositions = mutableListOf<matrixPosition>()

        for(l in inputLines.indices) {
            println("Line: ${inputLines.elementAt(l)}")

            for (c in inputLines[l].indices) {
                matrixInput[l][c] = inputLines[l].elementAt(c)

                if (matrixInput[l][c] == 'X') {
                    val newxPosition = matrixPosition(l, c)
                    xPositions.add(newxPosition)
                }
            }
        }

        printOutMatrix(matrixInput)

        for(p in xPositions) {

            val strings = findXmasStrings(matrixInput, p)
            print("Strings: $strings : ")

            val countOfStrings = strings.count { x -> x == "XMAS"}
            println("$countOfStrings")
            total += countOfStrings
        }

        println("Total: $total")
    }
    override fun part2(inputLines: List<String>) {
        var total = 0
        val matrixInput = Array(inputLines.size) { CharArray(inputLines.elementAt(0).length)}
        val aPositions = mutableListOf<matrixPosition>()

        for(l in inputLines.indices) {
            println("Line: ${inputLines.elementAt(l)}")

            for (c in inputLines[l].indices) {
                matrixInput[l][c] = inputLines[l].elementAt(c)

                if (matrixInput[l][c] == 'A') {
                    val newxPosition = matrixPosition(l, c)
                    aPositions.add(newxPosition)
                }
            }
        }

        printOutMatrix(matrixInput)

        for(p in aPositions) {

            val strings = findX_masStrings(matrixInput, p)
            print("Strings: $strings : ")

            val countOfStrings = strings.count { x -> x == "MAS"}
            print("$countOfStrings")
            if (countOfStrings == 2) {
                println(" : counts towards total")
                total += 1
            }else{
                println(" : does not count towards total")
            }

        }

        println("Total: $total")

    }

    private fun printOutMatrix(inputMatrix: Array<CharArray> ) {
        println("- **************** -")
        println("Printing inputMatrix")
        println("- ---------------- -")
        for (r in inputMatrix.indices) {
            for (c in inputMatrix[r].indices) {
                print(inputMatrix[r][c])
            }
            println()
        }
        println()
        println("- **************** -")
    }

    private fun findXmasStrings(inputMatrix: Array<CharArray> , startingPosition: matrixPosition) : MutableList<String> {
        val listOfFoundStrings = mutableListOf<String>()
        val r = startingPosition.row
        val c = startingPosition.col

        val stringLeftToRight = "${inputMatrix.getOrNull(r)?.getOrNull(c)}${inputMatrix.getOrNull(r)?.getOrNull(c + 1)}${inputMatrix.getOrNull(r)?.getOrNull(c + 2)}${inputMatrix.getOrNull(r)?.getOrNull(c + 3)}"
        val stringRightToLeft = "${inputMatrix.getOrNull(r)?.getOrNull(c)}${inputMatrix.getOrNull(r)?.getOrNull(c - 1)}${inputMatrix.getOrNull(r)?.getOrNull(c -2)}${inputMatrix.getOrNull(r)?.getOrNull(c -3)}"
        val stringTopToBottom = "${inputMatrix.getOrNull(r)?.getOrNull(c)}${inputMatrix.getOrNull(r+1)?.getOrNull(c)}${inputMatrix.getOrNull(r+2)?.getOrNull(c)}${inputMatrix.getOrNull(r+3)?.getOrNull(c)}"
        val stringBottomToTop = "${inputMatrix.getOrNull(r)?.getOrNull(c)}${inputMatrix.getOrNull(r-1)?.getOrNull(c)}${inputMatrix.getOrNull(r-2)?.getOrNull(c)}${inputMatrix.getOrNull(r-3)?.getOrNull(c)}"
        val stringDiagToUpperRight = "${inputMatrix.getOrNull(r)?.getOrNull(c)}${inputMatrix.getOrNull(r-1)?.getOrNull(c+1)}${inputMatrix.getOrNull(r-2)?.getOrNull(c+2)}${inputMatrix.getOrNull(r-3)?.getOrNull(c+3)}"
        val stringDiagToLowerRight = "${inputMatrix.getOrNull(r)?.getOrNull(c)}${inputMatrix.getOrNull(r+1)?.getOrNull(c+1)}${inputMatrix.getOrNull(r+2)?.getOrNull(c+2)}${inputMatrix.getOrNull(r+3)?.getOrNull(c+3)}"
        val stringDiagToUpperLeft = "${inputMatrix.getOrNull(r)?.getOrNull(c)}${inputMatrix.getOrNull(r-1)?.getOrNull(c-1)}${inputMatrix.getOrNull(r-2)?.getOrNull(c-2)}${inputMatrix.getOrNull(r-3)?.getOrNull(c-3)}"
        val stringDiagToLowerLeft = "${inputMatrix.getOrNull(r)?.getOrNull(c)}${inputMatrix.getOrNull(r+1)?.getOrNull(c-1)}${inputMatrix.getOrNull(r+2)?.getOrNull(c-2)}${inputMatrix.getOrNull(r+3)?.getOrNull(c-3)}"

        listOfFoundStrings.add(stringLeftToRight)
        listOfFoundStrings.add(stringRightToLeft)
        listOfFoundStrings.add(stringTopToBottom)
        listOfFoundStrings.add(stringBottomToTop)
        listOfFoundStrings.add(stringDiagToUpperRight)
        listOfFoundStrings.add(stringDiagToLowerRight)
        listOfFoundStrings.add(stringDiagToUpperLeft)
        listOfFoundStrings.add(stringDiagToLowerLeft)

        return listOfFoundStrings
    }


    private fun findX_masStrings(inputMatrix: Array<CharArray> , startingPosition: matrixPosition) : MutableList<String> {
        val listOfFoundStrings = mutableListOf<String>()
        val r = startingPosition.row
        val c = startingPosition.col

        val stringDiagUpperRightToLowerLeft = "${inputMatrix.getOrNull(r-1)?.getOrNull(c-1)}${inputMatrix.getOrNull(r)?.getOrNull(c)}${inputMatrix.getOrNull(r+1)?.getOrNull(c+1)}"
        val stringDiagUpperLeftToLowerRight = "${inputMatrix.getOrNull(r-1)?.getOrNull(c+1)}${inputMatrix.getOrNull(r)?.getOrNull(c)}${inputMatrix.getOrNull(r+1)?.getOrNull(c-1)}"
        val stringDiagLowerRightToUpperLeft = "${inputMatrix.getOrNull(r+1)?.getOrNull(c+1)}${inputMatrix.getOrNull(r)?.getOrNull(c)}${inputMatrix.getOrNull(r-1)?.getOrNull(c-1)}"
        val stringDiagLowerLeftToUpperRight = "${inputMatrix.getOrNull(r+1)?.getOrNull(c-1)}${inputMatrix.getOrNull(r)?.getOrNull(c)}${inputMatrix.getOrNull(r-1)?.getOrNull(c+1)}"

        listOfFoundStrings.add(stringDiagUpperRightToLowerLeft)
        listOfFoundStrings.add(stringDiagUpperLeftToLowerRight)
        listOfFoundStrings.add(stringDiagLowerRightToUpperLeft)
        listOfFoundStrings.add(stringDiagLowerLeftToUpperRight)

        return listOfFoundStrings
    }
}