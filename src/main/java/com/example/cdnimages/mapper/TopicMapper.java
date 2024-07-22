package com.example.cdnimages.mapper;

import com.example.cdnimages.data.entity.Topic;
import com.example.cdnimages.data.request.UpdateTopicRequest;
import com.example.cdnimages.data.response.TopicResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TopicMapper {

  TopicResponse toTopicResponse(Topic topic);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "updatedAt",expression = "java(java.time.LocalDateTime.now())")
  void updateTopicFromRequest(UpdateTopicRequest updateTopicRequest , @MappingTarget Topic topic);
}
