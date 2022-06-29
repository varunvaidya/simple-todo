package com.myproject.simpletodo.repo;

import com.myproject.simpletodo.model.Todo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class TodoRepository {
  private final Map<Integer,Todo> todosMap = new HashMap<>();

  public List<Todo> listAllTodos() {
    return new ArrayList<>(todosMap.values());
  }

  public List<Todo> listAllCompleted() {
    return todosMap.values().stream().filter(Todo::getCompleted).collect(Collectors.toList());
  }

  public List<Todo> listAllIncomplete() {
    return todosMap.values().stream().filter(todo -> !todo.getCompleted()).collect(Collectors.toList());
  }

  public Optional<Todo> add(Todo todo) {
    if(!todosMap.containsKey(todo.getId())) {
      todosMap.put(todo.getId(), todo);
    } else {
      return Optional.empty();
    }
    return this.todosMap.values().stream().filter(todo1 -> todo1.getId() == todo.getId()).findFirst();
  }

  public void remove(Todo todo) {
    this.todosMap.remove(todo.getId(), todo);
  }

  public Optional<Todo> getTodo(int todoId) {
    return Optional.ofNullable(this.todosMap.getOrDefault(todoId, null));
  }

  public void setCompleted(Todo todo) {
    todo.setCompleted(true);
    todosMap.put(todo.getId(), todo);
  }
}
