################# Shihao Wen #################
################# SHIWEN #################
################# 113085521 #################

################# DO NOT CHANGE THE DATA SECTION #################

.data
arg1_addr: .word 0
arg2_addr: .word 0
num_args: .word 0
invalid_arg_msg: .asciiz "One of the arguments is invalid\n"
args_err_msg: .asciiz "Program requires exactly two arguments\n"
invalid_hand_msg: .asciiz "Loot Hand Invalid\n"
newline: .asciiz "\n"
zero: .asciiz "Zero\n"
nan: .asciiz "NaN\n"
inf_pos: .asciiz "+Inf\n"
inf_neg: .asciiz "-Inf\n"
mantissa: .asciiz ""

.text
.globl hw_main
hw_main:
    sw $a0, num_args
    sw $a1, arg1_addr
    addi $t0, $a1, 2
    sw $t0, arg2_addr
    j start_coding_here

start_coding_here:
## Part 1: Validate Arguments ##
	li $v0, 0
	li $a0, 0
	li $a1, 0
	li $t0, 0
	li $t1, 0
	li $t2, 0
	li $t3, 0
	li $t4, 0
	li $t5, 0
	li $t6, 0
	li $t7, 0
	li $t8, 0
	li $t9, 0
	li $s0, 0
	li $s1, 0
	li $s2, 0
	li $s3, 0
	li $s4, 0
	li $s5, 0
	li $s6, 0
	li $s7, 0
	## 2 numbers ##
	lw $t0, num_args
	li $t1, 2
	bne $t0, $t1, done_err
	
	## second argument is 0 ##
	lw $t0, arg1_addr
	lbu $t1, 1($t0)
	bne $t1, $zero, done_invalid
	
	## frist argument is D,O,S,T,I or F ##
	lbu $t1, 0($t0)
	beq $t1, $zero, done_invalid
	li $t2, 'D'
	beq $t2, $t1, Part_2
	li $t2, 'O'
	beq $t2, $t1, Part_3
	li $t2, 'S'
	beq $t2, $t1, Part_3
	li $t2, 'T'
	beq $t2, $t1, Part_3
	li $t2, 'I'
	beq $t2, $t1, Part_3
	li $t2, 'F'
	beq $t2, $t1, Part_4
	li $t2, 'L'
	beq $t2, $t1, Part_5
	li $v0, 4
	la $a0, invalid_arg_msg
	syscall 
	li $v0, 10
	syscall
	
done_invalid:
	li $v0, 4
	la $a0, invalid_arg_msg
	syscall
	li $v0, 10
	syscall
	
done_err:
	li $v0, 4
	la $a0, args_err_msg
	syscall
	li $v0, 10
	syscall
	
## Part 2: String to Decimal ##
Part_2:
	## second argument is -, 0-9 ##
	lw $s0, arg2_addr
	li $t0, '-'
	## 0 ##
	li $t1, '0' 
	## 9 ##
	li $t3, '9'
	li $t4, 10
	li $t5, 1
	lbu $s1, 0($s0)
	bne $s1, $t0, Positive_loop
	addi $s0, $s0, 1
	j Negative_loop
	
	## Negetive string to Decimal ##
Negative_loop:
	lbu $s1, 0($s0)
	sle $t0, $s1, $t1
	beq $s1, $zero, Negative_done
	bne $t0, $zero, done_invalid
	sle $t0, $s1, $t3
	bne $t0, $t5, done_invalid
	addi $s0, $s0, 1
	
	sub $s1, $s1, $t1
	mul $s2, $s2, $t4
	addu $s2, $s2, $s1
	j Negative_loop
Negative_done:	
	li $t6, -1
	mul $s2, $s2, $t6
	li $v0, 1
	addu $a0, $s2, $zero
	syscall
	
	li $v0, 10
	syscall
	
	## Positive string to Decimal ##
Positive_loop:
	lbu $s1, 0($s0)
	sle $t0, $s1, $t1
	beq $s1, $zero, Positive_done
	bne $t0, $zero, done_invalid
	sle $t0, $s1, $t3
	bne $t0, $t5, done_invalid
	addi $s0, $s0, 1
	
	sub $s1, $s1, $t1
	mul $s2, $s2, $t4
	addu $s2, $s2, $s1
	j Positive_loop
Positive_done:
	li $v0, 1
	addu $a0, $s2, $zero
	syscall
	
	li $v0, 10
	syscall

## Part 3: Decode I-Type Instructions ##
Part_3:
	## begin is 0x##
	li $t0, 'x'
	## 0 ##
	li $t1, '/'
	## 9 ##
	li $t2, ':'
	## A ##
	li $t3, '@'
	## F ##
	li $t4, 'G'
	li $t5, 1
	lw $s0, arg2_addr
	j Part_3_0x
	
