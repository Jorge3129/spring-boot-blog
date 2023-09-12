package com.sopromadze.blogapi.controller;

import com.sopromadze.blogapi.model.Album;
import com.sopromadze.blogapi.payload.PagedResponse;
import com.sopromadze.blogapi.service.AlbumService;
import com.sopromadze.blogapi.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class AlbumForUserController {

  @Autowired
  private AlbumService albumService;

  @GetMapping("/{username}/albums")
  public ResponseEntity<PagedResponse<Album>> getUserAlbums(
      @PathVariable(name = "username") String username,
      @RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
      @RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size
  ) {
    PagedResponse<Album> response = albumService.getUserAlbums(username, page, size);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
