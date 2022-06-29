package com.myproject.simpletodo.controller;

import com.myproject.simpletodo.model.Todo;
import com.myproject.simpletodo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping(value = "/todo")
public class TodoController {

  @Autowired
  private TodoService todoService;

  @GetMapping(value = "/list")
  public ResponseEntity<List<Todo>> listAll(@RequestParam(name = "completed", required=false) Boolean completed) {
    if(completed == null) {
      return ResponseEntity.ok(todoService.listAll());
    }
    return ResponseEntity.ok(todoService.listAllWith(completed));
  }

  @PostMapping(value = "/add")
  public ResponseEntity<Todo> add(@RequestBody Todo todo) {
    return todoService.addTodo(todo).map(ResponseEntity::ok).orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
  }

  @DeleteMapping(value = "/{id}/remove")
  public ResponseEntity<String> deleteTodo(@PathVariable int id) {
    Optional<Todo> todo = todoService.getTodo(id);
    if(todo.isPresent()) {
      todoService.remove(todo.get());
      return ResponseEntity.status(HttpStatus.OK).body("Todo deleted successfully");
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Todo not found!");
    }
  }

  @PatchMapping(value = "/{id}/done")
  public ResponseEntity<String> setCompleted(@PathVariable int id) {
    Optional<Todo> todo = todoService.getTodo(id);
    if(todo.isPresent()) {
      todoService.setCompleted(todo.get());
      return ResponseEntity.status(HttpStatus.OK).body("Todo marked as complete");
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Todo not found!");
    }
  }

}
