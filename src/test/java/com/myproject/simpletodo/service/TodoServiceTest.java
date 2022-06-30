package com.myproject.simpletodo.service;

import com.myproject.simpletodo.model.Todo;
import com.myproject.simpletodo.repo.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

  private final List<Todo> todoList = new ArrayList<>();

  @InjectMocks
  TodoService mockService;

  @Mock
  TodoRepository mocTodoRepository;

  @BeforeEach
  void setUp() {
    Todo todo1 = new Todo(1, "my todo 1", false);
    Todo todo2 = new Todo(2, "my todo 2", false);
    Todo todo3 = new Todo(3, "my todo 3", false);
    Todo todo4 = new Todo(4, "my todo 4", true);
    todoList.add(todo1);
    todoList.add(todo2);
    todoList.add(todo3);
    todoList.add(todo4);
  }

  @Test
  void listAll() {
    when(mocTodoRepository.listAllTodos()).thenReturn(todoList);
    List<Todo> todos = mockService.listAll();
    assertEquals(4, todos.size());
    verify(mocTodoRepository, times(1)).listAllTodos();
  }

  @Test
  void listAllWith() {
    todoList.forEach(t -> t.setCompleted(true));
    when(mocTodoRepository.listAllCompleted()).thenReturn(todoList);
    List<Todo> todos = mockService.listAllWith(true);
    assertEquals(4, todos.size());
    verify(mocTodoRepository, times(1)).listAllCompleted();
  }

  @Test
  void addTodo() {
    when(mocTodoRepository.add(any())).thenReturn(Optional.ofNullable(todoList.get(0)));
    Optional<Todo> todo = mockService.addTodo(new Todo(1, "my todo 1", false));
    assertTrue(todo.isPresent());
    assertEquals("my todo 1", todo.get().getTitle());
  }

  @Test
  void getTodo() {
    when(mocTodoRepository.getTodo(anyInt())).thenReturn(Optional.ofNullable(todoList.get(0)));
    Optional<Todo> todo = mockService.getTodo(1);
    assertTrue(todo.isPresent());
    assertEquals("my todo 1", todo.get().getTitle());
  }

  @Test
  void remove() {
    when(mocTodoRepository.getTodo(anyInt())).thenReturn(Optional.ofNullable(todoList.get(0)));
    doReturn(true).when(mocTodoRepository).remove(any());
    assertTrue(mockService.remove(1));
  }

  @Test
  void setCompleted() {
    when(mocTodoRepository.getTodo(anyInt())).thenReturn(Optional.ofNullable(todoList.get(0)));
    doReturn(true).when(mocTodoRepository).setCompleted(any());
    assertTrue(mockService.setCompleted(1));
  }
}
