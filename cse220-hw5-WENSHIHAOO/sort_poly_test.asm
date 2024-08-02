############################ CHANGE THIS FILE AS YOU DEEM FIT ############################
.data
pairs: .word 2 3 3 1 14 5 7 0 0 -1

.text
main:
 la $a0, pairs
 jal create_polynomial
 move $a0, $v0
 jal sort_polynomial
 #write test code

exit:
 li $v0, 10
 syscall

.include "hw5.asm"
