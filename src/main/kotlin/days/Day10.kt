package days

class Trail(inTrailId: Int) {
    var trailId = inTrailId
    var trailSegments: MutableList<TrailSegment> = mutableListOf()
    var trailScore = 0
}

class TrailSegment(inX: Int, inY: Int, inPathNumber: Int) {
    var x = inX
    var y = inY
    var pathNumber = inPathNumber
    fun isTrailHead() : Boolean {
        return pathNumber == 0
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is TrailSegment) return false
        return x == other.x && y == other.y && pathNumber == other.pathNumber
    }
}

class Day10 : Iday {
    private var numberOfCompleteTrails = 0

    override fun part1(inputLines: List<String>) {
        var total = 0
        var trails = mutableListOf<Trail>()
        var trailId = 0
        var fullMap = mutableListOf<MutableList<TrailSegment>>()

        for (l in inputLines.indices) {
            println("Line: ${inputLines[l]}")
            var tempList = mutableListOf<TrailSegment>()
            for (c in 0 until inputLines.elementAt(l).length) {

                var tempInt = inputLines.elementAt(l).elementAt(c).digitToInt()
                //fullMap[l][c] = TrailSegment(c, l, tempInt)
                var tempTrailSegement = TrailSegment(c, l, tempInt)
                if (tempInt == 0) {
                    var tempTrail = Trail(trailId)
                    tempTrail.trailSegments.add(TrailSegment(c, l, tempInt))
                    trails.add(tempTrail)
                    trailId += 1
                }
                tempList.add(tempTrailSegement)
            }
            fullMap.add(tempList)
        }

        println("Total trailHeads: ${trails.size}")
        for(t in trails) {
            nextStep(fullMap, t.trailSegments.first(), t)
            println()
        }

        printMap(fullMap)

        var totalOfScores = trails.sumOf { it.trailScore }

        println("Total score: $totalOfScores")
    }

    private fun nextStep(fullMap: MutableList<MutableList<TrailSegment>>, currentTrailSegment: TrailSegment, currentTrail: Trail) {
        var lookForStepNumber = currentTrailSegment.pathNumber + 1
        var nextSteps = mutableListOf<TrailSegment>()
        print("->${currentTrailSegment.pathNumber}")
        if (!currentTrail.trailSegments.contains(currentTrailSegment)) {
            currentTrail.trailSegments.add(currentTrailSegment)
            if (currentTrailSegment.pathNumber == 9) {
                currentTrail.trailScore += 1
            }
        }

        if (currentTrailSegment.pathNumber == 9) {
            return
        }

        var trailUp = TrailSegment(currentTrailSegment.x, currentTrailSegment.y - 1, -1)
        var trailDown = TrailSegment(currentTrailSegment.x, currentTrailSegment.y + 1, -1)
        var trailRight = TrailSegment(currentTrailSegment.x + 1, currentTrailSegment.y, -1)
        var trailLeft = TrailSegment(currentTrailSegment.x - 1, currentTrailSegment.y, -1)

        if ((0 <= trailUp.x && trailUp.x <= fullMap[0].size - 1) &&
            (0 <= trailUp.y && trailUp.y <= fullMap.size - 1)){
            trailUp.pathNumber = fullMap[currentTrailSegment.y - 1][currentTrailSegment.x].pathNumber
            if (trailUp.pathNumber == lookForStepNumber) {
                nextSteps.add(trailUp)
            }
        }
        if ((0 <= trailDown.x && trailDown.x <= fullMap[0].size - 1) &&
            (0 <= trailDown.y && trailDown.y <= fullMap.size - 1)){
            trailDown.pathNumber = fullMap[currentTrailSegment.y + 1][currentTrailSegment.x].pathNumber
            if (trailDown.pathNumber == lookForStepNumber) {
                nextSteps.add(trailDown)
            }
        }
        if ((0 <= trailRight.x && trailRight.x <= fullMap[0].size - 1) &&
            (0 <= trailRight.y && trailRight.y <= fullMap.size - 1)){
            trailRight.pathNumber = fullMap[currentTrailSegment.y][currentTrailSegment.x + 1].pathNumber
            if (trailRight.pathNumber == lookForStepNumber) {
                nextSteps.add(trailRight)
            }
        }
        if ((0 <= trailLeft.x && trailLeft.x <= fullMap[0].size - 1) &&
            (0 <= trailLeft.y && trailLeft.y <= fullMap.size - 1)){
            trailLeft.pathNumber = fullMap[currentTrailSegment.y][currentTrailSegment.x - 1].pathNumber
            if (trailLeft.pathNumber == lookForStepNumber) {
                nextSteps.add(trailLeft)
            }
        }

        for (t in nextSteps) {
            nextStep(fullMap, t, currentTrail)
        }

        return
    }

