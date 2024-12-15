package days

class MapSpace (r: Int, c: Int, content: Char) {
    var row = r
    var col = c
    var content = content
    override fun equals(other: Any?): Boolean {
        if (other == null || other !is MapSpace) return false
        return row == other.row && col == other.col
    }
}
class Day6 : Iday {
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

        do {
            moveGuard(guardMap, guardPosition)

        } while (guardPosition.content != 'X')

        total = countGuardPath(guardMap)
        println()
        printGuardMap(guardMap)
        println("Total: $total")
    }

    fun moveGuard(guardMap: Array<CharArray>, guardPosition: MapSpace)  {
        guardMap[guardPosition.row][guardPosition.col] = getPositionChar(guardMap, guardPosition)
        when (guardPosition.content) {
            '^' -> {
                if (guardPosition.row > 0) {
                    if (guardMap[guardPosition.row-1][guardPosition.col] == '#') {
                        guardPosition.content = '>'
                    }
                    else {
                        guardPosition.row = guardPosition.row - 1
                    }
                }
                else if (guardPosition.row == 0) {
                    guardPosition.content = 'X'
                }
            }
            '>' -> {
                if (guardPosition.col < guardMap[0].size - 1) {
                    if (guardMap[guardPosition.row][guardPosition.col+1] == '#') {
                        guardPosition.content = 'V'
                    }
                    else {
                        guardPosition.col = guardPosition.col + 1
                    }
                }
                else if (guardPosition.col == guardMap[0].size - 1) {
                    guardPosition.content = 'X'
                }
            }
            'V' -> {
                if (guardPosition.row < guardMap.size - 1) {
                    if (guardMap[guardPosition.row+1][guardPosition.col] == '#') {
                        guardPosition.content = '<'
                    }
                    else {
                        guardPosition.row = guardPosition.row + 1
                    }
                }
                else if (guardPosition.row == guardMap.size - 1) {
                    guardPosition.content = 'X'
                }
            }
            '<' -> {

                if (guardPosition.col > 0) {
                    if (guardMap[guardPosition.row][guardPosition.col-1] == '#') {
                        guardPosition.content = '^'
                    }
                    else {
                        guardPosition.col = guardPosition.col -1
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
    override fun part2(inputLines: List<String>) {
        var total = 0
        var totalInfinite = 0
        var checkForLoopOnTheseChars = arrayOf('|','-','+','.')
        val guardMap = Array(inputLines.size) { CharArray(inputLines.elementAt(0).length)}
        var foundPotentialPositions = mutableListOf<MapSpace>()

        for (l in inputLines.indices){
            println("Line: ${inputLines[l]}")
            for (c in 0..inputLines.elementAt(l).length - 1) {
                guardMap[l][c] = inputLines.elementAt(l).elementAt(c)
            }
        }
        val guardPosition = findGuardPosition(guardMap)
        println("Start guardPosition: R:${guardPosition.row} C:${guardPosition.col} T:${guardPosition.content}")
        do {


            moveGuard(guardMap, guardPosition)
            println("Current guardPosition: R:${guardPosition.row} C:${guardPosition.col} T:${guardPosition.content}")
            if ((guardPosition.content == '<') && checkForLoopOnTheseChars.contains(guardMap[guardPosition.row][guardPosition.col+1]) ) {
                //println("Found + position")
                //printGuardMap(guardMap)
                var canWeMakeAnInfiniteLoop = checkForInfiniteLoop(guardMap, guardPosition)
                if (canWeMakeAnInfiniteLoop) {
                    printGuardMapWithNewObstruction(guardMap,guardPosition)
                    totalInfinite += 1
                    if (!foundPotentialPositions.contains(guardPosition)) foundPotentialPositions.add(MapSpace(guardPosition.row, guardPosition.col, guardPosition.content))
                }
            }
            if ((guardPosition.content == '^') && checkForLoopOnTheseChars.contains(guardMap[guardPosition.row+1][guardPosition.col])) {
                //println("Found + position")
                //printGuardMap(guardMap)
                var canWeMakeAnInfiniteLoop = checkForInfiniteLoop(guardMap, guardPosition)
                if (canWeMakeAnInfiniteLoop) {
                    printGuardMapWithNewObstruction(guardMap,guardPosition)
                    totalInfinite += 1
                    if (!foundPotentialPositions.contains(guardPosition)) foundPotentialPositions.add(MapSpace(guardPosition.row, guardPosition.col, guardPosition.content))
                }
            }
            if ((guardPosition.content == '>') && checkForLoopOnTheseChars.contains(guardMap[guardPosition.row][guardPosition.col-1])) {
                //println("Found + position")
                //printGuardMap(guardMap)
                var canWeMakeAnInfiniteLoop = checkForInfiniteLoop(guardMap, guardPosition)
                if (canWeMakeAnInfiniteLoop) {
                    printGuardMapWithNewObstruction(guardMap,guardPosition)
                    totalInfinite += 1
                    if (!foundPotentialPositions.contains(guardPosition)) foundPotentialPositions.add(MapSpace(guardPosition.row, guardPosition.col, guardPosition.content))
                }
            }
            if ((guardPosition.content == 'V') && checkForLoopOnTheseChars.contains(guardMap[guardPosition.row-1][guardPosition.col])) {
                //println("Found + position")
                //printGuardMap(guardMap)
                var canWeMakeAnInfiniteLoop = checkForInfiniteLoop(guardMap, guardPosition)
                if (canWeMakeAnInfiniteLoop) {
                    printGuardMapWithNewObstruction(guardMap,guardPosition)
                    totalInfinite += 1
                    if (!foundPotentialPositions.contains(guardPosition)) foundPotentialPositions.add(MapSpace(guardPosition.row, guardPosition.col, guardPosition.content))
                }
            }
        } while (guardPosition.content != 'X')

        total = countXs(guardMap)
        println()
        printGuardMap(guardMap)
        println("Total Xs: $total")
        println("Total infinites: ${foundPotentialPositions.size}")


    }


    private fun printGuardMapWithNewObstruction(guardMap: Array<CharArray>, guardPosition: MapSpace) {
        var tempGuardMapChar = guardMap[guardPosition.row][guardPosition.col]
        println("Found an infinite loop!")
        guardMap[guardPosition.row][guardPosition.col] = 'O'
        printGuardMap(guardMap)
        guardMap[guardPosition.row][guardPosition.col] = tempGuardMapChar
    }
    private fun checkForInfiniteLoop(guardMap: Array<CharArray>, guardPosition: MapSpace) : Boolean {
        // first figure out which direction we are going in and if there is no object there currently
        var objectThereAlready = false
        println("Checking checkForInfiniteLoop")
        when (guardPosition.content) {
            '^' -> {
                if (guardPosition.row > 0) {
                    if (guardMap[guardPosition.row][guardPosition.col] == '#') {
                        objectThereAlready = true
                    }
                }
                else if (guardPosition.row == 0) {
                    //guardPosition.content = 'X'
                    // not sure what to do here
                }
            }
            '>' -> {
                if (guardPosition.col < guardMap[0].size - 1) {
                    if (guardMap[guardPosition.row][guardPosition.col] == '#') {
                        objectThereAlready = true
                    }
                }
                else if (guardPosition.col == guardMap[0].size - 1) {
                    //guardPosition.content = 'X'
                    // not sure what to do here
                }
            }
            'V' -> {
                if (guardPosition.row < guardMap.size - 1) {
                    if (guardMap[guardPosition.row][guardPosition.col] == '#') {
                        objectThereAlready = true
                    }
                }
                else if (guardPosition.row == guardMap.size - 1) {
                    //guardPosition.content = 'X'
                    // not sure what to do here
                }
            }
            '<' -> {

                if (guardPosition.col > 0) {
                    if (guardMap[guardPosition.row][guardPosition.col] == '#') {
                        objectThereAlready = true
                    }
                }
                else if (guardPosition.col == 0) {
                    //guardPosition.content = 'X'
                    // not sure what to do here
                    //guardPosition.isExitGuardPosition = true
                }
            }
        }
        if (objectThereAlready) return false

        // Can we trace all around back to the current position?
        println("Checking if we can trace a square")
        var isCanTraceAroundToCurrentPosition = canTraceAroundToCurrentPosition(guardMap, guardPosition)


        return isCanTraceAroundToCurrentPosition
    }

    private fun getCharArrayOfCharactersAroundPosition() {

    }

    private fun canTraceAroundToCurrentPosition(guardMap: Array<CharArray>, guardPosition: MapSpace): Boolean {
        when (guardPosition.content) {
            '^' -> {
                var isThereAnObjectRight = lookForObstacle('>', MapSpace(guardPosition.row+1, guardPosition.col, '$'), guardMap)
                if (isThereAnObjectRight.content == 'N') {
                    return false
                }
                println("isThereAnObjectRight: ${isThereAnObjectRight.row}, ${isThereAnObjectRight.col}, ${isThereAnObjectRight.content}")
                var isThereAnObjectBelow = lookForObstacle('V', isThereAnObjectRight, guardMap)
                if (isThereAnObjectBelow.content == 'N') {
                    return false
                }
                println("isThereAnObjectBelow: ${isThereAnObjectBelow.row}, ${isThereAnObjectBelow.col}, ${isThereAnObjectBelow.content}")
                var isThereAnObjectLeft = lookForObstacle('<', isThereAnObjectBelow, guardMap)
                if (isThereAnObjectLeft.content == 'E') {
                    return false
                }
                println("isThereAnObjectLeft: ${isThereAnObjectLeft.row}, ${isThereAnObjectLeft.col}, ${isThereAnObjectLeft.content}}")
                //var isThereAnObjectAbove = lookForObstacle('^', isThereAnObjectLeft, guardMap)
                //if (isThereAnObjectAbove.content == 'N') {
                //    return false
               // }
               // println("isThereAnObjectAbove: ${isThereAnObjectAbove.row}, ${isThereAnObjectAbove.col}, ${isThereAnObjectAbove.content}")

                println("NewObject: ${guardPosition.row}, ${guardPosition.col}, O")

                if (guardPosition.col != guardMap[guardPosition.row].size - 1) {
                   return true
                }
            }
            '>' -> {
                var isThereAnObjectBelow = lookForObstacle('V', MapSpace(guardPosition.row, guardPosition.col-1, '$'), guardMap)
                if (isThereAnObjectBelow.content == 'N') {
                    return false
                }
                println("isThereAnObjectBelow: ${isThereAnObjectBelow.row}, ${isThereAnObjectBelow.col}, ${isThereAnObjectBelow.content}")
                var isThereAnObjectLeft = lookForObstacle('<', isThereAnObjectBelow, guardMap)
                if (isThereAnObjectLeft.content == 'E') {
                    return false
                }
                println("isThereAnObjectLeft: ${isThereAnObjectLeft.row}, ${isThereAnObjectLeft.col}, ${isThereAnObjectLeft.content}}")
                var isThereAnObjectAbove = lookForObstacle('^', isThereAnObjectLeft, guardMap)
                if (isThereAnObjectAbove.content == 'N') {
                    return false
                }
                println("isThereAnObjectAbove: ${isThereAnObjectAbove.row}, ${isThereAnObjectAbove.col}, ${isThereAnObjectAbove.content}")
                //var isThereAnObjectRight = lookForObstacle('>', isThereAnObjectAbove, guardMap)
                //if (isThereAnObjectRight.content == 'N') {
                //    return false
                //}
                //println("isThereAnObjectRight: ${isThereAnObjectRight.row}, ${isThereAnObjectRight.col}, ${isThereAnObjectRight.content}")
                println("NewObject: ${guardPosition.row}, ${guardPosition.col}, O")


                if (guardPosition.col != guardMap[guardPosition.row].size - 1) {
                   return true
                }
            }
            'V' -> {
                var isThereAnObjectLeft = lookForObstacle('<', MapSpace(guardPosition.row-1, guardPosition.col, '$'), guardMap)
                if (isThereAnObjectLeft.content == 'N') {
                    return false
                }
                println("isThereAnObjectLeft: ${isThereAnObjectLeft.row}, ${isThereAnObjectLeft.col}, ${isThereAnObjectLeft.content}")
                var isThereAnObjectAbove = lookForObstacle('^', isThereAnObjectLeft, guardMap)
                if (isThereAnObjectAbove.content == 'N') {
                    return false
                }
                println("isThereAnObjectAbove: ${isThereAnObjectAbove.row}, ${isThereAnObjectAbove.col}, ${isThereAnObjectAbove.content}")
                var isThereAnObjectRight = lookForObstacle('>', isThereAnObjectAbove, guardMap)
                if (isThereAnObjectRight.content == 'N') {
                    return false
                }
                println("isThereAnObjectRight: ${isThereAnObjectRight.row}, ${isThereAnObjectRight.col}, ${isThereAnObjectRight.content}")
                //var isThereAnObjectBelow = lookForObstacle('V', isThereAnObjectRight, guardMap)
                //if (isThereAnObjectBelow.content == 'N') {
                //    return false
                //}
                //println("isThereAnObjectBelow: ${isThereAnObjectBelow.row}, ${isThereAnObjectBelow.col}, ${isThereAnObjectBelow.content}")
                println("NewObject: ${guardPosition.row}, ${guardPosition.col}, O")

                if (guardPosition.row != guardMap.size - 1) {
                   return true
                }
            }
            '<' -> {
                var isThereAnObjectAbove = lookForObstacle('^', MapSpace(guardPosition.row, guardPosition.col+1, '$'), guardMap)
                if (isThereAnObjectAbove.content == 'N') {
                    return false
                }
                println("isThereAnObjectAbove: ${isThereAnObjectAbove.row}, ${isThereAnObjectAbove.col}, ${isThereAnObjectAbove.content}")
                var isThereAnObjectRight = lookForObstacle('>', isThereAnObjectAbove, guardMap)
                if (isThereAnObjectRight.content == 'N') {
                    return false
                }
                println("isThereAnObjectRight: ${isThereAnObjectRight.row}, ${isThereAnObjectRight.col}, ${isThereAnObjectRight.content}")
                var isThereAnObjectBelow = lookForObstacle('V', isThereAnObjectRight, guardMap)
                if (isThereAnObjectBelow.content == 'N') {
                    return false
                }
                println("isThereAnObjectBelow: ${isThereAnObjectBelow.row}, ${isThereAnObjectBelow.col}, ${isThereAnObjectBelow.content}")
                //var isThereAnObjectLeft = lookForObstacle('<', isThereAnObjectBelow, guardMap)
                //if (isThereAnObjectLeft.content == 'E') {
                //    return false
                //}
                println("NewObject: ${guardPosition.row}, ${guardPosition.col}, O")

                if (guardPosition.col != 0) {
                   return true
                }
            }
        }
        return true
    }

private fun lookForObstacle(direction: Char, position: MapSpace, guardMap: Array<CharArray>) : MapSpace {
    var returnMapSpace = MapSpace(-1, -1, 'N')

    if (direction == '^') {
        var distance = 0
        var validChars = arrayOf('|', '+','-')
        for (r in position.row downTo 0) {
            if (validChars.contains(guardMap[r][position.col])) {
                distance += 1
                continue;
            } else if (guardMap[r][position.col] == '#' && (distance > 0)) {
                return MapSpace(r + 1, position.col, '#')
            }
        }
    }
    if (direction == 'V') {
        var distance = 0
        var validChars = arrayOf('|', '+','-')
        for (r in position.row until guardMap.size) {
            if (validChars.contains(guardMap[r][position.col])) {
                distance += 1
                continue;
            } else if (guardMap[r][position.col] == '#' && (distance > 0)) {
                return MapSpace(r - 1, position.col, '#')
            }
        }
    }
    if (direction == '>') {
        var distance = 0
        var validChars = arrayOf('-', '+','|')
        for (c in position.col until guardMap[position.row].size) {
            if (validChars.contains(guardMap[position.row][c])) {
                distance += 1
                continue;
            } else if (guardMap[position.row][c] == '#' && (distance > 0)) {
                return MapSpace(position.row, c - 1, '#')
            }
            break
        }
    }
    if (direction == '<') {
        var distance = 0
        var validChars = arrayOf('-', '+','|')
        for (c in position.col downTo 0) {
            if (validChars.contains(guardMap[position.row][c])) {
                distance += 1
                continue;
            } else if (guardMap[position.row][c] == '#' && (distance > 0)) {
                return MapSpace(position.row, c + 1, '#')
            }
            break
        }
    }
    return returnMapSpace
}

}