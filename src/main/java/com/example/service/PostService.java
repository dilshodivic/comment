package com.example.service;

import com.example.dto.PostDTO;
import com.example.entity.PostEntity;
import com.example.exceptions.ItemNotFoundException;
import com.example.exceptions.PostCreateException;
import com.example.mapper.PostMapper;
import com.example.mapper.PostMapper2;
import com.example.mapper.PostMapper3;
import com.example.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ProfileService profileService;

    public PostDTO createPost(PostDTO postDTO) {
        postDTO.setCreatedDate(LocalDateTime.now());
        PostEntity postEntity = toEntity(postDTO);

        PostEntity save = postRepository.save(postEntity);
        if(save.getId()==0){
            throw new PostCreateException("Something went wrong");
        }
        postDTO.setId(postEntity.getId());
        return postDTO;
    }

    public List<PostDTO> getList() {
        List<PostEntity> postEntities = postRepository.findAll();

        List<PostDTO> postDTOS = new ArrayList<>();

        for (PostEntity postEntity : postEntities) {
            PostDTO postDTO = toDTO(postEntity);
            postDTOS.add(postDTO);
        }

        return postDTOS;
    }
    public PostDTO getPostById(Integer id) {
        PostEntity postEntity = postRepository.findById(id).orElseThrow(()->{
            throw new ItemNotFoundException("Item not found");
        });
        return toDTO(postEntity);
    }

    public Boolean deleteById(Integer id) {
        get(id);
        postRepository.deleteById(id);
        return true;
    }


    public PostEntity get(Integer id) {
        return postRepository.findById(id).orElseThrow(() -> {
           throw new ItemNotFoundException("Item not found");
        });
    }
    public PostDTO toDTO(PostEntity postEntity) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(postEntity.getId());
        postDTO.setTitle(postEntity.getTitle());
        postDTO.setContent(postEntity.getContent());
        postDTO.setProfile(profileService.toDTO(postEntity.getProfile()));
        postDTO.setCreatedDate(postEntity.getCreatedDate());
        return postDTO;
    }
    public PostEntity toEntity(PostDTO postDTO) {
        PostEntity postEntity = new PostEntity();
        postEntity.setContent(postDTO.getContent());
        postEntity.setTitle(postDTO.getTitle());
        postEntity.setProfile(profileService.get(postDTO.getProfileId()));
        postEntity.setCreatedDate(postDTO.getCreatedDate());
        return postEntity;
    }

    public Boolean updatePost(PostDTO postDTO, Integer id) {
        PostEntity postEntity = get(id);

        postEntity.setTitle(postDTO.getTitle());
        postEntity.setContent(postDTO.getContent());
        postEntity.setProfile(profileService.get(postDTO.getProfileId()));

        postRepository.update(postEntity.getTitle(), postEntity.getContent(),
                postEntity.getProfile(), postEntity.getId());
        return true;
    }

    public Page<PostDTO> getPostByProfileIdWithPagination(Integer profileId,
                                                          Integer size, Integer page) {
//        Sort sort = Sort.by(Sort.Direction.ASC, "createdDate");

        Pageable paging = PageRequest.of(page, size);

        Page<PostEntity> postEntities = postRepository.findAllByProfile_Id(profileId, paging);

        List<PostEntity> postEntityList = postEntities.getContent();

        List<PostDTO> postDTOS = new ArrayList<>();

        for (PostEntity postEntity : postEntityList) {
            PostDTO postDTO = toDTO(postEntity);
            postDTOS.add(postDTO);
        }

        Page<PostDTO> response =new PageImpl(postDTOS, paging, postEntities.getTotalElements());

        return response;
    }

    public PostMapper getByIdTitleCreated(Integer postId) {
        postRepository.findById(postId).orElseThrow(() -> {
            throw new ItemNotFoundException("Not found");
        });
        return  postRepository.getPostById(postId);

    }

    public List<PostDTO> getProfileIdPostList(Integer profileId) {

        List<PostEntity> profileIdPostList = postRepository.getProfileIdPostList(profileId);
        return getPostDTOS(profileIdPostList);
    }

    private List<PostDTO> getPostDTOS(List<PostEntity> profileIdPostList) {
        List<PostDTO> postDTOS = new LinkedList<>() ;

        for (PostEntity postEntity : profileIdPostList) {
            PostDTO postDTO = toDTO(postEntity);
            postDTOS.add(postDTO);
        }

        return postDTOS;
    }

    public List<PostDTO> getLimitPostLast(Integer profileId) {

      List<PostEntity> entityList =  postRepository.getLimitPostLast(profileId);
        return getPostDTOS(entityList);
    }

    public Long getCountPost(Integer profileId) {
        return postRepository.getCountPost(profileId);
    }

    public Long getTodayCount(Integer profileId) {
        return postRepository.getTodayCount(profileId);
    }

    public List<PostMapper2> getPostIdProfileIdCreatedDate(Integer profileId) {
        return postRepository.getPostIdProfileIdCreatedDate(profileId);
    }

    public List<PostMapper2> getPostIdProfileIdCreatedDateList() {
        return postRepository.getPostIdProfileIdCreatedDateList();
    }

    public List<PostDTO> postFullToday() {

      List<PostEntity> list =  postRepository.postFullToday();
        List<PostDTO> postDTOS = new LinkedList<>() ;
        for (PostEntity postEntity : list) {
            PostDTO postDTO = toDTOPost(postEntity);
            postDTOS.add(postDTO);
        }
        return postDTOS;

    }
    public PostDTO toDTOPost(PostEntity postEntity) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(postEntity.getId());
        postDTO.setTitle(postEntity.getTitle());
        postDTO.setContent(postEntity.getContent());
        postDTO.setProfileId(postEntity.getProfile().getId());
        postDTO.setCreatedDate(postEntity.getCreatedDate());
        return postDTO;
    }

    public List<PostMapper3> postFullTodayProfileName() {
        return postRepository.postFullTodayProfileName();
    }
}
