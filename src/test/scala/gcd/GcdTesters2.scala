// See README.md for license details.

package gcd

import chisel3._
import chisel3.tester._
import org.scalatest.FreeSpec
import chisel3.experimental.BundleLiterals._

/**
  * This is a trivial example of how to run this Specification
  * From within sbt use:
  * {{{
  * testOnly gcd.GcdDecoupledTester
  * }}}
  * From a terminal shell use:
  * {{{
  * sbt 'testOnly gcd.GcdDecoupledTester'
  * }}}
  */
//scalastyle:off magic.number
class GcdTesters2 extends FreeSpec with ChiselScalatestTester {

  "Gcd should calculate proper greatest common denominator" in {
    test(new DecoupledGcd(16)) { dut =>
      dut.input.initSource()
      dut.input.setSourceClock(dut.clock)
      dut.output.initSink()
      dut.output.setSinkClock(dut.clock)

      val testValues = for { x <- 0 to 10; y <- 0 to 10} yield (x, y)
      val inputSeq = testValues.map { case (x, y) => (new GcdInputBundle(16)).Lit(_.value1 -> x.U, _.value2 -> y.U) }
      val resultSeq = testValues.map { case (x, y) =>
        (new GcdOutputBundle(16)).Lit(_.value1 -> x.U, _.value2 -> y.U, _.gcd -> BigInt(x).gcd(BigInt(y)).U)
      }

      fork {
        // push inputs into the calculator, stall for 11 cycles one third of the way
        val (seq1, seq2) = inputSeq.splitAt(resultSeq.length / 3)
        dut.input.enqueueSeq(seq1)
        dut.clock.step(11)
        dut.input.enqueueSeq(seq2)
      }.fork {
        // retrieve computations from the calculator, stall for 10 cycles one half of the way
        val (seq1, seq2) = resultSeq.splitAt(resultSeq.length / 2)
        dut.output.expectDequeueSeq(seq1)
        dut.clock.step(10)
        dut.output.expectDequeueSeq(seq2)
      }.join()

    }
  }
}

class GcdTesters3 extends GcdTesters2 {
}
class GcdTesters4 extends GcdTesters2 {
}
class GcdTesters5 extends GcdTesters2 {
}
class GcdTesters6 extends GcdTesters2 {
}
class GcdTesters7 extends GcdTesters2 {
}
class GcdTesters8 extends GcdTesters2 {
}
class GcdTesters9 extends GcdTesters2 {
}
class GcdTesters10 extends GcdTesters2 {
}
class GcdTesters11 extends GcdTesters2 {
}
