import chisel3.iotesters.PeekPokeTester
import org.scalatest._

class InstDecTest(dut: InstDec) extends PeekPokeTester(dut) {
    poke(dut.io.Instruction, 2075264) //"b0000000000_11111_10101_01010_000000"
    expect(dut.io.opcode,0)
    expect(dut.io.aA,10)
    expect(dut.io.dA,21)
    expect(dut.io.bA,31)
    expect(dut.io.immidiate,0)
    expect(dut.io.offset,0)
    step(1)

    poke(dut.io.Instruction, 18852492) //"b00000001_000_11111_10101_01010_001100"
    expect(dut.io.opcode,12)
    expect(dut.io.aA,10)
    expect(dut.io.dA,0)
    expect(dut.io.bA,31)
    expect(dut.io.immidiate,0)
    expect(dut.io.offset,1)
    step(1)

    poke(dut.io.Instruction, 109200) //"b0000000000000001_10101_01010_010000"
    expect(dut.io.opcode,16)
    expect(dut.io.aA,10)
    expect(dut.io.dA,21)
    expect(dut.io.bA,0)
    expect(dut.io.immidiate,1)
    expect(dut.io.offset,0)
    step(1)

    poke(dut.io.Instruction, 18852508) //"b00000001_0001111110101_01010_011100"
    expect(dut.io.opcode,28)
    expect(dut.io.aA,10)
    expect(dut.io.dA,0)
    expect(dut.io.bA,0)
    expect(dut.io.immidiate,1013)
    expect(dut.io.offset,1)
    step(1)

    poke(dut.io.Instruction, 16820896) //"b00000001_00000000_10101_01010_100000"
    expect(dut.io.opcode,32)
    expect(dut.io.aA,10)
    expect(dut.io.dA,21)
    expect(dut.io.bA,0)
    expect(dut.io.immidiate,0)
    expect(dut.io.offset,1)
    step(1)
    
    poke(dut.io.Instruction, 112) //"b00000000000000000000000001_110000"
    expect(dut.io.opcode,48)
    expect(dut.io.aA,0)
    expect(dut.io.dA,0)
    expect(dut.io.bA,0)
    expect(dut.io.immidiate,1)
    expect(dut.io.offset,0)

}

class InstDecSpec extends FlatSpec with Matchers {
  "Register32 " should "pass" in {
    chisel3.iotesters.Driver(() => new InstDec) { c => new InstDecTest(c)} should be (true)
  }
}