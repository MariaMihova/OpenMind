package com.OpenMind.models.viewModels;

public class StatView {
    private final int anonymousRequests;
    private final int authRequests;

    public StatView(int anonymousRequests, int authRequests){
        this.anonymousRequests = anonymousRequests;
        this.authRequests = authRequests;
    }


    public int getAnonymousRequests() {
        return anonymousRequests;
    }


    public int getAuthRequests() {
        return authRequests;
    }


    public int getTotalRequests(){
      return  this.getAnonymousRequests() + this.getAuthRequests();
    }
}
