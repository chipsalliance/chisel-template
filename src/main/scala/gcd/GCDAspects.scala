package gcd

import chisel3.aop.inspecting.InspectingAspect
import chisel3.aop.Select
import chisel3.aop.injecting.InjectingAspect
import chisel3._

case class GCDInspection() extends InspectingAspect({
  top: GCD =>
    Select.registers(top).map(_.toTarget.serialize).foreach(println)
})

case class GCDInjection() extends InjectingAspect(
  {top: GCD => Seq(top)},
  {gcd: GCD =>
    gcd.io.outputValid := true.B
  }
)
