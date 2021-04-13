import chisel3.iotesters.PeekPokeTester
import org.scalatest._

class DatapathTestTest5(dut: Datapath) extends PeekPokeTester(dut) {
    poke(dut.io.test,5)

}

class DatapathTest5 extends FlatSpec with Matchers {
  "Datapath " should "pass" in {
    chisel3.iotesters.Driver(() => new Datapath) { c => new DatapathTestTest5(c)} should be (true)
  }
}