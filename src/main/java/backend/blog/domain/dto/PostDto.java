package backend.blog.domain.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record PostDto(UUID id, String title, String content, String readingTime, LocalDateTime createdAt,
        LocalDateTime updatedAt) {

}
