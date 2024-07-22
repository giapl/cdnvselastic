package com.example.cdnimages.data.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTopicRequest {

  private Long topicId;
  private String title;
  private String content;
}
