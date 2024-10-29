package com.bandlan.bandlabassignment.repository;

import com.bandlan.bandlabassignment.models.dbentity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p LEFT JOIN p.commentDetails comments GROUP BY p ORDER BY COUNT(comments) DESC")
    Page<Post> findPostsByCommentCount(Pageable pageable);


}
