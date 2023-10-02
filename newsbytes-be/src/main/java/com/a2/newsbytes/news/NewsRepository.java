package com.a2.newsbytes.news;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    @Query(value="select * from news where id = ?", nativeQuery = true)
    News getNewsById(Long id);

    @Query(value="select * from news where details_url = ?", nativeQuery = true)
    News getNewsByDetailsUrl(String detailsUrl);

    @Query(value="select * from news where tag_id = ?", nativeQuery = true)
    List<News> getNewsByTag(Long tagId);

    @Query(value="select * from news where newspaper_id = ? ORDER BY id DESC", nativeQuery = true)
    List<News> getNewsByNewspaper(Long newspaperId);



    @Modifying
    @Transactional
    @Query(value = "DELETE FROM news WHERE added_on != ?", nativeQuery = true)
    void deletePrevDatesNews(String todaysDate);


}
