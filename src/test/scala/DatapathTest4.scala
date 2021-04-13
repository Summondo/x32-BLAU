import chisel3.iotesters.PeekPokeTester
import org.scalatest._

class DatapathTestTest4(dut: Datapath) extends PeekPokeTester(dut) {
    poke(dut.io.test,4)

    step(1)//IF
    expect(dut.io.OutInst, 304)

    step(1)//ID IF
    expect(dut.io.InA,0)
    expect(dut.io.InB,0)
    expect(dut.io.InImm,4)  

    expect(dut.io.OutInst, 81936)
    
    step(1)//EX ID IF
    expect(dut.io.OutEx,0)

    expect(dut.io.InA,0)
    expect(dut.io.InB,0)
    expect(dut.io.InImm,0)

    expect(dut.io.OutInst, 0)

    step(1)//MA EX ID IF
    expect(dut.io.OutMA, 0)
    expect(dut.io.OutDA, 0)

    expect(dut.io.OutEx,0)

    expect(dut.io.InA,0)
    expect(dut.io.InB,0)
    expect(dut.io.InImm,0)

    expect(dut.io.OutInst, 278544)
}

class DatapathTest4 extends FlatSpec with Matchers {
  "Datapath " should "pass" in {
    chisel3.iotesters.Driver(() => new Datapath) { c => new DatapathTestTest4(c)} should be (true)
  }
}