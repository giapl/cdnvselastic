package com.example.cdnimages.service;

import com.example.cdnimages.data.request.TopicRequest;
import com.example.cdnimages.data.request.UpdateTopicRequest;
import com.example.cdnimages.data.response.TopicResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITopicService {

  // Create a new topic
  void createTopic(TopicRequest topicRequest);

  // Get all topics
  Page<TopicResponse> getAllTopics(Pageable pageable);

  // Get topic by id
  TopicResponse getTopicById(Long topicId);

  void updateTopic(UpdateTopicRequest updateTopicRequest);
}
