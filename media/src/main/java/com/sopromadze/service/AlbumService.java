package com.sopromadze.service;

import com.sopromadze.model.Album;
import com.sopromadze.payload.AlbumResponse;
import com.sopromadze.payload.ApiResponse;
import com.sopromadze.payload.PagedResponse;
import com.sopromadze.payload.request.AlbumRequest;
import com.sopromadze.security.UserPrincipal;
import org.springframework.http.ResponseEntity;

public interface AlbumService {

	PagedResponse<AlbumResponse> getAllAlbums(int page, int size);

	ResponseEntity<Album> addAlbum(AlbumRequest albumRequest, UserPrincipal currentUser);

	ResponseEntity<Album> getAlbum(Long id);

	ResponseEntity<AlbumResponse> updateAlbum(Long id, AlbumRequest newAlbum, UserPrincipal currentUser);

	ResponseEntity<ApiResponse> deleteAlbum(Long id, UserPrincipal currentUser);

	PagedResponse<Album> getUserAlbums(String username, int page, int size);
}
