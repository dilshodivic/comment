package com.example.controller;

import com.example.dto.PostDTO;
import com.example.mapper.PostMapper;
import com.example.mapper.PostMapper2;
import com.example.mapper.PostMapper3;
import com.example.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("")
    public ResponseEntity<?> createPost(@RequestBody PostDTO postDTO){
        PostDTO result = postService.createPost(postDTO);
        return ResponseEntity.ok(result);
    }

    @GetMapping("")
    public ResponseEntity<?> getList(){
        List<PostDTO> postDTOList = postService.getList();
        return ResponseEntity.ok(postDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Integer id){
        PostDTO result = postService.getPostById(id);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id){
        Boolean result = postService.deleteById(id);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Integer id,
                                        @RequestBody PostDTO postDTO){
        Boolean result = postService.updatePost(postDTO, id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/profileId")
    public ResponseEntity<?> getPostByProfileIdWithPagination(@RequestParam("profileId") Integer profileId,
                                                              @RequestParam("size") Integer size,
                                                              @RequestParam("page") Integer page){
        Page<PostDTO> postDTOS = postService.getPostByProfileIdWithPagination(profileId, size, page);
        return ResponseEntity.ok(postDTOS);
    }

    @GetMapping("/title/{id}")
    public ResponseEntity<?> getByIdTitleCreated(@PathVariable("id") Integer postId){
        PostMapper result = postService.getByIdTitleCreated(postId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<?> getProfileIdPostList(@PathVariable("id") Integer profileId){
        List<PostDTO> result = postService.getProfileIdPostList(profileId);
        return ResponseEntity.ok(result);
    }



    @GetMapping("/profile/limit/{id}")
    public ResponseEntity<?> getLimitPostLast(@PathVariable("id") Integer profileId){
        List<PostDTO> result = postService.getLimitPostLast(profileId);
        return ResponseEntity.ok(result);
    }



    @GetMapping("/profile/count/{id}")
    public ResponseEntity<?> getCountPost(@PathVariable("id") Integer profileId){
        Long result = postService.getCountPost(profileId);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/profile/today/{id}")
    public ResponseEntity<?> getTodayCount(@PathVariable("id") Integer profileId){
        Long result = postService.getTodayCount(profileId);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/profile_post_date/{id}")
    public ResponseEntity<?> getPostIdProfileIdCreatedDate(@PathVariable("id") Integer profileId){
        List<PostMapper2> result = postService.getPostIdProfileIdCreatedDate(profileId);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/profile_post_date")
    public ResponseEntity<?> getPostIdProfileIdCreatedDate(){
        List<PostMapper2> result = postService.getPostIdProfileIdCreatedDateList();
        return ResponseEntity.ok(result);
    }
    @GetMapping("/postFull")
    public ResponseEntity<?> postFullToday(){
        List<PostDTO> result = postService.postFullToday();
        return ResponseEntity.ok(result);
    }
    @GetMapping("/postFullName")
    public ResponseEntity<?> postFullTodayProfileName(){
        List<PostMapper3> result = postService.postFullTodayProfileName();
        return ResponseEntity.ok(result);
    }



}
