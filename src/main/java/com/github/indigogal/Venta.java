package com.github.indigogal;

import java.util.Objects;

/**
 * Venta
 */
public class Venta {
  private Integer idCliente;
  private Integer valor;
  private String articulo;
  private long creationTimestamp;
  private int saleID;

  public Venta(Integer _cliente, Integer _valor, String _articulo) {
    this.idCliente = _cliente;
    this.valor = _valor;
    this.articulo = _articulo;
    this.creationTimestamp = System.currentTimeMillis();
  }

  public Integer getIdCliente() {
    return idCliente;
  }

  public Integer getValor() {
    return valor;
  }

  public String getArticulo() {
    return articulo;
  }

  public int getSaleID() {
    return saleID;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.creationTimestamp, this.idCliente, this.articulo);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Venta ventaComparison = (Venta) o;
    return Objects.equals(this.idCliente, ventaComparison.getIdCliente())
        && Objects.equals(this.valor, ventaComparison.getValor())
        && Objects.equals(this.articulo, ventaComparison.getArticulo());
  }
}
