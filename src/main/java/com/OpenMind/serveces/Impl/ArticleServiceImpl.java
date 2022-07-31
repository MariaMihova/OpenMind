package com.OpenMind.serveces.Impl;

import com.OpenMind.models.bindingModels.ArticleBindingModel;
import com.OpenMind.models.entitis.Article;
import com.OpenMind.models.entitis.UserEntity;
import com.OpenMind.models.entitis.UserRole;
import com.OpenMind.models.enums.FieldName;
import com.OpenMind.models.enums.Role;
import com.OpenMind.models.serviceModels.ArticleUpdateServiceModel;
import com.OpenMind.models.viewModels.ArticleFullContentViewModel;
import com.OpenMind.models.viewModels.ArticleVewModel;
import com.OpenMind.repositories.ArticleRepository;
import com.OpenMind.serveces.ArticleService;
import com.OpenMind.serveces.UserService;
import com.OpenMind.serveces.ProfessionalFieldService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final ProfessionalFieldService professionalFieldService;

    public ArticleServiceImpl(ArticleRepository articleRepository, ModelMapper modelMapper,
                              UserService userService, ProfessionalFieldService professionalFieldService) {
        this.articleRepository = articleRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.professionalFieldService = professionalFieldService;
    }


    @Override
    public boolean addArticle(ArticleBindingModel articleBindingModel, String username) {

        Article article = modelMapper.map(articleBindingModel, Article.class);
        article.setProfessionalField(professionalFieldService.findByFieldName(FieldName.valueOf(articleBindingModel.getProfessionalField())));
        article.setCreated(LocalDate.now());
        UserEntity userEntity = userService.findByUsername(username);
        article.setUser(userEntity);

        try {
            articleRepository.save(article);
            return true;
        }catch (Exception e){
            return false;
        }
    }


    @Override
    public List<ArticleVewModel> findArticlesByUser(String username) {
        return articleRepository.findAllByUsername(username);
    }

    @Override
    public ArticleFullContentViewModel findById(long id) {
        Article article = articleRepository.findById(id).orElse(null);
        ArticleFullContentViewModel articleFullContentViewModel = modelMapper.map(article, ArticleFullContentViewModel.class);
        articleFullContentViewModel.setUsername(article.getUser().getUsername());
        return articleFullContentViewModel;
    }

    @Override
    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }

    @Override
    public boolean isOwner(String currentUserName, Long id) {

        Optional<Article> articleOp =  articleRepository.findById(id);

        if(articleOp.isEmpty()){
            return false;
        }

        Article article = articleOp.get();

        return isAdmin(userService.findByUsername(currentUserName)) || article.getUser().getUsername().equalsIgnoreCase(currentUserName);

    }

    @Override
    public void updateArticle(ArticleUpdateServiceModel serviceModel) {
        Article article = articleRepository.findById(serviceModel.getId()).orElse(null);
        article.setTitle(serviceModel.getTitle());
        article.setContent(serviceModel.getContent());
        article.setProfessionalField(professionalFieldService.findByFieldName(FieldName.valueOf(serviceModel.getProfessionalField())));
        articleRepository.save(article);
    }


    @Override
    public List<ArticleVewModel> findArticlesByProfessionalField(Long id) {
        return articleRepository.findAllByProfessionalFieldId(id);
    }

    private boolean isAdmin(UserEntity user) {
//
        if(user == null){
            return false;
        }

        Set<UserRole> authorities = user.getAuthorities();

        return authorities.stream()
                .map(UserRole::getRole)
                .anyMatch(r -> r == Role.ADMIN);
    }


}
