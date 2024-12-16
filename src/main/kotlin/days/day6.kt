package days

class MapSpace (r: Int, c: Int, content: Char) {
    var row = r
    var col = c
    var content = content
    var isTurning =false
    var countOfSame = 0

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is MapSpace) return false
        return row == other.row && col == other.col && content == other.content
    }
}

class Day6 : Iday {
    private lateinit var guardMapPart1: Array<CharArray>
    lateinit var startingPositionPart1: MapSpace
    private var listOfPositions = mutableListOf<MapSpace>()
    override fun part1(inputLines: List<String>) {
        var total = 0
        val guardMap = Array(inputLines.size) { CharArray(inputLines.elementAt(0).length)}

        for (l in inputLines.indices){
            println("Line: ${inputLines[l]}")
            for (c in 0..inputLines.elementAt(l).length - 1) {
                guardMap[l][c] = inputLines.elementAt(l).elementAt(c)
            }
        }
        val guardPosition = findGuardPosition(guardMap)
        startingPositionPart1 = MapSpace(guardPosition.row, guardPosition.col, guardPosition.content)

        do {
            moveGuard(guardMap, guardPosition)
            if (!listOfPositions.contains(MapSpace(guardPosition.row, guardPosition.col, 'X'))) {
                listOfPositions.add(MapSpace(guardPosition.row, guardPosition.col, 'X'))
            }

        } while (guardPosition.content != 'X')

        total = countGuardPath(guardMap)
        println()
        printGuardMap(guardMap)
        println("Total: $total")

        guardMapPart1 = guardMap
    }

    fun moveGuard(guardMap: Array<CharArray>, guardPosition: MapSpace)  {
        guardMap[guardPosition.row][guardPosition.col] = getPositionChar(guardMap, guardPosition)
        var validChars = arrayOf('#', 'O')
        when (guardPosition.content) {
            '^' -> {
                if (guardPosition.row > 0) {
                    if (validChars.contains(guardMap[guardPosition.row-1][guardPosition.col] )) {
                        guardPosition.content = '>'
                        guardPosition.isTurning = true
                    }
                    else {
                        guardPosition.row = guardPosition.row - 1
                        guardPosition.isTurning = false
                    }
                }
                else if (guardPosition.row == 0) {
                    guardPosition.content = 'X'
                }
            }
            '>' -> {
                if (guardPosition.col < guardMap[0].size - 1) {
                    if (validChars.contains(guardMap[guardPosition.row][guardPosition.col+1])) {
                        guardPosition.content = 'V'
                        guardPosition.isTurning = true
                    }
                    else {
                        guardPosition.col = guardPosition.col + 1
                        guardPosition.isTurning = false
                    }
                }
                else if (guardPosition.col == guardMap[0].size - 1) {
                    guardPosition.content = 'X'
                }
            }
            'V' -> {
                if (guardPosition.row < guardMap.size - 1) {
                    if (validChars.contains(guardMap[guardPosition.row+1][guardPosition.col])) {
                        guardPosition.content = '<'
                        guardPosition.isTurning = true
                    }
                    else {
                        guardPosition.row = guardPosition.row + 1
                        guardPosition.isTurning = false
                    }
                }
                else if (guardPosition.row == guardMap.size - 1) {
                    guardPosition.content = 'X'
                }
            }
            '<' -> {

                if (guardPosition.col > 0) {
                    if (validChars.contains(guardMap[guardPosition.row][guardPosition.col-1])) {
                        guardPosition.content = '^'
                        guardPosition.isTurning = true
                    }
                    else {
                        guardPosition.col = guardPosition.col -1
                        guardPosition.isTurning = false
                    }
                }
                else if (guardPosition.col == 0) {
                    guardPosition.content = 'X'

                }
            }
         }

    }

