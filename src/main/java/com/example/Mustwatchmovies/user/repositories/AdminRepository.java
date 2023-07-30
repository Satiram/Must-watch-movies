package com.example.Mustwatchmovies.user.repositories;

import com.example.Mustwatchmovies.user.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdminRepository extends JpaRepository<Admin,Integer> {
    @Query("SELECT u FROM Admin u WHERE u.userName = :username")
    Admin findByUsername(@Param("username") String username);
}
