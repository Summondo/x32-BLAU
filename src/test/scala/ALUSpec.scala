import chisel3.iotesters.PeekPokeTester
import org.scalatest._

class ALUTest(dut: ALU) extends PeekPokeTester(dut) {

  poke(dut.io.a, 65)
  poke(dut.io.b, 19)
  poke(dut.io.fs, 0)
  expect(dut.io.y, 65 + 19)

  poke(dut.io.fs, 1)
  expect(dut.io.y, 65 - 19)

  poke(dut.io.fs, 2)
  expect(dut.io.y, 65 + 1)

  poke(dut.io.fs, 3)
  expect(dut.io.y, 65 - 1)

  poke(dut.io.fs, 4)
  expect(dut.io.y, 65 & 19)

  poke(dut.io.fs, 5)
  expect(dut.io.y, 65  | 19 )

  poke(dut.io.fs, 6)
  expect(dut.io.y, 65  ^ 19 )

  poke(dut.io.fs, 7)
  expect(dut.io.y, ~65 )

  poke(dut.io.fs, 8)
  expect(dut.io.y, 65  * 19 )

  /*//is rest and not found out yet
  poke(dut.io.fs, 9)
  expect(dut.io.y, res)
  */

  poke(dut.io.fs, 10)
  expect(dut.io.y, 65  << 19 )

  poke(dut.io.fs, 11)
  expect(dut.io.y, 65  >> 19 )

  
  poke(dut.io.fs, 12)
  expect(dut.io.flag, false)

  poke(dut.io.fs, 13)
  expect(dut.io.flag, true)

  poke(dut.io.fs, 14)
  expect(dut.io.flag, false)
  
  poke(dut.io.fs, 15)
  expect(dut.io.flag, false)

  for (i <- 0 to 15){
      poke(dut.io.fs,i)
      val reg = peek(dut.io.y)
      println(i + ": " + reg + "\t")
    }
}

class ALUSpec extends FlatSpec with Matchers {
  "ALU " should "pass" in {
    chisel3.iotesters.Driver(() => new ALU) { c => new ALUTest(c)} should be (true)
  }
}