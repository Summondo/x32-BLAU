#include <stdio.h>
#include <string.h>

#define BYTE_TO_BINARY_PATTERN "%c%c%c%c%c%c%c%c"
#define BYTE_TO_BINARY(byte)  \
  (byte & 0x80 ? '1' : '0'), \
  (byte & 0x40 ? '1' : '0'), \
  (byte & 0x20 ? '1' : '0'), \
  (byte & 0x10 ? '1' : '0'), \
  (byte & 0x08 ? '1' : '0'), \
  (byte & 0x04 ? '1' : '0'), \
  (byte & 0x02 ? '1' : '0'), \
  (byte & 0x01 ? '1' : '0') 

int main(){
    char Commando[4], Path[100], fileName[20], fileR[120];
    int RegisterA = 0, RegisterB = 0, RegisterY = 0, Offset = 0, PC = 0, flag = 1;
    long Instruction = 0, Immidiate = 0;
    FILE *assCode, *instCode;
    
    printf("Where is the file: ");scanf(" %s", &Path);
    printf("What is the file named: ");scanf(" %s", &fileName);
	strcpy(Path,"F:\\Uni\\4.Semester\\ACA");
	strcpy(fileName,"Test0.txt");
    strcpy(fileR, Path);strcat(fileR, "\\");strcat(fileR, fileName);
	printf("%s\t%s\t%s\n", fileR,Path,fileName);
    assCode = fopen(fileR,"r");
    strcpy(fileR, Path);strcat(fileR, "\\instructions.txt");
	printf("%s\t%s\n", fileR,Path);
    instCode = fopen(fileR,"wb");

    while(flag == 1){
        fscanf(assCode," %s", &Commando);
            if(strcmp(Commando, "ADD")== 0){
                fscanf(assCode," Y%d, A%d, B%d", &RegisterY, &RegisterA, &RegisterB);
                Instruction = (RegisterB<<16) + (RegisterY<<11) + (RegisterA<<6) + 0x00;
            }

             else if(strcmp(Commando, "SUB")== 0){
                fscanf(assCode," Y%d, A%d, B%d", &RegisterY, &RegisterA, &RegisterB);
                Instruction = (RegisterB<<16) + (RegisterY<<11) + (RegisterA<<6) + 0x01;
            }

             else if(strcmp(Commando, "INC")== 0){
                fscanf(assCode," Y%d, A%d", &RegisterY, &RegisterA);
                Instruction = (RegisterY<<11) + (RegisterA<<6) + 0x02;
            }

             else if(strcmp(Commando, "DEC")== 0){
                fscanf(assCode," Y%d, A%d", &RegisterY, &RegisterA);
                Instruction = (RegisterY<<11) + (RegisterA<<6) + 0x03;
            }

             else if(strcmp(Commando, "AND")== 0){
                fscanf(assCode," Y%d, A%d, B%d", &RegisterY, &RegisterA, &RegisterB);
                Instruction = (RegisterB<<16) + (RegisterY<<11) + (RegisterA<<6) + 0x04;
            }

             else if(strcmp(Commando, "OR")== 0){
                fscanf(assCode," Y%d, A%d, B%d", &RegisterY, &RegisterA, &RegisterB);
                Instruction = (RegisterB<<16) + (RegisterY<<11) + (RegisterA<<6) + 0x05;
            }

             else if(strcmp(Commando, "XOR")== 0){
                fscanf(assCode," Y%d, A%d, B%d", &RegisterY, &RegisterA, &RegisterB);
                Instruction = (RegisterB<<16) + (RegisterY<<11) + (RegisterA<<6) + 0x06;
            }

             else if(strcmp(Commando, "NOT")== 0){
                fscanf(assCode," Y%d, A%d, B%d", &RegisterY, &RegisterA, &RegisterB);
                Instruction = (RegisterB<<16) + (RegisterY<<11) + (RegisterA<<6) + 0x07;
            }

             else if(strcmp(Commando, "MUL")== 0){
                fscanf(assCode," Y%d, A%d, B%d", &RegisterY, &RegisterA, &RegisterB);
                Instruction = (RegisterB<<16) + (RegisterY<<11) + (RegisterA<<6) + 0x08;
            }

            /* else if(strcmp(Commando, "")== 0){

            }*/
            
             else if(strcmp(Commando, "SHL")== 0){
                fscanf(assCode," Y%d, A%d, B%d", &RegisterY, &RegisterA, &RegisterB);
                Instruction = (RegisterB<<16) + (RegisterY<<11) + (RegisterA<<6) + 0x0a;
            }

             else if(strcmp(Commando, "SHR")== 0){
                fscanf(assCode," Y%d, A%d, B%d", &RegisterY, &RegisterA, &RegisterB);
                Instruction = (RegisterB<<16) + (RegisterY<<11) + (RegisterA<<6) + 0x0b;
            }

             else if(strcmp(Commando, "BRE")== 0){
                fscanf(assCode, " A%d, B%d, O%d", &RegisterA, &RegisterB, &Offset);
                Instruction = (Offset<<22) + (RegisterB<<16) + (RegisterA<<6) + 0x0c;
            }

             else if(strcmp(Commando, "BRG")== 0){
                fscanf(assCode, " A%d, B%d, O%d", &RegisterA, &RegisterB, &Offset);
                Instruction = (Offset<<22) + (RegisterB<<16) + (RegisterA<<6) + 0x0d;
            }

             else if(strcmp(Commando, "BRL")== 0){
                fscanf(assCode, " A%d, B%d, O%d", &RegisterA, &RegisterB, &Offset);
                Instruction = (Offset<<22) + (RegisterB<<16) + (RegisterA<<6) + 0x0e;
            }

             else if(strcmp(Commando, "BRN")== 0){
                fscanf(assCode, " A%d, O%d", &RegisterA, &Offset);
                Instruction = (Offset<<22) + (RegisterA<<6) + 0x0f;
            }

             else if(strcmp(Commando, "ADDI")== 0){
                fscanf(assCode," Y%d, A%d, I%ld [^\n]", &RegisterY, &RegisterA, &Immidiate);
                Instruction = (Immidiate<<16) + (RegisterY<<11) + (RegisterA<<6) + 0x10;

            }

             else if(strcmp(Commando, "SUBI")== 0){
                fscanf(assCode," Y%d, A%d, I%ld", &RegisterY, &RegisterA, &Immidiate);
                Instruction = (Immidiate<<16) + (RegisterY<<11) + (RegisterA<<6) + 0x11;
            }

             else if(strcmp(Commando, "INCI")== 0){
                fscanf(assCode," Y%d, I%ld", &RegisterY, &Immidiate);//??????????????
                Instruction = (RegisterY<<11) + (RegisterA<<6) + 0x12;
            }

             else if(strcmp(Commando, "DECI")== 0){
                fscanf(assCode," Y%d, I%ld", &RegisterY, &Immidiate);//??????????????
                Instruction = (RegisterY<<11) + (RegisterA<<6) + 0x13;
            }

             else if(strcmp(Commando, "ANDI")== 0){
                fscanf(assCode," Y%d, A%d, I%ld", &RegisterY, &RegisterA, &Immidiate);
                Instruction = (Immidiate<<16) + (RegisterY<<11) + (RegisterA<<6) + 0x14;
            }

             else if(strcmp(Commando, "ORI")== 0){
                fscanf(assCode," Y%d, A%d, I%ld", &RegisterY, &RegisterA, &Immidiate);
                Instruction = (Immidiate<<16) + (RegisterY<<11) + (RegisterA<<6) + 0x15;
            }

             else if(strcmp(Commando, "XORI")== 0){
                fscanf(assCode," Y%d, A%d, I%ld", &RegisterY, &RegisterA, &Immidiate);
                Instruction = (Immidiate<<16) + (RegisterY<<11) + (RegisterA<<6) + 0x16;
            }

             else if(strcmp(Commando, "NOTI")== 0){
                fscanf(assCode," Y%d, I%ld", &RegisterY, &Immidiate);
                Instruction = ((Immidiate & 0x001fffe0)<<11) + (RegisterY<<11) + ((Immidiate & 0x1f)<<6) + 0x17;
            }

             else if(strcmp(Commando, "MULI")== 0){
                fscanf(assCode," Y%d, A%d, I%ld", &RegisterY, &RegisterA, &Immidiate);
                Instruction = (Immidiate<<16) + (RegisterY<<11) + (RegisterA<<6) + 0x18;
            }

            /* else if(strcmp(Commando, "")== 0){

            }*/
            
             else if(strcmp(Commando, "SHLI")== 0){
                fscanf(assCode," Y%d, A%d, I%ld", &RegisterY, &RegisterA, &Immidiate);
                Instruction = (Immidiate<<16) + (RegisterY<<11) + (RegisterA<<6) + 0x1a;
            }

             else if(strcmp(Commando, "SHRI")== 0){
                fscanf(assCode," Y%d, A%d, I%ld", &RegisterY, &RegisterA, &Immidiate);
                Instruction = (Immidiate<<16) + (RegisterY<<11) + (RegisterA<<6) + 0x1b;
            }

             else if(strcmp(Commando, "BREI")== 0){
                fscanf(assCode, " A%d, I%ld, O%d", &RegisterA, &Immidiate, &Offset);
                Instruction = (Offset<<22) + ((Immidiate & 0x000007ff)<<11) + (RegisterA<<6) + 0x1c;
            }

             else if(strcmp(Commando, "BRGI")== 0){
                fscanf(assCode, " A%d, I%ld, O%d", &RegisterA, &Immidiate, &Offset);
                Instruction = (Offset<<22) + ((Immidiate & 0x000007ff)<<11) + (RegisterA<<6) + 0x1d;
            }

             else if(strcmp(Commando, "BRLI")== 0){
                fscanf(assCode, " A%d, I%ld, O%d", &RegisterA, &Immidiate, &Offset);
                Instruction = (Offset<<22) + ((Immidiate & 0x000007ff)<<11) + (RegisterA<<6) + 0x1e;
            }

             else if(strcmp(Commando, "BRNI")== 0){
                fscanf(assCode, " I%ld, O%d", &Immidiate, &Offset);
                Instruction = (Offset<<22) + ((Immidiate<<6) & 0x003FFFC0) + 0x1f;
            }

             else if(strcmp(Commando, "LOAD")== 0){
                fscanf(assCode," Y%d, O%d(A%d)", &RegisterY, &Offset, &RegisterA);
                Instruction = (Offset<<22) + (RegisterY<<11) + (RegisterA<<6) + 0x20;
            }

             else if(strcmp(Commando, "STR")== 0){
                fscanf(assCode," B%d , O%d(A%d)", &RegisterB, &Offset, &RegisterA);
                Instruction = (Offset<<22) + (RegisterB<<11) + (RegisterA<<6) + 0x28;
            }

             else if(strcmp(Commando, "STRI")== 0){
                 fscanf(assCode," I%ld , O%d(A%d)", &Immidiate, &Offset, &RegisterA);
                 Instruction = (Offset<<22) + ((Immidiate & 0x000007ff)<<11) + (RegisterA<<6) + 0x2a;
            }

             else if(strcmp(Commando, "JMP")== 0){
                fscanf(assCode," A%d, O%d", &RegisterA, &Offset);
                Instruction = (Offset<<22) + (RegisterA<<6) + 0x30;
            }

             else if(strcmp(Commando, "JMPI")== 0){
                fscanf(assCode," I%ld", &Immidiate);
                Instruction = (Immidiate<<6) + 0x38;
            }

            else{
                flag = 0;
            }

		if(flag == 1){
			fprintf(instCode,"instMem(%d)   :=  \"b"BYTE_TO_BINARY_PATTERN BYTE_TO_BINARY_PATTERN, PC++, BYTE_TO_BINARY(Instruction>>24), BYTE_TO_BINARY(Instruction>>16));
			fprintf(instCode, BYTE_TO_BINARY_PATTERN BYTE_TO_BINARY_PATTERN"\".U\n", BYTE_TO_BINARY(Instruction>>8), BYTE_TO_BINARY(Instruction));
			strcpy(Commando,"0000");
		}
    }
    fclose(assCode);
    fclose(instCode);
}
