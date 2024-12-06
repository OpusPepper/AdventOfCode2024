package days

import java.util.*

class pageOrderingRules (var x: Int, var y: Int)
class day5 : Iday {
    override fun part1(inputLines: List<String>) {
        var total = 0
        val listOfRules = mutableListOf<pageOrderingRules>()
        val printPages = mutableListOf<String>()

        for (l in inputLines){
            println("Line: $l")
            if (l.contains("|")) {
                val splitL = l.split("|")
                val newpageOrderingRules = pageOrderingRules(splitL.elementAt(0).toInt(), splitL.elementAt(1).toInt())
                listOfRules.add(newpageOrderingRules)
            }
            else if (l.contains(",")) {
                printPages.add(l)
            }
        }

        for(p in printPages) {
            println("Page: $p")
            val pagesInOrder = p.split(",").map { it.toInt() }
            var printIsValid = true

            for (n in pagesInOrder) {
                //println("Evaluating page: $n")
                val rulesForNum = listOfRules.filter { x -> x.x == n }

                for(x in rulesForNum) {
                    val positionX = pagesInOrder.indexOf(x.x)
                    val positionY = pagesInOrder.indexOf(x.y)

                    //println("Rule: ${x.x}|${x.y} - posX: $positionX, posY: $positionY")
                    if (positionX != -1 && positionY != -1) {
                        printIsValid = printIsValid && positionX < positionY
                    }
                    //println("$n Isvalid: $printIsValid")
                }
            }
            //println("${pagesInOrder.size} / 2 = ${pagesInOrder.size/2}")
            val middlePage = pagesInOrder.elementAt((pagesInOrder.size / 2) )
            println("PagesInOrder: $pagesInOrder - Valid: $printIsValid - Middle page: $middlePage")
            if (printIsValid) {
                total+= middlePage
            }
        }


        println("Total: $total")
    }

    override fun part2(inputLines: List<String>) {
        var total = 0
        var totalValidFirstTime = 0
        var totalValidSecondTime = 0

        val listOfRules = mutableListOf<pageOrderingRules>()
        val printPages = mutableListOf<String>()

        for (l in inputLines){
            println("Line: $l")
            if (l.contains("|")) {
                val splitL = l.split("|")
                val newpageOrderingRules = pageOrderingRules(splitL.elementAt(0).toInt(), splitL.elementAt(1).toInt())
                listOfRules.add(newpageOrderingRules)
            }
            else if (l.contains(",")) {
                printPages.add(l)
            }
        }

        for(p in printPages) {
            println("Page: $p")
            val pagesInOrder = p.split(",").map { it.toInt() }
            val printIsValid = isValid(pagesInOrder, listOfRules)

            //println("${pagesInOrder.size} / 2 = ${pagesInOrder.size/2}")
            var middlePage = pagesInOrder.elementAt((pagesInOrder.size / 2) )
            println("PagesInOrder: $pagesInOrder - Valid: $printIsValid - Middle page: $middlePage")
            if (printIsValid) {
                //total+= middlePage
                totalValidFirstTime+= 1
            }
            else {
                val mySortedList = sortMyListofPages(pagesInOrder, listOfRules)

                val isNewValid = isValid(mySortedList, listOfRules)
                middlePage = mySortedList.elementAt((mySortedList.size / 2) )
                println("Reordered list: $mySortedList - Valid: $isNewValid - Middle: $middlePage")
                if (isNewValid) {
                    totalValidSecondTime+= 1
                    total+= middlePage
                }

            }
        }

        println("Total print lists input: ${printPages.size}")
        println("Valid first time: $totalValidFirstTime")
        println("Valid first time: $totalValidSecondTime")
        println()
        println("Total of middlepages for second round valid: $total")
    }

    private fun isValid(listOfPages: List<Int>, listOfRules: MutableList<pageOrderingRules>) : Boolean {
        var printIsValid = true
        for (n in listOfPages) {
            //println("Evaluating page: $n")
            val rulesForNum = listOfRules.filter { x -> x.x == n }

            for(x in rulesForNum) {
                val positionX = listOfPages.indexOf(x.x)
                val positionY = listOfPages.indexOf(x.y)

                //println("Rule: ${x.x}|${x.y} - posX: $positionX, posY: $positionY")
                if (positionX != -1 && positionY != -1) {
                    printIsValid = printIsValid && positionX < positionY
                }
                //println("$n Isvalid: $printIsValid")
            }
        }
        return printIsValid
    }

    private fun sortMyListofPages(pagesInOrder: List<Int>, listOfRules: MutableList<pageOrderingRules>): List<Int> {
        val copyOfPagesInOrder = mutableListOf<Int>()
        for (p in pagesInOrder) {
            copyOfPagesInOrder.add(p)
        }
        for (p in pagesInOrder) {
            var whichWayToMove: Int
            do {
                val indexOfP = copyOfPagesInOrder.indexOf(p)
                val p1 = if (indexOfP == 0)  null else copyOfPagesInOrder.elementAt(indexOfP - 1)
                val p3 = if (indexOfP == (copyOfPagesInOrder.size - 1)) null else copyOfPagesInOrder.elementAt(indexOfP + 1)
                whichWayToMove = figureOutWhichWayToMove(p1, p, p3, listOfRules)

                if (whichWayToMove == -1) {
                    Collections.swap(copyOfPagesInOrder, indexOfP, indexOfP-1)
                } else if (whichWayToMove == 1) {
                    Collections.swap(copyOfPagesInOrder, indexOfP,indexOfP+1)
                }
                //println("Sorting: element: $p whichWayToMove: $whichWayToMove list: $copyOfPagesInOrder")
            } while (whichWayToMove != 0)
        }
        return copyOfPagesInOrder
    }

    private fun figureOutWhichWayToMove(pageToLeft: Int?, currentPage: Int, pageToRight: Int?, rules: MutableList<pageOrderingRules>) : Int {
        val findXRules = rules.filter { r -> r.x == currentPage}
        val findYRules = rules.filter { r -> r.y == currentPage }

        //println("L: $pageToLeft C: $currentPage R: $pageToRight")

        return if (pageToLeft != null && findXRules.any { r -> r.y == pageToLeft }) {
            -1
        } else if (pageToRight != null && findYRules.any { r -> r.x == pageToRight}) {
            1
        } else {
            0
        }
    }

}