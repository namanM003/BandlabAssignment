package com.bandlan.bandlabassignment.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostInfo {
    private Long postId;
    private String caption;
    private List<Comment> comments;
    private String imageUrl;
    private Long postUserId;
}