    private fun nextStep2(fullMap: MutableList<MutableList<TrailSegment>>, currentTrailSegment: TrailSegment, currentTrail: Trail) {
        var lookForStepNumber = currentTrailSegment.pathNumber + 1
        var nextSteps = mutableListOf<TrailSegment>()
        print("->${currentTrailSegment.pathNumber}")
        if (!currentTrail.trailSegments.contains(currentTrailSegment)) {
            currentTrail.trailSegments.add(currentTrailSegment)
        }

        if (currentTrailSegment.pathNumber == 9) {
            currentTrail.trailScore += 1
            return
        }

        var trailUp = TrailSegment(currentTrailSegment.x, currentTrailSegment.y - 1, -1)
        var trailDown = TrailSegment(currentTrailSegment.x, currentTrailSegment.y + 1, -1)
        var trailRight = TrailSegment(currentTrailSegment.x + 1, currentTrailSegment.y, -1)
        var trailLeft = TrailSegment(currentTrailSegment.x - 1, currentTrailSegment.y, -1)

        if ((0 <= trailUp.x && trailUp.x <= fullMap[0].size - 1) &&
            (0 <= trailUp.y && trailUp.y <= fullMap.size - 1)){
            trailUp.pathNumber = fullMap[currentTrailSegment.y - 1][currentTrailSegment.x].pathNumber
            if (trailUp.pathNumber == lookForStepNumber) {
                nextSteps.add(trailUp)
            }
        }
        if ((0 <= trailDown.x && trailDown.x <= fullMap[0].size - 1) &&
            (0 <= trailDown.y && trailDown.y <= fullMap.size - 1)){
            trailDown.pathNumber = fullMap[currentTrailSegment.y + 1][currentTrailSegment.x].pathNumber
            if (trailDown.pathNumber == lookForStepNumber) {
                nextSteps.add(trailDown)
            }
        }
        if ((0 <= trailRight.x && trailRight.x <= fullMap[0].size - 1) &&
            (0 <= trailRight.y && trailRight.y <= fullMap.size - 1)){
            trailRight.pathNumber = fullMap[currentTrailSegment.y][currentTrailSegment.x + 1].pathNumber
            if (trailRight.pathNumber == lookForStepNumber) {
                nextSteps.add(trailRight)
            }
        }
        if ((0 <= trailLeft.x && trailLeft.x <= fullMap[0].size - 1) &&
            (0 <= trailLeft.y && trailLeft.y <= fullMap.size - 1)){
            trailLeft.pathNumber = fullMap[currentTrailSegment.y][currentTrailSegment.x - 1].pathNumber
            if (trailLeft.pathNumber == lookForStepNumber) {
                nextSteps.add(trailLeft)
            }
        }

        for (t in nextSteps) {
            nextStep2(fullMap, t, currentTrail)
        }

        return
    }


    private fun printMap(fullMap: MutableList<MutableList<TrailSegment>>) {
        println()
        for(y in fullMap)
        {
            for (x in y) {
                print(x.pathNumber)
            }
            println()
        }
        println()
    }

    override fun part2(inputLines: List<String>) {
        var total = 0
        var trails = mutableListOf<Trail>()
        var trailId = 0
        var fullMap = mutableListOf<MutableList<TrailSegment>>()

        for (l in inputLines.indices) {
            println("Line: ${inputLines[l]}")
            var tempList = mutableListOf<TrailSegment>()
            for (c in 0 until inputLines.elementAt(l).length) {

                var tempInt = inputLines.elementAt(l).elementAt(c).digitToInt()
                //fullMap[l][c] = TrailSegment(c, l, tempInt)
                var tempTrailSegement = TrailSegment(c, l, tempInt)
                if (tempInt == 0) {
                    var tempTrail = Trail(trailId)
                    tempTrail.trailSegments.add(TrailSegment(c, l, tempInt))
                    trails.add(tempTrail)
                    trailId += 1
                }
                tempList.add(tempTrailSegement)
            }
            fullMap.add(tempList)
        }

        println("Total trailHeads: ${trails.size}")
        for(t in trails) {
            nextStep2(fullMap, t.trailSegments.first(), t)
            println()
        }

        printMap(fullMap)

        var totalOfScores = trails.sumOf { it.trailScore }

        println("Total score: $totalOfScores")
    }
}