import chisel3._
import chisel3.util._

class InstDec extends Module{
    val io = IO(new Bundle{
        val Instruction     =   Input(UInt(32.W))
        val opcode          =   Output(UInt(6.W))
        val immidiate       =   Output(SInt(32.W))
        val aA              =   Output(UInt(5.W))
        val bA              =   Output(UInt(5.W))
        val dA              =   Output(UInt(5.W))
        val offset          =   Output(SInt(8.W))
    })

    io.opcode   := io.Instruction(5,0)
    io.immidiate:= 0.S(32.W)
    io.aA       := 0.U(5.W)
    io.bA       := 0.U(5.W)
    io.dA       := 0.U(5.W)
    io.offset   := 0.S(8.W)

    switch(io.opcode(5,4)){
        //Basic ALU
        is(0.U){
            io.aA           := io.Instruction(10,6)
            io.dA           := io.Instruction(15,11)
            io.bA           := io.Instruction(20,16)
             when(io.opcode(3,2) === 3.U){
                io.offset   := io.Instruction(31,24).asSInt()
                io.dA       := 0.U(5.W)
            }
        }

        //Immidiate ALU
        is(1.U){
            io.aA           := io.Instruction(10,6)
            
            when(io.opcode(3,2) === 3.U){
                io.offset       := io.Instruction(31,24).asSInt()
                io.immidiate    := io.Instruction(23,11).asSInt()
                io.dA           := 0.U(5.W)
            } .otherwise{
                io.immidiate    := io.Instruction(31,16).asSInt()
                io.dA           := io.Instruction(15,11)
            }
        }

        //Load-Store                                         DO IT
        is(2.U){
            when(io.opcode(3) === 0.U){
                //Load
                when(io.opcode(2) === 0.U){
                    //Reg
                    io.aA           := io.Instruction(10,6)
                    io.dA           := io.Instruction(15,11)
                    io.offset       := io.Instruction(31,24).asSInt()
                }
            } .otherwise{
                //Store
                when(io.opcode(2) === 0.U){
                    //Reg
                    io.aA           := io.Instruction(10,6)
                    io.bA           := io.Instruction(20,16)
                    io.offset       := io.Instruction(31,24).asSInt()
                } .otherwise{
                    //Immidiate
                    io.aA           := io.Instruction(10,6)
                    io.immidiate    := io.Instruction(23,11).asSInt()
                    io.offset       := io.Instruction(31,24).asSInt()
                }
            }
            
        }

        //Jump
        is(3.U){
            io.immidiate := io.Instruction(31,6).asSInt
        }

    }
}   
