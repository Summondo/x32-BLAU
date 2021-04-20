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
                //Basic Instruction
        is(0.U){instMem(0)  :=  "b000000000100000_01000_00111_010000".U}    //ADDI Y8, A7, I32

        is(1.U){//All ALU functions
                instMem(0)  :=  "b000000000100000_01000_00111_010000".U     //ADDI Y8, A7, I32
                instMem(1)  :=  "b000000000001000_00111_00000_010001".U     //SUBI Y7, A0, I8
                instMem(2)  :=  "b000000000101010_10111_00000_010000".U     //ADDI Y23, A0, I42
                instMem(3)  :=  "b000000000001111_10110_00000_010000".U     //ADDI Y22, A0, I15
                instMem(4)  :=  "b000000000100000_00110_00111_000010".U     //INC Y6, A7
                instMem(5)  :=  "b0000000001_10111_01010_01000_000100".U    //AND Y10, A8, B23
                instMem(6)  :=  "b0000000001_10111_11010_01000_000101".U    //OR Y26, A8, B23
                instMem(7)  :=  "b0000000001_10111_01110_01000_000110".U    //XOR Y14, A8, B23
                instMem(8)  :=  "b000000000100111_10011_01000_000111".U     //NOT Y19, A8
                instMem(9)  :=  "b0000000001_10110_00001_10111_001000".U    //MUL Y1, A8, B22
                instMem(10) :=  "b0000000000_00000_00000_00000_001001".U    //????????????????
                instMem(11) :=  "b000000000000111_11110_01000_011010".U     //SHLI Y30, A8, I7
                instMem(12) :=  "b000000000000111_11111_01000_011011".U     //SHRI Y31, A8, I7
                
        }                                                      
        is(2.U){//Dataforwarding
                instMem(0)  :=  "b000000000100000_01000_00111_010000".U     //ADDI Y8, A7, I32
                instMem(1)  :=  "b000000000001000_00111_00000_010001".U     //SUBI Y7, A0, I8
                instMem(2)  :=  "b000000000100000_00110_00111_000010".U     //INC Y6, A7
                instMem(3)  :=  "b0000000001_00110_01010_01000_000100".U    //AND Y10, A8, B7
                instMem(4)  :=  "b0000000001_00110_01010_01000_000100".U    //AND Y10, A8. B7
        } 
        is(3.U){//Jump
                instMem(0)  :=  "b000000000000000000000000111_111000".U     //JMPI I7
                instMem(7)  :=  "b000000000000111_01000_00000_010000".U     //ADDI Y8, A0, I7
        }
        is(4.U){//Jump reset
                instMem(0)  :=  "b000000000000000000000000100_111000".U     //JMPI I4
                instMem(1)  :=  "b000000000000001_01000_00000_010000".U     //ADDI Y8, A0, I1
                instMem(2)  :=  "b000000000000010_01000_00000_010000".U     //ADDI Y8, A0, I2
                instMem(3)  :=  "b000000000000011_01000_00000_010000".U     //ADDI Y8, A0, I3
                instMem(4)  :=  "b000000000000100_01000_00000_010000".U     //ADDI Y8, A0, I4
                instMem(5)  :=  "b000000000000101_01000_00000_010000".U     //ADDI Y8, A0, I5
                instMem(6)  :=  "b000000000000110_01000_00000_010000".U     //ADDI Y8, A0, I6
        } 
        is(5.U){//Load

        } 
        is(6.U){//Save
            
        } 
        is(7.U){//Full program

        } 
    }
}