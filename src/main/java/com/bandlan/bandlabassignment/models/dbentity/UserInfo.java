package com.bandlan.bandlabassignment.models.dbentity;

import com.bandlan.bandlabassignment.models.dbentity.Post;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "UserInfo")
public class UserInfo {
    @Id
    @GeneratedValue
    private long id;

    private String userDisplayName;

    @OneToMany(mappedBy = "creatorInfo", cascade = CascadeType.ALL)
    private List<Post> post;

    //Can have additional fields like first name, last name, birthday etc etc based on fuctionality.
}
