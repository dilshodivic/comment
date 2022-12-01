package com.example.mapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostMapper2 {

     private Integer postId;
     private Integer profileId;
     private LocalDateTime getCreatedDate; // createdDate

}
