package com.example.cdnimages.elasticRepository;

import com.example.cdnimages.data.entity.StorageTopic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageTopicElasticRepository extends ElasticsearchRepository<StorageTopic, Long> {

}
