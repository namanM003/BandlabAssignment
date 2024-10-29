package com.bandlan.bandlabassignment.repository;

import com.bandlan.bandlabassignment.models.dbentity.CommentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentsRepository extends JpaRepository<CommentDetails, Long> {

}
