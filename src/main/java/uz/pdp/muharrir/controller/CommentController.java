package uz.pdp.muharrir.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.muharrir.aop.CheckPermission;
import uz.pdp.muharrir.entity.Comment;
import uz.pdp.muharrir.payload.ApiResponse;
import uz.pdp.muharrir.payload.CommentDto;
import uz.pdp.muharrir.service.CommentService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/role")
public class CommentController {
    @Autowired
    CommentService commentService;

    @PreAuthorize(value = "hasAuthority('ADD_COMMENT')")
    @PostMapping
    public HttpEntity<?> addComment(@Valid @RequestBody CommentDto commentDto) {
        ApiResponse apiResponse = commentService.addComment(commentDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    //    @PreAuthorize(value = "hasAuthority('ADD_ROLE')")
    @CheckPermission(value = "EDIT_COMMENT")
    @PutMapping("{id}")
    public HttpEntity<?> editComment(@PathVariable Long id, @Valid @RequestBody CommentDto commentDto) {
        ApiResponse apiResponse = commentService.editComment(id, commentDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAuthority('DELETE_COMMENT')")
    @DeleteMapping()
    public HttpEntity<?> deleteComment(@RequestParam Long id) {
        ApiResponse apiResponse = commentService.deleteComment(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAuthority('DELETE_MY_COMMENT')")
    @DeleteMapping()
    public HttpEntity<?> deleteMyComment(@RequestParam Long id) {
        ApiResponse apiResponse = commentService.deleteMyComment(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAuthority('VIEW_COMMENT')")
    @GetMapping()
    public HttpEntity<?> getAll() {
        List<Comment> all = commentService.getAll();
        if (all.isEmpty()) return ResponseEntity.status(409).body(all);
        return ResponseEntity.ok(all);
    }
}
