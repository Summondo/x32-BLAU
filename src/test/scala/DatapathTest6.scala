import chisel3.iotesters.PeekPokeTester
import org.scalatest._

class DatapathTestTest6(dut: Datapath) extends PeekPokeTester(dut) {
    poke(dut.io.test,6)

}

class DatapathTest6 extends FlatSpec with Matchers {
  "Datapath " should "pass" in {
    chisel3.iotesters.Driver(() => new Datapath) { c => new DatapathTestTest6(c)} should be (true)
  }
}