package playground

import chisel3._
import chisel3.util._
import chisel3.stage.ChiselStage
import java.nio.file.{Files, Paths}

class MemoryGenerator(
  filePath: String,
  addressBusWidth: Int = 32,
  dataBusWidth: Int = 32
)
extends Module {
  val io = IO(new Bundle {
    val address = Input(UInt(addressBusWidth.W))  // address of instruction
    val data_out = Output(UInt(dataBusWidth.W))   // instruction output
  })

  // read bytes from a file
  val fileBytes: List[Byte] = Files.readAllBytes(Paths.get(filePath)).toList

  // pad number of bytes to a multiple of `padWidth`
  val padWidth: Int = 4
  val paddedLength = (fileBytes.length + padWidth - 1)/padWidth*padWidth
  val padBytes: List[Byte] = List.fill(paddedLength - fileBytes.length)(0)
  val memorySignedBytes = fileBytes ::: padBytes
  val memoryBytes: List[Int] = memorySignedBytes.map(_&0xff)

  // create memory registers initialized with file bytes
  val memory = RegInit(VecInit(memoryBytes.map(_.U(8.W))))

  io.data_out := Cat(
    Cat(memory(io.address+3.U), memory(io.address+2.U)),
    Cat(memory(io.address+1.U), memory(io.address))
  )
}

object MemoryGeneratorVerilog extends App {
  val filePath = "/Users/user/Code/chisel/chisel-playground/resources/allbytes.bin"
  println((new ChiselStage).emitVerilog(new MemoryGenerator(filePath)))
}