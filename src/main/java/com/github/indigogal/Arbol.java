package com.github.indigogal;

public class Arbol {
  private Nodo raiz;

  public void insertar(Cliente c) {
    raiz = insertarRec(raiz, c);
  }

  private Nodo insertarRec(Nodo nodo, Cliente c) {
    if (nodo == null)
      return new Nodo(c);
    if (c.getId() < nodo.cliente.getId()) {
      nodo.izq = insertarRec(nodo.izq, c);
    } else {
      nodo.der = insertarRec(nodo.der, c);
    }
    return nodo;
  }

  public void preOrden(Nodo n) {
    if (n != null) {
      System.out.println(n.cliente);
      preOrden(n.izq);
      preOrden(n.der);
    }
  }

  public void inOrden(Nodo n) {
    if (n != null) {
      inOrden(n.izq);
      System.out.println(n.cliente);
      inOrden(n.der);
    }
  }

  public void postOrden(Nodo n) {
    if (n != null) {
      postOrden(n.izq);
      postOrden(n.der);
      System.out.println(n.cliente);
    }
  }

  public Nodo getRaiz() {
    return raiz;
  }
}
