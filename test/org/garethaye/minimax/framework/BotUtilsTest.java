package org.garethaye.minimax.framework;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class BotUtilsTest {
  List<List<Integer>> list;
  
  @Before
  public void setUp() {
    list = new LinkedList<List<Integer>>();
    List<Integer> row1 = new LinkedList<Integer>();
    row1.addAll(ImmutableList.of(1, 2, 3));
    list.add(row1);
  }
  
  @Test
  public void testClone() {    
    List<List<Integer>> clone = BotUtils.clone(list);
    assertEquals(list, clone);
    clone.remove(0);
    assertFalse(list.equals(clone));
  }
  
  @Test
  public void testIsFull() {
    assertTrue(BotUtils.isFull(list));
    list.get(0).set(0, 0);
    assertFalse(BotUtils.isFull(list));
  }
  
  @Test
  public void testAllEqual() {
    assertTrue(BotUtils.allEqual(new LinkedList<Integer>(), 1));
    assertTrue(BotUtils.allEqual(ImmutableList.of(1, 1, 1), 1));
    assertFalse(BotUtils.allEqual(ImmutableList.of(2, 1, 2), 1));
  }
}
