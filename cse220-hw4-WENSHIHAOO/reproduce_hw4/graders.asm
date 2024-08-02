.globl instructors_test_add_relation_prop
instructors_test_add_relation_prop:
addi $sp, $sp, -8
addi $t0, $0, 1
sw $t0, 0($sp)
sw $ra, 4($sp)
jal add_relation_property
lw $ra, 4($sp)
addi $sp, $sp, 8
jr $ra
