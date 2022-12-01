package com.example.mapper;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentMapper {
    private Integer commentId;
    private String comment;
    private Integer profileId;
    private String profileName;


}
