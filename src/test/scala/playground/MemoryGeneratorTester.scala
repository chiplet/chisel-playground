package playground

import chisel3._
import chiseltest._
import org.scalatest.FlatSpec

class MemoryGeneratorTester extends FlatSpec with ChiselScalatestTester {
  it should "read same bytes given in the file" in {
    test(new MemoryGenerator("/Users/user/Code/chisel/chisel-playground/resources/test.bin")) { c =>
      c.io.address.poke(0.U)
      c.io.data_out.expect(0x6c6c6568.U)
      c.io.address.poke(4.U)
      c.io.data_out.expect(0x6f77206f.U)
      c.io.address.poke(8.U)
      c.io.data_out.expect(0x00646c72.U)
    }
  }

  // hardcoded for 32-bit
  it should "read bytes from 0 to 255 correctly" in {
    test(new MemoryGenerator("/Users/user/Code/chisel/chisel-playground/resources/allbytes.bin")) { c =>
      for (i <- (0L until 64).map(_*4)) {
        c.io.address.poke(i.U)
        c.io.data_out.expect(((i+3) << 24 | (i+2) << 16 | (i+1) << 8 | i).U)
      }
    }
  }
}
