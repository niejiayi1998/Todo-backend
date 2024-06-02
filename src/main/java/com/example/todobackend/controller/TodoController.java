package com.example.todobackend.controller;

import com.example.todobackend.model.Todo;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/todos")
public class TodoController {
    private List<Todo> todos = new ArrayList<>();
    private AtomicInteger counter = new AtomicInteger();

    public TodoController() {
        todos.add(new Todo(counter.incrementAndGet(), "AWS Certificate", false));
        todos.add(new Todo(counter.incrementAndGet(), "project preparation", false));
        todos.add(new Todo(counter.incrementAndGet(), "Grocery Shopping", true));
    }

    @GetMapping
    public List<Todo> getTodos() {
        return this.todos;
    }

    @GetMapping("/{id}")
    public Todo getTodoByID(@PathVariable int id) {
        return todos.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @PostMapping
    public Todo addTodo(@RequestBody Todo newTodo) {
        newTodo.setId(counter.incrementAndGet());
        todos.add(newTodo);
        return newTodo;
    }

    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable int id, @RequestBody Todo todo) {
        Todo existingTodo = todos.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElse(null);
        if (existingTodo != null) {
            existingTodo.setDescription(todo.getDescription());
            existingTodo.setCompleted(todo.isCompleted());
        }
        return existingTodo;
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable int id) {
        todos.removeIf(t -> t.getId() == id);
    }
}
