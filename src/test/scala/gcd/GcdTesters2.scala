// See README.md for license details.

package gcd

import chisel3._
import chisel3.tester._
import firrtl.ExecutionOptionsManager
import org.scalatest.FreeSpec
import treadle.HasTreadleSuite

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
class GcdDecoupledTester extends FreeSpec with ChiselScalatestTester {

  "Gcd should calculate proper greatest common denominator" in {
    val manager = new ExecutionOptionsManager("decoupled gcd") with HasTreadleSuite {
      treadleOptions = treadleOptions.copy(
        writeVCD = true,
        setVerbose = false
      )
    }

    test(new DecoupledGcd(16), manager) { dut =>
      dut.input.initSource()
      dut.input.setSourceClock(dut.clock)
      dut.output.initSink()
      dut.output.setSinkClock(dut.clock)

      val testValues = for { x <- 1 to 10; y <- 1 to 10} yield (x, y)
      val inputSeq = testValues.map { case (x, y) => (new GcdInputBundle(16)).Lit(x.U, y.U) }
      val resultSeq = testValues.map { case (x, y) =>
        new GcdOutputBundle(16).Lit(x.U, y.U, BigInt(x).gcd(BigInt(y)).U)
      }

      fork {
        dut.input.enqueueSeq(inputSeq)
      }.fork {
        dut.output.expectDequeueSeq(resultSeq)
      }.join()

    }
  }
}
