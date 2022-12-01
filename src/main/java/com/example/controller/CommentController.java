package com.example.controller;

import com.example.dto.CommentDTO;
import com.example.dto.PostDTO;
import com.example.dto.ProfileDTO;
import com.example.mapper.CommentMapper;
import com.example.mapper.CommentMapper2;
import com.example.mapper.ProfileMapper;
import com.example.mapper.UniversalMapper;
import com.example.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("")
    public ResponseEntity<?> createComment(@RequestBody CommentDTO commentDTO) {
        CommentDTO result = commentService.createComment(commentDTO);
        return ResponseEntity.ok(result);
    }

    @GetMapping("")
    public ResponseEntity<?> getCommentList() {
        List<CommentDTO> commentDTOS = commentService.getCommentList();
        return ResponseEntity.ok(commentDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCommentById(@PathVariable Integer id) {
        CommentDTO commentDTO = commentService.getCommentById(id);
        return ResponseEntity.ok(commentDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id) {
        Boolean result = commentService.deleteById(id);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Integer id,
                                           @RequestBody CommentDTO commentDTO) {
        CommentDTO result = commentService.updateComment(commentDTO, id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/postId")
    public ResponseEntity<?> getByCommentListPostId(@RequestParam("postId") Integer postId,
                                                    @RequestParam("size") Integer size,
                                                    @RequestParam("page") Integer page) {
        Page<CommentDTO> result = commentService.getByCommentListPostId(postId, size, page);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/profileId")
    public ResponseEntity<?> getListOf(@RequestParam("profileId") Integer profileId) {
        List<CommentMapper> result = commentService.getListOf(profileId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/commentId")
    public ResponseEntity<?> getProfileCommentId(@RequestParam("commentId") Integer commentId) {
        ProfileMapper result = commentService.getProfileCommentId(commentId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/profileCount")
    public ResponseEntity<?> getCount(@RequestParam("profileCount") Integer profileId) {
        Long result = commentService.getCount(profileId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/postIdCount")
    public ResponseEntity<?> getCountPost(@RequestParam("postIdCount") Integer postId) {
        Long result = commentService.getCountPost(postId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/postLastComment")
    public ResponseEntity<?> getLastComment(@RequestParam("postLastComment") Integer postId) {
        CommentMapper2 result = commentService.getLastComment(postId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/universal")
    public ResponseEntity<?> getUniversal(@RequestParam("postId") Integer profileId,
                                                    @RequestParam("size") Integer size,
                                                    @RequestParam("page") Integer page) {
        Page<UniversalMapper> result = commentService.getUniversal(profileId, size, page);
        return ResponseEntity.ok(result);
    }




    @GetMapping("/commentListDateBetween/{from}/{to}")
    public ResponseEntity<?> getDateBetween(@PathVariable("from") String from , @PathVariable("to") String to ) {
        List<CommentDTO> result = commentService.getDateBetween(from,to);
        return ResponseEntity.ok(result);
    }

}
