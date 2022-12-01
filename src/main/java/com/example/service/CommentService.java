package com.example.service;

import com.example.dto.CommentDTO;
import com.example.entity.CommentEntity;
import com.example.exceptions.CommentCreateException;
import com.example.exceptions.ItemNotFoundException;
import com.example.mapper.CommentMapper;
import com.example.mapper.CommentMapper2;
import com.example.mapper.ProfileMapper;
import com.example.mapper.UniversalMapper;
import com.example.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private ProfileService profileService;

    public CommentDTO createComment(CommentDTO commentDTO) {
        commentDTO.setCreated_date(LocalDateTime.now());

        CommentEntity commentEntity = toEntity(commentDTO);
        commentRepository.save(commentEntity);

        commentDTO.setId(commentEntity.getId());
        return commentDTO;
    }

    public List<CommentDTO> getCommentList() {
        List<CommentEntity> commentEntities = commentRepository.findAll();

        List<CommentDTO> commentDTOS = new ArrayList<>();

        for (CommentEntity commentEntity : commentEntities) {
            CommentDTO commentDTO = toDTO(commentEntity);
            commentDTOS.add(commentDTO);
        }

        return commentDTOS;
    }

    public CommentDTO getCommentById(Integer id) {
        CommentEntity commentEntity = commentRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Item not found");
        });
        return toDTO(commentEntity);
    }


    private CommentDTO toDTO(CommentEntity commentEntity) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setPost(postService.toDTO(commentEntity.getPost()));
        commentDTO.setProfile(profileService.toDTO(commentEntity.getProfile()));
        commentDTO.setContent(commentEntity.getContent());
        commentDTO.setCreated_date(commentEntity.getCreated_date());
        commentDTO.setId(commentEntity.getId());
        return commentDTO;
    }


    private CommentDTO toDtoList(CommentEntity commentEntity) {
        CommentDTO commentDTO = new CommentDTO();
//        commentDTO.setPost(postService.toDTO(commentEntity.getPost()));
//        commentDTO.setProfile(profileService.toDTO(commentEntity.getProfile()));
        commentDTO.setContent(commentEntity.getContent());
        commentDTO.setCreated_date(commentEntity.getCreated_date());
        commentDTO.setId(commentEntity.getId());
        return commentDTO;
    }

//    private CommentDTO toDTOBetween(CommentEntity commentEntity) {
//        CommentDTO commentDTO = new CommentDTO();
//        commentDTO.setPost(postService.toDTO(commentEntity.getPost()));
//        commentDTO.setProfile(profileService.toDTO(commentEntity.getProfile()));
//        commentDTO.setContent(commentEntity.getContent());
//        commentDTO.setCreated_date(commentEntity.getCreated_date());
//        commentDTO.setId(commentEntity.getId());
//        return commentDTO;
//    }

    private CommentEntity toEntity(CommentDTO commentDTO) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setContent(commentDTO.getContent());
        commentEntity.setPost(postService.get(commentDTO.getPostId()));
        commentEntity.setProfile(profileService.get(commentDTO.getProfileId()));
        commentEntity.setCreated_date(commentDTO.getCreated_date());
        return commentEntity;
    }


    public Boolean deleteById(Integer id) {
        CommentEntity commentEntity = get(id);
        commentRepository.deleteById(id);
        return true;
    }

    private CommentEntity get(Integer id) {
        return commentRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Item not found");
        });
    }

    public CommentDTO updateComment(CommentDTO commentDTO, Integer id) {
        CommentEntity commentEntity = get(id);

        commentEntity.setContent(commentDTO.getContent());
        commentEntity.setPost(postService.get(commentDTO.getPostId()));
        commentEntity.setProfile(profileService.get(commentDTO.getProfileId()));

        int update = commentRepository.updateComment(commentEntity.getContent(), commentEntity.getProfile(), commentEntity.getPost(), id);

        if (update == 0) {
            throw new CommentCreateException("Comment create exception");
        }
        commentDTO.setId(id);
        return commentDTO;
    }

    public Page<CommentDTO> getByCommentListPostId(Integer postId, Integer size, Integer page) {
        Pageable paging = PageRequest.of(page, size);

        Page<CommentEntity> commentEntities = commentRepository.findAllByPost_Id(postId, paging);

        List<CommentEntity> list = commentEntities.getContent();

        List<CommentDTO> commentDTOS = new LinkedList<>();

        for (CommentEntity comment : list) {
            CommentDTO dto = toDtoList(comment);
            commentDTOS.add(dto);
        }

        Page<CommentDTO> response = new PageImpl(commentDTOS, paging, commentEntities.getTotalElements());

        return response;
    }

    public List<CommentMapper> getListOf(Integer profileId) {
        return commentRepository.getListOf(profileId);
    }

    public ProfileMapper getProfileCommentId(Integer commentId) {
        return commentRepository.getProfileCommentId(commentId);
    }

    public Long getCount(Integer profileId) {
        return commentRepository.getCount(profileId);
    }

    public Long getCountPost(Integer postId) {
        return commentRepository.getCountPost(postId);
    }

    public CommentMapper2 getLastComment(Integer postId) {
        return commentRepository.getLastComment(postId);
    }


    public Page<UniversalMapper> getUniversal(Integer profileId, Integer size, Integer page) {
        Pageable paging = PageRequest.of(page, size);
        Page<UniversalMapper> universalMappers = commentRepository.universal_Id(profileId, paging);
        List<UniversalMapper> list = universalMappers.getContent();


        Page<UniversalMapper> response = new PageImpl(list, paging, universalMappers.getTotalElements());
        return response;

    }

    public List<CommentDTO> getDateBetween(String from, String to) {
        DateTimeFormatter from1 = DateTimeFormatter.ofPattern("yyyy:MM:dd");
        DateTimeFormatter to1 = DateTimeFormatter.ofPattern("yyyy:MM:dd");
        LocalDateTime from2 = LocalDateTime.parse(from, from1);
        LocalDateTime to2 = LocalDateTime.parse(to, to1);
        List<CommentEntity> byCreatedDate = commentRepository.getByCreated_dateBetween(from2,to2);
        List<CommentDTO> dto = new LinkedList<>();
        for (CommentEntity entity : byCreatedDate) {
            CommentDTO commentDTO = toDTO(entity);
            dto.add(commentDTO);
        }
        return dto;

    }
}
