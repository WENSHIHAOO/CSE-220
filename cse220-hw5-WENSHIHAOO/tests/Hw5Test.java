import org.junit.*;

import static edu.gvsu.mipsunit.munit.MUnit.Register.*;
import static edu.gvsu.mipsunit.munit.MUnit.*;
import static edu.gvsu.mipsunit.munit.MARSSimulator.*;
import java.io.*;
import java.util.Arrays;
import org.junit.rules.Timeout;
import java.util.concurrent.TimeUnit;

public class Hw5Test {

  private int reg_sp = 0;
  private int reg_s0 = 0;
  private int reg_s1 = 0;
  private int reg_s2 = 0;
  private int reg_s3 = 0;
  private int reg_s4 = 0;
  private int reg_s5 = 0;
  private int reg_s6 = 0;
  private int reg_s7 = 0;

  @Rule
  public Timeout timeout = new Timeout(10000, TimeUnit.MILLISECONDS);

  @Before
  public void preTest() {
    this.reg_s0 = get(s0);
    this.reg_s1 = get(s1);
    this.reg_s2 = get(s2);
    this.reg_s3 = get(s3);
    this.reg_s4 = get(s4);
    this.reg_s5 = get(s5);
    this.reg_s6 = get(s6);
    this.reg_s7 = get(s7);
    this.reg_sp = get(sp);
  }

  @After
  public void postTest() {
    Assert.assertEquals("Register convention violated $s0", this.reg_s0, get(s0));
    Assert.assertEquals("Register convention violated $s1", this.reg_s1, get(s1));
    Assert.assertEquals("Register convention violated $s2", this.reg_s2, get(s2));
    Assert.assertEquals("Register convention violated $s3", this.reg_s3, get(s3));
    Assert.assertEquals("Register convention violated $s4", this.reg_s4, get(s4));
    Assert.assertEquals("Register convention violated $s5", this.reg_s5, get(s5));
    Assert.assertEquals("Register convention violated $s6", this.reg_s6, get(s6));
    Assert.assertEquals("Register convention violated $s7", this.reg_s7, get(s7));
    Assert.assertEquals("Register convention violated $sp", this.reg_sp, get(sp));
  }

  @Test
  public void verify_create_term_success_1() {
    run("create_term", 3, 5);
    Assert.assertEquals(3, getWord(get(v0)));
    Assert.assertEquals(5, getWord(get(v0)+4));
    Assert.assertEquals(0, getWord(get(v0)+8));
  }

  @Test
  public void verify_create_term_success_2() {
    run("create_term", 10, 4);
    Assert.assertEquals(10, getWord(get(v0)));
    Assert.assertEquals(4, getWord(get(v0)+4));
    Assert.assertEquals(0, getWord(get(v0)+8));
  }

  @Test
  public void verify_create_term_success_3() {
    run("create_term", 99, 1);
    Assert.assertEquals(99, getWord(get(v0)));
    Assert.assertEquals(1, getWord(get(v0)+4));
    Assert.assertEquals(0, getWord(get(v0)+8));
  }

  @Test
  public void verify_create_term_success_4() {
    run("create_term", -9, 1);
    Assert.assertEquals(-9, getWord(get(v0)));
    Assert.assertEquals(1, getWord(get(v0)+4));
    Assert.assertEquals(0, getWord(get(v0)+8));
  }

  @Test
  public void verify_create_term_success_5() {
    run("create_term", 8, 0);
    Assert.assertEquals(8, getWord(get(v0)));
    Assert.assertEquals(0, getWord(get(v0)+4));
    Assert.assertEquals(0, getWord(get(v0)+8));
  }

  @Test
  public void verify_create_poly_simple_short_1() {
    Label terms = wordData(9,2,4,1,5,0,0,-1);
    run("create_polynomial", terms);
    Assert.assertEquals("Unexpected term count", 3, getWord(get(v0)+4));
    int poly_addr = getWord(get(v0));
    int[] expected = new int[]{ 9,2,4,1,5,0 };
    int i = 0;
    while(poly_addr != 0) {
      Assert.assertEquals("Unexpected coefficent", expected[i++], getWord(poly_addr));
      Assert.assertEquals("Unexpected exponent", expected[i++], getWord(poly_addr+4));
      poly_addr = getWord(poly_addr+8);
    }
  }

