package com.github.indigogal;

import java.util.Objects;

public class GeneradorID {
  private static int contador = 1000; 

  public static int crearID(String nombre, double saldo) {
    return Math.abs(Objects.hash(nombre.toUpperCase(), (int) saldo, System.nanoTime())) % 100000 + contador++;
  }

  public static int crearIDSecuencial() {
    return contador++;
  }
}
