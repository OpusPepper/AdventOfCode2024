package utility

import java.io.File
import java.io.InputStream

class FileReader {
    fun readMyFile(fileInfo: File): List<String>  {
        val inputStream: InputStream = fileInfo.inputStream()
        val lineList = mutableListOf<String>()

        inputStream.bufferedReader().forEachLine { lineList.add(it) }
        //lineList.forEach{println(">  " + it)}

        return lineList
    }
}