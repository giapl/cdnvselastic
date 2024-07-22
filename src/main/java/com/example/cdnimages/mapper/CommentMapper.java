package com.example.cdnimages.mapper;

import com.example.cdnimages.data.entity.Comment;
import com.example.cdnimages.data.response.CommentResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {

  CommentResponse convertToCommentResponse(Comment comment);
}
