import scala.io.{Source, StdIn}
import scala.collection.mutable.Set

object ScalaSolver {
	var objective: String = ""
	var clues: Vector[String] = Vector()
	var data: Array[Array[Set[String]]] = Array()
	var answer = -1

	def main(args: Array[String]): Unit = {
		val iterator = Source.fromFile("EinsteinInput.txt").getLines
		objective = iterator.next
		val size = iterator.next.toInt
		data = Array.ofDim[Set[String]](size, size)
		for (i <- 0 until size) {
			val str = iterator.next
			for (j <- 0 until size) {
				data(i)(j) = Set[String]()
				for (k <- 0 until str.size) {
					data(i)(j) += ("" + str(k))
				}
			}
		}
		clues = iterator.toVector

		displayData(data)
		println("Press Enter to continue")
		StdIn.readLine

		while (!gameWon) {
			readRules
			eliminateOptions
			displayData(data)

			println("Press Enter to continue")
			StdIn.readLine
		}
		displayData(data)
		println("The object you are looking for is in area #" + (answer + 1))
	}

	//Print statement for 2 dimensional array of set of strings
	def displayData(data: Array[Array[Set[String]]]): Unit = {
		for (i <- 0 to data.size - 1) {
			for (j <- 0 to data(i).size - 1) {
				print("[" + data(i)(j).mkString(", ") + "] ")
			}
			println
		}
	}

	def readRules(): Unit = {}

	def eliminateOptions(): Unit = {}

	def isValidOption(i: Int, j: Int, str: String): Boolean = {
		data(i)(j).contains(str) || data(i)(j).contains("!" + str)
	}

	def removeOption(i: Int, j: Int, str: String): Unit = {
		data(i)(j).remove(str)
	}

	def gameWon(): Boolean = {
		val index = objective(0) - 48
		val c = objective(1)

		for (i <- 0 until data.length) {
			if (data(index)(i).contains("!" + c)) {
				answer = i
				return true
			}
		}
		false
	}

}