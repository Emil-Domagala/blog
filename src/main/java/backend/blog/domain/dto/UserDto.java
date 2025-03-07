package backend.blog.domain.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserDto(UUID id, String email, String password, String name, LocalDateTime createdAt) {
}
