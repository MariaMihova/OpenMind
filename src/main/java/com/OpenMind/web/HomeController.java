package com.OpenMind.web;

import com.OpenMind.models.entitis.Article;
import com.OpenMind.models.viewModels.ArticleVewModel;
import com.OpenMind.serveces.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
public class HomeController {

    private final ArticleService articleService;

    public HomeController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/")
    public String welcome(){
        return "/index";
    }

 @GetMapping("/home")
    public ModelAndView home(ModelAndView modelAndView){

        List<ArticleVewModel> articles = articleService.getLatest();
        modelAndView.addObject("articles", articles);
        modelAndView.setViewName("home");

        return modelAndView;
 }
}
