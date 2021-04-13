import chisel3.iotesters.PeekPokeTester
import org.scalatest._

class DatapathTestTest1(dut: Datapath) extends PeekPokeTester(dut) {
    poke(dut.io.test,1)

}

class DatapathTest1 extends FlatSpec with Matchers {
  "Datapath " should "pass" in {
    chisel3.iotesters.Driver(() => new Datapath) { c => new DatapathTestTest1(c)} should be (true)
  }
}