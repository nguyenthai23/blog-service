package vn.example.blog.dto;

import java.util.List;

import lombok.Data;

@Data
public class BlogDto {
    private long id;
    private List<String> referencees;
}