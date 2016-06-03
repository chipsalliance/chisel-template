// See LICENSE for license details.

package examples.test

import Chisel._
import Chisel.iotesters.{ChiselFlatSpec, SteppedHWIOTester}
import example.GCD

class GCDUnitTester extends SteppedHWIOTester {
  /**
    * compute the gcd and the number of steps it should take to do it
    *
    * @param a positive integer
    * @param b positive integer
    * @return the GCD of a and b
    */
  def computeGcd(a: Int, b: Int): (Int, Int) = {
    var x = a
    var y = b
    var depth = 1
    while(y > 0 ) {
      if (x > y) {
        x -= y
      }
      else {
        y -= x
      }
      depth += 1
    }
    (x, depth)
  }

  val device_under_test = Module(new GCD)
  val gcd = device_under_test

  for(i <- 1 to 100) {
    for(j <- 1 to 100) {
      val (a, b, z) = (64, 48, 16)

      poke(gcd.io.a, a)
      poke(gcd.io.b, b)
      poke(gcd.io.e, 1)
      step(1)
      poke(gcd.io.e, 0)

      val (expected_gcd, steps) = computeGcd(a, b)

      step(steps - 1) // -1 is because we step(1) already to toggle the enable
      expect(gcd.io.z, expected_gcd)
      expect(gcd.io.v, 1)
    }
}

class GCDTester extends ChiselFlatSpec {
  "GCD circuit" should "compute the correct greatest common denominator" in {
    assertTesterPasses { new GCDUnitTester }
  }
}
