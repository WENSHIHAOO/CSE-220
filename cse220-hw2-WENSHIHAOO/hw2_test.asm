########### Run and assemble this file to run hw2.asm ###########
########### Change the file to test hw2.asm ###########
.data
 plaintext: .ascii "SeaWolf"
 buf: .space 4
 ciphertext: .ascii "hellooye"
 key: .ascii "bH@pz0()"
 origtext: .space 8

#unix: .byte 10393
#unit: .byte 0
#un: .ascii "1234567"
.text:
#la $a0, un
#la $a1, unit
#la $a2, unix
#jal add_block



 #la $a0, plaintext
 #la $a1, key
 #la $a2, ciphertext
 #li $a3, 7
 #jal encrypt

 la $a0, ciphertext
 la $a1, key
 li $a2, 8
 la $a3, origtext
 jal decrypt

la $a0, ciphertext
  la $a1, key
 la $a3, origtext
 
 lb $t0, 3($a0)
 lb $t1, 0($a1)
 lb $t3, 0($a3)
 xor $t2, $t0, $t1
 li $t4, -1
 mul $t2, $t2, $t4
 
 add $a0, $t3, $t2
 li $v0, 1
 syscall
 li $v0, 10
 syscall




 la $a0, origtext
 li $a1, 1
 li $a2, 16
 jal substr

 la $a0, origtext
 li $v0, 4
 syscall

 li $v0, 10
 syscall

.include "hw2.asm"
