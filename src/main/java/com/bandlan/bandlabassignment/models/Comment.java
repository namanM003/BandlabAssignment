package com.bandlan.bandlabassignment.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private String comment;
    private long commentorId;
    private long commentId;
    private Date creationDateTime;

}
