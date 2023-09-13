package com.sopromadze.blogapi.service;

import com.sopromadze.blogapi.model.Category;
import com.sopromadze.blogapi.security.UserPrincipal;
import com.sopromadze.exception.UnauthorizedException;
import com.sopromadze.payload.ApiResponse;
import com.sopromadze.payload.PagedResponse;
import org.springframework.http.ResponseEntity;

public interface CategoryService {

	PagedResponse<Category> getAllCategories(int page, int size);

	ResponseEntity<Category> getCategory(Long id);

	ResponseEntity<Category> addCategory(Category category, UserPrincipal currentUser);

	ResponseEntity<Category> updateCategory(Long id, Category newCategory, UserPrincipal currentUser)
			throws UnauthorizedException;

	ResponseEntity<ApiResponse> deleteCategory(Long id, UserPrincipal currentUser) throws UnauthorizedException;

}
