package com.OpenMind.web;

import com.OpenMind.models.enums.FieldName;
import com.OpenMind.models.viewModels.ArticleVewModel;
import com.OpenMind.models.viewModels.UserViewModel;
import com.OpenMind.serveces.ArticleService;
import com.OpenMind.serveces.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class SpeechTherapyController {

    private final UserService userService;
    private final ArticleService articleService;
    private final Long PROFESSIONAL_FIELD = (long) FieldName.SPEECH_THERAPY.ordinal();

    public SpeechTherapyController(UserService userService, ArticleService articleService) {
        this.userService = userService;
        this.articleService = articleService;
    }

    @GetMapping("/speech-therapy")
    public ModelAndView speechTherapyPage(ModelAndView modelAndView) {

        List<UserViewModel> specialists = userService.findAllByProfessionalField(PROFESSIONAL_FIELD);
        modelAndView.addObject("specialists", specialists);


        List<ArticleVewModel> speechTherapyArticles = articleService.findArticlesByProfessionalField(PROFESSIONAL_FIELD);
        modelAndView.addObject("speechTherapyArticles", speechTherapyArticles);

        modelAndView.setViewName("/speech-therapy");

        return modelAndView;
    }
}
