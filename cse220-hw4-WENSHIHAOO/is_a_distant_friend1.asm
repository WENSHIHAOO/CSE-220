.data

FRIEND:.asciiz "FRIEND"
Name: .asciiz "NAME"
Name31: .asciiz "Janee"
Name11: .asciiz "Ajjjj"
Name6: .asciiz "Jasd"
Name5: .asciiz "Jfgh"
Name4: .asciiz "Jefd"
Name3: .asciiz "Jane"
Name2: .asciiz "Ajj"
Name1: .asciiz "Ajjj"
Name32: .asciiz "Jane"
Name22: .asciiz "Ajj"
Name12: .asciiz "Ajjj"


.align 2
Network:
  .word 6  #total_nodes
  .word 5   #total_edges
  .word 4   #size_of_node
  .word 12  #size_of_edge
  .word 0   #curr_num_of_nodes
  .word 0   #curr_num_of_edges
   # set of nodes
  .byte 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
   # set of edges
  .word 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
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
	
	la $a0, Network
  	jal create_person
  	la $a0, Network
  	move $a1, $v0		# return person
	la $a2, Name
	la $a3, Name3
	jal add_person_property
	
	la $a0, Network
  	jal create_person
  	la $a0, Network
  	move $a1, $v0		# return person
	la $a2, Name
	la $a3, Name4
	jal add_person_property
	
	la $a0, Network
  	jal create_person
  	la $a0, Network
  	move $a1, $v0		# return person
	la $a2, Name
	la $a3, Name5
	jal add_person_property
	
	la $a0, Network
  	jal create_person
  	la $a0, Network
  	move $a1, $v0		# return person
	la $a2, Name
	la $a3, Name6
	jal add_person_property
	
  	#write test code

	la $a0, Network
	la $a1, Name2
	la $a2, Name1
	jal add_relation
	
	la $a0, Network
	la $a1, Name2
	la $a2, Name5
	jal add_relation
	
	la $a0, Network
	la $a1, Name1
	la $a2, Name3
	jal add_relation
	
	la $a0, Network
	la $a1, Name4
	la $a2, Name3
	jal add_relation
	
	la $a0, Network
	la $a1, Name3
	la $a2, Name6
	jal add_relation
	
	la $a0, Network
	la $a1, Name31
	la $a2, Name6
	la $a3, FRIEND
	addi $sp, $sp, -4
	li $t0, 1
	sw $t0, 0($sp)
	jal add_relation_property
	
	la $a0, Network
	la $a1, Name2
	la $a2, Name5
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
	
	la $a0, Network
	la $a1, Name3
	la $a2, Name11
	la $a3, FRIEND
	addi $sp, $sp, -4
	li $t0, 1
	sw $t0, 0($sp)
	jal add_relation_property
	
	la $a0, Network
	la $a1, Name3
	la $a2, Name4
	la $a3, FRIEND
	addi $sp, $sp, -4
	li $t0, 1
	sw $t0, 0($sp)
	jal add_relation_property

	la $a0, Network
	la $a1, Name1
	la $a2, Name6
	jal is_a_distant_friend
exit:
	li $v0, 10
	syscall
.include "hw4.asm"
