package com.a2.newsbyte.news;

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



    /************************************************************************************/
    @Query(value="select id from newspapers where name = ?", nativeQuery = true)
    Long getNewsPaperIdByName(String name);

    @Query(value="select * from news where newspaper_id = ? ORDER BY id DESC LIMIT 3;", nativeQuery = true)
    List<News> getLatestNewsByNewsPaperId(Long newspaperId);

    @Query(value="select id from tags where name = ?", nativeQuery = true)
    Long getTagIdByCategory(String name);

    @Query(value="select * from news where newspaper_id = ?1 and tag_id = ?2 ORDER BY id DESC LIMIT 3;", nativeQuery = true)
    List<News> getNewsByCategory(Long newspaperId,Long tagId);

    @Query(value="select DISTINCT name from newspapers", nativeQuery = true)
    List<String> getAllChannelNames();
//    @Query(value = "select *, Count(t.id) as tag_count from news ")
//    List<News> findTop3TrendingNews();
}
