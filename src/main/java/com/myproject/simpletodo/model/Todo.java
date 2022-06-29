package com.myproject.simpletodo.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Todo {

  private int id;
  private String title;
  private Boolean completed;

  public Todo(int id, String title, Boolean completed) {
    this.id = id;
    this.title = title;
    this.completed = completed;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Boolean getCompleted() {
    return completed;
  }

  public void setCompleted(Boolean completed) {
    this.completed = completed;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Todo)) return false;
    Todo todo = (Todo) o;
    return getId() == todo.getId() &&
        Objects.equals(getTitle(), todo.getTitle());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getTitle());
  }
}
