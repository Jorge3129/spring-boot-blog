package com.sopromadze.blogapi.service;

import com.sopromadze.blogapi.model.Comment;
import com.sopromadze.blogapi.payload.CommentRequest;
import com.sopromadze.blogapi.security.UserPrincipal;
import com.sopromadze.payload.ApiResponse;
import com.sopromadze.payload.PagedResponse;

public interface CommentService {

	PagedResponse<Comment> getAllComments(Long postId, int page, int size);

	Comment addComment(CommentRequest commentRequest, Long postId, UserPrincipal currentUser);

	Comment getComment(Long postId, Long id);

	Comment updateComment(Long postId, Long id, CommentRequest commentRequest, UserPrincipal currentUser);

	ApiResponse deleteComment(Long postId, Long id, UserPrincipal currentUser);
}
