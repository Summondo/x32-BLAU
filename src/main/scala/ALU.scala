import chisel3._
import chisel3.util._


class ALU extends Module {
    val io = IO(new Bundle{
        val a       =   Input(SInt(32.W))
        val b       =   Input(SInt(32.W))
        val fs      =   Input(UInt(4.W))
        val y       =   Output(SInt(32.W))
        val flag    =   Output(Bool())
    })

    io.y := 0.S
    io.flag := false.B
    
    switch(io.fs){
        is(0.U) {io.y := io.a + io.b}       //Add a and b
        is(1.U) {io.y := io.a - io.b}       //Subtract a and b
        is(2.U) {io.y := io.a + 1.S}        //Increment a
        is(3.U) {io.y := io.a - 1.S}        //Decrement a
        is(4.U) {io.y := io.a & io.b}       //Bitwise AND a and b
        is(5.U) {io.y := io.a | io.b}       //Bitwise OR a and b 
        is(6.U) {io.y := io.a ^ io.b}       //Bitwise XOR a and b
        is(7.U) {io.y := ~io.a}             //Bitwise NOT a
        is(8.U) {io.y := io.a * io.b}       //Multiply a and b
        //is(9.U) {io.y := }
        is(10.U) {io.y := io.a << io.b(6,0)}//Shift a to the left
        is(11.U) {io.y := io.a >> io.b(6,0)}//Shift a to the right
        is(12.U) {io.flag := io.a === io.b} //Checks if a and b are equal
        is(13.U) {io.flag := io.a >= io.b}  //Checks if a are greater or eqaul to b
        is(14.U) {io.flag := io.a < io.b}   //Checks if a are less than b
        is(15.U) {io.flag := io.a(31)}      //Checks if a are negative
        
    }
}