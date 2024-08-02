# HW5 Rubric

- verify_create_term tests: 5*.4 = 2 pts.
- verify_create_poly tests: 8*1.5 = 12 pts.
- verify_sort_poly tests: 4*6 = 24 pts.
- verify_add_poly tests: 6*5 = 30 pts.
- verify_mult_poly tests: 4*7 = 28 pts.
- register convention tests: (4/27)*27 = 4 pts.

# Important Notes

Add the following functions to the end of your *hw5.asm* file.

```
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
```
# Running the tests

1. Compile if necessary
`$ javac -cp </path/to/munit.jar> Hw5Test.java`

2. Run the tests
`$ java -jar </path/to/munit.jar> Hw5Test.class hw5.asm`
