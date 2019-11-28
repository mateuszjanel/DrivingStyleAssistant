package com.example.drivingstyleassistant.domain.helpers;

import com.example.drivingstyleassistant.common.ContextService;
import com.example.drivingstyleassistant.domain.AppDatabase;
import com.example.drivingstyleassistant.domain.entities.Route;

import java.util.Calendar;
import java.util.Date;

public class RouteHelper {
    ContextService contextService = ContextService.getContextService();
    AppDatabase appDatabase = AppDatabase.getAppDatabase(contextService.appContext);

    public long startRoute() {
        Date currentTime = Calendar.getInstance().getTime();
        Route newRoute = new Route(currentTime);
        long newId;
        newId = appDatabase.routeDao().insert(newRoute);
        return newId;
    }

    public void setSmoothnessGrade(float grade, long routeId) {
        appDatabase.routeDao().updateSmoothnessGrade(routeId, grade);
    }

    public float getGradeFromRoute(String type, long routeId) {
        Route route = appDatabase.routeDao().getRouteById(routeId);
        float gradeToReturn = -1;
        switch (type) {
            case "smoothness":
                gradeToReturn = route.getSmoothness();
            case "acceleration":
                gradeToReturn = route.getAcceleratingGrade();
            case "breaking" :
                gradeToReturn = route.getBreakingGrade();
            case "cornering":
                gradeToReturn = route.getDangerousCornering();
        }
        return gradeToReturn;
    }

}
