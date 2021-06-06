package uz.pdp.muharrir.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.pdp.muharrir.entity.Comment;
import uz.pdp.muharrir.entity.Post;
import uz.pdp.muharrir.entity.User;
import uz.pdp.muharrir.payload.ApiResponse;
import uz.pdp.muharrir.payload.CommentDto;
import uz.pdp.muharrir.payload.PostDto;
import uz.pdp.muharrir.repository.CommentRepository;
import uz.pdp.muharrir.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    PostRepository postRepository;

    public ApiResponse addComment(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setText(commentDto.getText());
        Optional<Post> optionalPost = postRepository.findById(commentDto.getPostId());
        if (optionalPost.isPresent()) {
            return new ApiResponse("Post not found!", false);
        }
        comment.setPost(optionalPost.get());
        commentRepository.save(comment);
        return new ApiResponse("Comment added!", true);
    }

    public ApiResponse editComment(Long id, CommentDto commentDto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (!optionalComment.isPresent()) {
            return new ApiResponse("Comment not found!", false);
        }
        Comment comment = optionalComment.get();
        if (comment.getCreatedByUser().getId() == user.getId()) {
            comment.setText(commentDto.getText());
            commentRepository.save(comment);
            return new ApiResponse("Successfully editing", true);
        }
        return new ApiResponse("Error!", false);
    }

    public ApiResponse deleteComment(Long id) {
        try {
            Optional<Comment> optionalComment = commentRepository.findById(id);
            if (!optionalComment.isPresent()) {
                return new ApiResponse("Comment not found!", false);
            }
            commentRepository.deleteById(id);
            return new ApiResponse("Comment deleted!", true);
        } catch (Exception e) {
            return new ApiResponse("Error deleting comment!", false);
        }
    }

    public ApiResponse deleteMyComment(Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            Optional<Comment> optionalComment = commentRepository.findById(id);
            if (!optionalComment.isPresent()) {
                return new ApiResponse("Comment not found!", false);
            }
            Comment comment = optionalComment.get();
            if (comment.getCreatedByUser().getId() == user.getId()) {
                commentRepository.deleteById(id);
                return new ApiResponse("Comment deleted!", true);
            }
            return new ApiResponse("Error deleting comment!", true);
        } catch (Exception e) {
            return new ApiResponse("Error deleting comment!", false);
        }
    }

    public List<Comment> getAll() {
        List<Comment> all = commentRepository.findAll();
        return all;
    }

}
//KV18138541630
