.data

FRIEND:.asciiz "FRIEND"
Name: .asciiz "NAME"
Name31: .asciiz "Janee"
Name11: .asciiz "Ajjjj"
Name3: .asciiz "Jane"
Name2: .asciiz "Ajj"
Name1: .asciiz "Ajjj"
Name32: .asciiz "Jane"
Name22: .asciiz "Ajj"
Name12: .asciiz "Ajjj"


.align 2
Network:
  .word 3   #total_nodes
  .word 3   #total_edges
  .word 4   #size_of_node
  .word 12  #size_of_edge
  .word 1   #curr_num_of_nodes
  .word 1   #curr_num_of_edges
   # set of nodes
  .byte 74 97 110 101 0 0 0 0 0 0 0 0
   # set of edges
  .word 0 0 0 0 0 0 0 0 0
.text
main:
	  la $a0, Network
  	jal create_person
  	la $a0, Network
  	move $a1, $v0		# return person
	la $a2, Name
	la $a3, Name1
	jal add_person_property
	
	la $a0, Network
  	jal create_person
  	la $a0, Network
  	move $a1, $v0		# return person
	la $a2, Name
	la $a3, Name22
	jal add_person_property
  	#write test code

	la $a0, Network
	la $a1, Name2
	la $a2, Name12
	jal add_relation
	
	la $a0, Network
	la $a1, Name1
	la $a2, Name32
	jal add_relation
	
	la $a0, Network
	la $a1, Name31
	la $a2, Name11
	la $a3, FRIEND
	addi $sp, $sp, -4
	li $t0, 1
	sw $t0, 0($sp)
	jal add_relation_property
	
	la $a0, Network
	la $a1, Name2
	la $a2, Name11
	la $a3, FRIEND
	addi $sp, $sp, -4
	li $t0, 1
	sw $t0, 0($sp)
	jal add_relation_property

exit:
	li $v0, 10
	syscall
.include "hw4.asm"
