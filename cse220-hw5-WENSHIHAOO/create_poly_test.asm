############################ CHANGE THIS FILE AS YOU DEEM FIT ############################
.data
pairs: .word -9,5,18,1,4,5,-6,-1,-12,1,5,5,0,-1

.text
main:
 la $a0, pairs
 jal create_polynomial
 #write test code


exit:
 li $v0, 10
 syscall
.include "hw5.asm"