Part_3_0x:
	## x ##
	lbu $s1, 0($s0)
	beq $s1, $zero, done_invalid
	addi $t6, $t1, 1
	bne $s1, $t6, done_invalid
	lbu $s1, 1($s0)
	bne $s1, $t0, done_invalid
	addi $s0, $s0, 2
	lbu $s1, 0($s0)
	j Part_3_length_Character

Part_3_length_Character:
	##String lenth in [1,8] , Character is [0,9] and [A,F] ##
	## Character ##
	
	slt $t6, $s1, $t4
	beq $t6, $zero, done_invalid
	slt $t6, $t3, $s1
	beq $t6, $zero, Part_3_length_Character_09
	## $t7 is length ##
	addi $t7, $t7, 1
	addi $s0, $s0, 1
	lbu $s1, 0($s0)
	bne $s1, $zero, Part_3_length_Character
	
	li $t6, 9
	slt $t8, $t7, $t6
	beq $t8, $zero, done_invalid
	j Part_3_Decode
	
Part_3_length_Character_09:
	slt $t6, $s1, $t2
	beq $t6, $zero, done_invalid
	slt $t6, $t1, $s1
	beq $t6, $zero, done_invalid
	addi $t7, $t7, 1
	addi $s0, $s0, 1
	lbu $s1, 0($s0)
	bne $s1, $zero, Part_3_length_Character
	
	li $t6, 9
	slt $t8, $t7, $t6
	beq $t8, $zero, done_invalid
	j Part_3_Decode
	
Part_3_Decode:	
	## $t7 is length ##
	lw $s1, arg1_addr
	lbu  $t9, 0($s1)
	li $t8, 'O'
	beq $t8, $t9, Part_3_Opcode
	li $t8, 'S'
	beq $t8, $t9, Part_3_Source
	li $t8, 'T'
	beq $t8, $t9, Part_3_Destination
	li $t8, 'I'
	beq $t8, $t9, Part_3_Immediate

## Opcode ##
Part_3_Opcode:
	li $t8, 2
	li $s2, 0
	li $t6, 10
	li $t7, 8
	sub $s0, $s0, $t7
	lbu $t9, 0($s0)
	lbu $t7, 1($s0)
	j Part_3_HexToBinary1
## Source ##
Part_3_Source:
	li $t8, 2
	li $s2, 0
	li $t6, 10
	li $t7, 7
	sub $s0, $s0, $t7
	lbu $t9, 0($s0)
	lbu $t7, 1($s0)
	j Part_3_HexToBinary1

## Destination ##
Part_3_Destination:
	li $t8, 2
	li $s2, 0
	li $t6, 10
	li $t7, 6
	sub $s0, $s0, $t7
	lbu $t9, 0($s0)
	lbu $t7, 1($s0)
	j Part_3_HexToBinary1

Part_3_HexToBinary1:
	beq $t7, $zero, Part_37_x_zero
	beq $t7, $t0, Part_37_x_zero
	beq $t9, $zero, Part_39_x_zero
	beq $t9, $t0, Part_39_x_zero
	j Part_3_HexToBinary2
	
Part_37_x_zero:
	li $t7 '0'
	j Part_3_HexToBinary1
Part_39_x_zero:
	li $t9 '0'
	j Part_3_HexToBinary1
	
Part_3_HexToBinary2:	
	slt $s1, $t3, $t7
	beq $s1, $t5, Part_37_AF
	slt $s1, $t1, $t7
	beq $s1, $t5, Part_37_09
	slt $s1, $t3, $t9
	beq $s1, $t5, Part_39_AF
	slt $s1, $t1, $t9
	beq $s1, $t5, Part_39_09
	
	li $t8, 16
	mul $t9, $t9, $t8
	add $s2, $t9, $zero
	add $s2, $s2, $t7
	
	lw $s6, arg1_addr
	lbu $s7, 0($s6)
	li $s6, 'O'
	beq $s6, $s7, Opcode_done
	li $s6, 'S'
	beq $s6, $s7, Source_done
	li $s6, 'T'
	beq $s6, $s7, Destination_done
	li $s6, 'I'
	beq $s6, $s7, Immediate_done

Part_37_09:
	sub $t7, $t7, $t1
	sub $t7, $t7, $t5
	j Part_3_HexToBinary2
	
Part_37_AF:
	sub $t7, $t7, $t3
	sub $t7, $t7, $t5
	add $t7, $t7, $t6
	j Part_3_HexToBinary2
Part_39_09:
	sub $t9, $t9, $t1
	sub $t9, $t9, $t5
	j Part_3_HexToBinary2
Part_39_AF:
	sub $t9, $t9, $t3
	sub $t9, $t9, $t5
	add $t9, $t9, $t6
	j Part_3_HexToBinary2
	
