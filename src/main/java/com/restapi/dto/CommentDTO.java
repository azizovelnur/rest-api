package com.restapi.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private String comment;
    private Long userId;
    private Long postId;
}
