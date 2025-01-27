package com.example.test1.networking;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.test1.HomeActivity;
import com.example.test1.R;
import com.example.test1.adapters.LikeAdapter;
import com.example.test1.fragments.ChatFragment;
import com.example.test1.fragments.DfrPeopleLikeFragment;
import com.example.test1.fragments.MeLikeFragment;
import com.example.test1.interfaces.Listener;
import com.example.test1.models.Users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FunctionFavoriteFAN {

    Listener listener;

    public void insertFavorite(Context context, String emailBeLiked, String emailLiked) {

        Log.e("email", emailBeLiked + emailLiked);

        AndroidNetworking.post("https://poly-dating.herokuapp.com/api/favorites/insert")
                .addBodyParameter("emailBeLiked", emailBeLiked)
                .addBodyParameter("emailLiked", emailLiked)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        String firstStr = anError.getErrorBody().substring(29);
                        String lastStr = firstStr.substring(0, firstStr.length() - 2);
                        Toast.makeText(context, lastStr, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void deleteFavorite(Context context, String emailBeLiked, String emailLiked,String message) {

        AndroidNetworking.post("https://poly-dating.herokuapp.com/api/favorites/delete")
                .addBodyParameter("emailBeLiked", emailBeLiked)
                .addBodyParameter("emailLiked", emailLiked)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("error", anError.getErrorBody());
                        Toast.makeText(context, "Thất bại", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void getListBeLikedFavorite(Context context, String emailBeLiked, List<Users> likesList, RecyclerView rycLike, ProgressBar progressBar,
                                       TextView tvCountFavorite, TextView tv12) {

        AndroidNetworking.get("https://poly-dating.herokuapp.com/api/favorites/list/be_liked/{emailBeLiked}")
                .addPathParameter("emailBeLiked", emailBeLiked)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject arr = response.getJSONObject("data");
                            JSONArray usersJSON = arr.getJSONArray("favorites");
                            for (int i = 0; i < usersJSON.length(); i++) {
                                JSONObject jo = usersJSON.getJSONObject(i).getJSONObject("userLiked");
                                List<String> fileimg = new ArrayList<>();
                                List<String> hobbiesList = new ArrayList<>();

                                String email = jo.getString("email");
                                String name = jo.getString("name");
                                JSONArray avatars = jo.getJSONArray("images");
                                for (int j = 0; j < avatars.length(); j++) {
                                    fileimg.add(avatars.getString(j));
                                }
                                JSONArray hobbies = jo.getJSONArray("hobbies");
                                for (int j = 0; j < hobbies.length(); j++) {
                                    hobbiesList.add(hobbies.getString(j));
                                }
                                String birthDay = jo.getString("birthDay");
                                String gender = jo.getString("gender");
                                String description = jo.getString("description");
                                String facilities = jo.getString("facilities");
                                String specialized = jo.getString("specialized");
                                String course = jo.getString("course");

                                Users users = new Users();
                                users.setEmail(email);
                                users.setName(name);
                                users.setImageUrl(fileimg);
                                users.setHobbies(hobbiesList);
                                users.setBirthday(birthDay);
                                users.setGender(gender);
                                users.setDescription(description);
                                users.setFacilities(facilities);
                                users.setSpecialized(specialized);
                                users.setCourse(course);

                                likesList.add(users);
                                Log.e("abcdef", users.getEmail());
                            }
                            if (likesList.size() == 0) {
                                tvCountFavorite.setText(likesList.size() + " lượt thích");
                                tv12.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                                tv12.setText("Chưa ai thích bạn");

                            } else {
                                tvCountFavorite.setText(likesList.size() + " lượt thích");
                                progressBar.setVisibility(View.GONE);
                                tv12.setVisibility(View.GONE);
                            }
                            DfrPeopleLikeFragment.likeAdapter = new LikeAdapter(likesList, context, 1);
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false);
                            rycLike.setLayoutManager(gridLayoutManager);
                            rycLike.setAdapter(DfrPeopleLikeFragment.likeAdapter);
                            DfrPeopleLikeFragment.likeAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("có chạy vào đây ko ta", "đoán xem");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(context, "" + anError.getErrorBody(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void getListLikedFavorite(Context context, String emailLiked, List<Users> likesList, RecyclerView rycLike, ProgressBar progressBar,
                                     TextView tvCountFavorite, TextView tv12) {

        AndroidNetworking.get("https://poly-dating.herokuapp.com/api/favorites/list/liked/{emailLiked}")
                .addPathParameter("emailLiked", emailLiked)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject arr = response.getJSONObject("data");
                            JSONArray usersJSON = arr.getJSONArray("favorites");
                            for (int i = 0; i < usersJSON.length(); i++) {
                                JSONObject jo = usersJSON.getJSONObject(i).getJSONObject("userBeLiked");
                                List<String> fileimg = new ArrayList<>();
                                List<String> hobbiesList = new ArrayList<>();

                                String email = jo.getString("email");
                                String name = jo.getString("name");
                                JSONArray avatars = jo.getJSONArray("images");
                                for (int j = 0; j < avatars.length(); j++) {
                                    fileimg.add(avatars.getString(j));
                                }
                                JSONArray hobbies = jo.getJSONArray("hobbies");
                                for (int j = 0; j < hobbies.length(); j++) {
                                    hobbiesList.add(hobbies.getString(j));
                                }
                                String birthDay = jo.getString("birthDay");
                                String gender = jo.getString("gender");
                                String description = jo.getString("description");
                                String facilities = jo.getString("facilities");
                                String specialized = jo.getString("specialized");
                                String course = jo.getString("course");

                                Users users = new Users();
                                users.setEmail(email);
                                users.setName(name);
                                users.setImageUrl(fileimg);
                                users.setHobbies(hobbiesList);
                                users.setBirthday(birthDay);
                                users.setGender(gender);
                                users.setDescription(description);
                                users.setFacilities(facilities);
                                users.setSpecialized(specialized);
                                users.setCourse(course);

                                likesList.add(users);
                            }
                            if (likesList.size() == 0) {
                                tvCountFavorite.setText(likesList.size() + " đã lượt thích");
                                tv12.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                                tv12.setText("Bạn chưa thích ai");
                            } else {
                                tvCountFavorite.setText(likesList.size() + " đã lượt thích");
                                progressBar.setVisibility(View.GONE);
                                tv12.setVisibility(View.GONE);
                            }
                            MeLikeFragment.likeAdapter = new LikeAdapter(likesList, context, 2);
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false);
                            rycLike.setLayoutManager(gridLayoutManager);
                            rycLike.setAdapter(MeLikeFragment.likeAdapter);
                            MeLikeFragment.likeAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("có chạy vào đây ko ta", "đoán xem");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(context, "" + anError.getErrorBody(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void updateFavorite(Context context, String emailBeLiked, String emailLiked) {
        AndroidNetworking.post("https://poly-dating.herokuapp.com/api/favorites/update")
                .addBodyParameter("emailBeLiked", emailBeLiked)
                .addBodyParameter("emailLiked", emailLiked)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                            context.startActivity(new Intent(context, HomeActivity.class));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        Log.e("error", anError.getErrorBody());
                        Toast.makeText(context, "Thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
