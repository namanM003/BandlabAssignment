package com.bandlan.bandlabassignment.service;

import com.bandlan.bandlabassignment.models.dbentity.Post;
import com.bandlan.bandlabassignment.models.dbentity.UserInfo;
import com.bandlan.bandlabassignment.repository.PostRepository;
import com.bandlan.bandlabassignment.s3.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostService {
    PostRepository postRepository;
    UserService userService;

    @Autowired
    public PostService(PostRepository postRepository, UserService userService, S3Service s3Service) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public Post createPost(long creatorId, String caption) {
       String imageFileName = generateImageFileName();
       return postRepository.save(Post.builder()
                .caption(caption)
                .creatorInfo(findCreator(creatorId).get())
                .createdDateTime(Date.from(Instant.now()))
                .imageFileName(imageFileName)
                .build());
    }

    private String generateImageFileName() {
        return UUID.randomUUID().toString();
    }

    public List<Post> getPosts(int pageNo) {
        Pageable page = PageRequest.of(pageNo, 2);
        return postRepository.findPostsByCommentCount(page).getContent();
    }

    public Optional<Post> findPost(Long postId) {
        return postRepository.findById(postId);
    }

    public void deletePost(Long postId) {
        Optional<Post> post = findPost(postId);
        post.ifPresent(value -> postRepository.delete(value));
    }

    private Optional<UserInfo> findCreator(Long creatorId) {
        return userService.findUserById(creatorId);
    }
}
