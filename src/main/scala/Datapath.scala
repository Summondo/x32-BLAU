import chisel3._
import chisel3.util._
// Missing: rest in alu, test of memory, instDec and datapath,
class Datapath extends Module {
    val io = IO(new Bundle{
        val test    = Input(UInt(3.W))
        val cnt     = Output(UInt(8.W))
        val OutInst = Output(UInt(32.W))
        val InA     = Output(SInt(32.W))
        val InB     = Output(SInt(32.W))
        val InImm   = Output(SInt(32.W))
        val OutEx   = Output(SInt(32.W))
        val OutMA   = Output(SInt(32.W))
        val OutDA   = Output(UInt(5.W))
    })

    //Components--------------------------------------------------
    val Mem         = Module(new Memory()) 
    Mem.io.rddA     := 0.U(8.W)
    Mem.io.wrdA     := 0.U(8.W)
    Mem.io.wrdData  := 0.S(32.W)
    Mem.io.wrd      := false.B
    Mem.io.testSel  := io.test

    val Regx32      = Module(new Register32())

    val InstDec     = Module(new InstDec())

    val ALU         = Module(new ALU())

    //Registers-----------------------------------

    //IF-----------------------
    val InstReg = Reg(UInt(32.W))
    //ID-----------------------
    val AReg    = Reg(SInt(32.W))
    val aAReg   = Reg(UInt(5.W))
    val BReg    = Reg(SInt(32.W))
    val bAReg   = Reg(UInt(5.W))
    val ImmReg  = Reg(SInt(32.W))
    val opReg1  = Reg(UInt(6.W))
    val offReg  = Reg(SInt(10.W))
    //EX-----------------------
    val yReg    = Reg(SInt(32.W))
    //MEM-----------------------
    val resReg  = Reg(SInt(32.W))
    //WB-----------------------
    val wbReg1  = Reg(UInt(5.W))
    val wbReg2  = Reg(UInt(5.W))
    val wbReg3  = Reg(UInt(5.W))

    //Values-----------------------------------
    val AVal = Wire(SInt(32.W))
    val BVal = Wire(SInt(32.W))
    //Pipeline--------------------------------------------------

    //Instruction Fetch-----------------------------------
    var pc   = RegInit(0.U(8.W))
    pc      := pc + 1.U(8.W)

    Mem.io.rdiA := pc
    InstReg     := Mem.io.rdiData

    //Instruction Decode-----------------------------------
    InstDec.io.Instruction := InstReg

    Regx32.io.AA := InstDec.io.aA
    Regx32.io.BA := InstDec.io.bA
    wbReg1       := InstDec.io.dA
    AReg         := Regx32.io.AOut
    aAReg        := InstDec.io.aA
    BReg         := Regx32.io.BOut
    bAReg        := InstDec.io.bA
    ImmReg       := InstDec.io.immidiate
    opReg1       := InstDec.io.opcode
    offReg       := InstDec.io.offset
   
    //Execution-----------------------------------
    val opcode1 = opReg1
    val offset  = offReg

    ALU.io.a    :=  0.S(32.W)
    ALU.io.b    :=  0.S(32.W)
    ALU.io.fs   :=  0.U(4.W)

    //Data forwarding A value
    when(aAReg === wbReg2){
        AVal := yReg
    }  .elsewhen(aAReg === wbReg3){
        AVal := resReg
    }.otherwise{
        AVal := AReg
    }

    //Data forwarding B value
    when(bAReg === wbReg2){
        BVal := yReg
    }  .elsewhen(bAReg === wbReg3){
        BVal := resReg
    }.otherwise{
        BVal := BReg
    }
    
    //Giving the ALU the decoded instruction
    switch(opcode1(5,4)){
        //Basic ALU
        is(0.U){
            ALU.io.a    := AVal
            ALU.io.b    := BVal
            ALU.io.fs   := opcode1(3,0)
            yReg        := ALU.io.y
            //Branching
            when(ALU.io.flag === true.B){
                pc      := pc + offset.asUInt
                InstReg := 0.U(32.W)
                AReg    := 0.S(32.W)
                BReg    := 0.S(32.W)
                ImmReg  := 0.S(32.W)
                opReg1  := 0.U(32.W)
                offReg  := 0.S(32.W)
            }
        }
        //Basic ALU with immidiates
        is(1.U){
            ALU.io.a    := AVal
            ALU.io.b    := ImmReg
            ALU.io.fs   := opcode1(3,0)
            yReg        := ALU.io.y
            //Branching with immidiates
            when(ALU.io.flag === true.B){
                pc      := pc + offset.asUInt
                InstReg := 0.U(32.W)
                AReg    := 0.S(32.W)
                BReg    := 0.S(32.W)
                ImmReg  := 0.S(32.W)
                opReg1  := 0.U(32.W)
                offReg  := 0.S(32.W)
            }
        }
        //Store-Load
        is(2.U){
            val Address = AVal + offset
            //Load
            when (opcode1(3) === 0.U){
                //Reg
                when (opcode1(2) === 0.U){
                    Mem.io.wrd  := false.B
                    Mem.io.rddA := Address(7,0)
                    yReg        :=  Mem.io.rddData
                }
            } 
            //Store
            .otherwise{
                Mem.io.wrd  := true.B
                Mem.io.rddA := Address(7,0)
                //Reg
                when (opcode1(2) === 0.U){
                    Mem.io.wrdData := BVal
                } 
                //Immidiate
                .otherwise{
                    Mem.io.wrdData := ImmReg
                }
            yReg    := 0.S(32.W)
            }
        } 
        //Jump
        is(3.U){
            pc      := ImmReg(7,0)
            yReg    := 0.S(32.W)
            InstReg := 0.U(32.W)
            AReg    := 0.S(32.W)
            BReg    := 0.S(32.W)
            ImmReg  := 0.S(32.W)
            opReg1  := 0.U(32.W)
            offReg  := 0.S(32.W)
        }
    }

    val opReg2  = RegNext(opReg1)
    wbReg2  := wbReg1

    //Memory Access-----------------------------------
    val opcode2 = opReg2 

    when (opcode2(5,4) === 3.U){
        when (opcode2(3) === 0.U){
            resReg := Mem.io.rddData
        } .otherwise{
            resReg := 0.S(32.W)
        }
    } .otherwise{
        resReg := yReg

    }
    wbReg3  := wbReg2

    //Write Back-----------------------------------
    Regx32.io.DA := wbReg3
    
    when(Regx32.io.DA =/= 0.U){
        Regx32.io.DataIn := resReg
    } .otherwise{
        Regx32.io.DataIn := 0.S(32.W)
    }

    //Test--------------------------------------------------
    io.cnt     :=  pc
    io.OutInst :=  InstReg
    io.InA     :=  AReg
    io.InB     :=  BReg
    io.InImm   :=  ImmReg
    io.OutEx   :=  yReg
    io.OutMA   :=  resReg
    io.OutDA   :=  wbReg3
}