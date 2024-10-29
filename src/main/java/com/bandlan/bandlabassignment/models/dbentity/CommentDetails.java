package com.bandlan.bandlabassignment.models.dbentity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CommentDetails")
public class CommentDetails {

    @Id
    @GeneratedValue
    private long commentId;
    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonIgnore
    private Post post;
    private String comment;
    @ManyToOne
    private UserInfo creatorId;
    private Date createdDateTime;
}
