########### Shihao Wen ############
########### SHIWEN ################
########### 113085521 ################

###################################
##### DO NOT ADD A DATA SECTION ###
###################################

.text
## Part 1: Substring ##
.globl substr
substr:
move $t0, $a0
move $t1, $a1
move $t2, $a2
li $t3, 0

addi $sp, $sp, -4
sw $ra, 0($sp)

blt $t1, $zero, substr_err
blt $t2, $zero, substr_err

li $t8, 0
jal substr_length
li $t9, -1
add $t7, $zero, $t8
mul $t7, $t7, $t9
add $t0, $t0, $t7

addi $t8, $t8, -1
blt $t8, $t2, substr_err

add $t5, $t0, $t1
jal substr_1

lw $ra, 0($sp)
addi $sp, $sp, 4
 jr $ra

substr_length:
lb $t4, 0($t0)
addi $t8, $t8, 1
addi $t0, $t0, 1
bne $t4, $zero, substr_length
jr $ra

substr_1:
lb $t4, 0($t5)
beq $t4, $zero, substr_err
sb $t4, 0($t0)

addi $t3, $t3, -1
addi $t0, $t0, 1
addi $t5, $t5, 1

addi $t1, $t1, 1
bne  $t1, $t2, substr_1

sb $zero, 0($t0)
add $t0, $t0, $t3

li $v0, 0
jr $ra

substr_err:
lw $ra, 0($sp)
addi $sp, $sp, 4
li $v0, -1
jr $ra

## Part 2: Encrypt A Block ##
.globl encrypt_block
encrypt_block:
li $t4, 0

lb $t0, 3($a0)
lb $t1, 3($a1)
xor $t3, $t0, $t1
addu $t4, $t4, $t3

lb $t0, 2($a0)
lb $t1, 2($a1)
xor $t3, $t0, $t1
li $t5, 256
mul $t3, $t3, $t5
addu $t4, $t4, $t3

lb $t0, 1($a0)
lb $t1, 1($a1)
xor $t3, $t0, $t1
li $t5, 65536
mul $t3, $t3, $t5
addu $t4, $t4, $t3

lb $t0, 0($a0)
lb $t1, 0($a1)
xor $t3, $t0, $t1
li $t5, 16777216
mul $t3, $t3, $t5
addu $t4, $t4, $t3

addu $v0, $zero, $t4
 jr $ra

## Part 3: Add A Block ##
.globl add_block
add_block:
move $t0, $a0
move $t1, $a1
move $t2, $a2

li $t9, 4
mul $t1, $t1, $t9
add $t0, $t0, $t1

addi $sp, $sp -4
sw $ra, 0($sp)

jal byte_int
sb $t3, 0($t0)

jal byte_int
sb $t3, 1($t0)

jal byte_int
sb $t3, 2($t0)

jal byte_int
sb $t3, 3($t0)

lw $ra, 0($sp)
addi $sp, $sp 4
 jr $ra
 
byte_int:
li $t3, 0
li $t4, 2
li $t5, 1
li $t6, 7

div $t2, $t4
mflo $t2
mfhi $t7
add $t3, $t3, $t7
j byte_int1


byte_int1:
div $t2, $t4
mflo $t2
mfhi $t7

mul $t5, $t5, $t4
mul $t7, $t7, $t5
addi $t6, $t6, -1
add $t3, $t3, $t7
bne $t6, $zero, byte_int1
jr $ra

## Part 4: Generate Key ##
.globl gen_key
gen_key:
move $t0, $a0
move $t1, $a1

li $t2, 4
mul $t1, $t1, $t2
add $t0, $t0, $t1

li $a1, 128
li $v0, 42
syscall 
sb $a0, 0($t0)

li $a1, 128
li $v0, 42
syscall 
sb $a0, 1($t0)

li $a1, 128
li $v0, 42
syscall 
sb $a0, 2($t0)

li $a1, 128
li $v0, 42
syscall 
sb $a0, 3($t0)

 jr $ra

## Part 5: Encryption ##
.globl encrypt
encrypt:
move $t0, $a0
move $t1, $a1
move $t2, $a2
move $t3, $a3

