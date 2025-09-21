package com.github.indigogal;

/**
 * test
 */
public class test {

  public static void main(String[] args) {
    Cliente clt = new Cliente("Indigo", 3500);
    System.out.println((Integer) clt.hashCode());
  }
}
