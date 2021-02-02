// SPDX-License-Identifier: Apache-2.0

package gcd

import chisel3.stage.ChiselStage
import dotvisualizer.stage.DiagrammerStage
import firrtl.stage.FirrtlSourceAnnotation


/** This provides an example of how to produce a very simple diagram of a
  * chisel module. The `Array` below is a list of command line arguments.
  * You can see all possible options by adding `"--help"` to its arguments
  *
  * Diagrammer needs a low firrtl file to graph, the
  * `FirrtlSourceAnnotation(ChiselStage.emitFirrtl(new GCD))` is a simple
  * recipe for getting the a low firrtl version of Chisel Module
  *
  * The diagrammer will attempt to open the diagram in your browser. There are options
  * to change what that open command is
  *
  * Try replacing `new GCD` to `new DecoupledGcd(24)` to see the differences
  */
object GcdDiagramExample {
  def main(args: Array[String]): Unit = {
    (new DiagrammerStage).execute(
      Array(
        "--target-dir", "test_run_dir/gcd_diagram",
        "--rank-dir", "TB",
        "--rank-elements"
      ),
      Seq(
        FirrtlSourceAnnotation(ChiselStage.emitFirrtl(new GCD))
    ))
  }
}
