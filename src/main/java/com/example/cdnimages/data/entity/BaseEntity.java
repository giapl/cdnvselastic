package com.example.cdnimages.data.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity {

  protected String createdBy;
  protected String updatedBy;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss",shape = JsonFormat.Shape.STRING)
  protected LocalDateTime createdAt;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss",shape = JsonFormat.Shape.STRING)
  protected LocalDateTime updatedAt;

}
