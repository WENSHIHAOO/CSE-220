############## Shihao Wen ##############
############## SHIWEN #################
############## 113085521 ################

############################ DO NOT CREATE A .data SECTION ############################
############################ DO NOT CREATE A .data SECTION ############################
############################ DO NOT CREATE A .data SECTION ############################
.text:
.globl create_person
create_person:	
addi $t0, $a0, 8 
lw $a0, 0($a0)#$a0 is max person#
addi $t1, $t0, 8 
lw $t0, 0($t0)#$t0 is size name of person#
addi $t2, $t1, 8 
move $t3, $t1
lw $t1, 0($t1)#t1 is curr_num_of person#

beq $a0, $t1, max_person
addi $t4, $t1, 1
sw $t4, 0($t3)

mul $t3, $t0, $t1
add $t2, $t2, $t3 #t2 is curr_array person#

move $v0, $t2
addi $sp, $sp, -4
sw $ra 0($sp)
jal loop_create_person
lw $ra 0($sp)
addi $sp, $sp, 4
  jr $ra

max_person:
li $v0, -1
jr $ra

loop_create_person:
sb $zero 0($t2)
addi $t2, $t2, 1
addi $t0, $t0, -1

bne $t0, $zero, loop_create_person
jr $ra

.globl add_person_property
add_person_property:
addi $sp, $sp, -4
sw $ra, 0($sp)
move $v1, $sp
#1. prop_name is equal to the string constant “NAME”
lb $t0, 0($a2)
li $t1, 78 #N=78
bne $t0, $t1, add_person_error
lb $t0, 1($a2)
li $t1, 65 #A=65
bne $t0, $t1, add_person_error
lb $t0, 2($a2)
li $t1, 77 #M=77
bne $t0, $t1, add_person_error
lb $t0, 3($a2)
li $t1, 69 #E=69
bne $t0, $t1, add_person_error
lb $t0, 4($a2)
li $t1, 0
bne $t0, $t1, add_person_error

#2. person exists in Network
move $t0, $a0
addi $t0, $t0, 8
lw $t1, 0($t0) #size_of_node
addi $t0, $t0, 8
lw $t2, 0($t0) #curr_num_of_nodes
addi $t0, $t0, 8 #Node[0]
mul $t3, $t1, $t2

li $t2, -1
mul $t1, $t1, $t2
add $t3, $t3, $t1
mul $t1, $t1, $t2

add $t4, $t0, $t3 #Node[curr_num_of_nodes - 1]
bne $a1 $t4, add_person_error

#3. The length of prop_val (excluding null character) <= Network.size_of_node
li $t3, -1
addi $sp, $sp, -4
sw $a3, 0($sp)
jal add_person_3
lw $a3, 0($sp)
addi $sp, $sp, 4

lw $t2, 16($a0) #curr_num_of_nodes
li $t9 1
beq $t2, $t9, addp

#4. prop_val is unique in the Network.
addi $sp, $sp, -4
sw $a3, 0($sp)
jal add_person_4
lw $a3, 0($sp)
addi $sp, $sp, 4

#Done. add_person
addp:
jal add_person_Done
li $v0, 1

lw $ra, 0($sp)
addi $sp, $sp, 4
  jr $ra

add_person_3:
addi $t3, $t3, 1
lb $t5, 0($a3)
addi $a3, $a3, 1
bne $t5, $zero, add_person_3
blt $t1, $t3, add_person_error
jr $ra

add_person_4:
li $t7, 0
addi $sp, $sp, -12
sw $a3, 8($sp)
sw $ra, 4($sp)
sw $t0, 0($sp)
jal add_person_4_loop
lw $t0, 0($sp)
lw $ra, 4($sp)
lw $a3, 8($sp)
addi $sp, $sp, 12

add $t0, $t0, $t1
bne $t0, $t4, add_person_4
jr $ra

add_person_4_loop:
addi $t7, $t7, 1
lb $t5, 0($a3)
lb $t6, 0($t0)
bne $t5, $t6, add_person_4_return
beq $t5, $zero, add_person_error
beq $t7, $t1, add_person_error

addi $a3, $a3, 1
addi $t0, $t0, 1
j add_person_4_loop

add_person_4_return:
jr $ra

add_person_Done:
lb $t5, 0($a3)
sb $t5, 0($t4)
addi $a3, $a3, 1
addi $t4, $t4, 1
bne $t5, $zero, add_person_Done
jr $ra

add_person_error:
move $sp, $v1
lw $ra, 0($sp)
addi $sp, $sp, 4
li $v0, 0
jr $ra

