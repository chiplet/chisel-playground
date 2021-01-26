package playground

import org.scalatest._
import chiseltest._
import chisel3._

class PassthroughTester extends FlatSpec with ChiselScalatestTester {
  it should "pass 3 through unaltered" in {
    test(new Passthrough) { c =>
      c.io.in.poke(3.U)
      c.io.out.expect(3.U)
    }
  }
  it should "pass 1234 through unaltered" in {
    test(new Passthrough) { c =>
      c.io.in.poke(1234.U)
      c.io.out.expect(1234.U)
    }
  }
}