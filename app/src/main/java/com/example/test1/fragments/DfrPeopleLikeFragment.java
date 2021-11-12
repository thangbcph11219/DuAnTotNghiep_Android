package com.example.test1.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.test1.HomeActivity;
import com.example.test1.R;
import com.example.test1.adapters.LikeAdapter;
import com.example.test1.models.Users;
import com.example.test1.networking.FunctionFavoriteFAN;

import java.util.List;

public class DfrPeopleLikeFragment extends Fragment {

    public static TextView tvCountFavorite;
    public static ProgressBar progressBar;
    public static TextView tv12;
    public static RecyclerView rycLike;
    public static LikeAdapter likeAdapter;
    public static List<Users> likesList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dfr_people_like, container, false);
        tvCountFavorite = view.findViewById(R.id.tvCountBeLiked);
        progressBar = view.findViewById(R.id.progressBar);
        tv12 = view.findViewById(R.id.textView12);
        rycLike = view.findViewById(R.id.rycBeLiked);

        FunctionFavoriteFAN functionFavoriteFAN = new FunctionFavoriteFAN();
        functionFavoriteFAN.getListBeLikedFavorite(getActivity(), HomeActivity.users.getEmail());

        return view;
    }
}
