package com.sopromadze.blogapi.service.impl;

import com.sopromadze.blogapi.model.Todo;
import com.sopromadze.blogapi.repository.TodoRepository;
import com.sopromadze.blogapi.service.TodoService;
import com.sopromadze.exception.BadRequestException;
import com.sopromadze.exception.ResourceNotFoundException;
import com.sopromadze.exception.UnauthorizedException;
import com.sopromadze.payload.ApiResponse;
import com.sopromadze.payload.PagedResponse;
import com.sopromadze.security.UserPrincipal;
import com.sopromadze.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static com.sopromadze.utils.AppConstants.*;

@Service
public class TodoServiceImpl implements TodoService {

	@Autowired
	private TodoRepository todoRepository;

	@Override
	public Todo completeTodo(Long id, UserPrincipal currentUser) {
		Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(TODO, ID, id));

		if (todo.getUserId().equals(currentUser.getId())) {
			todo.setCompleted(Boolean.TRUE);
			return todoRepository.save(todo);
		}

		ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, YOU_DON_T_HAVE_PERMISSION_TO_MAKE_THIS_OPERATION);

		throw new UnauthorizedException(apiResponse);
	}

	@Override
	public Todo unCompleteTodo(Long id, UserPrincipal currentUser) {
		Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(TODO, ID, id));
		if (todo.getUserId().equals(currentUser.getId())) {
			todo.setCompleted(Boolean.FALSE);
			return todoRepository.save(todo);
		}

		ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, YOU_DON_T_HAVE_PERMISSION_TO_MAKE_THIS_OPERATION);

		throw new UnauthorizedException(apiResponse);
	}

	@Override
	public PagedResponse<Todo> getAllTodos(UserPrincipal currentUser, int page, int size) {
		validatePageNumberAndSize(page, size);
		Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, CREATED_AT);

		Page<Todo> todos = todoRepository.findByCreatedBy(currentUser.getId(), pageable);

		List<Todo> content = todos.getNumberOfElements() == 0 ? Collections.emptyList() : todos.getContent();

		return new PagedResponse<>(content, todos.getNumber(), todos.getSize(), todos.getTotalElements(),
				todos.getTotalPages(), todos.isLast());
	}

	@Override
	public Todo addTodo(Todo todo, UserPrincipal currentUser) {
		todo.setUserId(currentUser.getId());
		return todoRepository.save(todo);
	}

	@Override
	public Todo getTodo(Long id, UserPrincipal currentUser) {
		Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(TODO, ID, id));

		if (todo.getUserId().equals(currentUser.getId())) {
			return todo;
		}

		ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, YOU_DON_T_HAVE_PERMISSION_TO_MAKE_THIS_OPERATION);

		throw new UnauthorizedException(apiResponse);
	}

	@Override
	public Todo updateTodo(Long id, Todo newTodo, UserPrincipal currentUser) {
		Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(TODO, ID, id));
		if (todo.getUserId().equals(currentUser.getId())) {
			todo.setTitle(newTodo.getTitle());
			todo.setCompleted(newTodo.getCompleted());
			return todoRepository.save(todo);
		}

		ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, YOU_DON_T_HAVE_PERMISSION_TO_MAKE_THIS_OPERATION);

		throw new UnauthorizedException(apiResponse);
	}

	@Override
	public ApiResponse deleteTodo(Long id, UserPrincipal currentUser) {
		Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(TODO, ID, id));

		if (todo.getUserId().equals(currentUser.getId())) {
			todoRepository.deleteById(id);
			return new ApiResponse(Boolean.TRUE, "You successfully deleted todo");
		}

		ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, YOU_DON_T_HAVE_PERMISSION_TO_MAKE_THIS_OPERATION);

		throw new UnauthorizedException(apiResponse);
	}

	private void validatePageNumberAndSize(int page, int size) {
		if (page < 0) {
			throw new BadRequestException("Page number cannot be less than zero.");
		}

		if (size < 0) {
			throw new BadRequestException("Size number cannot be less than zero.");
		}

		if (size > AppConstants.MAX_PAGE_SIZE) {
			throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
		}
	}
}
