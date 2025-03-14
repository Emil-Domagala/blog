package backend.blog.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import backend.blog.domain.entities.User;
import backend.blog.repo.UserRepo;
import backend.blog.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;

    @Override
    public User getUserById(UUID id) {
        return userRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with Id: " + id));
    }

}
