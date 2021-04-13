import chisel3._
import chisel3.util._

class Register32 extends Module {
    val io = IO(new Bundle{
        val DataIn = Input(SInt(32.W))
        val DA = Input(UInt(5.W))
        val AA = Input(UInt(5.W))
        val BA = Input(UInt(5.W))
        val AOut = Output(SInt(32.W))
        val BOut = Output(SInt(32.W))
    })
    val Registers = RegInit(VecInit(Seq.fill(32)(0.S(32.W))))

    Registers(io.DA) := io.DataIn
    io.AOut := Registers(io.AA)
    io.BOut := Registers(io.BA)
}