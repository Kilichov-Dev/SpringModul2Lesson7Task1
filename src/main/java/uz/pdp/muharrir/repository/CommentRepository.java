package uz.pdp.muharrir.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.muharrir.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
