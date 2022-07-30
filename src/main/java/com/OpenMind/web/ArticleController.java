package com.OpenMind.web;

import com.OpenMind.models.bindingModels.MeetingBindingModel;
import com.OpenMind.models.exceptions.ResourceNotFoundException;
import com.OpenMind.models.bindingModels.ArticleBindingModel;
import com.OpenMind.models.serviceModels.ArticleUpdateServiceModel;
import com.OpenMind.models.viewModels.ArticleFullContentViewModel;
import com.OpenMind.serveces.ArticleService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;

@Controller
public class ArticleController {


    private final ArticleService articleService;
    private final ModelMapper modelMapper;

    public ArticleController(ArticleService articleService, ModelMapper modelMapper) {
        this.articleService = articleService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute
    public ArticleBindingModel articleBindingModel(){
        return new ArticleBindingModel();
    }

    @GetMapping("/add-article")
    public String addArticlePage(){
        return "add-article";
    }

    @PostMapping("/add-article")
    public String addArticleMethod(@Valid ArticleBindingModel articleBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal){

        if(bindingResult.hasErrors()){
            redirectIfError(articleBindingModel, bindingResult, redirectAttributes);
            return "redirect:add-article";
        }

        if(!articleService.addArticle(articleBindingModel, principal.getName())){
            return "redirect:add-article";
        }

        return "redirect:/profile";
    }

    @GetMapping("/article/{id}")
    public ModelAndView articlePage(@PathVariable("id") int id, HttpSession httpSession, ModelAndView modelAndView, Principal principal) {

        ArticleFullContentViewModel article = articleService.findById(id);
        boolean isOwner = articleService.isOwner(principal.getName(), article.getId());

        if(article != null){
            modelAndView.addObject("article", article);
            modelAndView.addObject("isOwner", isOwner);
            modelAndView.setViewName("article");
        }else {
            throw new ResourceNotFoundException();
        }

        return modelAndView;
    }

    @PreAuthorize("isOwner(#id)")
    @DeleteMapping("/article/{id}")
    public  String deleteArticle(@PathVariable Long id, Principal principal){

        articleService.deleteArticle(id);

        return "redirect:/profile#articles";
    }

    @PreAuthorize("isOwner(#id)")
    @GetMapping("/article/{id}/edit")
    public  String editArticlePage(@PathVariable("id") int id, Model model){

        ArticleFullContentViewModel article = articleService.findById(id);

        model.addAttribute("article", article);

        return "article-edit";
    }


    @PreAuthorize("isOwner(#id)")
    @PatchMapping("/article/{id}/edit")
    public String editArticleMethod(@PathVariable("id") int id, @Valid ArticleBindingModel article,
                                    BindingResult bindingResult, RedirectAttributes redirectAttributes){
        //todo не показва грешките при редирект
        if(bindingResult.hasErrors()){
            redirectAttributes.addAttribute("notUpdated", true);
           redirectIfError(article, bindingResult, redirectAttributes);
            return "redirect:edit";
        }

        ArticleUpdateServiceModel serviceModel = modelMapper.map(article, ArticleUpdateServiceModel.class);
        serviceModel.setId((long)id);
        articleService.updateArticle(serviceModel);
        return "redirect:/article/" + id;
    }

    private void redirectIfError(ArticleBindingModel articleBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("articleBindingModel", articleBindingModel)
                .addFlashAttribute("org.springframework.validation.BindingResult.articleBindingModel", bindingResult);
    }




//    @ExceptionHandler({ResourceNotFoundException.class})
//    public ModelAndView exceptionHandler(ResourceNotFoundException e){
//        ModelAndView modelAndView = new ModelAndView("404");
//        modelAndView.addObject("message", e.getMessage());
//        modelAndView.setStatus(HttpStatus.NOT_FOUND);
//        return modelAndView;
//    }

}
