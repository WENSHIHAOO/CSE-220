.data

Name: .asciiz "NAME"
#Name: .asciiz "NAMEE"
#Name: .asciiz "NAE"
#Name1: .asciiz "Jane"
Name2: .asciiz "Ajj"
Name1: .asciiz "Ajjj"


.align 2
Network:
  .word 3   #total_nodes
  .word 3   #total_edges
  .word 4   #size_of_node
  .word 12  #size_of_edge
  .word 1   #curr_num_of_nodes
  .word 0   #curr_num_of_edges
   # set of nodes
  .byte 0 0 0 0 0 0 0 0 0 0 0 0
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
	la $a3, Name2
	jal add_person_property
  	#write test code

	la $a0, Network
	la $a1, Name
	jal get_person


exit:
	li $v0, 10
	syscall
.include "hw4.asm"
