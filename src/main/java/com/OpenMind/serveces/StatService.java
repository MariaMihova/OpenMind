package com.OpenMind.serveces;

import com.OpenMind.models.viewModels.StatView;

public interface StatService {

    void onRequest();
    StatView getStats();

    void clean();
}
