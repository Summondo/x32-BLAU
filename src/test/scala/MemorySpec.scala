import chisel3.iotesters.PeekPokeTester
import org.scalatest._

class MemoryTest(dut: Memory) extends PeekPokeTester(dut) {
    poke(dut.io.wrdData,1013)
    poke(dut.io.wrdA,31)
    poke(dut.io.wrd,true)
    step(1)
    
    poke(dut.io.wrd,false)
    poke(dut.io.wrdA,2)
    step(1)
    
    poke(dut.io.wrdA,25)
    poke(dut.io.wrd,true)
    step(1)

    poke(dut.io.wrdData,1000)
    poke(dut.io.wrdA,31)
    step(1)

    poke(dut.io.rddA,31)
    step(1)
    expect(dut.io.rddData,1000)

    poke(dut.io.rddA,5)
    step(1)
    expect(dut.io.rddData,0)

    poke(dut.io.rddA,25)
    step(1)
    expect(dut.io.rddData,1013)

}

class MemorySpec extends FlatSpec with Matchers {
  "Register32 " should "pass" in {
    chisel3.iotesters.Driver(() => new Memory) { c => new MemoryTest(c)} should be (true)
  }
}