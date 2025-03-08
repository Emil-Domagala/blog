package backend.blog.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.blog.domain.entities.User;

@Repository
public interface UserRepo extends JpaRepository<User,UUID> {

    
} 
