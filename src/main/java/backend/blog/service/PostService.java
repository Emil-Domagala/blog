package backend.blog.service;

import java.util.List;
import java.util.UUID;

import backend.blog.domain.CreatePostRequest;
import backend.blog.domain.UpdatePostRequest;
import backend.blog.domain.entities.Post;
import backend.blog.domain.entities.User;

public interface PostService {
    List<Post> getAllPosts(UUID categoryId, UUID tagId);

    List<Post> getDraftPosts(User user);

    Post createPost(CreatePostRequest createPostRequest, User user);

    Post updatePost(UUID postId, UpdatePostRequest updatePostRequest);

    Post getPostById(UUID postId);

    void deletePost(UUID postId);

}
