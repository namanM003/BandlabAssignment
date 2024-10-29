package com.bandlan.bandlabassignment.controller;

import com.bandlan.bandlabassignment.models.dbentity.Post;
import com.bandlan.bandlabassignment.models.requestbody.CreatePostRequest;
import com.bandlan.bandlabassignment.models.responsebody.CreatePostResponse;
import com.bandlan.bandlabassignment.s3.S3Service;
import com.bandlan.bandlabassignment.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostControllerTest {

    private final String PRESIGNED_URL = "presignedUrl";
    private final String IMAGE_FILE_NAME = "imageFile";
    private final Long USER_ID = 1L;
    private final Long POST_ID = 2L;
    private final String CAPTION = "caption";

    @Mock
    private PostService postService;

    @Mock
    private S3Service s3Service;

    @Mock
    private Post post;

    @Mock
    private CreatePostRequest createPostRequest;

    @InjectMocks
    private PostController postController;

    @Captor
    ArgumentCaptor<Map<String, String>> metadataCaptor;

    @BeforeEach
    public void setup() {
        when(createPostRequest.getUserId()).thenReturn(USER_ID);
        when(createPostRequest.getCaption()).thenReturn(CAPTION);
        when(post.getId()).thenReturn(POST_ID);
        when(post.getImageFileName()).thenReturn(IMAGE_FILE_NAME);
    }


    @Test
    void createPost() {
        when(postService.createPost(anyLong(), anyString())).thenReturn(post);
        when(s3Service.generatePresignedUploadUrl(any(), any())).thenReturn(PRESIGNED_URL);

        CreatePostResponse response = postController.createPost(createPostRequest);

        verify(postService).createPost(eq(USER_ID), eq(CAPTION));
        verify(s3Service).generatePresignedUploadUrl(metadataCaptor.capture(), eq(IMAGE_FILE_NAME));
        assertEquals(metadataCaptor.getValue().get("postId"), Long.toString(POST_ID));
        assertEquals(response.getPostId(), POST_ID);
        assertEquals(response.getFileUploadUrl(), PRESIGNED_URL);
    }
}