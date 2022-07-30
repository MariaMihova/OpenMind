package com.OpenMind.config;

import com.OpenMind.serveces.ArticleService;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public class OwnerSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    private ArticleService articleService;
    private Object filterObject;
    private Object returnObject;

    public OwnerSecurityExpressionRoot(Authentication authentication){
        super(authentication);
    }

    public void setArticleService(ArticleService articleService){
        this.articleService = articleService;
    }



    public boolean isOwner(Long id){
        String username = currentUserName();

        if(username != null){
            return articleService.isOwner(currentUserName(), id);
        }

        return false;
    }

    private String currentUserName() {

       Authentication authentication = getAuthentication();
       if(authentication.getPrincipal() instanceof UserDetails){
           return ((UserDetails) authentication.getPrincipal()).getUsername();
       }

        return null;
    }

    @Override
    public void setFilterObject(Object filterObject){
        this.filterObject = filterObject;
    }

    @Override
    public Object getFilterObject(){
        return this.filterObject;
    }

    @Override
    public void setReturnObject(Object returnObject){
        this.returnObject = returnObject;
    }

    @Override
    public Object getReturnObject(){
        return this.returnObject;
    }


    @Override
    public Object getThis(){
        return this;
    }

}
