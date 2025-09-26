package com.github.indigogal;

import java.util.Objects;

public class Cliente {
  private String Nombre;
  private Integer Saldo;
  private long creationTimestamp;
  private int ID;

  public Cliente(String _Nombre, Integer _Saldo) {
    this.Nombre = _Nombre.toUpperCase();
    this.Saldo = _Saldo;
    this.creationTimestamp = System.currentTimeMillis();
    this.ID = this.hashCode();
  }

  public Cliente(int id, String nombre, double saldo) {
    this.ID = id;
    this.Nombre = nombre.toUpperCase();
    this.Saldo = (int) saldo;
    this.creationTimestamp = System.currentTimeMillis();
  }

  public void setNombre(String nombre) {
    Nombre = nombre.toUpperCase();
  }

  public void setSaldo(Integer saldo) {
    Saldo = saldo;
  }

  public Integer getSaldo() {
    return Saldo;
  }

  public int getID() {
    return ID;
  }

  public int getId() {
    return ID;
  }

  public String getNombre() {
    return Nombre;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.Nombre, this.creationTimestamp);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Cliente clienteComparison = (Cliente) o;
    return Objects.equals(this.Nombre, clienteComparison.Nombre) &&
        Objects.equals(this.Saldo, clienteComparison.Saldo);
  }

  @Override
  public String toString() {
    return String.format("Cliente{ID=%d, Nombre='%s', Saldo=%d}", ID, Nombre, Saldo);
  }
}
