############## Shihao Wen ##############
############## 113085521 #################
############## SHIWEN ################

############################ DO NOT CREATE A .data SECTION ############################
############################ DO NOT CREATE A .data SECTION ############################
############################ DO NOT CREATE A .data SECTION ############################
.text:
.globl create_term
create_term:
move $t7, $a0
beq $t7, $zero, term_error
blt $a1, $zero,  term_error

li $a0, 12
li $v0, 9
syscall 

sw $t7, 0($v0)
sw $a1, 4($v0)
sw $zero, 8($v0)
  jr $ra

term_error:
li $v0, -1
jr $ra

.globl create_polynomial
create_polynomial:
addi $sp, $sp, -4
sw $ra, 0($sp)
move $t0, $sp
li $t8, 0
jal create_loop

li $a0, 8#Create A Polynomial
li $v0, 9
syscall 
move $a2, $v0
sw $t8, 4($a2) 
move $t8, $a2

move $t9, $sp
addi $t9, $t9, -8

lw $t6, 4($a2)
li $t5, 0
li $t4, 0
next1:
addi $t5, $t5, 1
lw $a0, 0($t9)
lw $a1, 4($t9)
jal create_term
li $t7, -1
beq $v0, $t7, next
sw $v0, 0($t8)
move $t8, $v0
addi $t4, $t4, 1
j create_p

next:
addi $t9, $t9, -8
beq $t6, $t5, next2
j next1

next2:
sw $zero, 0($a2)

create_done:
sw $t4, 4($a2)
move $v0, $a2
lw $ra, 0($sp)
addi $sp, $sp, 4
  jr $ra
  
create_p:
beq $t6, $t5, create_NULL
addi $t5, $t5, 1

addi $t9, $t9, -8
lw $a0, 0($t9)
beq $a0, $zero, create_p
lw $a1, 4($t9)
jal create_term
sw $v0, 8($t8)
move $t8, $v0
addi $t4, $t4, 1
j create_p
  
create_loop:
lw $t2, 4($a0) #exponent
li $t4, -1
beq $t2, $t4, create_return
lw $t4, 0($a0) #coefficient
addi $a0, $a0, 8
move $t1, $t0
j create_loop_sp

create_loop_sp:
beq $t1, $sp, create_add
lw $t3, 4($t1)
beq $t3, $t2, same_exponent
addi $t1, $t1, 8
j create_loop_sp

create_add:
addi $t8, $t8, 1
addi $t0, $t0, -8
sw $t2, 4($t0)
sw $t4, 0($t0)
j create_loop

same_exponent:
lw $t3, 0($t1)
add $t3, $t3, $t4
sw $t3, 0($t1)
j create_loop

create_return:
addi $t0, $t0, -8
sw $t2, 4($t0)
sw $t4, 0($t0)
jr $ra

create_NULL:
sw $zero, 8($t8)
j create_done

.globl sort_polynomial
sort_polynomial:
addi $sp, $sp, -4
sw $ra, 0($sp)
lw $t0, 0($a0)
beq $t0, $zero, sort_done_1

move $t1, $sp 
addi $t1, $t1, -4
sw $t0, 0($t1) #t0 is link
j sort_loop

sort_done:
sw $zero, 8($a0)
sort_done_1:
lw $ra, 0($sp)
addi $sp, $sp, 4
  jr $ra

sort_loop:
move $t9, $t1 
lw $t0, 8($t0)
beq $t0, $zero, sort_loop_done
lw $t4, 4($t0)
addi $t1, $t1, -4
j sort_loop_1

sort_loop_1:
beq $t9, $sp, t1_add
lw $t3, 0($t9)
lw $t5, 4($t3)

blt $t4, $t5, t1_add
sw $t3, -4($t9)
addi $t9, $t9, 4
j sort_loop_1

t1_add:
sw $t0, -4($t9)
j sort_loop

sort_loop_done:
move $t9, $sp
addi $t9, $t9, -4
lw $t2, 0($t9)
sw $t2, 0($a0)
lw $a0, 0($a0)

sort_loop_done_loop:
beq $t9, $t1, sort_done
addi $t9, $t9, -4
lw $t2, 0($t9)
sw $t2, 8($a0)
lw $a0, 8($a0)
j sort_loop_done_loop

.globl add_polynomial
add_polynomial:
move $t9, $sp
addi $t9, $t9, 8
addi $sp, $sp, -4
sw $ra, 0($sp)

