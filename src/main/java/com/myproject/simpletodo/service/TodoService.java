package com.myproject.simpletodo.service;

import com.myproject.simpletodo.model.Todo;
import com.myproject.simpletodo.repo.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

  @Autowired
  private TodoRepository todoRepository;


  public List<Todo> listAll() {
    return todoRepository.listAllTodos();
  }

  public List<Todo> listAllWith(Boolean completed) {
    return checkFlag(completed) ? todoRepository.listAllCompleted() : todoRepository.listAllIncomplete();
  }

  public Optional<Todo> addTodo(Todo todo) {
    return todoRepository.add(todo);
  }

  public Optional<Todo> getTodo(int id) {
    return todoRepository.getTodo(id);
  }

  public void remove(Todo todo) {
    todoRepository.remove(todo);
  }

  public void setCompleted(Todo todo) {
    todoRepository.setCompleted(todo);
  }

  private Boolean checkFlag(Boolean flag) {
    if(flag == null) {
      return false;
    }
    return flag;
  }
}
