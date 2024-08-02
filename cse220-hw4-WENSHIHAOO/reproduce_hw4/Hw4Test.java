import org.junit.*;

import static edu.gvsu.mipsunit.munit.MUnit.Register.*;
import static edu.gvsu.mipsunit.munit.MUnit.*;
import static edu.gvsu.mipsunit.munit.MARSSimulator.*;
import java.io.*;
import java.util.Arrays;
import org.junit.rules.Timeout;
import java.util.concurrent.TimeUnit;

public class Hw4Test {

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
  public Timeout timeout = new Timeout(5000, TimeUnit.MILLISECONDS);

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
  public void verify_create_person_success_1() {
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,0,0);
    String node_data = "@#$(_@#$(_@#$(_@#$(_@#$(_@#$(_@#$(_@#$(_@#$(_@#$(_@#$(_@#$(_";
    Label nodes = byteData(node_data.getBytes());
    Label edges = wordData(218,121,222,33211,211,218,121,222,33211,211,218,121,222,33211,211,218,121,222,33211,211,218,121,222,33211,211,218,121,222,33211,211);
    run("create_person", ntwrk_info);
    Assert.assertEquals(nodes.address(), get(v0));
    Assert.assertEquals(1, getWord(ntwrk_info.address()+16));
    Assert.assertEquals(3, Arrays.stream(getWords(nodes.address(),3)).filter(val-> val == 0).count());
  }

  @Test
  public void verify_create_person_success_2() {
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,3,0);
    String node_data = "@#$(_@#$(_@#$(_@#$(_@#$(_@#$(_@#$(_@#$(_@#$(_@#$(_@#$(_@#$(_";
    Label nodes = byteData(node_data.getBytes());
    Label edges = wordData(218,121,222,33211,211,218,121,222,33211,211,218,121,222,33211,211,218,121,222,33211,211,218,121,222,33211,211,218,121,222,33211,211);
    run("create_person", ntwrk_info);
    Assert.assertEquals(nodes.address()+36, get(v0));
    Assert.assertEquals(4, getWord(ntwrk_info.address()+16));
    Assert.assertEquals(3, Arrays.stream(getWords(nodes.address()+36,3)).filter(val-> val == 0).count());
  }

  @Test
  public void verify_create_person_success_3() {
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,4,0);
    String node_data = "@#$(_@#$(_@#$(_@#$(_@#$(_@#$(_@#$(_@#$(_@#$(_@#$(_@#$(_@#$(_";
    Label nodes = byteData(node_data.getBytes());
    Label edges = wordData(218,121,222,33211,211,218,121,222,33211,211,218,121,222,33211,211,218,121,222,33211,211,218,121,222,33211,211,218,121,222,33211,211);
    run("create_person", ntwrk_info);
    Assert.assertEquals(nodes.address()+48, get(v0));
    Assert.assertEquals(5, getWord(ntwrk_info.address()+16));
    Assert.assertEquals(3, Arrays.stream(getWords(nodes.address()+48,3)).filter(val-> val == 0).count());
  }

  @Test
  public void verify_create_person_fail() {
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,0);
    String node_data = "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@";
    Label nodes = byteData(node_data.getBytes());
    Label edges = wordData(218,121,222,33211,211,218,121,222,33211,211,218,121,222,33211,211,218,121,222,33211,211,218,121,222,33211,211,218,121,222,33211,211);
    run("create_person", ntwrk_info);
    Assert.assertEquals(-1, get(v0));
    Assert.assertEquals(5, getWord(ntwrk_info.address()+16));
    byte[] expected = node_data.getBytes();
    byte[] actual = getBytes(nodes, 0, 60);
    for(int i = 0; i < 60; i++) {
      Assert.assertEquals(expected[i], actual[i]);
    }
  }


  @Test
  public void verify_add_person_property_success_1() {
    Label prop_val = asciiData(true, "Haanda");
    Label prop_name = asciiData(true, "NAME");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,2,0);
    String node_data = "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    run("add_person_property", ntwrk_info, nodes, prop_name, prop_val);
    Assert.assertEquals(1, get(v0));
    Assert.assertEquals("Haanda", getString(nodes.address()));
  }

  @Test
  public void verify_add_person_property_success_2() {
    Label prop_val = asciiData(true, "02022000");
    Label prop_name = asciiData(true, "DOB");
    Label prop_name_1 = asciiData(true, "NAME");
    Label prop_val_1 = asciiData(true, "alibaba");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,2,0);
    String node_data = "Haanda\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    run("add_person_property", ntwrk_info, nodes, prop_name, prop_val);
    Assert.assertEquals(0, get(v0));
    Assert.assertEquals("Haanda", getString(nodes.address()));
    run("add_person_property", ntwrk_info, nodes.address()+12, prop_name_1, prop_val_1);
    Assert.assertEquals(1, get(v0));
    Assert.assertEquals("alibaba", getString(nodes.address()+12));
  }

  @Test
  public void verify_add_person_property_success_3() {
    Label prop_val = asciiData(true, "02022000");
    Label prop_name = asciiData(true, "DOB");
    Label prop_name_1 = asciiData(true, "NAME");
    Label prop_val_1 = asciiData(true, "Bhola Zhang");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,0);
    String node_data = "Haanda\u0000\u0000\u0000\u0000\u0000\u0000lat\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Jali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    run("add_person_property", ntwrk_info, nodes, prop_name, prop_val);
    Assert.assertEquals(0, get(v0));
    Assert.assertEquals("Haanda", getString(nodes.address()));
    Assert.assertEquals("lat", getString(nodes.address()+12));
    Assert.assertEquals("Jali", getString(nodes.address()+24));
    Assert.assertEquals("Hulo", getString(nodes.address()+36));
    run("add_person_property", ntwrk_info, nodes.address()+48, prop_name_1, prop_val_1);
    Assert.assertEquals("Unexpected return", 1, get(v0));
    Assert.assertEquals("Expected to add person name but did not", "Bhola Zhang", getString(nodes.address()+48));
  }

  @Test
  public void verify_add_person_property_person_notexists_1() {
    Label prop_val = asciiData(true, "02022000");
    Label prop_name = asciiData(true, "DOB");
    Label prop_name_1 = asciiData(true, "NAME");
    Label prop_val_1 = asciiData(true, "Bhola Zhang");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,0);
    String node_data = "Haanda\u0000\u0000\u0000\u0000\u0000\u0000lat\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Jali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    set(v0, 9911);
    run("add_person_property", ntwrk_info, nodes, prop_name, prop_val);
    Assert.assertEquals(0, get(v0));
    Assert.assertEquals("Haanda", getString(nodes.address()));
    Assert.assertEquals("lat", getString(nodes.address()+12));
    Assert.assertEquals("Jali", getString(nodes.address()+24));
    Assert.assertEquals("Hulo", getString(nodes.address()+36));
    set(v0, 9912);
    run("add_person_property", ntwrk_info, nodes.address()+60, prop_name_1, prop_val_1);
    Assert.assertEquals("Person does not exist but name added!", 0, get(v0));
  }

  @Test
  public void verify_add_person_property_person_notexists_2() {
    Label prop_val = asciiData(true, "02022000");
    Label prop_name = asciiData(true, "DOB");
    Label prop_name_1 = asciiData(true, "NAME");
    Label prop_val_1 = asciiData(true, "Bhola Zhang");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,0);
    String node_data = "Haanda\u0000\u0000\u0000\u0000\u0000\u0000lat\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Jali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    set(v0, 2828);
    run("add_person_property", ntwrk_info, nodes, prop_name, prop_val);
    Assert.assertEquals(0, get(v0));
    Assert.assertEquals("Haanda", getString(nodes.address()));
    Assert.assertEquals("lat", getString(nodes.address()+12));
    Assert.assertEquals("Jali", getString(nodes.address()+24));
    Assert.assertEquals("Hulo", getString(nodes.address()+36));
    set(v0, 2828);
    run("add_person_property", ntwrk_info, nodes.address()+1982, prop_name_1, prop_val_1);
    Assert.assertEquals("Person does not exist but name added!", 0, get(v0));
  }

  @Test
  public void verify_add_person_property_person_notexists_3() {
    Label prop_name = asciiData(true, "NAME");
    Label prop_val = asciiData(true, "kuku");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,0);
    String node_data = "Haanda\u0000\u0000\u0000\u0000\u0000\u0000lat\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Jali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    set(v0, 19190);
    run("add_person_property", ntwrk_info, prop_name, prop_name, prop_val);
    Assert.assertEquals("Person node not passed as argument but name added!", 0, get(v0));
  }

  @Test
  public void verify_add_person_property_badname_1() {
    Label prop_name = asciiData(true, "NAME");
    Label prop_val = asciiData(true, "Oshleen Harris");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,0);
    String node_data = "Haanda\u0000\u0000\u0000\u0000\u0000\u0000lat\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Jali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    set(v0, 13267);
    run("add_person_property", ntwrk_info, nodes, prop_name, prop_val);
    Assert.assertEquals("Name too long but added name!", 0, get(v0));
  }

  @Test
  public void verify_add_person_property_dupname_1() {
    Label prop_name = asciiData(true, "NAME");
    Label prop_val = asciiData(true, "Haanda");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,0);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    String node_data = "Haanda\u0000\u0000\u0000\u0000\u0000\u0000lat\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Jali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    set(v0, 2181);
    run("add_person_property", ntwrk_info, nodes, prop_name, prop_val);
    Assert.assertEquals("Name exists but added!", 0, get(v0));
    Assert.assertEquals("Haanda", getString(nodes.address()));
    Assert.assertEquals("lat", getString(nodes.address()+12));
    Assert.assertEquals("Jali", getString(nodes.address()+24));
    Assert.assertEquals("Hulo", getString(nodes.address()+36));
  }

  @Test
  public void verify_add_person_property_dupname_2() {
    Label prop_name = asciiData(true, "NAME");
    Label prop_val = asciiData(true, "lat");
    Label prop_name_1 = asciiData(true, "DOB");
    Label prop_val_1 = asciiData(true, "0901");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,0);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    String node_data = "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000lat\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Jali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    set(v0, 37827);
    run("add_person_property", ntwrk_info, nodes, prop_name_1, prop_val_1);
    run("add_person_property", ntwrk_info, nodes.address()+12, prop_name, prop_val);
    Assert.assertEquals("Duplicate name or wrong property added", 0, get(v0));
    Assert.assertEquals("lat", getString(nodes.address()+12));
    Assert.assertEquals("Jali", getString(nodes.address()+24));
    Assert.assertEquals("Hulo", getString(nodes.address()+36));
  }

  @Test
  public void verify_add_person_property_dupname_3() {
    Label prop_name = asciiData(true, "NAME");
    Label prop_val = asciiData(true, "Kali");
    Label prop_name_1 = asciiData(true, "DOB");
    Label prop_val_1 = asciiData(true, "0901");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,0);
    String node_data = "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000lat\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    set(v0, 2811);
    run("add_person_property", ntwrk_info, nodes, prop_name_1, prop_val_1);
    Assert.assertEquals("Property is not NAME but still added!", 0, get(v0));
    set(v0, 2812);
    run("add_person_property", ntwrk_info, nodes.address()+36, prop_name, prop_val);
    Assert.assertEquals("Name exists but was still added!", 0, get(v0));
    Assert.assertEquals("lat", getString(nodes.address()+12));
    Assert.assertEquals("Kali", getString(nodes.address()+24));
    Assert.assertEquals("Hulo", getString(nodes.address()+36));
  }

  @Test
  public void verify_add_person_property_dupname_4() {
    Label prop_name = asciiData(true, "NAME");
    Label prop_val = asciiData(true, "Hulo");
    Label prop_name_1 = asciiData(true, "DOB");
    Label prop_val_1 = asciiData(true, "0901");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,0);
    String node_data = "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000lat\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    set(v0, 11010);
    run("add_person_property", ntwrk_info, nodes, prop_name_1, prop_val_1);
    Assert.assertEquals("Bad property!", 0, get(v0));
    set(v0, 2818);
    run("add_person_property", ntwrk_info, nodes.address()+48, prop_name, prop_val);
    Assert.assertEquals("Hulu exists and yet it was added!", 0, get(v0));
    Assert.assertEquals("lat", getString(nodes.address()+12));
    Assert.assertEquals("Kali", getString(nodes.address()+24));
    Assert.assertEquals("Hulo", getString(nodes.address()+36));
  }

  @Test
  public void verify_get_person_success_1() {
    Label name = asciiData(true, "Kimchee");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,0);
    String node_data = "Yolanda\u0000\u0000\u0000\u0000\u0000lat\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kimchee\u0000\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    run("get_person", ntwrk_info, name);
    Assert.assertEquals("Kimchee", getString(get(v0)));
  }

  @Test
  public void verify_get_person_success_2() {
    Label name = asciiData(true, "lat");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,0);
    String node_data = "Yolanda\u0000\u0000\u0000\u0000\u0000lat\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kimchee\u0000\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    run("get_person", ntwrk_info, name);
    Assert.assertEquals("lat", getString(get(v0)));
  }

  @Test
  public void verify_get_person_success_3() {
    Label name = asciiData(true, "Kali");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,0);
    String node_data = "Yolanda\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kimchee\u0000\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    run("get_person", ntwrk_info, name);
    Assert.assertEquals("Expected to add Kali", "Kali", getString(get(v0)));
  }

  @Test
  public void verify_get_person_fail_1() {
    Label name = asciiData(true, "lat");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,0);
    String node_data = "Yolanda\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kimchee\u0000\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    set(v0, -12);
    run("get_person", ntwrk_info, name);
    Assert.assertEquals("Did not expect to find lat", 0, get(v0));
  }

  @Test
  public void verify_get_person_fail_2() {
    Label name = asciiData(true, "Kim");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,0);
    String node_data = "Yolanda\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kimchee\u0000\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    set(v0, -11);
    run("get_person", ntwrk_info, name);
    Assert.assertEquals("Did not expect to find Kim", 0, get(v0));
  }

  @Test
  public void verify_get_person_fail_3() {
    Label name = asciiData(true, "Keshto");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,0);
    String node_data = "Yolanda\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kimchee\u0000\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    set(v0, 12);
    run("get_person", ntwrk_info, name);
    Assert.assertEquals("Did not expect to find Keshto!", 0, get(v0));
  }

  @Test
  public void verify_add_relation_success_1() {
    Label name1 = asciiData(true, "Yolanda");
    Label name2 = asciiData(true, "Keblaa");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,0);
    String node_data = "Yolanda\u0000\u0000\u0000\u0000\u0000Keblaa\u0000\u0000\u0000\u0000\u0000\u0000Kali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kimchee\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    run("add_relation", ntwrk_info, name1, name2);
    try {
      Assert.assertEquals("Yolanda", getString(getWord(edges.address())));
    }
    catch(AssertionError e) {
      Assert.assertEquals("Yolanda", getString(getWord(edges.address()+4)));
      Assert.assertEquals("Keblaa", getString(getWord(edges.address())));
    }
    Assert.assertEquals("Keblaa", getString(getWord(edges.address()+4)));
    Assert.assertEquals("Unexpected relation type", 0, getWord(edges.address()+8));
    Assert.assertEquals("Unexpected edge count", 1, getWord(ntwrk_info.address()+20));
  }

  @Test
  public void verify_add_relation_success_2() {
    // make network struct
    Label name1 = asciiData(true, "Keblaa");
    Label name2 = asciiData(true, "Hulo");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,2);
    String node_data = "Yolanda\u0000\u0000\u0000\u0000\u0000Keblaa\u0000\u0000\u0000\u0000\u0000\u0000Kali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kimchee\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    run("add_relation", ntwrk_info, name1, name2);
    try {
      Assert.assertEquals("Keblaa", getString(getWord(edges.address()+24)));
    }
    catch(AssertionError e) {
      Assert.assertEquals("Hulo", getString(getWord(edges.address()+28)));
      Assert.assertEquals("Keblaa", getString(getWord(edges.address()+24)));
    }
    Assert.assertEquals("Hulo", getString(getWord(edges.address()+28)));
    Assert.assertEquals("Unexpected relation type", 0, getWord(edges.address()+32));
    Assert.assertEquals("Unexpected edge count", 3, getWord(ntwrk_info.address()+20));
  }

  @Test
  public void verify_add_relation_success_3() {
    // make network struct
    Label name1 = asciiData(true, "Kimchee");
    Label name2 = asciiData(true, "Kali");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,4);
    String node_data = "Yolanda\u0000\u0000\u0000\u0000\u0000Keblaa\u0000\u0000\u0000\u0000\u0000\u0000Kali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kimchee\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    run("add_relation", ntwrk_info, name1, name2);
    try {
      Assert.assertEquals("Kimchee", getString(getWord(edges.address()+48)));
    }
    catch(AssertionError e) {
      Assert.assertEquals("Kimchee", getString(getWord(edges.address()+52)));
      Assert.assertEquals("Kali", getString(getWord(edges.address()+48)));
    }
    Assert.assertEquals("Kali", getString(getWord(edges.address()+52)));
    Assert.assertEquals("Unexpected relation type", 0, getWord(edges.address()+56));
    Assert.assertEquals("Unexpected edge count", 5, getWord(ntwrk_info.address()+20));
  }

  @Test
  public void verify_add_relation_fail_noperson_1() {
    // make network struct
    Label name1 = asciiData(true, "Kimchee");
    Label name2 = asciiData(true, "Kalindi");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,5);
    String node_data = "Yolanda\u0000\u0000\u0000\u0000\u0000Keblaa\u0000\u0000\u0000\u0000\u0000\u0000Kali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kimchee\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    set(v0, 12);
    run("add_relation", ntwrk_info, name1, name2);
    Assert.assertEquals("Unexpectedly related non person", 0, get(v0));
    for(int i = 0; i < 120; i = i + 4) {
      Assert.assertEquals("Unexpected changed edges", 0, getWord(edges.address()+i));
    }
  }

  @Test
  public void verify_add_relation_fail_noperson_2() {
    // make network struct
    Label name1 = asciiData(true, "Amla");
    Label name2 = asciiData(true, "Yolanda");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,5);
    String node_data = "Yolanda\u0000\u0000\u0000\u0000\u0000Keblaa\u0000\u0000\u0000\u0000\u0000\u0000Kali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kimchee\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    set(v0, 12);
    run("add_relation", ntwrk_info, name1, name2);
    Assert.assertEquals("Unexpectedly related non person", 0, get(v0));
    for(int i = 0; i < 120; i = i + 4) {
      Assert.assertEquals("Unexpected changed edges", 0, getWord(edges.address()+i));
    }
  }

  @Test
  public void verify_add_relation_fail_limit_1() {
    // make network struct
    Label name1 = asciiData(true, "Keblaa");
    Label name2 = asciiData(true, "Yolanda");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,10);
    String node_data = "Yolanda\u0000\u0000\u0000\u0000\u0000Keblaa\u0000\u0000\u0000\u0000\u0000\u0000Kali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kimchee\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    set(v0, 12);
    run("add_relation", ntwrk_info, name1, name2);
    Assert.assertEquals("Unexpectedly added edge beyond capacity", 0, get(v0));
    for(int i = 0; i < 120; i = i + 4) {
      Assert.assertEquals("Unexpectedly added edge beyond capacity", 0, getWord(edges.address()+i));
    }
  }

  @Test
  public void verify_add_relation_fail_limit_2() {
    // make network struct
    Label name1 = asciiData(true, "Keblaa");
    Label name2 = asciiData(true, "Yolanda");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,20);
    String node_data = "Yolanda\u0000\u0000\u0000\u0000\u0000Keblaa\u0000\u0000\u0000\u0000\u0000\u0000Kali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kimchee\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    set(v0, 12);
    run("add_relation", ntwrk_info, name1, name2);
    Assert.assertEquals("Unexpectedly added edge beyond capacity", 0, get(v0));
    for(int i = 0; i < 120; i = i + 4) {
      Assert.assertEquals("Unexpectedly changed edge beyond capacity", 0, getWord(edges.address()+i));
    }
  }

  @Test
  public void verify_add_relation_fail_relexists_1() {
    // make network struct
    Label name1 = asciiData(true, "Keblaa");
    Label name2 = asciiData(true, "Yolanda");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,3);
    String node_data = "Yolanda\u0000\u0000\u0000\u0000\u0000Keblaa\u0000\u0000\u0000\u0000\u0000\u0000Kali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kimchee\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    set(v0, 12);
    run("add_relation", ntwrk_info, name1, name2);
    Assert.assertEquals("Unexpected return value", 1, get(v0));
    int[] expected = new int[30];
    for(int i = 0; i < 120; i = i + 4) {
      expected[i/4] = getWord(edges.address()+i);
    }
    run("add_relation", ntwrk_info, name1, name2);
    for(int i = 0; i < 30; i++) {
      Assert.assertEquals("Unexpectly added existing edge", expected[i], getWord(edges.address()+(i*4)));
    }
  }

  @Test
  public void verify_add_relation_fail_relexists_2() {
    // make network struct
    Label name1 = asciiData(true, "Kali");
    Label name2 = asciiData(true, "Kimchee");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,3);
    String node_data = "Yolanda\u0000\u0000\u0000\u0000\u0000Keblaa\u0000\u0000\u0000\u0000\u0000\u0000Kali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kimchee\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    set(v0, 12);
    run("add_relation", ntwrk_info, name1, name2);
    Assert.assertEquals("Unexpected return value", 1, get(v0));
    int[] expected = new int[30];
    for(int i = 0; i < 120; i = i + 4) {
      expected[i/4] = getWord(edges.address()+i);
    }
    run("add_relation", ntwrk_info, name2, name1);
    for(int i = 0; i < 30; i++) {
      Assert.assertEquals("Unexpectly added reverse edge", expected[i], getWord(edges.address()+(i*4)));
    }
  }

  @Test
  public void verify_add_relation_fail_samerel_1() {
    // make network struct
    Label name1 = asciiData(true, "Kali");
    Label name2 = asciiData(true, "Kali");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,3);
    String node_data = "Yolanda\u0000\u0000\u0000\u0000\u0000Keblaa\u0000\u0000\u0000\u0000\u0000\u0000Kali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kimchee\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    set(v0, 12);
    run("add_relation", ntwrk_info, name1, name2);
    Assert.assertEquals("Unexpectly relating a person with themselves", 0, get(v0));
    for(int i = 0; i < 30; i++) {
      Assert.assertEquals("Unexpectly changed edges", 0, getWord(edges.address()+(i*4)));
    }
  }

  @Test
  public void verify_add_relation_fail_samerel_2() {
    // make network struct
    Label name1 = asciiData(true, "Hulo");
    Label name2 = asciiData(true, "Hulo");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,3);
    String node_data = "Yolanda\u0000\u0000\u0000\u0000\u0000Keblaa\u0000\u0000\u0000\u0000\u0000\u0000Kali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kimchee\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    set(v0, 12);
    run("add_relation", ntwrk_info, name1, name2);
    Assert.assertEquals("Unexpectly relating a person with themselves", 0, get(v0));
    for(int i = 0; i < 30; i++) {
      Assert.assertEquals("Unexpectly changed edges", 0, getWord(edges.address()+(i*4)));
    }
  }

  @Test
  public void verify_add_relation_property_frnd_success_1() {
    Label rel_prop = asciiData(true, "FRIEND");
    Label name1 = asciiData(true, "Yolanda");
    Label name2 = asciiData(true, "Keblaa");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,4);
    String node_data = "Yolanda\u0000\u0000\u0000\u0000\u0000Keblaa\u0000\u0000\u0000\u0000\u0000\u0000Kali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kimchee\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    // dummy method to get edge addresses
    run("create_person", ntwrk_info);
    Label edges = wordData(nodes.address(),nodes.address()+12,0,nodes.address()+48,nodes.address()+36,0,nodes.address()+12,nodes.address()+48,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("instructors_test_add_relation_prop", ntwrk_info, name1, name2, rel_prop);
    Assert.assertEquals("Unexpected return value", 1, get(v0));
    Assert.assertEquals("Unexpected friend value", 1, getWord(edges.address()+8));
  }

  @Test
  public void verify_add_relation_property_frnd_success_2() {
    Label rel_prop = asciiData(true, "FRIEND");
    Label name1 = asciiData(true, "Hulo");
    Label name2 = asciiData(true, "Keblaa");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,4);
    String node_data = "Yolanda\u0000\u0000\u0000\u0000\u0000Keblaa\u0000\u0000\u0000\u0000\u0000\u0000Kali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kimchee\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    // dummy method to get edge addresses
    run("create_person", ntwrk_info);
    Label edges = wordData(nodes.address(),nodes.address()+12,0,nodes.address()+48,nodes.address()+36,0,nodes.address()+12,nodes.address()+36,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("instructors_test_add_relation_prop", ntwrk_info, name1, name2, rel_prop);
    Assert.assertEquals("Unexpected return value", 1, get(v0));
    Assert.assertEquals("Unexpected friend value", 1, getWord(edges.address()+32));
  }

  @Test
  public void verify_add_relation_property_frnd_success_3() {
    Label rel_prop = asciiData(true, "FRIEND");
    Label name1 = asciiData(true, "Kali");
    Label name2 = asciiData(true, "Kimchee");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,4);
    String node_data = "Yolanda\u0000\u0000\u0000\u0000\u0000Keblaa\u0000\u0000\u0000\u0000\u0000\u0000Kali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kimchee\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    // dummy method to get edge addresses
    run("create_person", ntwrk_info);
    Label edges = wordData(nodes.address(),nodes.address()+12,0,nodes.address()+48,nodes.address()+36,0,nodes.address()+12,nodes.address()+36,0,nodes.address()+24,nodes.address()+48,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("instructors_test_add_relation_prop", ntwrk_info, name1, name2, rel_prop);
    Assert.assertEquals("Unexpected return value", 1, get(v0));
    Assert.assertEquals("Unexpected friend value", 1, getWord(edges.address()+44));
  }

  @Test
  public void verify_add_relation_property_frnd_no_rel_fail_1() {
    Label rel_prop = asciiData(true, "FRIEND");
    Label name1 = asciiData(true, "Yolanda");
    Label name2 = asciiData(true, "Kimchee");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,4);
    String node_data = "Yolanda\u0000\u0000\u0000\u0000\u0000Keblaa\u0000\u0000\u0000\u0000\u0000\u0000Kali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kimchee\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    // dummy method to get edge addresses
    run("create_person", ntwrk_info);
    Label edges = wordData(nodes.address(),nodes.address()+12,0,nodes.address()+48,nodes.address()+36,0,nodes.address()+12,nodes.address()+36,0,nodes.address()+24,nodes.address()+48,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    set(v0, 281);
    run("instructors_test_add_relation_prop", ntwrk_info, name1, name2, rel_prop);
    Assert.assertEquals("Unexpected return value", 0, get(v0));
  }

  @Test
  public void verify_add_relation_property_frnd_no_name_fail_1() {
    Label rel_prop = asciiData(true, "FRIEND");
    Label name1 = asciiData(true, "Yolanda");
    Label name2 = asciiData(true, "rukmini");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,4);
    String node_data = "Yolanda\u0000\u0000\u0000\u0000\u0000Keblaa\u0000\u0000\u0000\u0000\u0000\u0000Kali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kimchee\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    // dummy method to get edge addresses
    run("create_person", ntwrk_info);
    Label edges = wordData(nodes.address(),nodes.address()+12,0,nodes.address()+48,nodes.address()+36,0,nodes.address()+12,nodes.address()+36,0,nodes.address()+24,nodes.address()+48,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    set(v0, 281);
    run("instructors_test_add_relation_prop", ntwrk_info, name1, name2, rel_prop);
    Assert.assertEquals("Unexpected return value", 0, get(v0));
  }

  @Test
  public void verify_add_relation_property_frnd_no_name_fail_2() {
    Label rel_prop = asciiData(true, "FRIEND");
    Label name1 = asciiData(true, "Yolanda");
    Label name2 = asciiData(true, "Kebla");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,4);
    String node_data = "Yolanda\u0000\u0000\u0000\u0000\u0000Keblaa\u0000\u0000\u0000\u0000\u0000\u0000Kali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kimchee\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    // dummy method to get edge addresses
    run("create_person", ntwrk_info);
    Label edges = wordData(nodes.address(),nodes.address()+12,0,nodes.address()+48,nodes.address()+36,0,nodes.address()+12,nodes.address()+36,0,nodes.address()+24,nodes.address()+48,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    set(v0, 281);
    run("instructors_test_add_relation_prop", ntwrk_info, name1, name2, rel_prop);
    Assert.assertEquals("Unexpected return value", 0, get(v0));
  }

  @Test
  public void verify_add_relation_property_not_frnd_fail() {
    Label rel_prop = asciiData(true, "FIEND");
    Label name1 = asciiData(true, "Yolanda");
    Label name2 = asciiData(true, "Keblaa");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,4);
    String node_data = "Yolanda\u0000\u0000\u0000\u0000\u0000Keblaa\u0000\u0000\u0000\u0000\u0000\u0000Kali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kimchee\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    // dummy method to get edge addresses
    run("create_person", ntwrk_info);
    Label edges = wordData(nodes.address(),nodes.address()+12,0,nodes.address()+48,nodes.address()+36,0,nodes.address()+12,nodes.address()+36,0,nodes.address()+24,nodes.address()+48,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    set(v0, -11);
    run("instructors_test_add_relation_prop", ntwrk_info, name1, name2, rel_prop);
    Assert.assertEquals("Unexpected return value", 0, get(v0));
  }

  @Test
  public void verify_is_a_distant_friend_true_1() {
    Label name1 = asciiData(true, "Yolanda");
    Label name2 = asciiData(true, "Hulo");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,4);
    String node_data = "Yolanda\u0000\u0000\u0000\u0000\u0000Keblaa\u0000\u0000\u0000\u0000\u0000\u0000Kali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kimchee\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    // dummy method to get edge addresses
    run("create_person", ntwrk_info);
    Label edges = wordData(nodes.address(),nodes.address()+12,1,nodes.address()+48,nodes.address()+36,1,nodes.address()+12,nodes.address()+36,1,nodes.address()+24,nodes.address()+48,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("is_a_distant_friend", ntwrk_info, name1, name2);
    Assert.assertEquals("Unexpected return value", 1, get(v0));
  }

  @Test
  public void verify_is_a_distant_friend_true_2() {
    Label name1 = asciiData(true, "Kali");
    Label name2 = asciiData(true, "Hulo");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,4);
    String node_data = "Yolanda\u0000\u0000\u0000\u0000\u0000Keblaa\u0000\u0000\u0000\u0000\u0000\u0000Kali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kimchee\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    // dummy method to get edge addresses
    run("create_person", ntwrk_info);
    Label edges = wordData(nodes.address(),nodes.address()+36,1,nodes.address()+48,nodes.address()+36,1,nodes.address()+12,nodes.address(),1,nodes.address()+24,nodes.address()+12,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("is_a_distant_friend", ntwrk_info, name1, name2);
    Assert.assertEquals("Unexpected return value", 1, get(v0));
  }

  @Test
  public void verify_is_a_distant_friend_true_3() {
    Label name1 = asciiData(true, "Yolanda");
    Label name2 = asciiData(true, "Kali");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,6);
    String node_data = "Yolanda\u0000\u0000\u0000\u0000\u0000Keblaa\u0000\u0000\u0000\u0000\u0000\u0000Kali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kimchee\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    // dummy method to get edge addresses
    run("create_person", ntwrk_info);
    Label edges = wordData(nodes.address(),nodes.address()+36,1,nodes.address()+48,nodes.address()+36,1,nodes.address()+12,nodes.address(),1,nodes.address()+24,nodes.address()+12,1,nodes.address(),nodes.address()+48,1,nodes.address()+24,nodes.address()+48,1,0,0,0,0,0,0,0,0,0,0,0,0);
    run("is_a_distant_friend", ntwrk_info, name1, name2);
    Assert.assertEquals("Unexpected return value", 1, get(v0));
  }


  @Test
  public void verify_is_a_distant_friend_false_1() {
    Label name1 = asciiData(true, "Kali");
    Label name2 = asciiData(true, "Hulo");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,4);
    String node_data = "Yolanda\u0000\u0000\u0000\u0000\u0000Keblaa\u0000\u0000\u0000\u0000\u0000\u0000Kali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kimchee\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    // dummy method to get edge addresses
    run("create_person", ntwrk_info);
    Label edges = wordData(nodes.address(),nodes.address()+36,1,nodes.address()+48,nodes.address()+36,1,nodes.address()+12,nodes.address(),19,nodes.address()+24,nodes.address()+12,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("is_a_distant_friend", ntwrk_info, name1, name2);
    Assert.assertEquals("Unexpected return value", 0, get(v0));
  }

  @Test
  public void verify_is_a_distant_friend_false_2() {
    Label name1 = asciiData(true, "Kali");
    Label name2 = asciiData(true, "Kimchee");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,3);
    String node_data = "Yolanda\u0000\u0000\u0000\u0000\u0000Keblaa\u0000\u0000\u0000\u0000\u0000\u0000Kali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kimchee\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    // dummy method to get edge addresses
    run("create_person", ntwrk_info);
    Label edges = wordData(nodes.address()+48,nodes.address()+36,1,nodes.address()+12,nodes.address(),1,nodes.address()+24,nodes.address()+12,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("is_a_distant_friend", ntwrk_info, name1, name2);
    Assert.assertEquals("Unexpected return value", 0, get(v0));
  }

  @Test
  public void verify_is_a_distant_friend_false_3() {
    Label name1 = asciiData(true, "Kali");
    Label name2 = asciiData(true, "Kimchee");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,6);
    String node_data = "Yolanda\u0000\u0000\u0000\u0000\u0000Keblaa\u0000\u0000\u0000\u0000\u0000\u0000Kali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kimchee\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    // dummy method to get edge addresses
    run("create_person", ntwrk_info);
    Label edges = wordData(nodes.address(),nodes.address()+36,1,nodes.address()+48,nodes.address()+36,1,nodes.address()+12,nodes.address(),1,nodes.address()+24,nodes.address()+12,1,nodes.address(),nodes.address()+48,1,nodes.address()+24,nodes.address()+48,1,0,0,0,0,0,0,0,0,0,0,0,0);
    run("is_a_distant_friend", ntwrk_info, name1, name2);
    Assert.assertEquals("Unexpected return value", 0, get(v0));
  }

  @Test
  public void verify_is_a_distant_friend_not_empty_impl() {
    Label name1 = asciiData(true, "");
    Label name2 = asciiData(true, "");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,6);
    String node_data = "Yolanda\u0000\u0000\u0000\u0000\u0000Keblaa\u0000\u0000\u0000\u0000\u0000\u0000Kali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kimchee\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    // dummy method to change $v0
    run("create_person", ntwrk_info);
    run("is_a_distant_friend", ntwrk_info, name1, name2);
    Assert.assertEquals("Potential empty impl detected", -1, get(v0));
  }
}
