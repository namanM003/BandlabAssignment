package com.bandlan.bandlabassignment.models.requestbody;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePostRequest {
    private String caption;
    private Long userId;

}
