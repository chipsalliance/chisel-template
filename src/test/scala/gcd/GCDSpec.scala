// See README.md for license details.

package gcd

import chisel3._
import chisel3.experimental.BundleLiterals._
import chisel3.simulator.scalatest.ChiselSim
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers

/**
  * This is a trivial example of how to run this Specification
  * From within sbt use:
  * {{{
  * testOnly gcd.GCDSpec
  * }}}
  * From a terminal shell use:
  * {{{
  * sbt 'testOnly gcd.GCDSpec'
  * }}}
  * Testing from mill:
  * {{{
  * mill %NAME%.test.testOnly gcd.GCDSpec
  * }}}
  */
class GCDSpec extends AnyFreeSpec with Matchers with ChiselSim {

  "Gcd should calculate proper greatest common denominator" in {
    simulate(new DecoupledGcd(16)) { dut =>
      val testValues = for { x <- 0 to 10; y <- 0 to 10} yield (x, y)
      val inputSeq = testValues.map { case (x, y) => (new GcdInputBundle(16)).Lit(_.value1 -> x.U, _.value2 -> y.U) }
      val resultSeq = testValues.map { case (x, y) =>
        (new GcdOutputBundle(16)).Lit(_.value1 -> x.U, _.value2 -> y.U, _.gcd -> BigInt(x).gcd(BigInt(y)).U)
      }

      dut.reset.poke(true.B)
      dut.clock.step()
      dut.reset.poke(false.B)
      dut.clock.step()

      var sent, received, cycles: Int = 0
      while (sent != 100 && received != 100) {
        assert(cycles <= 1000, "timeout reached")

        if (sent < 100) {
          dut.input.valid.poke(true.B)
          dut.input.bits.value1.poke(testValues(sent)._1.U)
          dut.input.bits.value2.poke(testValues(sent)._2.U)
          if (dut.input.ready.peek().litToBoolean) {
            sent += 1
          }
        }

        if (received < 100) {
          dut.output.ready.poke(true.B)
          if (dut.output.valid.peekValue().asBigInt == 1) {
            dut.output.bits.gcd.expect(BigInt(testValues(received)._1).gcd(testValues(received)._2))
            received += 1
          }
        }

        // Step the simulation forward.
        dut.clock.step()
        cycles += 1
      }
    }
  }
}