    fun getPositionChar(guardMap: Array<CharArray>, guardPosition: MapSpace) : Char {
        var currentChar = guardMap[guardPosition.row][guardPosition.col]
        var returnChar = '$'

        when (guardPosition.content) {
            '^', 'V' -> returnChar = '|'
            '>', '<' -> returnChar = '-'
            else -> returnChar = 'E'
        }
        if ((currentChar == '|' && returnChar == '-') || (currentChar == '-' && returnChar == '|')) {
            returnChar = '+'
        } else if (currentChar == '+') {
            returnChar = currentChar
        }
        if (currentChar == '#') {
            returnChar = 'e'
        }
        return returnChar
    }
    private fun findGuardPosition(guardMap: Array<CharArray> ): MapSpace {
        val guardChars = arrayOf('>','V', '<', '^')
        val guardPosition: MapSpace

        for (r in guardMap.indices) {
            for (c in guardMap[r].indices){
                if (guardChars.contains(guardMap[r][c])) {
                    guardPosition = MapSpace(r, c, guardMap[r][c])

                    return guardPosition
                }
            }
        }
        return MapSpace(-1, -1, 'X')
    }

    private fun countXs(guardMap: Array<CharArray> ): Int {
        var total = 0
        for (r in guardMap.indices) {
            for (c in guardMap[r].indices){
                if (guardMap[r][c] == 'X') {
                    total+= 1
                }
            }
        }
        return total
    }

    private fun countGuardPath(guardMap: Array<CharArray> ): Int {
        var total = 0
        var countTheseChars = arrayOf('|','-','+','X')
        for (r in guardMap.indices) {
            for (c in guardMap[r].indices){
                if (countTheseChars.contains(guardMap[r][c])) {
                    total+= 1
                }
            }
        }
        return total
    }
    private fun printGuardMap(guardMap: Array<CharArray> ) {

        for (r in guardMap.indices) {
            for (c in guardMap[r].indices) {
                print("${guardMap[r][c]}")
            }
            println()
        }
    }

    private fun buildMap(inputLines: List<String>) : Array<CharArray> {
        var guardMap = Array(inputLines.size) { CharArray(inputLines.elementAt(0).length)}
        for (l in inputLines.indices){
            //println("Line: ${inputLines[l]}")
            for (c in 0..inputLines.elementAt(l).length - 1) {
                guardMap[l][c] = inputLines.elementAt(l).elementAt(c)
            }
        }
        return guardMap
    }
    override fun part2(inputLines: List<String>) {
        // Getting back to the first take on this solution, i.e. get the path of part1, try each spot on the path with a new obstacle
        println("Call part1 to get the path: ")
        part1(inputLines)
        var totalPossibleSolutions = 0
        println("ListofPositionSize: ${listOfPositions.size}")

        for(possiblePosition in listOfPositions) {
            var isOneSolution = canTraceAroundToCurrentPosition2(guardMapPart1, startingPositionPart1, possiblePosition, inputLines)
            if (isOneSolution) {
                totalPossibleSolutions += 1
            }
        }

        println("Possible total solutions: ${totalPossibleSolutions}")
    }


    private fun printGuardMapWithNewObstruction(guardMap: Array<CharArray>, guardPosition: MapSpace) {
        var tempGuardMapChar = guardMap[guardPosition.row][guardPosition.col]
        println("Found an infinite loop!")
        guardMap[guardPosition.row][guardPosition.col] = 'O'
        //printGuardMap(guardMap)
        guardMap[guardPosition.row][guardPosition.col] = tempGuardMapChar
    }

