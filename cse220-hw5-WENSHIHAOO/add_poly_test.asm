############################ CHANGE THIS FILE AS YOU DEEM FIT ############################
.data
pairs1: .word 21 3 12 4 9 2 7 1 0 -1
pairs2: .word 3 4 8 3 21 2 33 1 25 0 0 -1

.text
main:
 la $a0, pairs1
 jal create_polynomial
 move $s0, $v0

 la $a0, pairs2
 jal create_polynomial
 move $s1, $v0

 move $a0, $s0
 move $a1, $s1
 jal add_polynomial
 #write test code


exit:
 li $v0, 10
 syscall

.include "hw5.asm"
