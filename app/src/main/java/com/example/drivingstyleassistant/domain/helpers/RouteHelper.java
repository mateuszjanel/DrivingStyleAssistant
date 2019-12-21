package com.example.drivingstyleassistant.domain.helpers;

import com.example.drivingstyleassistant.common.ContextService;
import com.example.drivingstyleassistant.domain.AppDatabase;
import com.example.drivingstyleassistant.domain.entities.Route;

import java.util.Calendar;
import java.sql.Date;
import java.util.List;

public class RouteHelper {
    ContextService contextService = ContextService.getContextService();
    AppDatabase appDatabase = AppDatabase.getAppDatabase(contextService.appContext);

    public long startRoute() {
        Date currentTime = (Date) Calendar.getInstance().getTime();
        Route newRoute = new Route(currentTime);
        long newId;
        newId = appDatabase.routeDao().insert(newRoute);
        return newId;
    }

    public void setSmoothnessGrade(float grade, long routeId) {
        appDatabase.routeDao().updateSmoothnessGrade(routeId, grade);
    }



    public void updateGrade(float gradeLoss, long routeId, String type)  {
        Route route = appDatabase.routeDao().getRouteById(routeId);
        CommonHelper commonHelper = new CommonHelper();
        float lastGrade, newGrade;
        switch(type){
            case "acceleration":
                lastGrade = route.getAcceleratingGrade();
                newGrade = commonHelper.checkGradeLimits(lastGrade - gradeLoss);

                route.setAcceleratingGrade(newGrade);
            case "breaking" :
                lastGrade = route.getBreakingGrade();
                newGrade = commonHelper.checkGradeLimits(lastGrade - gradeLoss);
                route.setSuddenBreakingsNumber(newGrade);
            case "cornering":
                lastGrade = route.getDangerousCornering();
                newGrade = commonHelper.checkGradeLimits(lastGrade - gradeLoss);
                route.setDangerousCornering(newGrade);
        }
        appDatabase.routeDao().updateRoute(routeId, (Date) route.getRouteDate(), route.getMark(), route.getBreakingGrade(), route.getAcceleratingGrade(), route.getSmoothness(), route.getDangerousCornering());
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

    public float getMeanGrade(int option){
        List<Route> routes = appDatabase.routeDao().getRoutes();
        float meanGrade = 0;
        float accelerationGrade = 0, breakingsGrade = 0, smoothnessGrade = 0, corneringGrade = 0;
        for (Route route:routes) {
            accelerationGrade = accelerationGrade + route.getAcceleratingGrade();
            breakingsGrade = breakingsGrade + route.getBreakingGrade();
            smoothnessGrade = smoothnessGrade + route.getSmoothness();
            corneringGrade = corneringGrade + route.getDangerousCornering();
            meanGrade = meanGrade + ((accelerationGrade + breakingsGrade + smoothnessGrade + corneringGrade) / 4);
        }
        int sizeOfRoutesList = routes.size();
        accelerationGrade = accelerationGrade / sizeOfRoutesList;
        breakingsGrade = breakingsGrade / sizeOfRoutesList;
        smoothnessGrade = smoothnessGrade / sizeOfRoutesList;
        corneringGrade = corneringGrade / sizeOfRoutesList;
        meanGrade = meanGrade / sizeOfRoutesList;

        float toReturn = -1;

        switch (option){
            case 0:
                toReturn = meanGrade;
            case 1:
                toReturn = accelerationGrade;
            case 2:
                toReturn = breakingsGrade;
            case 3:
                toReturn = smoothnessGrade;
            case 4:
                toReturn = corneringGrade;
        }

        return toReturn;
    }

}
