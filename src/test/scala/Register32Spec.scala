import chisel3.iotesters.PeekPokeTester
import org.scalatest._

class Register32Test(dut: Register32) extends PeekPokeTester(dut) {
    poke(dut.io.DataIn, 65)
    poke(dut.io.AA,2)
    poke(dut.io.BA,20)
    poke(dut.io.DA,1)
    step(1)
    poke(dut.io.DA,5)
    step(1)
    poke(dut.io.DA,2)
    step(1)
    poke(dut.io.DA,17)
    step(1)
    poke(dut.io.DA,20)
    step(1)
    poke(dut.io.DataIn,1)
    step(1)
    expect(dut.io.AOut,65)
    expect(dut.io.BOut,1)

    for (i <- 0 to 31){
      poke(dut.io.AA,i)
      val reg = peek(dut.io.AOut)
      println(i + ": " + reg + "\t")
    }
}

class Register32Spec extends FlatSpec with Matchers {
  "Register32 " should "pass" in {
    chisel3.iotesters.Driver(() => new Register32) { c => new Register32Test(c)} should be (true)
  }
}