package backend.blog.service;

import java.util.UUID;

import backend.blog.domain.entities.User;

public interface UserService {
    User getUserById(UUID id);
}
