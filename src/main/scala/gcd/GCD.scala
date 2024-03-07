// See README.md for license details.

package gcd

import chisel3._
import circt.stage.ChiselStage

/**
  * Compute GCD using subtraction method.
  * Subtracts the smaller from the larger until register y is zero.
  * value in register x is then the GCD
  */
class GCD extends Module {
  val io = IO(new Bundle {
    val value1        = Input(UInt(16.W))
    val value2        = Input(UInt(16.W))
    val loadingValues = Input(Bool())
    val outputGCD     = Output(UInt(16.W))
    val outputValid   = Output(Bool())
  })

  val x  = Reg(UInt())
  val y  = Reg(UInt())

  when(x > y) { x := x - y }
    .otherwise { y := y - x }

  when(io.loadingValues) {
    x := io.value1
    y := io.value2
  }

  io.outputGCD := x
  io.outputValid := y === 0.U
}

/**
 * Generate Verilog sources and save it in file GCD.v
 */
object GCD extends App {
  val verilog_src = ChiselStage.emitSystemVerilog(
      new GCD(),
      firtoolOpts = Array("-disable-all-randomization",
                          "-strip-debug-info"))
  val fverilog = os.pwd / "GCD.v"
  if(os.exists(fverilog))
    os.remove(fverilog)
  os.write(fverilog, verilog_src)
}