Opcode_done:
	## cancal last 2 digit ##
	li $t8, 2
	div $s2, $t8
	mflo $s2
	div $s2, $t8
	mflo $s2
	
	j Part_3_done

Source_done:
	## cancal frist 2 and last 1 digit ##
	li $t8, 2
	li $s6, 128
	li $s7, 64
	
	sle $s5, $s6, $s2
	bne $s5, $zero, Source_128
	sle $s5, $s7, $s2
	bne $s5, $zero, Source_64
	
	div $s2, $t8
	mflo $s2
	
	j Part_3_done
	
Source_128:
	sub $s2, $s2, $s6
	j Source_done
Source_64:
	sub $s2, $s2, $s7
	j Source_done
	
Destination_done:
	## cancal frist 3 digit ##
	li $t8, 2
	li $s6, 128
	li $s7, 64
	li $t9, 32
	
	sle $s5, $s6, $s2
	bne $s5, $zero, Destination_128
	sle $s5, $s7, $s2
	bne $s5, $zero, Destination_64
	sle $s5, $t9, $s2
	bne $s5, $zero, Destination_32
	
	j Part_3_done

Destination_128:
	sub $s2, $s2, $s6
	j Destination_done
Destination_64:
	sub $s2, $s2, $s7
	j Destination_done
Destination_32:
	sub $s2, $s2, $t9
	j Destination_done

## Immediate ##
Part_3_Immediate:
	li $t1, '0'
	li $t2, '9'
	li $t3, 'A'
	li $t4, 'F'
	li $t8, 2
	li $t5, 1
	li $s6, 1
	li $s5, 0
	li $s4, 5
	
	
	li $s2, 0
	li $t6, 16
	li $t7, 4
	sub $s0, $s0, $t7
	lbu $t9, 0($s0)
	lbu $s7, 0($s0)
	
	j Immediate_Positive
	
Immediate_Positive:
	addi $s5, $s5, 1
	beq $s5, $s4, Immediate_done
	mul $s2, $s2, $t6
	lbu $t9, 0($s0)
	addi $s0, $s0, 1
	j Immediate_P_HexToDecimal
	
Immediate_P_HexToDecimal:
	sle $t7, $t9, $t2
	beq $t7, $zero, Immediate_P_AF
	sub $t9, $t9, $t1
	add $s2, $s2, $t9
	j Immediate_Positive
	
Immediate_P_AF:
	sub $t9, $t9, $t3
	addi $t9, $t9, 10
	add $s2, $s2, $t9
	j Immediate_Positive

Immediate_Negative:
	div $s2, $t8
	mflo $s2
	mfhi $s3
	beq $s3, $s6, Immediate_Negative1
	beq $s2, $zero, Immediate_Negative_done
	add $s4, $s4, $t5
	j Immediate_Negative1

Immediate_Negative1:
	div $s2, $t8
	mflo $s2
	mfhi $s3
	mul $t5, $t5, $t8
	beq $s3, $s6, Immediate_Negative1
	beq $s2, $zero, Immediate_Negative_done
	add $s4, $s4, $t5
	j Immediate_Negative1

Immediate_Negative_done:
	li $t5, -1
	addi $s2, $s4, 1
	mul $s2, $s2, $t5
	j Part_3_done

Immediate_done:
	div $s2, $t6
	
	li $t9, 56
	li $s4, 0
	slt $t7, $s7, $t9
	beq $t7, $zero, Immediate_Negative
	j Part_3_done
	
Part_3_done:
	li $v0, 1
	move $a0, $s2
	syscall  
	
	li $v0, 10
	syscall

Part_4:
	li $s0, '0'
	li $s1, '9'
	li $s2, 'A'
	li $s3, 'F'
	li $s4, 8
	li $s5, 16
	li $s6, 10
	
	li $t2, 0
	li $t4, 0
	lw $t0, arg2_addr
	j Part_4_Length_Char

Part_4_Length_Char:
	lbu $t1, 0($t0)
	beq $t1, $zero, Part_4_next1
	mul $t4, $t4, $s5
	addi $t2, $t2, 1
	addi $t0, $t0, 1
	
	sleu $t3, $t1, $s1
	beq $t3, $zero, Part_4_AF
	sleu $t3, $s0, $t1
	beq $t3, $zero, done_invalid
	# $t4 is Hex To Decimal #
	subu $t1, $t1, $s0
	addu $t4, $t4, $t1
	j Part_4_Length_Char
	
Part_4_AF:
	sltu $t3, $t1, $s2
	bne $t3, $zero, done_invalid
	sleu $t3, $t1, $s3
	beq $t3, $zero, done_invalid
	subu $t1, $t1, $s2
	addu $t4, $t4, $t1
	addu $t4, $t4, $s6
	j Part_4_Length_Char


