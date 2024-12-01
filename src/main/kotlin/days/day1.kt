package days

import kotlin.math.abs

class day1 : Iday {

    override fun part1(inputLines: List<String>) {
        val list1 = mutableListOf<Int>()
        val list2 = mutableListOf<Int>()

        for (l in inputLines) {
            //println("Line is: $l")
            val parts = l.split(Regex("\\s+"))

            list1.add(parts[0].toInt())
            list2.add(parts[1].toInt())
        }
        list1.sort()
        list2.sort()

        var total = 0
        for (i in list1.indices) {
            //println("list1["+i+"]: ${list1.elementAt(i)}")
            //println("list2["+i+"]: ${list2.elementAt(i)}")
            total += (abs(list1[i] - list2[i]))
        }

        println("Total: $total")
    }

    override fun part2(inputLines: List<String>) {
        val list1 = mutableListOf<Int>()
        val list2 = mutableListOf<Int>()

        for (l in inputLines) {
            //println("Line is: $l")
            val parts = l.split(Regex("\\s+"))

            list1.add(parts[0].toInt())
            list2.add(parts[1].toInt())
        }
        list1.sort()
        list2.sort()

        var total = 0
        for (i in list1.indices) {
            //println("list1["+i+"]: ${list1.elementAt(i)}")
            //println("list2["+i+"]: ${list2.elementAt(i)}")
            //println("list1[$i]: ${list1.elementAt(i)} = " + numberOfTimesNumberAppears(list2, list1.elementAt(i)) * list1.elementAt(i))
            total += numberOfTimesNumberAppears(list2, list1.elementAt(i)) * list1.elementAt(i)
        }

        println("Total: $total")
    }

    private fun numberOfTimesNumberAppears(inputList: List<Int>, numberToFind: Int): Int {
        return inputList.count { x -> x == numberToFind }
    }

}