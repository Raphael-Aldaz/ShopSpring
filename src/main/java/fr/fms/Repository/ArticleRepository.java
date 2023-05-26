package fr.fms.Repository;

import fr.fms.entities.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findByDescriptionContains(String keyword, Pageable pageable);
    @Query("SELECT a FROM Article a WHERE a.category.id = :categoryId")
    Page<Article> findByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);
}
