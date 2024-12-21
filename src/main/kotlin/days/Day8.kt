package days

class Antenna (inChar: Char) {
    var antennaCharacter = inChar
    var coordinates: MutableList<AntennaCoordinate> = mutableListOf()
    var antiNodes: MutableList<AntennaCoordinate> = mutableListOf()

    override fun toString(): String {
        var newString = ""
        newString = "inChar: $antennaCharacter \n  Coordinates: ${coordinates.toString()} \n  antiNodes: ${antiNodes.toString()}"
        return newString
    }
}
class AntennaCoordinate (x: Int, y: Int) {
    var x = x
    var y = y

    override fun toString(): String {
        return "[$x, $y]"
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is AntennaCoordinate) return false
        return x == other.x && y == other.y
    }
}
class Day8 : Iday {
    private var rowsSize = 0
    private var colsSize = 0
    override fun part1(inputLines: List<String>) {
        var total = 0
        val antennaMap = Array(inputLines.size) { CharArray(inputLines.elementAt(0).length)}
        var antennas = mutableListOf<Antenna>()

        for (l in inputLines.indices){
            println("Line: ${inputLines[l]}")
            for (c in 0 until inputLines.elementAt(l).length) {
                var tempChar = inputLines.elementAt(l).elementAt(c)
                antennaMap[l][c] = tempChar

                if (tempChar != '.') {
                    var foundAntenna = antennas.find { it.antennaCharacter == tempChar }
                    if (foundAntenna != null) {
                        foundAntenna.coordinates.add(AntennaCoordinate(c, l))
                    }
                    else {
                        var newAntenna = Antenna(tempChar)
                        newAntenna.coordinates.add(AntennaCoordinate(c, l))
                        antennas.add(newAntenna)
                    }
                }
            }
        }

        rowsSize = antennaMap.size - 1
        colsSize = antennaMap[0].size - 1
        var checkAllPositions = false

        for(a in antennas) {
            processAntenna(a, checkAllPositions)
        }

        var uniqueAntinodes: MutableList<AntennaCoordinate> = mutableListOf()

        for(a in antennas) {
            //print("Antenna: ${a.antennaCharacter}, Coordinates: ")
            //for(c in a.coordinates) {
            //    print("[${c.x}, ${c.y}]")
           /// }
            //println()
            //println("Tostring: ${a.coordinates.toString()}")
            println(a.toString())
            for (c in a.antiNodes) {
                if ((c.x >= 0 && c.x <= antennaMap[0].size - 1) &&
                    (c.y >= 0 && c.y <= antennaMap.size - 1)) {

                    if (!uniqueAntinodes.contains(c)) {
                        uniqueAntinodes.add(AntennaCoordinate(c.x, c.y))
                    }
                }
            }
        }

        printMap(antennaMap, antennas)

        println("Total: ${uniqueAntinodes.size}")
    }

    private fun processAntenna(inAntenna: Antenna, checkAllPositions: Boolean) {
        var currentAntennaCoordinateNum = 0

        do {
            for(c in inAntenna.coordinates) {
                var coordinate1 = inAntenna.coordinates[currentAntennaCoordinateNum]
                var coordinate2 = c
                var isDebug = coordinate1.x == 5 && coordinate1.y == 2 && coordinate2.x == 4 && coordinate2.y == 4
                println("Evaluating: ${coordinate1}, ${coordinate2}")

                if (isDebug) {
                    println("Found ${coordinate1} and ${coordinate2}")
                }

                if (coordinate1.equals(coordinate2)) {
                    // if the coordinate is the same, move to the next
                    continue
                }

                var newAntinodes = calculateAntinodes(coordinate1, coordinate2, checkAllPositions, colsSize, rowsSize)

                for(a in newAntinodes) {
                    if (!inAntenna.antiNodes.contains(a)) {
                        inAntenna.antiNodes.add(AntennaCoordinate(a.x, a.y))
                    }
                }

            }
            currentAntennaCoordinateNum += 1
        } while (currentAntennaCoordinateNum != inAntenna.coordinates.size - 1)
    }

