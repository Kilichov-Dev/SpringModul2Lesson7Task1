package uz.pdp.muharrir.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.muharrir.entity.Post;

public interface PostRepository extends JpaRepository<Post,Long> {
}
