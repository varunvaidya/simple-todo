package com.myproject.simpletodo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject.simpletodo.model.Todo;
import com.myproject.simpletodo.service.TodoService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TodoController.class)
class TodoControllerTest {

  private List<Todo> todoList = new ArrayList<>();

  @MockBean
  TodoService mockTodoService;

  @Autowired
  MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    Todo todo1 = new Todo(1, "my todo 1", false);
    Todo todo2 = new Todo(1, "my todo 2", false);
    Todo todo3 = new Todo(1, "my todo 3", false);
    todoList.add(todo1);
    todoList.add(todo2);
    todoList.add(todo3);
  }

  @Test
  void listAll() throws Exception{
   when(mockTodoService.listAll()).thenReturn(todoList);
    mockMvc.perform(get("/todo/list"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", Matchers.hasSize(3)))
        .andExpect(jsonPath("$[0].title", Matchers.is("my todo 1")));
  }

  @Test
  void listAllWithParam() throws Exception{
    todoList.remove(0);
    when(mockTodoService.listAllWith(anyBoolean())).thenReturn(todoList);
    mockMvc.perform(get("/todo/list").param("completed", "true"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", Matchers.hasSize(2)))
        .andExpect(jsonPath("$[0].title", Matchers.is("my todo 2")));
  }

  @Test
  void add() throws Exception {
    Todo todo1 = new Todo(1, "my todo 1", false);
    when(mockTodoService.addTodo(any())).thenReturn(java.util.Optional.of(todo1));
    mockMvc.perform(post("/todo/add").
        contentType(MediaType.APPLICATION_JSON).content(toJson(todo1)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.title", Matchers.is("my todo 1")));
  }

  @Test
  void deleteTodo() throws Exception {
    doReturn(true).when(mockTodoService).remove(anyInt());
    mockMvc.perform(delete("/todo/1/remove"))
        .andExpect(status().isOk())
        .andExpect(content().string(Matchers.is("Todo deleted successfully")));
  }

  @Test
  void deleteTodoNotFound() throws Exception {
    doReturn(false).when(mockTodoService).remove(anyInt());
    mockMvc.perform(delete("/todo/1/remove"))
        .andExpect(status().isNotFound())
        .andExpect(content().string(Matchers.is("Todo not found!")));
  }

  @Test
  void setCompleted() throws Exception {
    doReturn(true).when(mockTodoService).setCompleted(anyInt());
    mockMvc.perform(patch("/todo/1/done"))
        .andExpect(status().isOk())
        .andExpect(content().string(Matchers.is("Todo marked as complete")));
  }

  private String toJson(Object obj) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.writeValueAsString(obj);
  }
}