    private fun canTraceAroundToCurrentPosition2(guardMap: Array<CharArray>, guardPosition: MapSpace, potentialObject: MapSpace, inputLines: List<String>) : Boolean {
        var tempMap: Array<CharArray>
        //println("Trying new map")
        var listOfUniquePathPositions = mutableListOf<MapSpace>()
        tempMap = buildMap(inputLines)
        val oldGuardPosition = findGuardPosition(tempMap)
        tempMap[oldGuardPosition.row][oldGuardPosition.col] = '.'
        tempMap[guardPosition.row][guardPosition.col] = guardPosition.content
        tempMap[potentialObject.row][potentialObject.col] = 'O'
        //printGuardMap(tempMap)
        var origGuardPosition = MapSpace(guardPosition.row, guardPosition.col, guardPosition.content)
        var newGuardPosition =  MapSpace(guardPosition.row, guardPosition.col, guardPosition.content)
        //println(" Starting temp map")
        //println(" First position: R:${origGuardPosition.row} C:${origGuardPosition.col}")
        //println(" Potential object: R:${potentialObject.row} C:${potentialObject.col}")
        //printGuardMap(tempMap)
        //println()
        var totalNumberOfTimesVisitingGuardPosition = 0
        //var prevPosition = MapSpace(origGuardPosition.row, origGuardPosition.col, origGuardPosition.content)
        var counter = 0
        var sizeOfListLastCheck = 0
        var anyHaveMoreThan2Count = 0
        var breakBecauseOfTenThousand = false
        do {
            var newMapSpace = MapSpace(newGuardPosition.row, newGuardPosition.col, newGuardPosition.content)
            if (!listOfUniquePathPositions.contains(newMapSpace)) {
                newMapSpace.countOfSame += 1
                listOfUniquePathPositions.add(newMapSpace)
            }
            else {
                listOfUniquePathPositions[listOfUniquePathPositions.indexOf(newMapSpace)].countOfSame += 1
            }
            val predicate: (MapSpace) -> Boolean = {it.countOfSame > 1}
            anyHaveMoreThan2Count = listOfUniquePathPositions.count(predicate)
            moveGuard(tempMap, newGuardPosition)
            tempMap[newGuardPosition.row][newGuardPosition.col] = newGuardPosition.content
            //printGuardMap(tempMap)
            //println()
            //println("newGuardPosition: R:${newGuardPosition.row} C:${newGuardPosition.col}")
            //println("prevGuardPosition: R:${prevPosition.row} C:${prevPosition.col}")
            //println()
            if ((newGuardPosition.row == origGuardPosition.row && newGuardPosition.col == origGuardPosition.col) &&
                !newGuardPosition.isTurning) {
                totalNumberOfTimesVisitingGuardPosition += 1
                //println("Overlap guardPosition: R:${origGuardPosition.row} C:${origGuardPosition.col}")
            }
            if (newGuardPosition.isTurning) {
                //println("Turn: guardPosition: R:${origGuardPosition.row} C:${origGuardPosition.col} ${origGuardPosition.content}")
            }
            //prevPosition = MapSpace(origGuardPosition.row, origGuardPosition.col, origGuardPosition.content)
            //if (potentialObject.row == 83 && potentialObject.col == 74) {
            //    println("newGuardPosition: R:${newGuardPosition.row} C:${newGuardPosition.col} P:${newGuardPosition.content}")
            //    Thread.sleep(2000L)
           // }
            if ((counter % 10000 == 0 ) && counter > 9999) {
                // failsafe - i.e. if we made 10,000 moves and the didn't get any new positions, we are stuck in a loop
                // not sure why the positions check (any
                //&& (potentialObject.row == 83 && potentialObject.col == 74)
                //println()
                //printGuardMap(tempMap)
                //println()
                //Thread.sleep(2000L)
                println("lastCheckListSize: ${sizeOfListLastCheck}, uniquePathPositions: ${listOfUniquePathPositions.size}")
                if (sizeOfListLastCheck == listOfUniquePathPositions.size) {
                    // if after 10,000 moves the size of the unique positions list is still the same then maybe it's a loop!
                    totalNumberOfTimesVisitingGuardPosition += 1
                    breakBecauseOfTenThousand = true
                }
                sizeOfListLastCheck = listOfUniquePathPositions.size

            }
            counter += 1
        } while ((newGuardPosition.content != 'X') &&  anyHaveMoreThan2Count == 0 && !breakBecauseOfTenThousand)

        if (newGuardPosition.content != 'X' && anyHaveMoreThan2Count != 0) {
            tempMap[newGuardPosition.row][newGuardPosition.col] = newGuardPosition.content
            tempMap[potentialObject.row][potentialObject.col] = 'O'
            //printGuardMap(tempMap)
        }
        /*if ((potentialObject.row == 8 && potentialObject.col ==3)){
            println("Checking space 8, 3 position")
            println("anyHaveMoreThan2Count: ${anyHaveMoreThan2Count}")
            println("newGuardPosition.content: ${newGuardPosition.content}")
            println("totalNumberOfTimesVisitingGuardPosition: ${totalNumberOfTimesVisitingGuardPosition}")
            printGuardMap(tempMap)
        }*/
        //println(" Ended temp map")
        return newGuardPosition.content != 'X' && (anyHaveMoreThan2Count != 0 || breakBecauseOfTenThousand)
    }

}