package com.bandlan.bandlabassignment.repository;


import com.bandlan.bandlabassignment.models.dbentity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, Long> {

}
