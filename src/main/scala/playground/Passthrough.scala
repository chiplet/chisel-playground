package playground

import chisel3._
import chisel3.stage.ChiselStage

class Passthrough extends Module {
  val io = IO(new Bundle {
    val in = Input(UInt(64.W))
    val out = Output(UInt(64.W))
  })
  io.out := io.in
}

object PassthroughVerilog extends App {
  val verilog = (new ChiselStage).emitVerilog(new Passthrough)
  println(verilog)
}