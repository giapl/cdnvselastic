package com.example.cdnimages.mapper;

import com.example.cdnimages.data.entity.StorageTopic;
import com.example.cdnimages.data.response.StorageTopicResponse;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StorageTopicMapper {

  StorageTopicResponse toStorageTopicResponse(StorageTopic storageTopic);

  List<StorageTopicResponse> toStorageTopicResponse(List<StorageTopic> storageTopics);
}
