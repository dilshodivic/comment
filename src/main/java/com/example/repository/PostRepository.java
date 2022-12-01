package com.example.repository;

import com.example.dto.PostDTO;
import com.example.entity.PostEntity;
import com.example.entity.ProfileEntity;
import com.example.mapper.PostMapper;
import com.example.mapper.PostMapper2;
import com.example.mapper.PostMapper3;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.lang.management.PlatformManagedObject;
import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Integer> {
    @Transactional
    @Modifying
    @Query("update PostEntity set title= ?1, content= ?2, profile= ?3 where id= ?4")
    void update(String title, String content, ProfileEntity profile, Integer id);


    Page<PostEntity> findAllByProfile_Id(Integer profileId, Pageable paging);

    @Query(value = "select title as title ,created_date as createdDate from post where id =?1", nativeQuery = true)
    PostMapper getPostById(Integer id);


    @Query("from PostEntity p inner join p.profile where p.id = ?1")
    List<PostEntity> getProfileIdPostList(Integer profileId);


    @Query(value = "select p.* from post as p inner join profile as pr on p.profile_id = pr.id where pr.id =?1 order by p.id desc limit 5", nativeQuery = true)
    List<PostEntity> getLimitPostLast(Integer profileId);

    @Query(value = "select count(p.*) from post as p inner join profile as pr on p.profile_id = pr.id where pr.id =?1", nativeQuery = true)
    Long getCountPost(Integer profileId);

    @Query(value = "select count(p.*) from post as p inner join profile as pr on p.profile_id = pr.id " +
            "where pr.id =1  and (select cast(created_date as date))= Current_date", nativeQuery = true)
    Long getTodayCount(Integer profileId);

    @Query("select new com.example.mapper.PostMapper2(p.id , p.profile.id , p.createdDate) from PostEntity p where p.profile.id = ?1")
    List<PostMapper2> getPostIdProfileIdCreatedDate(Integer profileId);


    @Query("select new com.example.mapper.PostMapper2(p.id , p.profile.id , p.createdDate) from PostEntity p ")
    List<PostMapper2> getPostIdProfileIdCreatedDateList();


    @Query(value = "SELECT  * from post as p where  (select cast(created_date as date))= Current_date", nativeQuery = true)
    List<PostEntity> postFullToday();

    @Query(value = "select p.id as postId , p.title as title , p.content " +
            "as postContent , p.created_date as" +
            " createdDate , pr.name as profileName" +
            " from post p inner join profile as pr on p.profile_id  = pr.id " +
            "where (select cast(created_date as date))= Current_date", nativeQuery = true)
    List<PostMapper3> postFullTodayProfileName();
}
