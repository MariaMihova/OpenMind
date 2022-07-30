package com.OpenMind.serveces.Impl;

import com.OpenMind.models.viewModels.StatView;
import com.OpenMind.serveces.StatService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class StatServiceImpl implements StatService {

    private int anonymousRequests;
    private int authRequests;


    @Override
    public void onRequest() {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if(authentication != null && (authentication.getPrincipal() instanceof UserDetails)){
            authRequests++;
        }else {
            anonymousRequests++;
        }

    }

    @Override
    public StatView getStats() {
        return new StatView(anonymousRequests, authRequests);
    }

    @Override
    public void clean() {
        anonymousRequests = 0;
        authRequests = 0;
    }


}
