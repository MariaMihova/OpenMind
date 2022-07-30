package com.OpenMind.serveces;

import com.OpenMind.models.bindingModels.ArticleBindingModel;
import com.OpenMind.models.serviceModels.ArticleUpdateServiceModel;
import com.OpenMind.models.viewModels.ArticleFullContentViewModel;
import com.OpenMind.models.viewModels.ArticleVewModel;

import java.util.List;

public interface ArticleService {
    boolean addArticle(ArticleBindingModel articleBindingModel, String username);

    List<ArticleVewModel> findArticlesByUser(String username);

    ArticleFullContentViewModel findById(long id);

    void deleteArticle(Long id);

    void updateArticle(ArticleUpdateServiceModel serviceModel);

    List<ArticleVewModel> findArticlesByProfessionalField(Long id);

    boolean isOwner(String currentUserName, Long id);
}
