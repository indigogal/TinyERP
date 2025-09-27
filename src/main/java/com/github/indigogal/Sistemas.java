package com.github.indigogal;

import com.github.indigogal.PriorityTree.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.List;

public class Sistemas {
  private PriorityTree arbol = new PriorityTree();
  private ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);
  private List<PriorityNode> runningTasks = new ArrayList<>();
  private boolean isProcessing = false;

  public void insertarTarea(Tasks task, int prioridad, int taskTime) {
    arbol.origin = insertarRecursivo(arbol.origin, null, task, prioridad, taskTime);
  }

  private PriorityNode insertarRecursivo(PriorityNode actual, PriorityNode padre, Tasks task, int prioridad,
      int taskTime) {
    if (actual == null) {
      PriorityNode nuevo = new PriorityNode(task, prioridad, taskTime);
      nuevo.padre = padre;
      return nuevo;
    }

    if (prioridad < actual.priority) {
      actual.ChildLeft = insertarRecursivo(actual.ChildLeft, actual, task, prioridad, taskTime);
    } else {
      actual.ChildRight = insertarRecursivo(actual.ChildRight, actual, task, prioridad, taskTime);
    }

    return actual;
  }

  public void removeTask() {
    if (arbol.origin == null) {
      System.out.println("No hay tareas para eliminar.");
      return;
    }
    arbol.origin = eliminarMin(arbol.origin);
  }

  private PriorityNode eliminarMin(PriorityNode actual) {
    if (actual.ChildLeft == null) {
      return actual.ChildRight;
    }
    actual.ChildLeft = eliminarMin(actual.ChildLeft);
    return actual;
  }

  public void showTasks() {
    if (arbol.origin == null) {
      System.out.println("No hay tareas.");
    } else {
      System.out.println("=== LISTA DE TAREAS ===");
      recorrerInOrder(arbol.origin);
    }
  }

  private void recorrerInOrder(PriorityNode nodo) {
    if (nodo != null) {
      recorrerInOrder(nodo.ChildLeft);
      String statusStr = nodo.status.toString();
      double progress = nodo.getExecutionProgress();
      System.out.printf("Tarea: %-15s | Prioridad: %2d | Tiempo: %3ds | Estado: %-9s | Progreso: %.1f%%\n",
          nodo.task, nodo.priority, nodo.taskTime, statusStr, progress);
      recorrerInOrder(nodo.ChildRight);
    }
  }

  // Ejecutar tareas de forma asíncrona
  public void ejecutarTareas() {
    if (isProcessing) {
      System.out.println("Ya se están procesando las tareas.");
      return;
    }

    isProcessing = true;
    System.out.println("Iniciando ejecución de tareas...");

    CompletableFuture.runAsync(() -> {
      procesarTareasEnOrden();
    }).whenComplete((result, throwable) -> {
      isProcessing = false;
      if (throwable != null) {
        System.out.println("Error durante la ejecución: " + throwable.getMessage());
      } else {
        System.out.println("Todas las tareas han sido procesadas.");
      }
    });
  }

  private void procesarTareasEnOrden() {
    while (arbol.origin != null) {
      PriorityNode tareaMinima = encontrarMinimo(arbol.origin);
      if (tareaMinima != null && tareaMinima.status == TaskStatus.PENDING) {
        ejecutarTarea(tareaMinima);
        // Esperar a que termine la tarea antes de continuar con la siguiente
        try {
          tareaMinima.executionFuture.get();
        } catch (Exception e) {
          System.out.println("Error ejecutando tarea: " + e.getMessage());
        }
        // Remover la tarea completada del árbol
        arbol.origin = eliminarNodo(arbol.origin, tareaMinima);
      } else {
        break;
      }
    }
  }

  private PriorityNode encontrarMinimo(PriorityNode nodo) {
    if (nodo == null)
      return null;
    while (nodo.ChildLeft != null) {
      nodo = nodo.ChildLeft;
    }
    return nodo;
  }

  private PriorityNode eliminarNodo(PriorityNode raiz, PriorityNode nodoAEliminar) {
    if (raiz == null)
      return raiz;

    if (nodoAEliminar.priority < raiz.priority) {
      raiz.ChildLeft = eliminarNodo(raiz.ChildLeft, nodoAEliminar);
    } else if (nodoAEliminar.priority > raiz.priority) {
      raiz.ChildRight = eliminarNodo(raiz.ChildRight, nodoAEliminar);
    } else {
      // Nodo encontrado
      if (raiz.ChildLeft == null) {
        return raiz.ChildRight;
      } else if (raiz.ChildRight == null) {
        return raiz.ChildLeft;
      }

      // Nodo con dos hijos
      PriorityNode sucesor = encontrarMinimo(raiz.ChildRight);
      raiz.priority = sucesor.priority;
      raiz.task = sucesor.task;
      raiz.taskTime = sucesor.taskTime;
      raiz.ChildRight = eliminarNodo(raiz.ChildRight, sucesor);
    }
    return raiz;
  }

  private void ejecutarTarea(PriorityNode nodo) {
    nodo.status = TaskStatus.RUNNING;
    nodo.startTime = System.currentTimeMillis();
    runningTasks.add(nodo);

    System.out.println("Iniciando ejecución: " + nodo.task + " (Prioridad: " + nodo.priority + ")");

    nodo.executionFuture = CompletableFuture.runAsync(() -> {
      try {
        // Simular la ejecución de la tarea
        Thread.sleep(nodo.taskTime * 1000L);
        nodo.status = TaskStatus.COMPLETED;
        nodo.endTime = System.currentTimeMillis();
        System.out.println("Completada: " + nodo.task);
      } catch (InterruptedException e) {
        nodo.status = TaskStatus.FAILED;
        System.out.println("Error en: " + nodo.task);
        Thread.currentThread().interrupt();
      } finally {
        runningTasks.remove(nodo);
      }
    }, executor);
  }

  // Mostrar el progreso de las tareas en ejecución
  public void mostrarProgreso() {
    if (runningTasks.isEmpty()) {
      System.out.println("No hay tareas ejecutándose actualmente.");
      return;
    }

    System.out.println("=== PROGRESO DE TAREAS EN EJECUCIÓN ===");
    for (PriorityNode tarea : runningTasks) {
      double progreso = tarea.getExecutionProgress();
      String barra = crearBarraProgreso(progreso);
      System.out.printf("%-15s [%s] %.1f%%\n", tarea.task, barra, progreso);
    }
  }

  private String crearBarraProgreso(double progreso) {
    int longitud = 20;
    int completado = (int) (progreso / 100.0 * longitud);
    StringBuilder barra = new StringBuilder();
    for (int i = 0; i < longitud; i++) {
      if (i < completado) {
        barra.append("█");
      } else {
        barra.append("░");
      }
    }
    return barra.toString();
  }

  // Monitor continuo de progreso
  public void iniciarMonitorProgreso() {
    executor.scheduleAtFixedRate(() -> {
      if (!runningTasks.isEmpty()) {
        mostrarProgreso();
      }
    }, 0, 2, TimeUnit.SECONDS);
  }

  public void shutdown() {
    executor.shutdown();
    try {
      if (!executor.awaitTermination(800, TimeUnit.MILLISECONDS)) {
        executor.shutdownNow();
      }
    } catch (InterruptedException e) {
      executor.shutdownNow();
    }
  }

  // Método para obtener estadísticas
  public void mostrarEstadisticas() {
    System.out.println("=== ESTADÍSTICAS DEL SISTEMA ===");
    System.out.println("Tareas en cola: " + contarNodos(arbol.origin));
    System.out.println("Tareas ejecutándose: " + runningTasks.size());
    // Usamos operador ternario para hacer output del status de procesamiento
    System.out.println("Sistema procesando: " + (isProcessing ? "Sí" : "No"));
  }

  private int contarNodos(PriorityNode nodo) {
    if (nodo == null)
      return 0;
    return 1 + contarNodos(nodo.ChildLeft) + contarNodos(nodo.ChildRight);
  }
}