  @Test
  public void verify_create_poly_simple_short_2() {
    int[] expected = new int[]{ 2,5,18,1,3,7,1,6,0,-1 };
    Label terms = wordData(expected);
    run("create_polynomial", terms);
    int i = 0;
    Assert.assertEquals("Unexpected term count", (expected.length-2)/2, getWord(get(v0)+4));
    int poly_addr = getWord(get(v0));
    while(poly_addr != 0) {
      Assert.assertEquals("Unexpected coefficent", expected[i++], getWord(poly_addr));
      Assert.assertEquals("Unexpected exponent", expected[i++], getWord(poly_addr+4));
      poly_addr = getWord(poly_addr+8);
    }
  }

  @Test
  public void verify_create_poly_simple_long() {
    int [] expected = new int[100];
    for(int x = 0; x < expected.length-2; x++) {
      expected[x] = x + 12;
    }
    expected[98] = 0;
    expected[99] = -1;
    Label terms = wordData(expected);
    run("create_polynomial", terms);
    int i = 0;
    Assert.assertEquals("Unexpected term count", (expected.length-2)/2, getWord(get(v0)+4));
    int poly_addr = getWord(get(v0));
    while(poly_addr != 0) {
      Assert.assertEquals("Unexpected coefficent", expected[i++], getWord(poly_addr));
      Assert.assertEquals("Unexpected exponent", expected[i++], getWord(poly_addr+4));
      poly_addr = getWord(poly_addr+8);
    }
  }

  @Test
  public void verify_create_poly_dup_short_1() {
    Label terms = wordData(2,5,18,1,3,7,6,1,0,-1);
    run("create_polynomial", terms);
    int[] expected = new int[]{ 2,5,24,1,3,7 };
    int i = 0;
    Assert.assertEquals("Unexpected term count", 3, getWord(get(v0)+4));
    int poly_addr = getWord(get(v0));
    while(poly_addr != 0) {
      Assert.assertEquals("Unexpected coefficent", expected[i++], getWord(poly_addr));
      Assert.assertEquals("Unexpected exponent", expected[i++], getWord(poly_addr+4));
      poly_addr = getWord(poly_addr+8);
    }
  }

  @Test
  public void verify_create_poly_dup_short_2() {
    Label terms = wordData(-9,5,18,1,4,5,5,5,0,-1);
    run("create_polynomial", terms);
    int[] expected = new int[]{ 18,1};
    int i = 0;
    Assert.assertEquals("Unexpected term count", 1, getWord(get(v0)+4));
    int poly_addr = getWord(get(v0));
    while(poly_addr != 0) {
      Assert.assertEquals("Unexpected coefficent", expected[i++], getWord(poly_addr));
      Assert.assertEquals("Unexpected exponent", expected[i++], getWord(poly_addr+4));
      poly_addr = getWord(poly_addr+8);
    }
  }

  @Test
  public void verify_create_poly_empty() {
    Label terms = wordData(-9,5,18,1,4,5,-6,1,-12,1,5,5,0,-1);
    run("create_polynomial", terms);
    int[] expected = new int[]{ 18,1};
    int i = 0;
    Assert.assertEquals("Unexpected term count", 0, getWord(get(v0)+4));
    Assert.assertEquals("Unexpected term in empty poly", 0, getWord(get(v0)));
  }

  @Test
  public void verify_create_poly_invalid_terms_1() {
    Label terms = wordData(-9,5,18,1,4,5,-6,-1,-12,1,5,5,0,-1);
    run("create_polynomial", terms);
    Assert.assertEquals("Unexpected return value", 0, get(v0));
  }

  @Test
  public void verify_create_poly_invalid_terms_2() {
    Label terms = wordData(-9,5,18,1,4,5,0,1,-12,1,5,5,0,-1);
    run("create_polynomial", terms);
    Assert.assertEquals("Unexpected return value", 0, get(v0));
  }

  @Test
  public void verify_sort_poly_simple() {
    Label terms = wordData(2,5,24,1,3,7,0,-1);
    run("instructors_hw5_sort_poly_test", terms);
    int poly_addr = getWord(get(v0));
    int[] expected = new int[]{ 3,7,2,5,24,1 };
    int i = 0;
    while(poly_addr != 0) {
      Assert.assertEquals("Unexpected coefficent", expected[i++], getWord(poly_addr));
      Assert.assertEquals("Unexpected exponent", expected[i++], getWord(poly_addr+4));
      poly_addr = getWord(poly_addr+8);
    }
  }

