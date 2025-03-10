package backend.blog.service;

import java.util.List;
import java.util.UUID;

import backend.blog.domain.entities.Post;

public interface PostService {
    List<Post>getAllPosts(UUID categoryId, UUID tagId);
}
