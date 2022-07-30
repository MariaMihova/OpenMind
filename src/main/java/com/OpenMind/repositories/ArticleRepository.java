package com.OpenMind.repositories;

import com.OpenMind.models.entitis.Article;
import com.OpenMind.models.viewModels.ArticleVewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.validation.Valid;
import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("select new com.OpenMind.models.viewModels.ArticleVewModel(a.id, a.title, substring(a.content,1, 200), u.username) from Article a join a.user u where u.username= :username")
    List<ArticleVewModel> findAllByUsername(@Param(value = "username") String username);

    @Query("select new com.OpenMind.models.viewModels.ArticleVewModel(a.id, a.title, substring(a.content,1, 200), u.username) from Article a join a.user u join a.professionalField f where f.id= :id")
    List<ArticleVewModel> findAllByProfessionalFieldId(@Param(value = "id") Long id);
}
