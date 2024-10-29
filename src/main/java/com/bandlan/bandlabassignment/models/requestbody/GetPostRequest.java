package com.bandlan.bandlabassignment.models.requestbody;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@Data
@AllArgsConstructor
public class GetPostRequest {
    Integer pageNo;
}
