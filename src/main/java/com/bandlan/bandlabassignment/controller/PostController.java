package com.bandlan.bandlabassignment.controller;

import com.bandlan.bandlabassignment.models.PostInfo;
import com.bandlan.bandlabassignment.models.dbentity.Post;
import com.bandlan.bandlabassignment.models.requestbody.CreatePostRequest;
import com.bandlan.bandlabassignment.models.requestbody.GetPostRequest;
import com.bandlan.bandlabassignment.models.responsebody.CreatePostResponse;
import com.bandlan.bandlabassignment.models.responsebody.GetPostResponse;
import com.bandlan.bandlabassignment.s3.S3Service;
import com.bandlan.bandlabassignment.service.PostService;
import com.bandlan.bandlabassignment.utility.DataTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private S3Service s3Service;

    @Value("${aws.s3.processImagesBucketName}")
    private String processedImageBucket;

    @PostMapping("/create")
    public CreatePostResponse createPost(@RequestBody CreatePostRequest createPostRequest) {
        Post post = postService.createPost(createPostRequest.getUserId(), createPostRequest.getCaption());
        Map<String, String> metadata = Collections.singletonMap("postId", Long.toString(post.getId()));
        String presignedUpLoadUrl = s3Service.generatePresignedUploadUrl(metadata, post.getImageFileName());

        return CreatePostResponse.builder()
                .postId(post.getId())
                .fileUploadUrl(presignedUpLoadUrl)
                .build();
    }

    @GetMapping("/getPost")
    public GetPostResponse getPosts(@RequestBody GetPostRequest request) {
        int pageNo = request.getPageNo() == null ? 0 : request.getPageNo();
        List<Post> posts = postService.getPosts(pageNo);
        if (posts.isEmpty()) {
            return GetPostResponse.builder().build();
            // Or should we throw no more post exception?
        }
        List<PostInfo> postInfos = posts.stream()
                .map(post -> DataTransformer.transformPostDBEntityToPostInfo(post, processedImageBucket))
                .toList();
        return GetPostResponse.builder()
                .posts(postInfos)
                .pageNo(pageNo+1)
                .build();
    }
}
