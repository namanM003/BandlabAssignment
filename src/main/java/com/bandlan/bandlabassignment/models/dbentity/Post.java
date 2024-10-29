package com.bandlan.bandlabassignment.models.dbentity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PostDetails")
public class Post {
    @Id
    @GeneratedValue
    private long id;
    private String caption;
    //This will be linked to userId table.
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserInfo creatorInfo;
    private String imageFileName;
    private Date createdDateTime;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @OrderBy("createdDateTime DESC")
    private List<CommentDetails> commentDetails;
}