  @Test
  public void verify_sort_poly_long() {
    int [] init = new int[100];
    int k = 50;
    for(int x = 0; x < 50; x++) {
      init[x] = k--;
    }
    for(int x = 50; x < 98; x++) {
      init[x] = x+1;
    }
    init[98] = 0;
    init[99] = -1;
    Label terms = wordData(init);
    run("instructors_hw5_sort_poly_test", terms);
    int poly_addr = getWord(get(v0));
    int[] expected = new int[98];
    k = 97;
    for(int x = 0; x < 48; x=x+2) {
      expected[x] = k;
      expected[x+1] = k+1;
      k = k-2;
    }

    k = 50;
    for(int x = 48; x < 98; x=x+2) {
      expected[x] = k--;
      expected[x+1] = k--;
    }
    int i = 0;
    while(poly_addr != 0) {
      Assert.assertEquals("Unexpected coefficent", expected[i++], getWord(poly_addr));
      Assert.assertEquals("Unexpected exponent", expected[i++], getWord(poly_addr+4));
      poly_addr = getWord(poly_addr+8);
    }
  }

  @Test
  public void verify_sort_poly_dup_short_1() {
    Label terms = wordData(2,5,18,1,3,7,6,1,0,-1);
    run("instructors_hw5_sort_poly_test", terms);
    int[] expected = new int[]{ 3,7,2,5,24,1 };
    int i = 0;
    Assert.assertEquals("Unexpected term count", 3, getWord(get(v0)+4));
    int poly_addr = getWord(get(v0));
    while(poly_addr != 0) {
      Assert.assertEquals("Unexpected coefficent", expected[i++], getWord(poly_addr));
      Assert.assertEquals("Unexpected exponent", expected[i++], getWord(poly_addr+4));
      poly_addr = getWord(poly_addr+8);
    }
  }

  @Test
  public void verify_sort_poly_dup_short_2() {
    Label terms = wordData(-9,5,18,1,4,5,5,5,0,-1);
    run("instructors_hw5_sort_poly_test", terms);
    int[] expected = new int[]{ 18,1};
    int i = 0;
    Assert.assertEquals("Unexpected term count", 1, getWord(get(v0)+4));
    int poly_addr = getWord(get(v0));
    while(poly_addr != 0) {
      Assert.assertEquals("Unexpected coefficent", expected[i++], getWord(poly_addr));
      Assert.assertEquals("Unexpected exponent", expected[i++], getWord(poly_addr+4));
      poly_addr = getWord(poly_addr+8);
    }
  }

  @Test
  public void verify_add_poly_1() {
    Label terms1 = wordData(21,3,12,4,9,2,7,1,0,-1);
    Label terms2 = wordData(3,4,8,3,21,2,33,1,25,0,0,-1);
    run("instructors_hw5_add_poly_test", terms1, terms2);
    Assert.assertEquals("Unexpected term count", 5, getWord(get(v0)+4));
    int[] expected = new int[] {15,4,29,3,30,2,40,1,25,0};
    int poly_addr = getWord(get(v0));
    int i = 0;
    while(poly_addr != 0) {
      Assert.assertEquals("Unexpected coefficent", expected[i++], getWord(poly_addr));
      Assert.assertEquals("Unexpected exponent", expected[i++], getWord(poly_addr+4));
      poly_addr = getWord(poly_addr+8);
    }
  }

  @Test
  public void verify_add_poly_2() {
    Label terms1 = wordData(-30,2,21,3,12,4,9,2,0,-1);
    Label terms2 = wordData(3,4,8,4,21,2,33,1,25,0,0,-1);
    run("instructors_hw5_add_poly_test", terms1, terms2);
    Assert.assertEquals("Unexpected term count", 4, getWord(get(v0)+4));
    int[] expected = new int[] {23,4,21,3,33,1,25,0};
    int poly_addr = getWord(get(v0));
    int i = 0;
    while(poly_addr != 0) {
      Assert.assertEquals("Unexpected coefficent", expected[i++], getWord(poly_addr));
      Assert.assertEquals("Unexpected exponent", expected[i++], getWord(poly_addr+4));
      poly_addr = getWord(poly_addr+8);
    }
  }
  @Test
  public void verify_add_poly_3() {
    Label terms1 = wordData(21,3,12,4,9,2,7,4,0,-1);
    Label terms2 = wordData(3,4,8,4,-21,2,-33,1,25,0,0,-1);
    run("instructors_hw5_add_poly_test", terms1, terms2);
    Assert.assertEquals("Unexpected term count", 5, getWord(get(v0)+4));
    int[] expected = new int[] {30,4,21,3,-12,2,-33,1,25,0};
    int poly_addr = getWord(get(v0));
    int i = 0;
    while(poly_addr != 0) {
      Assert.assertEquals("Unexpected coefficent", expected[i++], getWord(poly_addr));
      Assert.assertEquals("Unexpected exponent", expected[i++], getWord(poly_addr+4));
      poly_addr = getWord(poly_addr+8);
    }
  }