.globl get_person
get_person:
move $t0, $a0
addi $t0, $t0, 8
lw $t1, 0($t0) #size_of_node
addi $t0, $t0, 8
lw $t2, 0($t0) #curr_num_of_nodes
addi $t0, $t0, 8 #Node[0]
mul $t3, $t1, $t2
add $t4, $t0, $t3 #Node[curr_num_of_nodes]

addi $sp, $sp, -4
sw $ra, 0($sp)
move $v1, $sp
addi $sp, $sp, -4
sw $a1, 0($sp)
jal get_person_1
lw $a1, 0($sp)
addi $sp, $sp, 4
lw $ra, 0($sp)
addi $sp, $sp, 4

li $v0, 0
  jr $ra
  
get_person_1:
li $t7, 0
addi $sp, $sp , -12
sw $ra, 8($sp)
sw $t0, 4($sp)
sw $a1, 0($sp)
jal get_person_1_loop
lw $a1, 0($sp)
lw $t0, 4($sp)
lw $ra, 8($sp)
addi $sp, $sp , 12

add $t0, $t0, $t1
bne $t0, $t4, get_person_1
jr $ra

get_person_1_loop:
addi $t7, $t7, 1
lb $t5, 0($a1)
lb $t6, 0($t0)
addi $a1, $a1, 1
addi $t0, $t0, 1
bne $t5, $t6, get_person_1_return
beq $t5, $zero, get_person_Done
beq $t7, $t1, get_person_Done
j get_person_1_loop

get_person_1_return:
jr $ra

get_person_Done:
lw $t0, 4($sp)

move $sp, $v1
lw $ra, 0($sp)
addi $sp, $sp, 4

move $v0, $t0
jr $ra

.globl add_relation
add_relation:
#1. If no person with name1 exists in the network
addi $sp, $sp, -4
sw $ra, 0($sp)
addi $sp, $sp, -12
sw $a0, 8($sp)
sw $a1, 4($sp)
sw $a2, 0($sp)
jal get_person
lw $a0, 8($sp)
lw $a1, 4($sp)
lw $a2, 0($sp)
addi $sp, $sp, 12

move $v1, $sp

beq $v0, $zero, add_relation_error
move $t9, $v0

#2. If no person with name2 exists in the network
addi $sp, $sp, -12
sw $a0, 8($sp)
sw $a1, 4($sp)
sw $a2, 0($sp)

move $t0, $a1
move $a1, $a2
jal get_person
lw $a0, 8($sp)
lw $a1, 4($sp)
lw $a2, 0($sp)
addi $sp, $sp, 12

move $v1, $sp

beq $v0, $zero, add_relation_error
move $t8, $v0

#3. The network is at capacity,that is, it already contains the maximum no. of edges possible
lw $t0, 4($a0)#total_edges
lw $t1, 20($a0)#curr_num_of_edges
beq $t0, $t1, add_relation_error

#4. A relation between a person with name1 and a person with name2 already exists in the network.
lw $t2, 12($a0)#size_of_edge

lw $t3, 8($a0)#size_of_node
lw $t4, 0($a0)#total_nodes 
addi $t5, $a0, 24# set of nodes 

mul $t6, $t3, $t4
add $t3, $t6, $t5# set of edges

li $t6, -1
mul $t6, $t6, $t2

mul $t4, $t1, $t2
add $t4, $t4, $t3# set curr_of edges
add $t3, $t3, $t6# set of edges -1
jal check_a1a2

#5. name1==name2
jal check_relation_done

#6. done
sw $t9, 0($t4)
sw $t8, 4($t4)
sw $zero, 8($t4)
lw $t1, 20($a0)
addi $t1, $t1, 1
sw $t1, 20($a0)

li $v0, 1
lw $ra, 0($sp)
addi $sp, $sp, 4
  jr $ra

check_a1a2:
add $t3, $t3, $t2
beq $t3, $t4, check_a1a2_done

lw $t5, 0($t3)
lw $t6, 4($t3)
j check_a1a2_check

check_a1a2_check:
bne $t9, $t5, check_a2a1_check
bne $t8, $t6, check_a1a2
j add_relation_error

check_a2a1_check:
bne $t9, $t6, check_a1a2
bne $t8, $t5, check_a1a2
j add_relation_error

check_a1a2_done:
jr $ra

check_relation_done:
lb $t0, 0($a1)
lb $t1, 0($a2)
addi $a1, $a1, 1
addi $a2, $a2, 1
bne $t0, $t1, check_relation_done_return
bne $t0, $zero, check_relation_done
j add_relation_error

