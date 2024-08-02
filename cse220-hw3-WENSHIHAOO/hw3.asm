######### Shihao Wen ##########
######### 113085521 ##########
######### SHIWEN ##########

######## DO NOT ADD A DATA SECTION ##########
######## DO NOT ADD A DATA SECTION ##########
######## DO NOT ADD A DATA SECTION ##########

.text
.globl initialize
initialize:
move $t4, $a0
move $a3, $a1
move $t0, $a1
addi $t0, $t0, 400
li $v0, 13
li $a1, 0
li $a2, 0
syscall
move $v1, $v0

li $v0, 14
move $a0, $v1
move $a1, $t0
li $a2, 3
syscall

li $t9, 48
li $t8, 57
li $t7, 10
li $t6, 0
li $t5, 0

addi $sp, $sp, -4
sw $ra, 0($sp)

lb $t1, 0($t0)
lb $t2, 2($t0)
ble $t1, $t9, error
blt $t8, $t1, error
ble $t2, $t9, error
blt $t8, $t2, error

addi $t1, $t1, -48
addi $t2, $t2, -48

li $v0, 14
move $a0, $v1
move $a1, $t0
mul $a2, $t1, $t2
add $a2, $a2, $t1
addi $a2, $a2, 1
syscall

li $v0, 16
move $a0, $v1
syscall

addi $t2, $t2, 1
jal check
addi $t2, $t2, -1

li $v0, 13
move $a0, $t4
li $a1, 0
li $a2, 0
syscall
move $v1, $v0

li $v0, 14
move $a0, $v1
move $a1, $t0
li $a2, 4
syscall
lb $t6, 0($t0)
addi $t6, $t6, -48
lb $t7, 2($t0)
addi $t7, $t7, -48
sw $t6, 0($a3)
sw $t7, 4($a3)
addi $a3, $a3, 8

mul $t9, $t1, $t2
add $t9, $t9, $t1
addi $t9, $t9, -1
li $a2, 1
li $t8, 0
addi $t2, $t2, 1
jal load_w

lw $ra, 0($sp)
addi $sp, $sp, 4
li $v0, 1
 jr $ra

load_w:
li $v0, 14
move $a0, $v1
move $a1, $t0
syscall
lb $t7, 0($t0)
addi $t7, $t7, -48
sw $t7, 0($a3)

addi $t8, $t8, 1
div $t8, $t2
mfhi $t6
beq $t6, $zero, add_1

addi $a3, $a3, 4

add_1:
bne $t8, $t9, load_w
jr $ra

check:
beq $t5, $t1, check_done

addi $t0, $t0, 1
addi $t6, $t6, 1
beq $t6, $t2, check_LF

lb $t3, 0($t0)
blt $t3, $t9, error
blt $t8, $t3, error
j check

check_LF:
addi $t5, $t5, 1
lb $t3, 0($t0)
bne $t3, $t7, error
li $t6, 0
j check

check_done:
jr $ra

error:
lw $ra, 0($sp)
addi $sp, $sp, 4
li $v0, -1
jr $ra

.globl write_file
write_file:
move $t0, $a1
addi $t0, $t0, 400
addi $sp, $sp -8
sw $t0, 0($sp)
sw $ra, 4($sp)

lw $t1, 0($a1)
lw $t2, 4($a1)
li $t3, 10
li $t4, 0

addi $t1, $t1, 48
sb $t1, 0($t0)
addi $t1, $t1, -48
sb $t3, 1($t0)
addi $t2, $t2, 48
sb $t2, 2($t0)
addi $t2, $t2, -48
sb $t3, 3($t0)
addi $t0, $t0, 4
addi $a1, $a1, 8

mul $t7, $t1, $t2
add $t7, $t7, $t1
addi $t2, $t2, 1
jal load_b
addi $t2, $t2, -1

lw $t0, 0($sp)
lw $ra, 4($sp)
addi $sp, $sp 8
addi $t7, $t7, 4

li $v0, 13
li $a1, 1
li $a2, 0
syscall
move $t8, $v0

li $v0, 15
move $a0, $t8
move $a1, $t0
move $a2, $t7
syscall

li $v0, 16
move $a0, $t8
syscall
 
jr $ra

load_b:
addi $t4, $t4, 1
div $t4, $t2
mfhi $t6
beq $t6, $zero, add_LF

lw $t5, 0($a1)
addi $t5, $t5, 48
sb $t5, 0($t0)
addi $t0, $t0, 1
addi $a1, $a1, 4
j load_b

add_LF:
sb $t3, 0($t0)
addi $t0, $t0, 1

bne $t4, $t7, load_b
jr $ra

.globl rotate_clkws_90
rotate_clkws_90:
move $t0, $a0
move $a2, $a0
addi $a2, $a2, 400
move $a0, $a2

lw $t1, 0($t0)
lw $t2, 4($t0)
sw $t1, 4($a2)
sw $t2, 0($a2)
addi $t0, $t0, 8
addi $a2, $a2, 8

li $t6, -1
li $t7, 4
mul $t5, $t1, $t2
mul $t2, $t2, $t6
move $t3, $t2
addi $sp, $sp, -4
sw $ra, 0($sp)

mul $t5, $t5, $t7
add $t5, $t0, $t5
mul $t2, $t2, $t7
li $t6, 1
jal clkws_90


move $t0, $a0
move $a0, $a1
move $a1, $t0
jal write_file

lw $ra, 0($sp)
addi $sp, $sp, 4
 jr $ra

