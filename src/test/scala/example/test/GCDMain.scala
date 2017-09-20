// See LICENSE for license details.

package example.test

import chisel3._
import example.GCD

/**
  * This provides an alternate way to run tests, by executing then as a main
  * From sbt (Note: the test: prefix is because this main is under the test package hierarchy):
  * {{{
  * test:runMain example.test.GCDMain
  * }}}
  * To see all command line options use:
  * {{{
  * test:runMain example.test.GCDMain --help
  * }}}
  * To run with verilator:
  * {{{
  * test:runMain example.test.GCDMain --backend-name verilator
  * }}}
  * To run with verilator from your terminal shell use:
  * {{{
  * sbt 'test:runMain example.test.GCDMain --backend-name verilator'
  * }}}
  */
object GCDMain extends App {
  iotesters.Driver.execute(args, () => new GCD) {
    c => new GCDUnitTester(c)
  }
}