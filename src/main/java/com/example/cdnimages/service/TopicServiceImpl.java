// src/main/java/com/example/cdnimages/service/TopicServiceImpl.java
package com.example.cdnimages.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.cdnimages.data.entity.StorageTopic;
import com.example.cdnimages.data.entity.Topic;
import com.example.cdnimages.data.request.TopicRequest;
import com.example.cdnimages.data.request.UpdateTopicRequest;
import com.example.cdnimages.data.response.TopicResponse;
import com.example.cdnimages.mapper.TopicMapper;
import com.example.cdnimages.repository.StorageTopicRepository;
import com.example.cdnimages.repository.TopicRepository;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class TopicServiceImpl implements ITopicService {

  private final TopicRepository topicRepository;
  private final Cloudinary cloudinary;
  private final StorageTopicRepository storageTopicRepository;
  private final TopicMapper topicMapper;

  @Autowired
  public TopicServiceImpl(TopicRepository topicRepository, Cloudinary cloudinary,
      StorageTopicRepository storageTopicRepository, TopicMapper topicMapper) {
    this.topicRepository = topicRepository;
    this.cloudinary = cloudinary;
    this.storageTopicRepository = storageTopicRepository;
    this.topicMapper = topicMapper;
  }

  @Override
  @Transactional
  public void createTopic(TopicRequest topicRequest) {
    Topic topic = new Topic();
    topic.setTitle(topicRequest.getTitle());
    topic.setCreatedAt(LocalDateTime.now());
    topic.setUpdatedAt(LocalDateTime.now());

    StringBuilder contentBuilder = new StringBuilder();
    if (topicRequest.getImage() != null) {
      for (MultipartFile image : topicRequest.getImage()) {
        try {
          // Upload image to Cloudinary
          Map<String, Object> uploadResult = uploadImagesCdn(List.of(image));
          // Get the image URL
          String imageUrl = (String) uploadResult.get("url");

          // Append the image URL to the content with specified width and height
          contentBuilder.append("<div class='content'><div class='content-text'>")
              .append(topicRequest.getContent())
              .append("</div>")
              .append("<div>")
              .append("<img src='")
              .append(imageUrl)
              .append("' width='658' height='439' class='content-image'/>")
              .append("</div></div>");
          topic.setContent(contentBuilder.toString());
          topic = topicRepository.save(topic);

          // Get the image type
          String imageType = image.getContentType();


          // Save the image URL to the database
          StorageTopic storageTopic = new StorageTopic();
          storageTopic.setUrl(imageUrl);
          storageTopic.setType(imageType);
          storageTopic.setCreatedAt(LocalDateTime.now());
          storageTopic.setUpdatedAt(LocalDateTime.now());
          storageTopic.setTopic(topic);
          storageTopicRepository.save(storageTopic);
        } catch (IOException e) {
          log.error("Error uploading image to CDN: {}", e.getMessage());
        }
      }
    } else {
      topic.setContent("<div class='content'><div class='content-text'>" + topicRequest.getContent() + "</div></div>");
      topicRepository.save(topic);
    }
  }

  @Async
  public Map<String,Object> uploadImagesCdn(List<MultipartFile> file) throws IOException {
    // Upload image to Cloudinary
    Map uploadResult = null;
    try {
      // Upload image to Cloudinary
      for (MultipartFile image : file) {
        uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
        log.info("Image uploaded to CDN: {}", uploadResult.get("url"));
      }
    } catch (IOException e) {
      log.error("Error uploading image to CDN: {}", e.getMessage());
      throw e;
    }
    return uploadResult;
  }

  @Override
  public Page<TopicResponse> getAllTopics(Pageable pageable) {
    Page<Topic> topicPage = topicRepository.findAllByOrderByIdDesc(pageable);
    return topicPage.map(topicMapper::toTopicResponse);
  }

  @Override
  public TopicResponse getTopicById(Long topicId) {
    return topicRepository.findById(topicId)
        .map(topicMapper::toTopicResponse)
        .orElseThrow(() -> new RuntimeException("Topic not found"));
  }

  @Override
  public void updateTopic(UpdateTopicRequest updateTopicRequest) {
    Topic topic = topicRepository.findById(updateTopicRequest.getTopicId())
        .orElseThrow(() -> new RuntimeException("Topic not found"));

    topicMapper.updateTopicFromRequest(updateTopicRequest, topic);
    topicRepository.save(topic);
  }
}