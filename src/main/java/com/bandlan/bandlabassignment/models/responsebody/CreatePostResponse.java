package com.bandlan.bandlabassignment.models.responsebody;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostResponse {
    private long postId;
    private String fileUploadUrl;

}
