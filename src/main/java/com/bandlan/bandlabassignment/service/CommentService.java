package com.bandlan.bandlabassignment.service;

import com.bandlan.bandlabassignment.models.dbentity.CommentDetails;
import com.bandlan.bandlabassignment.models.dbentity.Post;
import com.bandlan.bandlabassignment.models.dbentity.UserInfo;
import com.bandlan.bandlabassignment.repository.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    CommentsRepository commentsRepository;

    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    public void addComment(Long postId, String comment, Long commentorUserId) {
        Optional<UserInfo> userInfo = userService.findUserById(commentorUserId);
        Optional<Post> post = postService.findPost(postId);
        if (post.isEmpty()) {
            throw new RuntimeException("Post does not exist");
        }
        if (userInfo.isEmpty()) {
            throw new RuntimeException("Commentor does not exist");
        }
        CommentDetails commentDetails = CommentDetails.builder()
                                                    .comment(comment)
                                                    .post(post.get())
                                                    .creatorId(userInfo.get())
                                                    .createdDateTime(Date.from(Instant.now()))
                                                    .build();
        commentsRepository.save(commentDetails);
    }

    public CommentDetails getComment(long commentId) {
        Optional<CommentDetails> commentDetails = commentsRepository.findById(commentId);
        return commentDetails.orElse(null);
    }

    public void deleteComment(Long commentId) {
        Optional<CommentDetails> commentDetails = commentsRepository.findById(commentId);
        commentDetails.ifPresent(commentDetail -> commentsRepository.delete(commentDetail));
    }
}
