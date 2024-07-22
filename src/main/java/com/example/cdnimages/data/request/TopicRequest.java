package com.example.cdnimages.data.request;

import java.util.List;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class TopicRequest {

  private String title;
  private String content;
  private List<MultipartFile> image;
}