Part_4_next1:
	bne $t2, $s4, done_invalid
	beq $t4, $zero, Part_4_Zero
	li $t7, 2147483648
	beq $t4, $t7, Part_4_Zero
	li $t7, 4286578688
	beq $t4, $t7, Part_4_nInf
	li $t7, 2139095040
	beq $t4, $t7, Part_4_pInf

	li $t7, 4286578689
	sleu $t1, $t7, $t4
	bne $t1, $zero, Part_4_Nan
	
	li $t7, 2139095041
	sltu $t1, $t4, $t7
	bne $t1, $zero, Part_4_next2
	
	li $t7, 2147483647
	sltu $t1, $t7, $t4
	bne $t1, $zero, Part_4_next2
	j Part_4_Nan

Part_4_next2:
	li $s0, '0'
	li $s1, '1'
	li $s3, 2
	li $s4, 23
	li $s5, 1

	li $t2, 0
	la $t3, mantissa
	
	li $t7, 2147483648
	sleu $t1, $t7, $t4
	bne $t1, $zero, Part_4_N
	li $t7, 0
	j Part_4_mantissa

Part_4_N:
	subu $t4, $t4, $t7
	li $t7, '-'
	j Part_4_mantissa
	
Part_4_Zero:
	li $v0, 4
	la $a0, zero
	syscall
	li $v0, 10
	syscall
Part_4_nInf:
	li $v0, 4
	la $a0, inf_neg
	syscall
	li $v0, 10
	syscall
Part_4_pInf:
	li $v0, 4
	la $a0, inf_pos
	syscall
	li $v0, 10
	syscall
Part_4_Nan:
	li $v0, 4
	la $a0, nan
	syscall
	li $v0, 10
	syscall
	
Part_4_mantissa:
	beq $t2, $s4, Part_4_done1
	addiu $t2, $t2, 1
	
	divu $t4, $s3
	mflo $t4
	mfhi $t1
	beq $t1, $s5, mantissa_sp_1
	beq $t1, $zero, mantissa_sp_0
	
mantissa_sp_1:
	addiu $sp,$sp,-4
	sw $s1, 0($sp)
	j Part_4_mantissa
mantissa_sp_0:
	addiu $sp,$sp,-4
	sw $s0, 0($sp)
	j Part_4_mantissa

Part_4_done1:
	bne $t7, $zero, Part_4_done1_1
	sb $s1, 0($t3)
	li $s7, '.'
	sb $s7, 1($t3)
	addiu $t3, $t3, 2
	j Part_4_done2
	
Part_4_done1_1:
	sb $t7, 0($t3)
	sb $s1, 1($t3)
	li $s7, '.'
	sb $s7, 2($t3)
	addiu $t3, $t3, 3
	j Part_4_done2

Part_4_done2:
	lw $t0, 0($sp)
	beq $t0, $zero, Part_4_done3
	addiu $sp, $sp, 4	
	sb $t0, 0($t3)	
	addiu $t3, $t3, 1
	j Part_4_done2
	
Part_4_done3:
	li $t7, 127
	subu $t4, $t4, $t7
	
	li $v0, 1     
	add $a0, $t4, $zero    
	syscall 
	
	# how to print two args ? #
	la $a1, mantissa
	
	li $v0,10
	syscall	

Part_5:
	li $s0, 'M'
	li $s1, 'P'
	li $s2, '3'
	li $s3, '8'
	li $s4, '1'
	li $s5, '4'
	li $s6, 8

	lw $t0, arg2_addr
	li $t3, 1
	li $t4, 0
	li $t5, 0
	j Part_5_rule
	
Part_5_rule:
	addi $t3, $t3, 1
	beq $t3, $s6, Part_5_value
	lbu $t1, 0($t0)
	beq $t1, $s0, Part_5_M
	beq $t1, $s1, Part_5_P
	j done_invalid_hand
	
Part_5_M:
	addi $t4, $t4, 1
	lbu $t1, 1($t0)
	addi $t0, $t0, 2
	slt $t2, $t1, $s2
	bne $t2, $zero, done_invalid_hand
	slt $t2, $s3, $t1
	bne $t2, $zero, done_invalid_hand
	j Part_5_rule

Part_5_P:
	addi $t5, $t5, 1
	lbu $t1, 1($t0)
	addi $t0, $t0, 2
	slt $t2, $t1, $s4
	bne $t2, $zero, done_invalid_hand
	slt $t2, $s5, $t1
	bne $t2, $zero, done_invalid_hand
	j Part_5_rule
	
done_invalid_hand:
	li $v0, 4
	la $a0, invalid_hand_msg
	syscall
	li $v0, 10
	syscall
	
Part_5_value:
	mul $t4, $t4, $s6
	add $t5, $t5, $t4
	
	li $v0, 1
	add $a0, $t5, $zero
	syscall 
	
	li $v0 10
	syscall 
	
	
	
	
	
	
	
	
	
	
