package com.example.cdnimages.data.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "topic")
@Document(indexName = "topic")
@Entity
public class Topic {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Field(name = "id",type = FieldType.Long)
  private Long id;

  @Field(name = "title",type = FieldType.Text)
  private String title;

  @Lob
  @Column(columnDefinition = "TEXT")
  @Field(name = "content",type = FieldType.Text)
  private String content;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  @Field(name = "created_at",type = FieldType.Date)
  private LocalDateTime createdAt;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  @Field(name = "updated_at",type = FieldType.Date)
  private LocalDateTime updatedAt;

  @JsonManagedReference
  @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, orphanRemoval = true)
  @Field(name = "storage_topics",type = FieldType.Nested)
  private List<StorageTopic> storageTopics;
}