move $t8, $t9

lw $t0, 0($a0)
bne $t0, $zero, last_term
last_term_next:

lw $t0, 0($a1)
bne $t0, $zero, last_term_1
last_term_next_1:

li $t7, -1
sw $zero, -8($t8)
sw $t7, -4($t8)

addi $t9, $t9, -8
move $a0, $t9
jal create_polynomial

move $a0, $v0
jal sort_polynomial

lw $ra, 0($sp)
addi $sp, $sp, 4
  jr $ra
  
last_term_1:
lw $t1, 4($t0)
lw $t2, 0($t0)
sw $t1, -4($t8)
sw $t2, -8($t8)
addi $t8, $t8, 8

lw $t3, 8($t0)
beq $t3, $zero, last_term_next_1
move $t0, $t3
j last_term_1
  
last_term:
lw $t1, 4($t0)
lw $t2, 0($t0)
sw $t1, -4($t8)
sw $t2, -8($t8)
addi $t8, $t8, 8

lw $t3, 8($t0)
beq $t3, $zero, last_term_next
move $t0, $t3
j last_term


.globl mult_polynomial
mult_polynomial:
move $t9, $sp
addi $t9, $t9, 8
addi $sp, $sp, -4
sw $ra, 0($sp)

move $t8, $t9

lw $t4, 0($a1)
beq $t4, $zero, mult_term_done
lw $t0, 0($a0)
bne $t0, $zero, mult_term
mult_term_done:

li $t7, -1
sw $zero, -8($t8)
sw $t7, -4($t8)

addi $t9, $t9, -8
move $a0, $t9
jal create_polynomial

move $a0, $v0
jal sort_polynomial

lw $ra, 0($sp)
addi $sp, $sp, 4
  jr $ra

mult_term:
lw $t1, 4($t0) #exp
lw $t2, 0($t0) #coeff
move $t7, $t4
jal mult_term_1

lw $t3, 8($t0)
beq $t3, $zero, mult_term_done
move $t0, $t3
j mult_term

mult_term_1:
lw $t5, 4($t7) #exp
lw $t6, 0($t7) #coeff

add $t5, $t5, $t1
sw $t5, -4($t8)
mul $t6, $t6, $t2
sw $t6, -8($t8)
addi $t8, $t8, 8

lw $t3, 8($t7)
move $t7, $t3
bne $t3, $zero, mult_term_1
jr $ra

.globl instructors_hw5_sort_poly_test
instructors_hw5_sort_poly_test:
 addi $sp, $sp, -8
 sw $ra, 0($sp)
 sw $s0, 4($sp)
 jal create_polynomial
 move $s0, $v0
 move $a0, $v0
 jal sort_polynomial
 move $v0, $s0
 lw $ra, 0($sp)
 lw $s0, 4($sp)
 addi $sp, $sp, 8
 jr $ra

.globl instructors_hw5_add_poly_test
instructors_hw5_add_poly_test:
addi $sp, $sp, -20
sw $ra, 0($sp)
sw $s0, 4($sp)
sw $s1, 8($sp)
sw $s2, 12($sp)
sw $s3, 16($sp)
move $s1, $a0
move $s2, $a1
move $a0, $s1
jal create_polynomial
move $s0, $v0
move $a0, $s2
jal create_polynomial
move $s3, $v0
move $a0, $s0
move $a1, $s3
jal add_polynomial
lw $ra, 0($sp)
lw $s0, 4($sp)
lw $s1, 8($sp)
lw $s2, 12($sp)
lw $s3, 16($sp)
addi $sp, $sp, 20
jr $ra

.globl instructors_hw5_mult_poly_test
instructors_hw5_mult_poly_test:
addi $sp, $sp, -20
sw $ra, 0($sp)
sw $s0, 4($sp)
sw $s1, 8($sp)
sw $s2, 12($sp)
sw $s3, 16($sp)
move $s1, $a0
move $s2, $a1
move $a0, $s1
jal create_polynomial
move $s0, $v0
move $a0, $s2
jal create_polynomial
move $s3, $v0
move $a0, $s0
move $a1, $s3
jal mult_polynomial
lw $ra, 0($sp)
lw $s0, 4($sp)
lw $s1, 8($sp)
lw $s2, 12($sp)
lw $s3, 16($sp)
addi $sp, $sp, 20
jr $ra
