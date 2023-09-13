package com.sopromadze.service;

import com.sopromadze.payload.ApiResponse;
import com.sopromadze.payload.PagedResponse;
import com.sopromadze.payload.PhotoResponse;
import com.sopromadze.payload.request.PhotoRequest;
import com.sopromadze.security.UserPrincipal;

public interface PhotoService {
	PagedResponse<PhotoResponse> getAllPhotos(int page, int size);

	PhotoResponse getPhoto(Long id);

	PhotoResponse updatePhoto(Long id, PhotoRequest photoRequest, UserPrincipal currentUser);

	PhotoResponse addPhoto(PhotoRequest photoRequest, UserPrincipal currentUser);

	ApiResponse deletePhoto(Long id, UserPrincipal currentUser);

	PagedResponse<PhotoResponse> getAllPhotosByAlbum(Long albumId, int page, int size);
}