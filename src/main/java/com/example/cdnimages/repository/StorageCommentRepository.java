package com.example.cdnimages.repository;

import com.example.cdnimages.data.entity.StorageComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageCommentRepository extends JpaRepository<StorageComment, Long> {

}
