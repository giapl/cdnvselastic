package com.example.cdnimages.service;

import com.example.cdnimages.data.entity.Comment;
import com.example.cdnimages.data.request.CommentRequest;
import com.example.cdnimages.data.response.CommentResponse;
import java.util.List;

public interface ICommentService {

  void createComment(CommentRequest commentRequest);

  List<CommentResponse> getCommentById(Long TopicId);
}