li $t4, 4
div $t3, $t4
mfhi $t5
mflo $t6
add $t0 $t0, $t3
 bne $t5, $zero, encrypt_length
 
 encrypt1:
li $t5, -1
mul $t5, $t5, $t3
add $t0 $t0, $t5

li $t5, 0

addi $sp, $sp, -4
sw $ra, 0($sp)
addi $sp, $sp, -20
sw $t0, 0($sp)
sw $t1, 4($sp)
sw $t2, 8($sp)
sw $t5, 12($sp)
sw $t6, 16($sp)
jal Part4_2_3

 jr $ra
 
encrypt_length:
li $a1, 128
li $v0, 42
syscall 
sb $a0, 0($t0)

addi $t3, $t3, 1
addi $t5, $t5, 1
addi $t0, $t0, 1
bne $t5, $t4, encrypt_length
addi $t6, $t6, 1
j encrypt1

Part4_2_3:
lw $t1, 4($sp)
move $a0, $t1
li $a1, 0
jal gen_key

lw $t0, 0($sp)
lw $t1, 4($sp)
move $a0, $t0
move $a1, $t1
jal encrypt_block
lw $t0, 0($sp)
lw $t1, 4($sp)
addi $t0, $t0, 4
addi $t1, $t1, 4
sw $t0, 0($sp)
sw $t1, 4($sp)


lw $t2, 8($sp)
move $a0, $t2
li $a1, 0
move $a2, $v0
jal add_block
lw $t2, 8($sp)
addi $t2, $t2, 4
sw $t2, 8($sp)


lw $t5, 12($sp)
lw $t6, 16($sp)
addi $t5, $t5, 1
sw $t5, 12($sp)
bne $t6, $t5, Part4_2_3

addi $sp, $sp, 20
lw $ra, 0($sp)
addi $sp, $sp, 4
jr $ra


## Part 6: Decrypt A Block ##
.globl decrypt_block
decrypt_block:
move $t1, $a1

lb $t2, 0($t1)
lb $t3, 3($t1)
sb $t2, 3($t1)
sb $t3, 0($t1)
lb $t2, 1($t1)
lb $t3, 2($t1)
sb $t2, 2($t1)
sb $t3, 1($t1)

addi $sp, $sp, -4
sw $ra, 0($sp)

jal encrypt_block

lw $ra, 0($sp)
addi $sp, $sp, 4
 jr $ra
 
## Part 7: Decrypt ##
.globl decrypt
decrypt:
move $t0, $a0
move $t1, $a1
move $t2, $a2
move $t3, $a3
li $t5, 0

addi $sp, $sp, -4
sw $ra, 0($sp)
addi $sp, $sp, -20
sw $t0, 0($sp)
sw $t1, 4($sp)
sw $t2, 8($sp)
sw $t3, 12($sp)
sw $t5, 16($sp)
jal Part6_3

 jr $ra
 
Part6_3:
lw $t0, 0($sp)
lw $t1, 4($sp)
move $a0, $t0
move $a1, $t1
jal decrypt_block
lw $t1, 4($sp)
lb $t6, 0($t1)
lb $t7, 3($t1)
sb $t6, 3($t1)
sb $t7, 0($t1)
lb $t6, 1($t1)
lb $t7, 2($t1)
sb $t6, 2($t1)
sb $t7, 1($t1)

lw $t3, 12($sp)
move $a0, $t3
li $a1, 0
move $a2, $v0
jal add_block

lw $t0, 0($sp)
lw $t1, 4($sp)
lw $t2, 8($sp)
lw $t3, 12($sp)
lw $t5, 16($sp)
addi $t0, $t0, 4
addi $t1, $t1, 4
addi $t3, $t3, 4
addi $t5, $t5, 4
sw $t0, 0($sp)
sw $t1, 4($sp)
sw $t3, 12($sp)
sw $t5, 16($sp)
bne $t2, $t5, Part6_3

addi $sp, $sp, 20
lw $ra, 0($sp)
addi $sp, $sp, 4
jr $ra
