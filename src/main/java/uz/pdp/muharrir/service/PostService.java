package uz.pdp.muharrir.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.muharrir.entity.Post;
import uz.pdp.muharrir.payload.ApiResponse;
import uz.pdp.muharrir.payload.PostDto;
import uz.pdp.muharrir.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    public ApiResponse addPost(PostDto postDto) {
        Post post = new Post();
        post.setText(postDto.getText());
        post.setTitle(postDto.getTitle());
        post.setUrl(postDto.getUrl());
        postRepository.save(post);
        return new ApiResponse("Post added!", true);
    }

    public ApiResponse editPost(Long id, PostDto postDto) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (!optionalPost.isPresent()) {
            return new ApiResponse("Post not found!", false);
        }

        Post post = optionalPost.get();
        post.setText(postDto.getText());
        post.setTitle(postDto.getTitle());
        post.setUrl(postDto.getUrl());
        postRepository.save(post);
        return new ApiResponse("Successfully editing", true);
    }

    public ApiResponse deletePost(Long id) {
        try {

            Optional<Post> optionalPost = postRepository.findById(id);
            if (!optionalPost.isPresent()) {
                return new ApiResponse("Post not found!", false);
            }
            postRepository.deleteById(id);
            return new ApiResponse("Post deleted!", true);
        } catch (Exception e) {
            return new ApiResponse("Error deleting post!", false);
        }
    }


    public Post getAll(Integer page1, Integer page2) {
        Pageable pageable = PageRequest.of(page1, page2);
        Page<Post> all = postRepository.findAll(pageable);
        return (Post) all;
    }

}
//KV18138541630