check_relation_done_return:
jr $ra

add_relation_error:
move $sp, $v1
li $v0, 0
lw $ra, 0($sp)
addi $sp, $sp, 4
  jr $ra

.globl add_relation_property
add_relation_property:
lw $t9, 0($sp)
lw $t0, 8($a0) #size_of_node 

addi $sp, $sp, -4
sw $ra, 0($sp)
move $v1, $sp
# If the length of both strings is greater than the node size then ignore the additional characters.
move $t1, $a1
li $t2, 0
jal ignore_length
move $t1, $a2
li $t2, 0
jal ignore_length

#2. The argument prop_name == “FRIEND”
lb $t0, 0($a3)
li $t1, 70 #F=70
bne $t0, $t1, add_relation_property_error
lb $t0, 1($a3)
li $t1, 82 #R=82
bne $t0, $t1, add_relation_property_error
lb $t0, 2($a3)
li $t1, 73 #I=73
bne $t0, $t1, add_relation_property_error
lb $t0, 3($a3)
li $t1, 69 #E=69
bne $t0, $t1, add_relation_property_error
lb $t0, 4($a3)
li $t1, 78 #N=78
bne $t0, $t1, add_relation_property_error
lb $t0, 5($a3)
li $t1, 68 #D=68
bne $t0, $t1, add_relation_property_error
lb $t0, 6($a3)
li $t1, 0
bne $t0, $t1, add_relation_property_error

#3. The argument prop_val == 1
li $t0, 1
bne $t9, $t0, add_relation_property_error

#1. A relation between a person with name1 and person2 with name2 exists in the network

#1.1 If no person with name1 exists in the network
addi $sp, $sp, -12
sw $a0, 8($sp)
sw $a1, 4($sp)
sw $a2, 0($sp)
jal get_person
lw $a0, 8($sp)
lw $a1, 4($sp)
lw $a2, 0($sp)
addi $sp, $sp, 12

move $v1, $sp

beq $v0, $zero, add_relation_property_error
move $t9, $v0

#1.2 If no person with name2 exists in the network
addi $sp, $sp, -12
sw $a0, 8($sp)
sw $a1, 4($sp)
sw $a2, 0($sp)

move $t0, $a1
move $a1, $a2
jal get_person
lw $a0, 8($sp)
lw $a1, 4($sp)
lw $a2, 0($sp)
addi $sp, $sp, 12

move $v1, $sp

beq $v0, $zero, add_relation_property_error
move $t8, $v0

lw $t1, 20($a0)#curr_num_of_edges
lw $t2, 12($a0)#size_of_edge
lw $t3, 8($a0)#size_of_node
lw $t4, 0($a0)#total_nodes 
addi $t5, $a0, 24# set of nodes 

mul $t6, $t3, $t4
add $t3, $t6, $t5# set of edges

li $t6, -1
mul $t6, $t6, $t2

mul $t4, $t1, $t2
add $t4, $t4, $t3# set curr_of edges
add $t3, $t3, $t6# set of edges -1
jal property_check_a1a2

#done
li $t0, 1
sw $t0, 8($t3)

li $v0, 1
lw $ra, 0($sp)
addi $sp, $sp, 4
  jr $ra

property_check_a1a2:
add $t3, $t3, $t2
beq $t3, $t4, add_relation_property_error

lw $t5, 0($t3)
lw $t6, 4($t3)
j property_check_a1a2_check

property_check_a1a2_check:
bne $t9, $t5, property_check_a2a1_check
bne $t8, $t6, property_check_a1a2
j property_check_a1a2_done

property_check_a2a1_check:
bne $t9, $t6, property_check_a1a2
bne $t8, $t5, property_check_a1a2
j property_check_a1a2_done

property_check_a1a2_done:
jr $ra


ignore_length:
blt $t0, $t2, ignore_length_0
lb $t3, 0($t1)
addi $t1, $t1, 1
addi $t2, $t2, 1
bne $t3, $zero, ignore_length

jr $ra

ignore_length_0:
sb $zero, -1($t1)
jr $ra

add_relation_property_error:
move $sp, $v1
li $v0, 0
lw $ra, 0($sp)
addi $sp, $sp, 4
  jr $ra

.globl is_a_distant_friend
is_a_distant_friend:
#1. If no person with name1 exists in the network
addi $sp, $sp, -16
sw $ra, 12($sp)
sw $a0, 8($sp)
sw $a1, 4($sp)
sw $a2, 0($sp)
jal get_person
lw $a0, 8($sp)
lw $a1, 4($sp)
lw $a2, 0($sp)
addi $sp, $sp, 12

