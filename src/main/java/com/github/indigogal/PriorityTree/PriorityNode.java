package com.github.indigogal.PriorityTree;

import java.util.concurrent.CompletableFuture;
import com.github.indigogal.PriorityTree.TaskStatus;

public class PriorityNode {
  public int priority;
  public int taskTime; // en segundos
  public Tasks task;
  public TaskStatus status;
  public PriorityNode padre;
  public PriorityNode ChildLeft;
  public PriorityNode ChildRight;
  public CompletableFuture<Void> executionFuture;
  public long startTime;
  public long endTime;
  public int progress; // 0-100

  public PriorityNode(Tasks _task, int priority, int taskTime) {
    this.task = _task;
    this.padre = null;
    this.status = TaskStatus.PENDING;
    this.progress = 0;
    this.executionFuture = null;
    determinePriority();
  }

  public void setChildRight(PriorityNode childRight) {
    ChildRight = childRight;
  }

  public void setChildLeft(PriorityNode childLeft) {
    ChildLeft = childLeft;
  }

  public PriorityNode getChildLeft() {
    return ChildLeft;
  }

  public PriorityNode getChildRight() {
    return ChildRight;
  }

  public int getPriority() {
    return priority;
  }

  private void determinePriority() {
    switch (task) {
      case DEPURAR:
        this.priority = 5;
        this.taskTime = 180;
        break;
      case RESPALDAR:
        this.priority = 8;
        this.taskTime = 300;
        break;
      case PROBAR:
        this.priority = 7;
        this.taskTime = 120;
        break;
      case MIGRAR:
        this.priority = 10;
        this.taskTime = 360;
        break;
      case GENERAR_REPORTE:
        this.priority = 1;
        this.taskTime = 60;
        break;
    }
  }

  public boolean isCompleted() {
    return status == TaskStatus.COMPLETED || status == TaskStatus.FAILED;
  }

  public boolean isRunning() {
    return status == TaskStatus.RUNNING;
  }

  public double getExecutionProgress() {
    if (status == TaskStatus.PENDING)
      return 0.0;
    if (status == TaskStatus.COMPLETED)
      return 100.0;
    if (status == TaskStatus.RUNNING && startTime > 0) {
      long elapsed = System.currentTimeMillis() - startTime;
      double progress = Math.min(100.0, (elapsed / 1000.0 / taskTime) * 100.0);
      return progress;
    }
    return 0.0;
  }

}
