package com.sopromadze.blogapi.controller;

import com.sopromadze.blogapi.model.Post;
import com.sopromadze.blogapi.service.PostService;
import com.sopromadze.payload.PagedResponse;
import com.sopromadze.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class PostForUserController {

  @Autowired
  private PostService postService;

  @GetMapping("/{username}/posts")
  public ResponseEntity<PagedResponse<Post>> getPostsCreatedBy(
      @PathVariable(value = "username") String username,
      @RequestParam(value = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
      @RequestParam(value = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size
  ) {
    PagedResponse<Post> response = postService.getPostsByCreatedBy(username, page, size);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
