// See LICENSE for license details.

package examples.test

class GCDUnitTester extends SteppedHWIOTester {
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

  val (a, b, z) = (64, 48, 16)
  val device_under_test = Module(new GCD)
  val gcd = device_under_test

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

class GCDTester extends ChiselFlatSpec {
  "a" should "b" in {
    assertTesterPasses { new GCDUnitTester }
  }
}
