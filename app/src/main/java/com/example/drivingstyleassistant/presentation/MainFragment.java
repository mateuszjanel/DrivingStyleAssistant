package com.example.drivingstyleassistant.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.drivingstyleassistant.R;
import com.example.drivingstyleassistant.domain.entities.EventTypeConverter;
import com.example.drivingstyleassistant.domain.entities.Events;
import com.example.drivingstyleassistant.domain.helpers.RouteHelper;
import com.example.drivingstyleassistant.presentation.viewmodels.MainViewModel;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;

    Button startRoute;
    Events.EventType chosenEventType;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        TextView meanGradeTextView = (TextView) getActivity().findViewById(R.id.meanGradeTextView);
        ImageView meanGradeBackground = (ImageView) getActivity().findViewById(R.id.meanGradeBackgroundImageView);

        TextView acceleratingGradeTextView = (TextView) getActivity().findViewById(R.id.accelerationGradeText);
        ImageView acceleratingGradeBackground = (ImageView) getActivity().findViewById(R.id.acceleratingGradeBackgroundImageView);

        TextView brakingGradeTextView = (TextView) getActivity().findViewById(R.id.brakingGradeText);
        ImageView brakingGradeBackground = (ImageView) getActivity().findViewById(R.id.brakingGradeBackgroundImageView);

        TextView smoothnessGradeTextView = (TextView) getActivity().findViewById(R.id.smoothnessGradeText);
        ImageView smoothnessGradeBackground = (ImageView) getActivity().findViewById(R.id.smoothnessGradeBackgroundImageView);

        TextView corneringGradeTextView = (TextView) getActivity().findViewById(R.id.corneringGradeText);
        ImageView corneringGradeBackground = (ImageView) getActivity().findViewById(R.id.corneringGradeBackgroundImageView);

        startRoute = getActivity().findViewById(R.id.startRouteButton);

        startRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new RouteFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.view_content, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        final Fragment fragment = new EventFragment();

        acceleratingGradeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chosenEventType = Events.EventType.AggressiveAcceleration;
                int eventTypeCode = EventTypeConverter.toInt(chosenEventType);
                Bundle bundle = new Bundle();
                bundle.putInt("typeCode", eventTypeCode);
                fragment.setArguments(bundle);
                showFragment(fragment);
            }
        });

        brakingGradeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chosenEventType = Events.EventType.SuddenBraking;
                int eventTypeCode = EventTypeConverter.toInt(chosenEventType);
                Bundle bundle = new Bundle();
                bundle.putInt("typeCode", eventTypeCode);
                fragment.setArguments(bundle);
                showFragment(fragment);
            }
        });

        corneringGradeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chosenEventType = Events.EventType.DangerousCornering;
                int eventTypeCode = EventTypeConverter.toInt(chosenEventType);
                Bundle bundle = new Bundle();
                bundle.putInt("typeCode", eventTypeCode);
                fragment.setArguments(bundle);
                showFragment(fragment);
            }
        });

        RouteHelper routeHelper = new RouteHelper();

        float meanGrade = routeHelper.getMeanGrade(0);
        float acceleratingGrade = routeHelper.getMeanGrade(1);
        float brakingGrade = routeHelper.getMeanGrade(2);
        float smoothnessGrade = routeHelper.getMeanGrade(3);
        float corneringGrade = routeHelper.getMeanGrade(4);
        changeGrade(meanGradeBackground, meanGrade, meanGradeTextView);
        changeGrade(acceleratingGradeBackground, acceleratingGrade, acceleratingGradeTextView);
        changeGrade(brakingGradeBackground, brakingGrade, brakingGradeTextView);
        changeGrade(smoothnessGradeBackground, smoothnessGrade, smoothnessGradeTextView);
        changeGrade(corneringGradeBackground, corneringGrade, corneringGradeTextView);
    }


    void changeGrade(ImageView gradeBackground, float grade, TextView gradeText) {
        gradeText.setText(String.valueOf((Math.round(grade*10.0))/10.0));
        if (grade < 3) {
            gradeBackground.setImageResource(R.color.negativeGrade);
        } else if (grade >= 3 && grade < 4) {
            gradeBackground.setImageResource(R.color.averageGrade);
        } else if (grade >= 4 && grade <= 5) {
            gradeBackground.setImageResource(R.color.positiveGrade);
        }
    }

    void showFragment(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.view_content, Objects.requireNonNull(fragment));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
