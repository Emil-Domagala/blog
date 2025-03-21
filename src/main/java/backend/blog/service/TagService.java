package backend.blog.service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import backend.blog.domain.entities.Tag;

public interface TagService {

    List<Tag> getTags();

    Tag getTagById(UUID id);

    List<Tag> createTags(Set<String> tagNames);
    List<Tag> getTagsByIds(Set<UUID> ids);

    void deleteTag(UUID id);

}
