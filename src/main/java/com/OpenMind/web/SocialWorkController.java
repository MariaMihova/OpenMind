package com.OpenMind.web;

import com.OpenMind.models.viewModels.ArticleVewModel;
import com.OpenMind.models.viewModels.UserViewModel;
import com.OpenMind.serveces.ArticleService;
import com.OpenMind.serveces.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class SocialWorkController {

    private final UserService userService;
    private final ArticleService articleService;
    private final Long PROFESSIONAL_FIELD = 2L;

    public SocialWorkController(UserService userService, ArticleService articleService) {
        this.userService = userService;
        this.articleService = articleService;
    }

    @GetMapping("/social-work")
    public ModelAndView psychology(ModelAndView modelAndView){

        List<UserViewModel> specialists = userService.findAllByProfessionalField(PROFESSIONAL_FIELD);
        modelAndView.addObject("specialists", specialists);


        List<ArticleVewModel> socialArticles = articleService.findArticlesByProfessionalField(PROFESSIONAL_FIELD);
        modelAndView.addObject("socialArticles", socialArticles);

        modelAndView.setViewName("/social-work");

        return modelAndView;

    }

}
