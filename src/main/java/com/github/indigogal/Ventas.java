package com.github.indigogal;

import java.util.Hashtable;

/**
 * Ventas
 */
public class Ventas {
  private Hashtable<Integer, Venta> ventas = new Hashtable<Integer, Venta>();

  public void addVenta(int _clienteID, String _articulo, int _valor) {
    Venta tempVenta = new Venta(_clienteID, _valor, _articulo);
    ventas.put(tempVenta.getIdCliente(), tempVenta);
  }

  public void showAllVenta() {
    for (Venta venta : ventas.values()) {
      System.out.println();
      System.out.println("Venta: " + venta.getSaleID());
      System.out.println("Cliente: " + venta.getIdCliente());
      System.out.println("Articulo: " + venta.getArticulo());
      System.out.println("Total: " + venta.getValor());
      System.out.println();
    }
  }

  public void showVentaCliente(Integer _clienteID) {
    for (Venta venta : ventas.values()) {
      if (_clienteID.equals(venta.getIdCliente())) {
        System.out.println();
        System.out.println("Venta: " + venta.getSaleID());
        System.out.println("Cliente: " + venta.getIdCliente());
        System.out.println("Articulo: " + venta.getArticulo());
        System.out.println("Total: " + venta.getValor());
        System.out.println();
      }
    }
  }

  public Hashtable getTable() {
    return ventas;
  }
}
