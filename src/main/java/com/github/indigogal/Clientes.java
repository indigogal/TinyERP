package com.github.indigogal;

import java.util.*;

public class Clientes {
  private HashMap<Integer, Cliente> tabla = new HashMap<>();

  public void precargar() {
    for (int i = 1; i <= 10; i++) {
      String nombre = "Cliente" + i;
      double saldo = i * 100;
      int id = GeneradorID.crearID(nombre, saldo);
      tabla.put(id, new Cliente(id, nombre, saldo));
    }
  }

  public void registrar(String nombre, double saldo) {
    int id = GeneradorID.crearID(nombre, saldo);
    Cliente c = new Cliente(id, nombre, saldo);
    tabla.put(id, c);
    System.out.println("Registrado: " + c);
  }

  public void borrar(int id) {
    if (tabla.remove(id) != null) {
      System.out.println("Cliente con ID " + id + " eliminado.");
    } else {
      System.out.println("No existe un cliente con ID " + id);
    }
  }

  public void listar() {
    List<Cliente> lista = new ArrayList<>(tabla.values());
    lista.sort(Comparator.comparingInt(Cliente::getId));

    Arbol arbol = new Arbol();
    balancear(lista, 0, lista.size() - 1, arbol);

    System.out.println("\n--- PreOrden ---");
    arbol.preOrden(arbol.getRaiz());

    System.out.println("\n--- InOrden ---");
    arbol.inOrden(arbol.getRaiz());

    System.out.println("\n--- PostOrden ---");
    arbol.postOrden(arbol.getRaiz());
  }

  private void balancear(List<Cliente> lista, int ini, int fin, Arbol arbol) {
    if (ini > fin)
      return;
    int medio = (ini + fin) / 2;
    arbol.insertar(lista.get(medio));
    balancear(lista, ini, medio - 1, arbol);
    balancear(lista, medio + 1, fin, arbol);
  }
}
