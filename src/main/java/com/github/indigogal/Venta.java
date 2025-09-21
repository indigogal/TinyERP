package com.github.indigogal;

/**
 * Venta
 */
public class Venta {
  private Integer idComprador;
  private Integer valor;
  private String articulo;

  public Venta(Integer _comprador, Integer _valor, String _articulo) {
    this.idComprador = _comprador;
    this.valor = _valor;
    this.articulo = _articulo;
  }
}
