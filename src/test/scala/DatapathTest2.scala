import chisel3.iotesters.PeekPokeTester
import org.scalatest._

class DatapathTestTest2(dut: Datapath) extends PeekPokeTester(dut) {
    poke(dut.io.test,2)

    step(1)//IF 0 0 0 0
    expect(dut.io.OutInst,2114000)

    step(1)//ID IF 0 0 0
    expect(dut.io.InA,0)
    expect(dut.io.InB,0)
    expect(dut.io.InImm,32)  

    expect(dut.io.OutInst,538641)

    step(1)//EX ID IF 0 0
    expect(dut.io.OutEx,32)

    expect(dut.io.InA,0)
    expect(dut.io.InB,0)
    expect(dut.io.InImm,8) 

    expect(dut.io.OutInst,2109890)

    step(1)//MA EX ID IF 0
    expect(dut.io.OutMA,32)
    expect(dut.io.OutDA, 8)

    expect(dut.io.OutEx,-8)

    expect(dut.io.InA,0)
    expect(dut.io.InB,0)
    expect(dut.io.InImm,0)

    expect(dut.io.OutInst,2511364) 

    step(1)//WB MA EX ID IF


    expect(dut.io.OutDA, 7)
    expect(dut.io.OutMA,-8)

    expect(dut.io.OutEx, -7)

    expect(dut.io.InA,32)
    expect(dut.io.InB,0)
    expect(dut.io.InImm,0)

    expect(dut.io.OutInst,2511364) 
    
    
    step(1)//WB MA EX ID 0    


    expect(dut.io.OutDA, 6)
    expect(dut.io.OutMA, -7)

    expect(dut.io.OutEx, 32)

    expect(dut.io.InA,32)
    expect(dut.io.InB,0)
    expect(dut.io.InImm,0)


    step(1)//WB MA EX 0 0


    expect(dut.io.OutDA, 10)
    expect(dut.io.OutMA, 32)

    expect(dut.io.OutEx, 32)

    step(1)//WB MA 0 0 0

    expect(dut.io.OutDA, 10)
    expect(dut.io.OutMA, 32)



}

class DatapathTest2 extends FlatSpec with Matchers {
  "Datapath " should "pass" in {
    chisel3.iotesters.Driver(() => new Datapath) { c => new DatapathTestTest2(c)} should be (true)
  }
}