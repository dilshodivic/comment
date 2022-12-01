package com.example.dto;

import com.example.entity.ProfileEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostDTO {
    private Integer id;
    private String title;
    private String content;
    private LocalDateTime createdDate;

    private Integer profileId;
    private ProfileDTO profile;
}
