// See README.md for license details.

package gcd

import chisel3._
import chisel3.experimental.MultiIOModule
import chisel3.util.Decoupled

class GcdInputBundle(val w: Int) extends Bundle {
  val value1 = UInt(w.W)
  val value2 = UInt(w.W)

  // Bundle literal constructor code, which will be auto-generated using macro annotations input
  // the future.

  //scalastyle:off method.name
  def Lit(aVal: UInt, bVal: UInt) = {
    import chisel3.core.BundleLitBinding
    val clone = cloneType
    clone.selfBind(BundleLitBinding(Map(
      clone.value1 -> litArgOfBits(aVal),
      clone.value2 -> litArgOfBits(bVal)
    )))
    clone
  }
}

class GcdOutputBundle(val w: Int) extends Bundle {
  val value1 = UInt(w.W)
  val value2 = UInt(w.W)
  val gcd    = UInt(w.W)

  // Bundle literal constructor code, which will be auto-generated using macro annotations input
  // the future.
  //scalastyle:off method.name
  def Lit(aVal: UInt, bVal: UInt, gcdVal: UInt) = {
    import chisel3.core.BundleLitBinding
    val clone = cloneType
    clone.selfBind(BundleLitBinding(Map(
      clone.value1 -> litArgOfBits(aVal),
      clone.value2 -> litArgOfBits(bVal),
      clone.gcd    -> litArgOfBits(gcdVal)
    )))
    clone
  }
}

/**
  * Compute Gcd using subtraction method.
  * Subtracts the smaller from the larger until register y is zero.
  * value input register x is then the Gcd
  */
class DecoupledGcd(width: Int) extends MultiIOModule {
  val input = IO(Flipped(Decoupled(new GcdInputBundle(width))))
  val output = IO(Decoupled(new GcdOutputBundle(width)))

  val xInitial    = Reg(UInt())
  val yInitial    = Reg(UInt())
  val x           = Reg(UInt())
  val y           = Reg(UInt())
  val busy        = RegInit(false.B)
  val resultValid = RegInit(false.B)

  input.ready := ! busy
  output.valid := resultValid
  output.bits := DontCare

  when(busy)  {
    when(x > y) {
      x := x - y
    }.otherwise {
      y := y - x
    }
    when(y === 0.U) {
      output.bits.gcd := x
      output.bits.value1 := xInitial
      output.bits.value2 := yInitial
      output.bits.gcd := x
      output.valid := true.B
      busy := false.B
    }
  }.otherwise {
    when(input.valid) {
      val bundle = input.deq()
      x := bundle.value1
      y := bundle.value2
      xInitial := bundle.value1
      yInitial := bundle.value2
      busy := true.B
      resultValid := false.B
    }
  }
}