    private fun calculateAntinodes(antenna1: AntennaCoordinate, antenna2: AntennaCoordinate): MutableList<AntennaCoordinate> {
        var diffX = antenna1.x - antenna2.x  //5,2  1, -2
        var diffy = antenna1.y - antenna2.y  //4,4
        var isDebug = antenna1.x == 5 && antenna1.y == 2 && antenna2.x == 4 && antenna2.y == 4
        var outputList = mutableListOf<AntennaCoordinate>()

        var newCoordinate1 = AntennaCoordinate(antenna1.x + diffX, antenna1.y + diffy)
        var newCoordinate2 = AntennaCoordinate(antenna1.x - diffX, antenna1.y - diffy)
        var newCoordinate3 = AntennaCoordinate(antenna2.x + diffX, antenna2.y + diffy)
        var newCoordinate4 = AntennaCoordinate(antenna2.x - diffX, antenna2.y - diffy)

        if (isDebug) {
            println("newCoordinate1: ${newCoordinate1}")
            println("newCoordinate2: ${newCoordinate2}")
            println("newCoordinate3: ${newCoordinate3}")
            println("newCoordinate4: ${newCoordinate4}")
        }

        if (!newCoordinate1.equals(antenna1) && !newCoordinate1.equals(antenna2)) {
            outputList.add(newCoordinate1)
        }
        if (!newCoordinate2.equals(antenna1) && !newCoordinate2.equals(antenna2)) {
            outputList.add(newCoordinate2)
        }
        if (!newCoordinate3.equals(antenna1) && !newCoordinate3.equals(antenna2)) {
            outputList.add(newCoordinate3)
        }
        if (!newCoordinate4.equals(antenna1) && !newCoordinate4.equals(antenna2)) {
            outputList.add(newCoordinate4)
        }

        return outputList
    }

    private fun printMap(matrixMap: Array<CharArray>, antennas: MutableList<Antenna> ) {

        for (r in matrixMap.indices) {
            for (c in matrixMap[r].indices) {
                var tempAntennaCoordinate = AntennaCoordinate(c, r)
                var displayChar = matrixMap[r][c]
                var isEmpty = matrixMap[r][c] == '.'
                for (a in antennas) {
                    if (a.antiNodes.contains(tempAntennaCoordinate)) {
                        displayChar = '#'
                    }
                }
                if (!isEmpty && displayChar == '#') {
                    // if the map had an antenna in the spot we are putting an antinode, then reset to the antenna char
                    displayChar = matrixMap[r][c]
                }
                print("${displayChar}")
            }
            println()
        }
    }

    override fun part2(inputLines: List<String>) {
        var total = 0
        val antennaMap = Array(inputLines.size) { CharArray(inputLines.elementAt(0).length)}
        var antennas = mutableListOf<Antenna>()

        for (l in inputLines.indices){
            println("Line: ${inputLines[l]}")
            for (c in 0 until inputLines.elementAt(l).length) {
                var tempChar = inputLines.elementAt(l).elementAt(c)
                antennaMap[l][c] = tempChar

                if (tempChar != '.') {
                    var foundAntenna = antennas.find { it.antennaCharacter == tempChar }
                    if (foundAntenna != null) {
                        foundAntenna.coordinates.add(AntennaCoordinate(c, l))
                    }
                    else {
                        var newAntenna = Antenna(tempChar)
                        newAntenna.coordinates.add(AntennaCoordinate(c, l))
                        antennas.add(newAntenna)
                    }
                }
            }
        }

        rowsSize = antennaMap.size - 1
        colsSize = antennaMap[0].size - 1
        var checkAllPositions = true

        for(a in antennas) {
            processAntenna(a, checkAllPositions)
        }

        var uniqueAntinodes: MutableList<AntennaCoordinate> = mutableListOf()

        for(a in antennas) {
            //print("Antenna: ${a.antennaCharacter}, Coordinates: ")
            //for(c in a.coordinates) {
            //    print("[${c.x}, ${c.y}]")
            /// }
            //println()
            //println("Tostring: ${a.coordinates.toString()}")
            println(a.toString())
            for (c in a.antiNodes) {
                if ((c.x >= 0 && c.x <= antennaMap[0].size - 1) &&
                    (c.y >= 0 && c.y <= antennaMap.size - 1)) {

                    if (!uniqueAntinodes.contains(c)) {
                        uniqueAntinodes.add(AntennaCoordinate(c.x, c.y))
                    }
                }
            }
        }

        printMap(antennaMap, antennas)

        println("Total: ${uniqueAntinodes.size}")
    }

