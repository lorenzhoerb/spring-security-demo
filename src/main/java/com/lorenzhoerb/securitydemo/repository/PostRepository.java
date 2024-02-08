package com.lorenzhoerb.securitydemo.repository;

import com.lorenzhoerb.securitydemo.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
