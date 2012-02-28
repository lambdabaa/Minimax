package org.garethaye.minimax.framework;

import java.util.LinkedList;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

public class BotUtils {
  public static List<List<Integer>> clone(List<List<Integer>> board) {
    List<List<Integer>> list = new LinkedList<List<Integer>>();
    for (List<Integer> row : board) {
      LinkedList<Integer> rowClone = new LinkedList<Integer>();
      rowClone.addAll(row);
      list.add(rowClone);
    }
    
    return list;
  }
  
  public static boolean isFull(List<List<Integer>> board) {
    return !Iterables.any(board, new Predicate<List<Integer>>() {
      @Override
      public boolean apply(List<Integer> row) {
        return row.contains(0);
      }
    });
  }
  
  public static boolean allEqual(List<Integer> list, final int value) {
    return Iterables.all(list, new Predicate<Integer>() {
      @Override
      public boolean apply(Integer elt) {
        return value == elt;
      }
    });
  }
}
