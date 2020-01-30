package intervals

import chisel3._
import chisel3.experimental.{ChiselRange, FixedPoint, Interval}



class IntervalExample extends MultiIOModule {
  /*
  val i1 = Interval()                 // No range specified.
  val i2 = Interval(range"[?,?]")     // Another way of specifying no range.
  val i3 = Interval(range"[?,?].4")   // Also inferred range, but with a binary point of 2
  val i4 = Interval(range"[-8,6).3")  // Range goes from -8 to 5.875, binary point is 3
  */

  val inInterval = IO(Input(Interval(range"[0, 100].3")))
  val outInterval = IO(Output(Interval(range"[?, ?].?")))
  val inFixed = IO(Input(FixedPoint(11.W, 3.BP)))
  val outFixed = IO(Output(FixedPoint()))

  val consts = 1 until 10
  val regsInterval = consts.map(_ => RegInit(inInterval))
  regsInterval.foldLeft(inInterval){
    (d, r) => r := d; r
  }

  regsInterval.zip(consts).foldLeft()

  outInterval := consts.foldLeft(inInterval) {
    case (i: Interval, const) => RegNext(i * const.I)
  }

  outFixed := consts.foldLeft(inFixed) {
    case (i: FixedPoint, const) => RegNext(i * const.F(0.BP))
  }

}
