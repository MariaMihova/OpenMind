package com.OpenMind.web;

import com.OpenMind.models.viewModels.ArticleVewModel;
import com.OpenMind.models.viewModels.MeetingViewModel;
import com.OpenMind.serveces.ArticleService;
import com.OpenMind.serveces.MeetingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
public class HomeController {

    private final ArticleService articleService;
    private final MeetingService meetingService;

    public HomeController(ArticleService articleService, MeetingService meetingService) {
        this.articleService = articleService;
        this.meetingService = meetingService;
    }

    @GetMapping("/")
    public String welcome(){
        return "/index";
    }

 @GetMapping("/home")
    public ModelAndView home(ModelAndView modelAndView){

        List<ArticleVewModel> articles = articleService.getLatest();
        List<MeetingViewModel> meetings = meetingService.getTop5Meetings();

        modelAndView.addObject("articles", articles);
        modelAndView.addObject("meetings", meetings);
        modelAndView.setViewName("home");

        return modelAndView;
 }
}