  @Test
  public void verify_add_poly_4() {
    Label terms1 = wordData(21,3,12,4,9,2,7,4,0,-1);
    Label terms2 = wordData(2,-1,0,-1);
    run("instructors_hw5_add_poly_test", terms1, terms2);
    Assert.assertEquals("Unexpected term count", 3, getWord(get(v0)+4));
    int[] expected = new int[] {19,4,21,3,9,2};
    int poly_addr = getWord(get(v0));
    int i = 0;
    while(poly_addr != 0) {
      Assert.assertEquals("Unexpected coefficent", expected[i++], getWord(poly_addr));
      Assert.assertEquals("Unexpected exponent", expected[i++], getWord(poly_addr+4));
      poly_addr = getWord(poly_addr+8);
    }
  }

  @Test
  public void verify_add_poly_5() {
    Label terms1 = wordData(21,3,12,4,9,2,7,4,0,-1);
    Label terms2 = wordData(2,-1,0,-1);
    run("instructors_hw5_add_poly_test", terms2, terms1);
    Assert.assertEquals("Unexpected term count", 3, getWord(get(v0)+4));
    int[] expected = new int[] {19,4,21,3,9,2};
    int poly_addr = getWord(get(v0));
    int i = 0;
    while(poly_addr != 0) {
      Assert.assertEquals("Unexpected coefficent", expected[i++], getWord(poly_addr));
      Assert.assertEquals("Unexpected exponent", expected[i++], getWord(poly_addr+4));
      poly_addr = getWord(poly_addr+8);
    }
  }

  @Test
  public void verify_add_poly_6() {
    Label terms1 = wordData(2,-1,0,-1);
    Label terms2 = wordData(2,-1,0,-1);
    run("instructors_hw5_add_poly_test", terms1, terms2);
    Assert.assertEquals("Unexpected term count", 0, getWord(get(v0)+4));
    int poly_addr = getWord(get(v0));
    Assert.assertEquals("Unexpected non empty polynomial", 0, poly_addr);
  }

  @Test
  public void verify_mult_poly_1() {
    Label terms1 = wordData(21,3,12,4,9,2,7,1,0,-1);
    Label terms2 = wordData(21,2,8,3,4,4,33,1,25,0,0,-1);
    run("instructors_hw5_mult_poly_test", terms1, terms2);
    Assert.assertEquals("Unexpected term count", 8, getWord(get(v0)+4));
    int[] expected = new int[] {48,8,180,7,456,6,937,5,1238,4,969,3,456,2,175,1};
    int poly_addr = getWord(get(v0));
    int i = 0;
    while(poly_addr != 0) {
      Assert.assertEquals("Unexpected coefficent", expected[i++], getWord(poly_addr));
      Assert.assertEquals("Unexpected exponent", expected[i++], getWord(poly_addr+4));
      poly_addr = getWord(poly_addr+8);
    }
  }

  @Test
  public void verify_mult_poly_2() {
    Label terms1 = wordData(33,3,12,4,9,2,3,1,0,-1);
    Label terms2 = wordData(-12,2,4,4,32,1,25,0,0,-1);
    run("instructors_hw5_mult_poly_test", terms1, terms2);
    Assert.assertEquals("Unexpected term count", 7, getWord(get(v0)+4));
    int[] expected = new int[] {48,8,132,7,-108,6,1248,4,1077,3,321,2,75,1};
    int poly_addr = getWord(get(v0));
    int i = 0;
    while(poly_addr != 0) {
      Assert.assertEquals("Unexpected coefficent", expected[i++], getWord(poly_addr));
      Assert.assertEquals("Unexpected exponent", expected[i++], getWord(poly_addr+4));
      poly_addr = getWord(poly_addr+8);
    }
  }

  @Test
  public void verify_mult_poly_empty_1() {
    Label terms1 = wordData(33,3,12,4,9,2,3,1,0,-1);
    Label terms2 = wordData(0,2,0,-1);
    run("instructors_hw5_mult_poly_test", terms1, terms2);
    Assert.assertEquals("Unexpected term count", 0, getWord(get(v0)+4));
    int poly_addr = getWord(get(v0));
    Assert.assertEquals("Unexpected non empty polynomial", 0, poly_addr);
  }

  @Test
  public void verify_mult_poly_empty_2() {
    Label terms1 = wordData(33,3,12,4,9,2,3,1,0,-1);
    Label terms2 = wordData(0,2,0,-1);
    run("instructors_hw5_mult_poly_test", terms2, terms1);
    Assert.assertEquals("Unexpected term count", 0, getWord(get(v0)+4));
    int poly_addr = getWord(get(v0));
    Assert.assertEquals("Unexpected non empty polynomial", 0, poly_addr);
  }
}
