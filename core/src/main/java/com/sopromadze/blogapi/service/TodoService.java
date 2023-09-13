package com.sopromadze.blogapi.service;

import com.sopromadze.blogapi.model.Todo;
import com.sopromadze.blogapi.security.UserPrincipal;
import com.sopromadze.payload.ApiResponse;
import com.sopromadze.payload.PagedResponse;

public interface TodoService {

	Todo completeTodo(Long id, UserPrincipal currentUser);

	Todo unCompleteTodo(Long id, UserPrincipal currentUser);

	PagedResponse<Todo> getAllTodos(UserPrincipal currentUser, int page, int size);

	Todo addTodo(Todo todo, UserPrincipal currentUser);

	Todo getTodo(Long id, UserPrincipal currentUser);

	Todo updateTodo(Long id, Todo newTodo, UserPrincipal currentUser);

	ApiResponse deleteTodo(Long id, UserPrincipal currentUser);
}
