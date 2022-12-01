package com.example.repository;
import com.example.entity.CommentEntity;
import com.example.entity.PostEntity;
import com.example.entity.ProfileEntity;
import com.example.mapper.CommentMapper;
import com.example.mapper.CommentMapper2;
import com.example.mapper.ProfileMapper;
import com.example.mapper.UniversalMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
    @Transactional
    @Modifying
    @Query("update CommentEntity set profile=?1, post=?2, content=?3 where id=?4")
    int updateComment(String content, ProfileEntity profile, PostEntity post, Integer id);


    Page<CommentEntity> findAllByPost_Id(Integer postId, Pageable paging);


    @Query("select new com.example.mapper.CommentMapper(c.id , c.content , c.profile.id , c.profile.name) " +
            "from CommentEntity c inner join c.profile where c.profile.id = ?1")
    List<CommentMapper> getListOf(Integer profileId);

    @Query("select new com.example.mapper.ProfileMapper(c.profile.id , c.profile.name,c.profile.surname) " +
            "from CommentEntity c inner join c.profile where c.id = ?1")
    ProfileMapper getProfileCommentId(Integer commentId);


    @Query("select count(c) from CommentEntity c inner join c.profile where c.profile.id = ?1")
    Long getCount(Integer profileId);



    @Query("select count(c) from CommentEntity c inner join c.post where c.post.id = ?1")
    Long getCountPost(Integer postId);

    @Query(value = "select c.content as content from comment as c" +
            " inner join post p on (c.post_id = p.id)" +
            " where p.id = ?1 " +
            " order by c.id desc" +
            " limit 1",nativeQuery = true)
    CommentMapper2 getLastComment(Integer postId);


    @Query("select new com.example.mapper.UniversalMapper(c.post.id, c.post.title, c.id, c.content, c.created_date, c.profile.id, c.profile.name) from CommentEntity as c where c.profile.id=?1")
    Page<UniversalMapper> universal_Id(Integer profileId, Pageable paging);

    //@Query(value = "SELECT  * from post as p where  (select cast(created_date as date))= Current_date", nativeQuery = true)
@Query("from CommentEntity c where c.created_date between ?1 and ?2")
List<CommentEntity> getByCreated_dateBetween(LocalDateTime from2, LocalDateTime to2);
}
