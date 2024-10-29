package com.bandlan.bandlabassignment.models.requestbody;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeleteCommentRequest {
    private Long commentId;
    private Long commentorUserId;

}
