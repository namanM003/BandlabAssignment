package com.bandlan.bandlabassignment.controller;

import com.bandlan.bandlabassignment.exception.UnauthorizedException;
import com.bandlan.bandlabassignment.models.dbentity.CommentDetails;
import com.bandlan.bandlabassignment.models.dbentity.UserInfo;
import com.bandlan.bandlabassignment.models.requestbody.CreateCommentRequest;
import com.bandlan.bandlabassignment.models.requestbody.DeleteCommentRequest;
import com.bandlan.bandlabassignment.service.CommentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentControllerTest {

    private static final String COMMENT = "comment";
    private static final Long POST_ID = 1L;
    private static final Long COMMENT_ID = 2L;
    private static final Long COMMENTOR_USER_ID = 3L;

    @Mock
    CommentService commentService;

    @Mock
    CreateCommentRequest createCommentRequest;

    @Mock
    CommentDetails commentDetails;

    @Mock
    DeleteCommentRequest deleteCommentRequest;

    @Mock
    UserInfo userInfo;

    @InjectMocks
    CommentController commentController;

    @Test
    public void addComment_HappyCase() {
        when(createCommentRequest.getComment()).thenReturn(COMMENT);
        when(createCommentRequest.getPostId()).thenReturn(POST_ID);
        when(createCommentRequest.getCommentorUserId()).thenReturn(COMMENTOR_USER_ID);

        commentController.addComment(createCommentRequest);

        verify(commentService).addComment(eq(POST_ID), eq(COMMENT), eq(COMMENTOR_USER_ID));
    }

    @Test
    public void deleteComment_HappyCase() {
        when(commentService.getComment(anyLong())).thenReturn(commentDetails);
        when(commentDetails.getCreatorId()).thenReturn(userInfo);
        when(userInfo.getId()).thenReturn(COMMENTOR_USER_ID);
        when(deleteCommentRequest.getCommentId()).thenReturn(COMMENT_ID);
        when(deleteCommentRequest.getCommentorUserId()).thenReturn(COMMENTOR_USER_ID);

        commentController.deleteComment(deleteCommentRequest);

        verify(commentService).deleteComment(eq(COMMENT_ID));
    }

    @Test
    public void deleteComment_CommentDetailsIsNull_ShouldNotCallCommentService() {
        when(deleteCommentRequest.getCommentId()).thenReturn(COMMENT_ID);
        when(commentService.getComment(anyLong())).thenReturn(null);

        commentController.deleteComment(deleteCommentRequest);

        verify(commentService, never()).deleteComment(anyLong());
    }

    @Test
    public void deleteComment_CommentorIdNotMatching_ShouldThrowUnauthorizedException() {
        when(deleteCommentRequest.getCommentId()).thenReturn(COMMENT_ID);
        when(commentService.getComment(anyLong())).thenReturn(commentDetails);
        when(commentDetails.getCreatorId()).thenReturn(userInfo);
        when(userInfo.getId()).thenReturn(100L);

        assertThrows(UnauthorizedException.class, () -> commentController.deleteComment(deleteCommentRequest));

        verify(commentService, never()).deleteComment(anyLong());
    }

}