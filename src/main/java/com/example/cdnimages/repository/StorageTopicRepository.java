package com.example.cdnimages.repository;

import com.example.cdnimages.data.entity.StorageTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageTopicRepository extends JpaRepository<StorageTopic,Long>{

}
