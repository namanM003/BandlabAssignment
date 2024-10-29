package com.bandlan.bandlabassignment.controller;

import com.bandlan.bandlabassignment.exception.UnauthorizedException;
import com.bandlan.bandlabassignment.models.requestbody.CreateCommentRequest;
import com.bandlan.bandlabassignment.models.dbentity.CommentDetails;
import com.bandlan.bandlabassignment.models.requestbody.DeleteCommentRequest;
import com.bandlan.bandlabassignment.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CommentController {

    @Autowired
    CommentService commentService;

    Logger logger = LoggerFactory.getLogger(CommentController.class);

    @PostMapping("/addComment")
    public void addComment(@RequestBody CreateCommentRequest createCommentRequest) {
        commentService.addComment(createCommentRequest.getPostId(), createCommentRequest.getComment(), createCommentRequest.getCommentorUserId());
    }

    @DeleteMapping("/deleteComment")
    public void deleteComment(@RequestBody DeleteCommentRequest deleteCommentRequest) {
        CommentDetails commentDetails = commentService.getComment(deleteCommentRequest.getCommentId());
        if (commentDetails == null) {
            return;
        }
        if (commentDetails.getCreatorId().getId() == deleteCommentRequest.getCommentorUserId()) {
            commentService.deleteComment(deleteCommentRequest.getCommentId());
        } else {
            logger.error("Commentor is not same as delete requester, " + deleteCommentRequest);
            throw new UnauthorizedException("Requester is not authorized to delete this comment");
        }
    }

}
