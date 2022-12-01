package com.example.mapper;
import java.time.LocalDateTime;
public interface PostMapper3 {

      Integer getPostId();
      String getTitle();
      String getPostContent();
      LocalDateTime getCreatedDate();
      String getProfileName();

}
