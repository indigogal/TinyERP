package com.github.indigogal;

import java.util.InputMismatchException;
import java.util.Scanner;
import com.github.indigogal.PriorityTree.Tasks;

public class App {
  private static Clientes clientesManager = new Clientes();
  private static Ventas ventasManager = new Ventas();
  private static Sistemas sistemasManager = new Sistemas();
  private static Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) {
    clientesManager.precargar();
    precargarVentas();
    precargarTareas();
    System.out.println("Tareas de sistema precargadas.");

    sistemasManager.iniciarMonitorProgreso();
    mainMenu();
    sistemasManager.shutdown();
  }

  private static void mainMenu() {
    int opcion;
    do {
      System.out.println("\n=============================");
      System.out.println("      MENU PRINCIPAL ERP     ");
      System.out.println("=============================");
      System.out.println("1. Clientes");
      System.out.println("2. Ventas");
      System.out.println("3. Sistemas (Gestor de Tareas)");
      System.out.println("0. Salir");
      System.out.print("Seleccione una opción: ");

      try {
        opcion = scanner.nextInt();
        scanner.nextLine(); // Consumir nueva línea

        switch (opcion) {
          case 1:
            menuClientes();
            break;
          case 2:
            menuVentas();
            break;
          case 3:
            menuSistemas();
            break;
          case 0:
            return;
          default:
            System.out.println("Opción no válida. Intente de nuevo.");
        }
      } catch (InputMismatchException e) {
        System.out.println("Entrada no válida. Por favor, ingrese un número.");
        scanner.nextLine(); // Limpiar el buffer
        opcion = -1;
      }
    } while (opcion != 0);
  }

  // -------------------------------------------------------------------
  // MENU CLIENTES
  // -------------------------------------------------------------------

  private static void menuClientes() {
    int opcion;
    do {
      System.out.println("\n=============================");
      System.out.println("        MENU CLIENTES        ");
      System.out.println("=============================");
      System.out.println("1. Registrar Nuevo Cliente");
      System.out.println("2. Borrar Cliente");
      System.out.println("3. Listar Clientes (Árbol Binario)");
      System.out.println("0. Volver al Menú Principal");
      System.out.print("Seleccione una opción: ");

      try {
        opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion) {
          case 1:
            registrarCliente();
            break;
          case 2:
            borrarCliente();
            break;
          case 3:
            listarClientes();
            break;
          case 0:
            return;
          default:
            System.out.println("Opción no válida.");
        }
      } catch (InputMismatchException e) {
        System.out.println("Entrada no válida. Por favor, ingrese un número.");
        scanner.nextLine();
        opcion = -1;
      }
    } while (opcion != 0);
  }

  private static void registrarCliente() {
    System.out.println("\n--- REGISTRAR NUEVO CLIENTE ---");
    System.out.print("Nombre del cliente: ");
    String nombre = scanner.nextLine();
    double saldo = 0.0;
    boolean validSaldo = false;
    while (!validSaldo) {
      System.out.print("Saldo inicial: ");
      try {
        saldo = scanner.nextDouble();
        scanner.nextLine();
        validSaldo = true;
      } catch (InputMismatchException e) {
        System.out.println("Saldo no válido. Ingrese un número.");
        scanner.nextLine();
      }
    }
    clientesManager.registrar(nombre, saldo); // Registra el cliente
  }

  private static void borrarCliente() {
    System.out.println("\n--- BORRAR CLIENTE ---");
    System.out.print("Ingrese el ID del cliente a borrar: ");
    try {
      int id = scanner.nextInt();
      scanner.nextLine();
      clientesManager.borrar(id); // Borra el cliente
    } catch (InputMismatchException e) {
      System.out.println("ID no válido. Ingrese un número.");
      scanner.nextLine();
    }
  }

  private static void listarClientes() {
    System.out.println("\n--- LISTAR CLIENTES ---");
    clientesManager.listar(); // Llama a listar, que implementa la lógica de Árbol Balanceado
    System.out.println("\n*NOTA: La lista fue generada a partir de un Árbol Binario de Búsqueda (ABB) balanceado.");
    System.out.println(
        "  La raíz del árbol se selecciona para que corresponda al elemento central (mediana) de la lista ordenada.");
  }

  // -------------------------------------------------------------------
  // MENU VENTAS
  // -------------------------------------------------------------------

  private static void precargarVentas() {
    // Para simplificar, obtenemos una lista de IDs válidos de los clientes
    // precargados.
    // En una aplicación real, se obtendrían de la tabla hash.
    // Usaremos los IDs 1000 al 1009 ya que el contador de GeneradorID inicia en
    // 1000
    ventasManager.addVenta(1000, "LAPTOP", 1200);
    ventasManager.addVenta(1001, "MONITOR", 350);
    ventasManager.addVenta(1002, "MOUSE", 25);
    ventasManager.addVenta(1003, "TECLADO", 75);
    ventasManager.addVenta(1004, "TABLET", 450);
    ventasManager.addVenta(1005, "IMPRESORA", 200);
    ventasManager.addVenta(1006, "SCANNER", 150);
    ventasManager.addVenta(1007, "ROUTER", 90);
    ventasManager.addVenta(1008, "SWITCH", 180);
    ventasManager.addVenta(1009, "CABLE_RED", 10);
  }

  private static void menuVentas() {
    int opcion;
    do {
      System.out.println("\n=============================");
      System.out.println("        MENU VENTAS          ");
      System.out.println("=============================");
      System.out.println("1. Capturar Venta");
      System.out.println("2. Listar Ventas General");
      System.out.println("3. Listar Ventas por Cliente");
      System.out.println("0. Volver al Menú Principal");
      System.out.print("Seleccione una opción: ");

      try {
        opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion) {
          case 1:
            capturarVenta();
            break;
          case 2:
            ventasManager.showAllVenta(); // Listar ventas general
            break;
          case 3:
            listarVentasPorCliente();
            break;
          case 0:
            return;
          default:
            System.out.println("Opción no válida.");
        }
      } catch (InputMismatchException e) {
        System.out.println("Entrada no válida. Por favor, ingrese un número.");
        scanner.nextLine();
        opcion = -1;
      }
    } while (opcion != 0);
  }

  private static void capturarVenta() {
    System.out.println("\n--- CAPTURAR VENTA ---");
    int idCliente = -1;
    boolean validId = false;

    // Se asume que el ID del cliente es un campo de la tabla Venta
    while (!validId) {
      System.out.print("ID del Cliente (Debe ser un ID válido): ");
      try {
        idCliente = scanner.nextInt();
        scanner.nextLine();
        // Validación simplificada: En un sistema real, se verificaría que el ID exista
        // en clientesManager.
        validId = true;
      } catch (InputMismatchException e) {
        System.out.println("ID de Cliente no válido. Ingrese un número.");
        scanner.nextLine();
      }
    }

    System.out.print("Artículo: ");
    String articulo = scanner.nextLine();

    int valor = 0;
    boolean validValor = false;
    while (!validValor) {
      System.out.print("Valor (Total de la venta): ");
      try {
        valor = scanner.nextInt();
        scanner.nextLine();
        validValor = true;
      } catch (InputMismatchException e) {
        System.out.println("Valor no válido. Ingrese un número.");
        scanner.nextLine();
      }
    }

    ventasManager.addVenta(idCliente, articulo, valor); // Captura la venta
    System.out.println("Venta capturada exitosamente.");
  }

  private static void listarVentasPorCliente() {
    System.out.println("\n--- LISTAR VENTAS POR CLIENTE ---");
    System.out.print("Ingrese el ID del Cliente: ");
    try {
      int id = scanner.nextInt();
      scanner.nextLine();
      ventasManager.showVentaCliente(id); // Muestra ventas por cliente
    } catch (InputMismatchException e) {
      System.out.println("ID no válido. Ingrese un número.");
      scanner.nextLine();
    }
  }

  // -------------------------------------------------------------------
  // MENU SISTEMAS (Tareas de Prioridad)
  // -------------------------------------------------------------------

  private static void precargarTareas() {
    // Tareas con prioridad/tiempo predefinido en Tasks.java/PriorityNode.java
    sistemasManager.insertarTarea(Tasks.RESPALDAR, 8, 300); // 5 min
    sistemasManager.insertarTarea(Tasks.GENERAR_REPORTE, 1, 60); // 1 min
    sistemasManager.insertarTarea(Tasks.DEPURAR, 5, 180); // 3 min
    sistemasManager.insertarTarea(Tasks.MIGRAR, 10, 360); // 6 min
    sistemasManager.insertarTarea(Tasks.PROBAR, 7, 120); // 2 min
  }

  private static void menuSistemas() {
    int opcion;
    do {
      System.out.println("\n=============================");
      System.out.println("        MENU SISTEMAS        ");
      System.out.println("=============================");
      System.out.println("1. Mostrar Tareas Pendientes");
      System.out.println("2. Ejecutar Tareas (Asíncrono)");
      System.out.println("3. Eliminar Tarea de Mínima Prioridad (Min-Heap simulado)");
      System.out.println("4. Mostrar Estadísticas del Sistema");
      System.out.println("0. Volver al Menú Principal");
      System.out.print("Seleccione una opción: ");

      try {
        opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion) {
          case 1:
            sistemasManager.showTasks();
            break;
          case 2:
            sistemasManager.ejecutarTareas();
            break;
          case 3:
            sistemasManager.removeTask();
            break;
          case 4:
            sistemasManager.mostrarEstadisticas();
            break;
          case 0:
            return;
          default:
            System.out.println("Opción no válida.");
        }
      } catch (InputMismatchException e) {
        System.out.println("Entrada no válida. Por favor, ingrese un número.");
        scanner.nextLine();
        opcion = -1;
      }
    } while (opcion != 0);
  }
}
