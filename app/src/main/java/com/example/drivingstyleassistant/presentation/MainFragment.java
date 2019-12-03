package com.example.drivingstyleassistant.presentation;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.drivingstyleassistant.R;
import com.example.drivingstyleassistant.domain.helpers.RouteHelper;
import com.example.drivingstyleassistant.presentation.viewmodels.MainViewModel;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;

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
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        // TODO: Use the ViewModel
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

        RouteHelper routeHelper = new RouteHelper();

        float meanGrade = routeHelper.getMeanGrade(0);
        float acceleratingGrade = routeHelper.getMeanGrade(1);
        float brakingGrade = routeHelper.getMeanGrade(2);
        float smoothnessGrade = routeHelper.getMeanGrade(3);
        float corneringGrade = routeHelper.getMeanGrade(4);
        changeGrade(meanGradeBackground, meanGrade, meanGradeTextView);
        changeGrade(corneringGradeBackground, acceleratingGrade, acceleratingGradeTextView);
        changeGrade(brakingGradeBackground, brakingGrade, brakingGradeTextView);
        changeGrade(smoothnessGradeBackground, smoothnessGrade, smoothnessGradeTextView);
        changeGrade(corneringGradeBackground, corneringGrade, corneringGradeTextView);
    }


    void changeGrade(ImageView gradeBackground, float grade, TextView gradeText) {
        gradeText.setText(String.valueOf(grade));
        if (grade < 3) {
            gradeBackground.setImageResource(R.color.negativeGrade);
        } else if (grade >= 3 && grade < 4) {
            gradeBackground.setImageResource(R.color.averageGrade);
        } else if (grade >= 4 && grade <= 5) {
            gradeBackground.setImageResource(R.color.positiveGrade);
        }
    }
}
