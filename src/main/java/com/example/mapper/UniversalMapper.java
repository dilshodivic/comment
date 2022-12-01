package com.example.mapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UniversalMapper {
    private Integer postId;
    private String postTitle;
    private Integer commentId;
    private String commentContent;
    private LocalDateTime commentCreatedDate;
    private Integer profileId;
    private String profileName;
}
