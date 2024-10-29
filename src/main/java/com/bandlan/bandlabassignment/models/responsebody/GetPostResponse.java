package com.bandlan.bandlabassignment.models.responsebody;

import com.bandlan.bandlabassignment.models.PostInfo;
import com.bandlan.bandlabassignment.models.dbentity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class GetPostResponse {

    private List<PostInfo> posts;
    private Integer pageNo;
}
