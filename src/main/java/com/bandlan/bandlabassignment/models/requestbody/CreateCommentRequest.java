package com.bandlan.bandlabassignment.models.requestbody;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentRequest {
    private Long postId;
    private String comment;
    private Long commentorUserId;
}
