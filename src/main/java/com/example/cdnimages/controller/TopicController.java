package com.example.cdnimages.controller;

import com.example.cdnimages.data.request.TopicRequest;
import com.example.cdnimages.data.request.UpdateTopicRequest;
import com.example.cdnimages.service.ITopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/topic")
public class TopicController {

  private final ITopicService topicService;

  @Autowired
  public TopicController(ITopicService topicService) {
    this.topicService = topicService;
  }

  @PostMapping("/create")
  public ResponseEntity<?> createTopic(@ModelAttribute TopicRequest topicRequest) {
    topicService.createTopic(topicRequest);
    return ResponseEntity.ok(null);
  }

  @GetMapping("/all")
  public ResponseEntity<?> getAllTopics(
      @PageableDefault(page = 0, size = 15) @SortDefault.SortDefaults(@SortDefault(sort = "updatedAt", direction = Direction.DESC))
      Pageable pageable) {
    return ResponseEntity.ok(topicService.getAllTopics(pageable));
  }
  @GetMapping("/search/{topicId}")
  public ResponseEntity<?> getTopicById(@PathVariable Long topicId) {
    return ResponseEntity.ok(topicService.getTopicById(topicId));
  }
  @PutMapping("/update")
  public ResponseEntity<?> updateTopic(@RequestBody UpdateTopicRequest updateTopicRequest) {
    topicService.updateTopic(updateTopicRequest);
    return ResponseEntity.ok("Topic updated successfully");
  }
}
