package com.github.indigogal.PriorityTree;

/**
 * PriorityNode
 */
public class PriorityNode {
  int priority;
  Tasks task;
  int taskTime; // en segundos
  PriorityNode ChildLeft;
  PriorityNode ChildRight;

  public PriorityNode(Tasks _task) {
    this.task = _task;
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
}
