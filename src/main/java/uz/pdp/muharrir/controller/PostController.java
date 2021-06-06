package uz.pdp.muharrir.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.muharrir.aop.CheckPermission;
import uz.pdp.muharrir.entity.Post;
import uz.pdp.muharrir.payload.ApiResponse;
import uz.pdp.muharrir.payload.PostDto;
import uz.pdp.muharrir.service.PostService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/post")
public class PostController {
    @Autowired
    PostService postService;

    @PreAuthorize(value = "hasAuthority('ADD_POST')")
    @PostMapping
    public HttpEntity<?> addPost(@Valid @RequestBody PostDto postDto) {
        ApiResponse apiResponse = postService.addPost(postDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    //    @PreAuthorize(value = "hasAuthority('ADD_ROLE')")
    @CheckPermission(value = "EDIT_POST")
    @PutMapping("{id}")
    public HttpEntity<?> editPost(@PathVariable Long id, @Valid @RequestBody PostDto postDto) {
        ApiResponse apiResponse = postService.editPost(id, postDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAuthority('DELETE_ROLE')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteRole(@RequestParam Long id) {
        ApiResponse apiResponse = postService.deletePost(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAuthority('VIEW_POST')")
    @GetMapping()
    public HttpEntity<?> getAll(@RequestParam Integer page1, @RequestParam Integer size) {
        Post all = postService.getAll(page1, size);
        return ResponseEntity.ok(all);
    }
}
