import chisel3.iotesters.PeekPokeTester
import org.scalatest._

class DatapathTestTest7(dut: Datapath) extends PeekPokeTester(dut) {
    poke(dut.io.test,7)

}

class DatapathTest7 extends FlatSpec with Matchers {
  "Datapath " should "pass" in {
    chisel3.iotesters.Driver(() => new Datapath) { c => new DatapathTestTest7(c)} should be (true)
  }
}