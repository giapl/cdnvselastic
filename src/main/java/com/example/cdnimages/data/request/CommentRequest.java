package com.example.cdnimages.data.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {

  private String message;
  private Long topicId;
  private MultipartFile image;
}
