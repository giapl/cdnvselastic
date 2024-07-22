package com.example.cdnimages.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.cdnimages.data.entity.Comment;
import com.example.cdnimages.data.entity.StorageComment;
import com.example.cdnimages.data.request.CommentRequest;
import com.example.cdnimages.data.response.CommentResponse;
import com.example.cdnimages.mapper.CommentMapper;
import com.example.cdnimages.repository.CommentRepository;
import com.example.cdnimages.repository.StorageCommentRepository;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
@Slf4j
public class CommentServiceImpl implements ICommentService {

  private CommentRepository commentRepository;
  private Cloudinary cloudinary;
  private StorageCommentRepository storageCommentRepository;
  private CommentMapper commentMapper;

  @Override
  public void createComment(CommentRequest commentRequest) {
    Comment comment = new Comment();
    comment.setTopicId(commentRequest.getTopicId());
    comment.setCreatedAt(LocalDateTime.now());
    comment.setUpdatedAt(LocalDateTime.now());

    StringBuilder messageBuilder = new StringBuilder();

    if (commentRequest.getImage() != null) {
      try {
        Map uploadResult = uploadIMage(commentRequest.getImage());
        String imageUrl = (String) uploadResult.get("url");

        messageBuilder
            .append("<div class='message-wrapper'>")
            .append("<p>")
            .append(commentRequest.getMessage())
            .append("</p>")
            .append("<div class='images-wrapper'>")
            .append("<img src='")
            .append(imageUrl)
            .append("' alt='image' />")
            .append("</div>")
            .append("</div>");
        comment.setMessage(messageBuilder.toString());
        commentRepository.save(comment);

        String imageType = (String) uploadResult.get("format");
        String imageName = (String) uploadResult.get("public_id");

        // Save image to database
        StorageComment storageComment = new StorageComment();
        storageComment.setName(imageName);
        storageComment.setType(imageType);
        storageComment.setUrl(imageUrl);
        storageComment.setCreatedAt(LocalDateTime.now());
        storageComment.setUpdatedAt(LocalDateTime.now());
        storageComment.setCommentId(comment.getId());
        storageCommentRepository.save(storageComment);
      } catch (IOException e) {
        log.error("Error uploading image: {}", e.getMessage());
      }
    } else {
      comment.setMessage("<div class='message-wrapper'><p>" + commentRequest.getMessage() + "</p></div>");
    }
    commentRepository.save(comment);

  }

  @Override
  public List<CommentResponse> getCommentById(Long TopicId) {
    return commentRepository.findByTopicId(TopicId)
        .stream()
        .map(commentMapper::convertToCommentResponse)
        .collect(Collectors.toList());
  }

  private Map<String, Object> uploadIMage(MultipartFile images) throws IOException {

    String typeImage = images.getContentType();
    String nameImage = images.getOriginalFilename();

    // Upload image to CDN
    Map uploadResult = null;
    try {
      uploadResult = cloudinary.uploader().upload(images.getBytes(), ObjectUtils.emptyMap());
      log.info("Image uploaded to CDN: {}", uploadResult.get("url"));
    } catch (IOException e) {
      log.error("Error uploading image to CDN: {}", e.getMessage());
    }

    uploadResult.put("format", typeImage);
    uploadResult.put("public_id", nameImage);

    return uploadResult;
  }
}
