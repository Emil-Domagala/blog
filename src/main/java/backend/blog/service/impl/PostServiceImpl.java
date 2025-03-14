package backend.blog.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import backend.blog.domain.CreatePostRequest;
import backend.blog.domain.PostStatus;
import backend.blog.domain.UpdatePostRequest;
import backend.blog.domain.entities.Category;
import backend.blog.domain.entities.Post;
import backend.blog.domain.entities.Tag;
import backend.blog.domain.entities.User;
import backend.blog.repo.PostRepo;
import backend.blog.service.CategoryService;
import backend.blog.service.PostService;
import backend.blog.service.TagService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepo postRepo;
    private final CategoryService categoryService;
    private final TagService tagService;

    private static final int WORDS_PER_MINUTE = 200;

    @Transactional(readOnly = true)
    @Override
    public List<Post> getAllPosts(UUID categoryId, UUID tagId) {
        if (categoryId != null && tagId != null) {
            Category category = categoryService.getCategoryById(categoryId);
            Tag tag = tagService.getTagById(tagId);
            return postRepo.findAllByStatusAndCategoryAndTagsContaining(PostStatus.PUBLISHED, category, tag);
        }
        if (categoryId != null) {
            Category category = categoryService.getCategoryById(categoryId);
            return postRepo.findAllByStatusAndCategory(PostStatus.PUBLISHED, category);
        }
        if (tagId != null) {
            Tag tag = tagService.getTagById(tagId);
            return postRepo.findAllByStatusAndTagsContaining(PostStatus.PUBLISHED, tag);
        }
        return postRepo.findAllByStatus(PostStatus.PUBLISHED);
    }

    @Override
    public List<Post> getDraftPosts(User user) {
        return postRepo.findAllByStatusAndAuthor(PostStatus.DRAFT, user);
    }

    @Transactional
    @Override
    public Post createPost(CreatePostRequest createPostRequest, User user) {
        Post newPost = new Post();
        newPost.setTitle(createPostRequest.getTitle());
        newPost.setContent(createPostRequest.getContent());
        newPost.setStatus(createPostRequest.getStatus());
        newPost.setAuthor(user);
        newPost.setReadingTime(calculateReadingTime(createPostRequest.getContent()));

        Category category = categoryService.getCategoryById(createPostRequest.getCategoryId());
        newPost.setCategory(category);

        Set<UUID> tagIds = createPostRequest.getTagIds();
        List<Tag> tags = tagService.getTagsByIds(tagIds);
        newPost.setTags(new HashSet<>(tags));

        return postRepo.save(newPost);
    }

    private Integer calculateReadingTime(String content) {
        if (content == null || content.isEmpty()) {
            return 0;
        }
        int wordsCount = content.trim().split("\\s+").length;
        return (int) Math.ceil((double) wordsCount / WORDS_PER_MINUTE);

    }

    @Transactional
    @Override
    public Post updatePost(UUID postId, UpdatePostRequest updatePostRequest) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Couldn't find post with id: " + postId));

        post.setTitle(updatePostRequest.getTitle());
        post.setContent(updatePostRequest.getContent());
        post.setStatus(updatePostRequest.getStatus());
        post.setReadingTime(calculateReadingTime(updatePostRequest.getContent()));
        UUID updatePostRequestCategoryId = updatePostRequest.getCategoryId();

        if (!post.getCategory().getId().equals(updatePostRequestCategoryId)) {
            Category newCategory = categoryService.getCategoryById(updatePostRequestCategoryId);
            post.setCategory(newCategory);
        }
        Set<UUID> exTagIds = post.getTags().stream().map(Tag::getId).collect(Collectors.toSet());
        Set<UUID> updatePostRequestTagIds = updatePostRequest.getTagIds();
        if (!exTagIds.equals(updatePostRequestTagIds)) {
            List<Tag> newTags = tagService.getTagsByIds(updatePostRequestTagIds);
            post.setTags(new HashSet<>(newTags));
        }
        return postRepo.save(post);

    }

    @Transactional
    @Override
    public void deletePost(UUID postId) {
        Post post = getPostById(postId);
        postRepo.delete(post);
    }

    @Override
    public Post getPostById(UUID postId) {
        return postRepo.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Couldn't find post with id: " + postId));
    }
}
