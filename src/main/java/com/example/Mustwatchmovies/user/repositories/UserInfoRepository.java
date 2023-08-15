package com.example.Mustwatchmovies.user.repositories;

import com.example.Mustwatchmovies.user.models.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo,Integer> {
    @Query("SELECT u FROM UserInfo u WHERE u.userId = :userId")
    UserInfo findByUserId(@Param("userId") String userId);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM UserInfo u WHERE u.userName = :username")
    boolean existsByUsername(String username);
}
