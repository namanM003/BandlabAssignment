package com.bandlan.bandlabassignment.utility;

import com.bandlan.bandlabassignment.models.Comment;
import com.bandlan.bandlabassignment.models.PostInfo;
import com.bandlan.bandlabassignment.models.dbentity.CommentDetails;
import com.bandlan.bandlabassignment.models.dbentity.Post;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DataTransformer {
    public static PostInfo transformPostDBEntityToPostInfo(Post post, String processedImageBucketName) {
        return PostInfo.builder()
                .caption(post.getCaption())
                .imageUrl(createImageUrl(processedImageBucketName, post.getImageFileName()))
                .postId(post.getId())
                .comments(convertCommentDetailsToComments(post.getCommentDetails()))
                .postUserId(post.getCreatorInfo().getId())
                .build();
    }

    private static List<Comment> convertCommentDetailsToComments(List<CommentDetails> commentDetails) {
        return commentDetails.stream()
                .map(DataTransformer::transformCommentDetailsDBEntityToComment)
                .sorted(Comparator.comparing(Comment::getCreationDateTime))
                .collect(Collectors.toList());
    }

    public static Comment transformCommentDetailsDBEntityToComment(CommentDetails commentDetails) {
        return Comment.builder()
                .comment(commentDetails.getComment())
                .commentorId(commentDetails.getCreatorId().getId())
                .creationDateTime(commentDetails.getCreatedDateTime())
                .commentId(commentDetails.getCommentId())
                .build();
    }

    private static String createImageUrl(String processedImageBucketName, String imageFileName) {
        return "https://" + processedImageBucketName + "s3.amazonaws.com" + "/" + imageFileName;
    }
}