clkws_90:
move $t0, $t5
mul $t4, $t3, $t7
add $t0, $t0, $t4

li $t8, 0
addi $t3, $t3, 1
bne $t3, $t6, clkws_90_loop
jr $ra

clkws_90_loop:
lw $t9, 0($t0)
sw $t9, 0($a2)
add $t0, $t0, $t2
addi $a2, $a2, 4

addi $t8, $t8, 1
bne $t8, $t1, clkws_90_loop
j clkws_90

.globl rotate_clkws_180
rotate_clkws_180:
move $t0, $a0
move $a2, $a0
addi $a2, $a2, 400
move $a0, $a2

lw $t1, 0($t0)
lw $t2, 4($t0)
sw $t1, 0($a2)
sw $t2, 4($a2)
addi $t0, $t0, 8
addi $a2, $a2, 8

li $t7, 4
li $t6, 0
mul $t5, $t1, $t2
mul $t5, $t5, $t7
add $t0, $t0, $t5
addi $t0, $t0, -4
mul $t5, $t1, $t2

addi $sp, $sp, -4
sw $ra, 0($sp)
jal clkws_180

move $t0, $a0
move $a0, $a1
move $a1, $t0
jal write_file

lw $ra, 0($sp)
addi $sp, $sp, 4
 jr $ra

clkws_180:
lw $t1, 0($t0)
sw $t1, 0($a2)
addi $t0, $t0, -4
addi $a2, $a2, 4

addi $t6, $t6, 1
bne $t6, $t5, clkws_180
jr $ra

.globl rotate_clkws_270
rotate_clkws_270:
move $t0, $a0
move $a2, $a0
addi $a2, $a2, 400
move $a0, $a2

lw $t1, 0($t0)
lw $t2, 4($t0)
sw $t1, 4($a2)
sw $t2, 0($a2)
addi $t0, $t0, 8
addi $a2, $a2, 8

li $t6, -1
li $t7, 4
li $t3, 0
mul $t5, $t2, $t7
add $t5, $t0, $t5
addi $t5, $t5, -4
mul $t2, $t2, $t6
mul $t6, $t2, $t6
mul $t6, $t6, $t7
addi $t2, $t2, -1

addi $sp, $sp, -4
sw $ra, 0($sp)
jal clkws_270

move $t0, $a0
move $a0, $a1
move $a1, $t0
jal write_file

lw $ra, 0($sp)
addi $sp, $sp, 4
 jr $ra

clkws_270:
move $t0, $t5
mul $t4, $t3, $t7
add $t0, $t0, $t4

li $t8, 0
addi $t3, $t3, -1
bne $t3, $t2, clkws_270_loop
jr $ra

clkws_270_loop:
lw $t9, 0($t0)
sw $t9, 0($a2)
add $t0, $t0, $t6
addi $a2, $a2, 4

addi $t8, $t8, 1
bne $t8, $t1, clkws_270_loop
j clkws_270

.globl mirror
mirror:
move $t0, $a0
move $a2, $a0
addi $a2, $a2, 400
move $a0, $a2

lw $t1, 0($t0)
lw $t2, 4($t0)
sw $t1, 0($a2)
sw $t2, 4($a2)
addi $t0, $t0, 4
addi $a2, $a2, 8

li $t6, -1
li $t7, 4
li $t3, 0
li $t8, -1
mul $t5, $t2, $t7
add $t5, $t0, $t5

addi $sp, $sp, -4
sw $ra, 0($sp)
jal mirror_1

move $t0, $a0
move $a0, $a1
move $a1, $t0
jal write_file

lw $ra, 0($sp)
addi $sp, $sp, 4
 jr $ra
 
mirror_1:
move $t0, $t5
mul $t4, $t3, $t7
add $t0, $t0, $t4
add $t3, $t3, $t2

li $t6, 0
addi $t8, $t8, 1
bne $t8, $t1, mirror_1_loop
jr $ra

mirror_1_loop:
lw $t9, 0($t0)
sw $t9, 0($a2)
addi $t0, $t0, -4
addi $a2, $a2, 4

addi $t6, $t6, 1
bne $t6, $t2, mirror_1_loop
j mirror_1

.globl duplicate
duplicate:
move $t0, $a0
lw $t1, 0($t0)
lw $t2, 4($t0)
addi $t0, $t0, 8
li $t4, 4

addi $sp, $sp, -4
sw $ra, 0($sp)

mul $t5, $t2, $t4
li $t3, -1
mul $t6, $t5, $t3
move $a0, $t0
li $t3, 0
jal duplicate_1

li $v0, -1
li $v1, 0
lw $ra, 0($sp)
addi $sp, $sp, 4
 jr $ra

duplicate_1:
move $a1, $a0
add $a0, $a0, $t5

addi $t3, $t3, 1
bne $t3, $t1, duplicate_1_loop
jr $ra

duplicate_1_loop:
li $t7, 0
move $a2, $a0
move $a3, $a1

move $t8, $a1
add $a1, $a1, $t6
ble $t0, $t8, duplicate_1_loop_check
j duplicate_1

duplicate_1_loop_check:
lw $t8, 0($a2)
lw $t9, 0($a3)

addi $a2, $a2, 4
addi $a3, $a3, 4

bne $t8, $t9, duplicate_1_loop
addi $t7, $t7, 1
bne $t7, $t2, duplicate_1_loop_check
j duplicate_dup


duplicate_dup:
li $v0, 1
move $v1, $t3
addi $v1, $v1, 1

lw $ra, 0($sp)
addi $sp, $sp, 4
 jr $ra
