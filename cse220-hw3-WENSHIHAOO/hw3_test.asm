# This is a test file. Use this file to run the functions in hw3.asm
#
# Change data section as you deem fit.
# Change filepath if necessary.
.data
Filename: .asciiz "inputs/dup1p.txt"
OutFile: .asciiz "out.txt"
Buffer:
    .word 3	# num rows
    .word 2	# num columns
    # matrix
    .word 1 2 3 4 5 6 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 8383

.text
main:
 la $a0, Filename
 la $a1, Buffer
 jal initialize
 
 #la $a1, Filename
 #la $a0, Buffer
 #jal duplicate

 # write additional test code as you deem fit.


la $a0, Buffer
li $v0 4
syscall

 li $v0, 10
 syscall

.include "hw3.asm"