    private fun calculateAntinodes(antenna1: AntennaCoordinate, antenna2: AntennaCoordinate, checkAllPositions: Boolean, colSize: Int, rowSize: Int): MutableList<AntennaCoordinate> {
        var diffX = antenna1.x - antenna2.x  //5,2  1, -2
        var diffy = antenna1.y - antenna2.y  //4,4
        var isDebug = antenna1.x == 5 && antenna1.y == 2 && antenna2.x == 4 && antenna2.y == 4
        var outputList = mutableListOf<AntennaCoordinate>()

        var outOfBounds = false
        var multiplier1 = 1
        var multiplier2 = 1
        do {
            var newCoordinate1 = AntennaCoordinate(antenna1.x + (diffX * multiplier1), antenna1.y + (diffy * multiplier1))
            var newCoordinate2 = AntennaCoordinate(antenna1.x - (diffX * multiplier2), antenna1.y - (diffy * multiplier2))
            //var newCoordinate3 = AntennaCoordinate(antenna2.x + (diffX * multiplier), antenna2.y + (diffy * multiplier))
            //var newCoordinate4 = AntennaCoordinate(antenna2.x - (diffX * multiplier), antenna2.y - (diffy * multiplier))

            if (isDebug) {
                println("newCoordinate1: ${newCoordinate1}")
                println("newCoordinate2: ${newCoordinate2}")
                //println("newCoordinate3: ${newCoordinate3}")
                //println("newCoordinate4: ${newCoordinate4}")
            }

            /*if (newCoordinate1 == antenna2) {
                multiplier1 += 1
                newCoordinate1 = AntennaCoordinate(antenna1.x + (diffX * multiplier1), antenna1.y + (diffy * multiplier1))
            }*/
            /*if (newCoordinate2 == antenna2) {
                multiplier2 += 1
                newCoordinate2 = AntennaCoordinate(antenna1.x - (diffX * multiplier2), antenna1.y - (diffy * multiplier2))
            }*/

            println("newCoordinate1: ${newCoordinate1}, newCoordinate2: ${newCoordinate2}")
            //if (!newCoordinate1.equals(antenna1) && !newCoordinate1.equals(antenna2)) {
                if (!outputList.contains(newCoordinate1)) {
                    if ((newCoordinate1.x in 0..colSize) &&
                            (newCoordinate1.y in 0..rowSize)) {
                        outputList.add(newCoordinate1)
                    }
                }
            //}
            //if (!newCoordinate2.equals(antenna1) && !newCoordinate2.equals(antenna2)) {
                if (!outputList.contains(newCoordinate2)) {
                    if ((newCoordinate2.x in 0..colSize) &&
                        (newCoordinate2.y in 0..rowSize)) {
                        outputList.add(newCoordinate2)
                    }
                }
            //}
            /* if (!newCoordinate3.equals(antenna1) && !newCoordinate3.equals(antenna2)) {
                if (!outputList.contains(newCoordinate3)) {
                    outputList.add(newCoordinate3)
                }
            }
            if (!newCoordinate4.equals(antenna1) && !newCoordinate4.equals(antenna2)) {
                if (!outputList.contains(newCoordinate4)) {
                    outputList.add(newCoordinate4)
                }
            } */

            if ((newCoordinate1.x !in 0..colSize ||
                        newCoordinate1.y !in 0..rowSize) &&
                    (newCoordinate2.x !in 0..colSize ||
                            newCoordinate2.y !in 0..rowSize)) {
                outOfBounds = true
            }
            multiplier1 += 1
            multiplier2 += 1
        } while (!outOfBounds && checkAllPositions)



        return outputList
    }
}