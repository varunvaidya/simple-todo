package com.myproject.simpletodo.repo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TodoRepositoryTest {

  TodoRepository todoRepository;
  Todo todo1, todo2, todo3, todo4;

  @BeforeEach
  void setUp() {
    todoRepository = new TodoRepository();
    todo1 = new Todo(1, "my todo 1", false);
    todo2 = new Todo(2, "my todo 2", false);
    todo3 = new Todo(3, "my todo 3", false);
    todo4 = new Todo(4, "my todo 4", true);
    todoRepository.add(todo1);
    todoRepository.add(todo2);
    todoRepository.add(todo3);
    todoRepository.add(todo4);
  }

  @Test
  void listAllTodos() {
    List<Todo> todos = todoRepository.listAllTodos();
    assertEquals(4, todos.size());
  }

  @Test
  void listAllCompleted() {
    List<Todo> todos = todoRepository.listAllCompleted();
    assertEquals(1, todos.size());
    assertEquals("my todo 4", todos.get(0).getTitle());
  }

  @Test
  void listAllIncomplete() {
    List<Todo> todos = todoRepository.listAllIncomplete();
    assertEquals(3, todos.size());
  }

  @Test
  void add() {
    Optional<Todo> todo = todoRepository.add(new Todo(5, "my todo 5", true));
    assertTrue(todo.isPresent());
    assertEquals(5, todoRepository.listAllTodos().size());

    // test already present
    Optional<Todo> todo1 = todoRepository.add(new Todo(5, "my todo 5", true));
    assertFalse(todo1.isPresent());
    assertEquals(5, todoRepository.listAllTodos().size());
  }

  @Test
  void remove() {
    Todo toRemove = new Todo(4, "my todo 4", true);
    todoRepository.remove(toRemove);
    assertEquals(3, todoRepository.listAllTodos().size());
  }

  @Test
  void getTodo() {
    // not found
    Optional<Todo> notFound = todoRepository.getTodo(8);
    assertFalse(notFound.isPresent());

    // found
    Optional<Todo> found = todoRepository.getTodo(1);
    assertTrue(found.isPresent());
  }

  @Test
  void setCompleted() {
    Todo todo1 = new Todo(1, "my todo 1", false);
    todoRepository.setCompleted(todo1);
    assertTrue(todoRepository.getTodo(1).get().getCompleted());
  }
}
