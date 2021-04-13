import chisel3.iotesters.PeekPokeTester
import org.scalatest._

class DatapathTestTest0(dut: Datapath) extends PeekPokeTester(dut) {
    poke(dut.io.test,0)

    step(1)//IF
    expect(dut.io.OutInst, 2114000)

    step(1)//ID
    expect(dut.io.InA,0)
    expect(dut.io.InB,0)
    expect(dut.io.InImm,32)  

    step(1)//EX
    expect(dut.io.OutEx,32)

    step(1)//MA
    expect(dut.io.OutMA,32)
    expect(dut.io.OutDA, 8)

    step(1)//WB
    

}

class DatapathTest0 extends FlatSpec with Matchers {
  "Datapath " should "pass" in {
    chisel3.iotesters.Driver(() => new Datapath) { c => new DatapathTestTest0(c)} should be (true)
  }
}