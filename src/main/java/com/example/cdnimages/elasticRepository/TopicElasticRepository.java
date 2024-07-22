package com.example.cdnimages.elasticRepository;

import com.example.cdnimages.data.entity.Topic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicElasticRepository extends ElasticsearchRepository<Topic, Long> {

}
