package com.example.cdnimages.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "storage_topic")
@Document(indexName = "storage_topic")
@Entity
public class StorageTopic {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Field(name = "id",type = FieldType.Long)
  private Long id;

  @Field(name = "type",type = FieldType.Text)
  private String type;

  @Field(name = "url",type = FieldType.Text)
  private String url;

  @Field(name = "created_at",type = FieldType.Date)
  private LocalDateTime createdAt;

  @Field(name = "updated_at",type = FieldType.Date)
  private LocalDateTime updatedAt;

  @JsonBackReference
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "topic_id")
  @Field(name = "topic",type = FieldType.Nested)
  private Topic topic;
}
