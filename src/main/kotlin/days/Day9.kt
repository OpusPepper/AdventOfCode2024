package days

class DiskBlock(inPosition: Int) {
    var position = inPosition
    var blockValue: Char = '.'
    var fileId = 0
    var isFreeSpace = false
    var fileWidth = 0
}
class Day9 : Iday {
    private var fileSystem: MutableList<DiskBlock> = mutableListOf()
    override fun part1(inputLines: List<String>) {
        println("Starting program")
        var diskMap = inputLines[0]
        var checksum = 0L
        var position = 0
        var fileId = 0
        println("diskMap: ${diskMap}")

        for(b in diskMap.indices) {
            var modOfB = b % 2
            //println("Modofb: $modOfB")

            if (modOfB == 0) {
                for (b2 in 1..diskMap[b].digitToInt()) {
                    var newAllocation = DiskBlock(position)
                    newAllocation.blockValue = '*'
                    newAllocation.fileId = fileId
                    newAllocation.isFreeSpace = false
                    position += 1
                    fileSystem.add(newAllocation)
                }
                if (diskMap[b].digitToInt() > 0 ) {
                    fileId += 1
                }
            }
            else if (modOfB == 1) {
                for (b2 in 1..diskMap[b].digitToInt()) {
                    var newAllocation = DiskBlock(position)
                    newAllocation.isFreeSpace = true
                    position += 1
                    fileSystem.add(newAllocation)
                }
            }
            else {
                println("Error")
            }
        }
        println("last fileid: ${fileId}")
        printFileSystem(fileSystem)

        var movingPosition = fileSystem.size - 1

        var numberOfBlocksMoved = 0
        var isFinished = false

        do {
            if (!fileSystem.elementAt(movingPosition).isFreeSpace) {
                moveNextBlock(fileSystem, movingPosition)
                //printFileSystem(fileSystem)
                //println()
                numberOfBlocksMoved += 1
                movingPosition -= 1
            }
            else {
                movingPosition -=1
            }

            if (movingPosition % 10000 == 0) {
                println("movingPosition: ${movingPosition}")
            }
        } while (movingPosition != 0 && stillFreeSpaceToOptimize(fileSystem))

        checksum = calculateChecksumOnFileSystem(fileSystem)
        printFileSystem(fileSystem)
        println("Checksum: ${checksum}")
    }

    private fun moveNextBlock(fileSystem: MutableList<DiskBlock>, positionToMove: Int) {
        var nextFreeBlockPosition = fileSystem.indexOfFirst { it.isFreeSpace }

        fileSystem[nextFreeBlockPosition] = fileSystem[positionToMove].also { fileSystem[positionToMove] = fileSystem[nextFreeBlockPosition] }
    }

    private fun stillFreeSpaceToOptimize(fileSystem: MutableList<DiskBlock>) : Boolean {

        var lastPositionToHaveANumber = fileSystem.indexOfLast { it.blockValue != '.' }
        var lastPositionToHaveAFreeSpace = fileSystem.indexOfFirst { it.blockValue == '.' }

        return lastPositionToHaveAFreeSpace < lastPositionToHaveANumber
    }

    private fun calculateChecksumOnFileSystem(inFileSystem: MutableList<DiskBlock>) : Long {
        var positionOfLastDigit = inFileSystem.indexOfLast { it.blockValue != '.' }
        var total = 0L
        for(n in 0 .. positionOfLastDigit) {
            //println("$n * ${inFileSystem[n].fileId} = ${n * inFileSystem[n].fileId}")
            total += n * inFileSystem[n].fileId
        }
        return total
    }

    private fun printFileSystem(inFileSystem: MutableList<DiskBlock>) {
        for(b in inFileSystem) {
            print(b.blockValue)
        }
        println()
    }

    override fun part2(inputLines: List<String>) {
        println("Starting program")
        var diskMap = inputLines[0]
        var checksum = 0L
        var position = 0
        var fileId = 0
        var widthCounter = 0
        println("diskMap: ${diskMap}")

        for(b in diskMap.indices) {
            var modOfB = b % 2
            //println("Modofb: $modOfB")

            if (modOfB == 0) {
                for (b2 in 1..diskMap[b].digitToInt()) {
                    var newAllocation = DiskBlock(position)
                    newAllocation.blockValue = '*'
                    //newAllocation.blockValue = fileId.digitToChar() // use this for debugging with the example
                    newAllocation.fileId = fileId
                    newAllocation.isFreeSpace = false
                    position += 1
                    fileSystem.add(newAllocation)
                }
                if (diskMap[b].digitToInt() > 0 ) {
                    fileId += 1
                }
            }
            else if (modOfB == 1) {
                //FreeSpace
                for (b2 in 1..diskMap[b].digitToInt()) {
                    var newAllocation = DiskBlock(position)
                    newAllocation.isFreeSpace = true
                    position += 1
                    fileSystem.add(newAllocation)
                }
            }
            else {
                println("Error")
            }
        }  // Finished loading
        println("last fileid: ${fileId}")
        var getLastFileIndex = fileSystem.indexOfLast { !it.isFreeSpace }
        var lastFileId = fileSystem[getLastFileIndex].fileId
        println("actual last fileid: ${lastFileId}")
        printFileSystem(fileSystem)

        var movingPosition = fileSystem.size - 1
        var movingFileId = lastFileId

        var numberOfFilesMoved = 0

        //printFileSystem(fileSystem)
        do {
            var currentFile = fileSystem.filter { it.fileId == movingFileId }.toMutableList()
            moveNextFile(fileSystem, currentFile)
            numberOfFilesMoved += 1
            movingFileId -= 1

            if (movingFileId % 1000 == 0) {
                println("Moving fileid: ${movingFileId}")
            }
            //printFileSystem(fileSystem)
        } while (movingFileId != 0)

        checksum = calculateChecksumOnFileSystem(fileSystem)
        printFileSystem(fileSystem)
        println("Checksum: ${checksum}")
    }

    private fun moveNextFile(fileSystem: MutableList<DiskBlock>, fileIdToMove: MutableList<DiskBlock>) {
        //println("moveNextFile started")
        //println("fileId: ${fileIdToMove[0].fileId}")
        var findThisFreeSpace = ".".repeat(fileIdToMove.size)
        var fileSystemAsString = buildString { for (s in fileSystem) append(s.blockValue) }
        //println("fileSystemAsString: $fileSystemAsString")
        var firstFreeSpaceForFile = fileSystemAsString.indexOf(findThisFreeSpace)
        //println("findThisFreeSpace: ${findThisFreeSpace}, firstFreeSpacForFile: ${firstFreeSpaceForFile}")
        var filePositionToSwap = fileIdToMove.minOf { it.position }
        if (firstFreeSpaceForFile > 0 && (firstFreeSpaceForFile < filePositionToSwap)) {
            var freeSpacePositionToSwap = firstFreeSpaceForFile

            //println("freeSpacePositionToSwap: ${freeSpacePositionToSwap}, filePositionToSwap: ${filePositionToSwap} ")
            for (p in 0 until fileIdToMove.size) {
                fileSystem[freeSpacePositionToSwap] = fileSystem[filePositionToSwap].also { fileSystem[filePositionToSwap] = fileSystem[freeSpacePositionToSwap] }
                freeSpacePositionToSwap += 1
                filePositionToSwap += 1
            }
        }
    }
}