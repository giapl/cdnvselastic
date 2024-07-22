package com.example.cdnimages.controller;

import com.example.cdnimages.data.request.CommentRequest;
import com.example.cdnimages.service.ICommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/comment")
public class CommentController {

  private ICommentService commentService;

  @PostMapping("/create")
  public ResponseEntity<?> createComment(@ModelAttribute CommentRequest commentRequest) {
    commentService.createComment(commentRequest);
    return ResponseEntity.ok(null);
  }

  @GetMapping("/{topicId}")
  public ResponseEntity<?> getCommentById(@PathVariable Long topicId) {
    return ResponseEntity.ok(commentService.getCommentById(topicId));
  }
}
