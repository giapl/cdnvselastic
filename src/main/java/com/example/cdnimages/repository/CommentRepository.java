package com.example.cdnimages.repository;

import com.example.cdnimages.data.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

  @Query(value = "select * from comment where comment.topic_id = :topicId", nativeQuery = true)
  List<Comment> findByTopicId(@Param("topicId") Long topicId);
}
