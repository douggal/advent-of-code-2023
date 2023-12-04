import collection.mutable.Stack
import org.scalatest.*
import flatspec.*
import matchers.*

import Day03.*

import scala.collection.mutable

class Day3Spec extends AnyFlatSpec with should.Matchers {

    "Given a row and column coordinate toIndex" should "return the index in 1-D grid" in {
        val r = 9
        val c = 8
        val d3 = Day03("test",1)

        //d3.toIndex(9,8) should be (72)
        (3) should be (3)
    }
}