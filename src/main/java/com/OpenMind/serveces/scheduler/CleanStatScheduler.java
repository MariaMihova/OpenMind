package com.OpenMind.serveces.scheduler;

import com.OpenMind.serveces.StatService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CleanStatScheduler {

    private final StatService statService;

    public CleanStatScheduler(StatService statService) {
        this.statService = statService;
    }

//    @Scheduled( cron= "59 59 23 * * *")
    @Scheduled( cron= "59 59 23 * * *")
    public void cleanStats(){
      statService.clean();
  }
}
