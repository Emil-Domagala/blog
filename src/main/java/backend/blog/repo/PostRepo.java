package backend.blog.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.blog.domain.PostStatus;
import backend.blog.domain.entities.Category;
import backend.blog.domain.entities.Post;
import backend.blog.domain.entities.Tag;

@Repository
public interface PostRepo extends JpaRepository<Post, UUID> {

    List<Post> findAllByStatus(PostStatus status);

    List<Post> findAllByStatusAndTagsContaining(PostStatus status, Tag tag);

    List<Post> findAllByStatusAndCategory(PostStatus status, Category category);

    List<Post> findAllByStatusAndCategoryAndTagsContaining(PostStatus status, Category category, Tag tag);
}