beq $v0, $zero, not_exist
move $t9, $v0

#2. If no person with name2 exists in the network
move $t0, $a1
move $a1, $a2

addi $sp, $sp, -12
sw $a0, 8($sp)
sw $a1, 4($sp)
sw $a2, 0($sp)
jal get_person
lw $a0, 8($sp)
lw $a1, 4($sp)
lw $a2, 0($sp)
addi $sp, $sp, 12

beq $v0, $zero, not_exist
move $t8, $v0

beq $t8, $t9, not_distant_friend
#if a person with name1 is not a distant friend of a person with name2
move $t0, $sp # lower of $sp
lw $t1, 0($a0)#total_nodes
lw $t2, 8($a0)#size_of_node 
lw $t3, 12($a0)#size_of_edge________*
lw $t4, 20($a0)#curr_num_of_edges

addi $t5, $a0, 24
mul $t6, $t1, $t2
add $t5, $t5, $t6# set of edges________*
mul $t6, $t3, $t4
add $t6, $t5, $t6# set curr_of edges________*

move $t1, $t5
li $t2, -1
mul $t2, $t2, $t3
add $t1, $t1, $t2
move $t5, $t1
jal check_not_distant_friend_loop

move $a1, $t9
move $a2, $t8
li $a3, 0
jal check_distant

move $sp, $t0
lw $ra, 0($sp)
addi $sp, $sp, 4
  jr $ra
## check_distant ##
check_distant:
beq $a3, $sp, not_distant_friend
move $a3, $sp#$a3 check if sp change
move $t1, $sp#t1 is change
j sp_loop

sp_loop:
lw $t2, 0($t1)

move $t4, $t5
move $t9, $ra#sp is used, so use t9 = ra
jal edge_loop
move $ra, $t9

addi $t1, $t1, 4
bne $t1, $t0, sp_loop
j check_distant

edge_loop:
add $t4, $t4, $t3
bne $t4, $t6, edge_1
jr $ra

edge_1:
lw $t7, 0($t4)
bne $t7, $t2, edge_2
j edge_number_1

edge_2:
lw $t7, 4($t4)
bne $t7, $t2, edge_loop
j edge_number_2

edge_number_1:
lw $t7, 8($t4)
li $t8, 1
bne $t7, $t8, edge_loop

lw $t7, 4($t4)
beq $t7, $a2, check_distant_done
beq $t7, $a1, edge_loop
move $v0, $a3
j add_sp_loop

edge_number_2:
lw $t7, 8($t4)
li $t8, 1
bne $t7, $t8, edge_loop

lw $t7, 0($t4)
beq $t7, $a2, check_distant_done
beq $t7, $a1, edge_loop
move $v0, $a3
j add_sp_loop

add_sp_loop:
lw $v1, 0($v0)
beq $t7, $v1, edge_loop
addi $v0, $v0, 4
bne $v0, $t0, add_sp_loop
j add_sp

add_sp:
addi $sp, $sp, -4
sw $t7, 0($sp)
j edge_loop

check_distant_done:
li $v0, 1
move $sp, $t0
lw $ra, 0($sp)
addi $sp, $sp, 4
  jr $ra

## check_not_distant ##
check_not_distant_friend_loop:
add $t1, $t1, $t3
bne $t1, $t6, check_not_distant_friend_1
jr $ra

check_not_distant_friend_1:
lw $t2, 0($t1)
bne $t2, $t9, check_not_distant_friend_2
lw $t2, 4($t1)
beq $t2, $t8, number_haveEdge
j number_NhaveEdge

check_not_distant_friend_2:
lw $t2, 4($t1)
bne $t2, $t9, check_not_distant_friend_loop
lw $t2, 0($t1)
beq $t2, $t8, number_haveEdge
j number_NhaveEdge

number_NhaveEdge:
lw $t7, 8($t1)
li $t4, 1
bne $t7, $t4, check_not_distant_friend_loop
addi $sp, $sp, -4
sw $t2, 0($sp)
j check_not_distant_friend_loop

number_haveEdge:
lw $t7, 8($t1)
li $t4, 1
beq $t7, $t4, not_distant_friend
j check_not_distant_friend_loop

## end ##
not_distant_friend:
li $v0, 0
move $sp, $t0
lw $ra, 0($sp)
addi $sp, $sp, 4
jr $ra

not_exist:
li $v0, -1
lw $ra, 0($sp)
addi $sp, $sp, 4
jr $ra
