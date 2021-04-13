import chisel3._
import chisel3.util._

class Memory extends Module {
    val io = IO(new Bundle{
        val rddA    = Input(UInt(8.W))
        val wrdA    = Input(UInt(8.W))
        val wrdData = Input(SInt(32.W))
        val rddData = Output(SInt(32.W))
        val wrd     = Input(Bool())

        val testSel = Input(UInt(3.W))
        val rdiA    = Input(UInt(8.W))
        val rdiData = Output(UInt(32.W))
    })
    val dataMem = SyncReadMem((scala.math.pow(2,8)).toInt, SInt(32.W))
    val instMem = Wire(Vec(100, UInt(32.W)));
    for( i <- 0 to 99){
        instMem(i) := 0.U(32.W)
    }


    io.rddData := dataMem.read(io.rddA)
    io.rdiData := instMem(io.rdiA)

    when(io.wrd === true.B){
        dataMem.write(io.wrdA, io.wrdData)
    }

    switch(io.testSel){
        is(0.U){instMem(0)  :=  "b000000000100000_01000_00111_010000".U} //Basic Instruction
        is(1.U){                                                         //All ALU functions
                instMem(0)  :=  "b000000000100000_01000_00111_010000".U
                instMem(1)  :=  "b000000000001000_00111_00000_010001".U
                instMem(2)  :=  "b000000000101010_10111_00000_010000".U
                instMem(3)  :=  "b000000000001111_10110_00000_010000".U
                instMem(4)  :=  "b000000000100000_00110_00111_000010".U
                instMem(5)  :=  "b0000000001_10111_01010_01000_000100".U
                instMem(6)  :=  "b0000000001_10111_11010_01000_000101".U
                instMem(7)  :=  "b0000000001_10111_01110_01000_000110".U
                instMem(8)  :=  "b0000000001_00111_11111_01000_000111".U
                instMem(9)  :=  "b0000000001_10110_00001_10111_001000".U
                instMem(10) :=  "b000000000000111_11110_01000_011001".U
        }                                                      
        is(2.U){                                                          //Dataforwarding
                instMem(0)  :=  "b000000000100000_01000_00111_010000".U
                instMem(1)  :=  "b000000000001000_00111_00000_010001".U
                instMem(2)  :=  "b000000000100000_00110_00111_000010".U
                instMem(3)  :=  "b0000000001_00110_01010_01000_000100".U
                instMem(4)  :=  "b0000000001_00110_01010_01000_000100".U
        } 
        is(3.U){                                                          //Jump
                instMem(0)  :=  "b000000000000000000000000111_110000".U
                instMem(7)  :=  "b000000000000111_01000_00000_010000".U
        }
        is(4.U){                                                          //Jump reset
                instMem(0)  :=  "b000000000000000000000000100_110000".U
                instMem(1)  :=  "b000000000000001_01000_00000_010000".U
                instMem(2)  :=  "b000000000000010_01000_00000_010000".U
                instMem(3)  :=  "b000000000000011_01000_00000_010000".U
                instMem(4)  :=  "b000000000000100_01000_00000_010000".U
                instMem(5)  :=  "b000000000000101_01000_00000_010000".U
                instMem(6)  :=  "b000000000000110_01000_00000_010000".U
        } 
        is(5.U){} //Save
        is(6.U){} //Load
        is(7.U){} //Full program
    }
}