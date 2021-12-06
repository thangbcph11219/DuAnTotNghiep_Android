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
import com.example.test1.adapters.LikeAdapter;
import com.example.test1.fragments.DfrPeopleLikeFragment;
import com.example.test1.fragments.LikeFragment;
import com.example.test1.fragments.ListFriendsFragment;
import com.example.test1.fragments.MeLikeFragment;
import com.example.test1.models.Users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FunctionFriendsFAN {

    public void insertFriends(Context context, String myEmail, String emailFriends, int check) {

        AndroidNetworking.post("https://poly-dating.herokuapp.com/api/friends/friend_request")
                .addBodyParameter("myEmail", myEmail)
                .addBodyParameter("emailFriend", emailFriends)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                            if (check == 1) {
                                LikeFragment.viewPager.setCurrentItem(3, true);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        String firstStr = anError.getErrorBody().substring(29);
                        String lastStr = firstStr.substring(0, firstStr.length() - 2);
                        Toast.makeText(context, lastStr, Toast.LENGTH_SHORT).show();
                        Log.e("insert", "Lỗi");
                    }
                });
    }

    public void getListFriendsRequetst(Context context, String email) {

        DfrPeopleLikeFragment.likesList = new ArrayList<>();

        AndroidNetworking.get("https://poly-dating.herokuapp.com/api/friends/list_friends_requests/{email}")
                .addPathParameter("email", email)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject arr = response.getJSONObject("data");
                            JSONArray usersJSON = arr.getJSONArray("friends");
                            for (int i = 0; i < usersJSON.length(); i++) {
                                JSONObject jo = usersJSON.getJSONObject(i).getJSONObject("myUser");
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

                                DfrPeopleLikeFragment.likesList.add(users);
                                Log.e("abcdef", users.getEmail());
                            }
                            if (DfrPeopleLikeFragment.likesList.size() == 0) {
                                DfrPeopleLikeFragment.tvCountFavorite.setText(DfrPeopleLikeFragment.likesList.size() + " lượt thích");
                                DfrPeopleLikeFragment.tv12.setVisibility(View.VISIBLE);
                                DfrPeopleLikeFragment.progressBar.setVisibility(View.GONE);
                                DfrPeopleLikeFragment.tv12.setText("Chưa ai thích bạn");

                            } else {
                                DfrPeopleLikeFragment.tvCountFavorite.setText(DfrPeopleLikeFragment.likesList.size() + " lượt thích");
                                DfrPeopleLikeFragment.progressBar.setVisibility(View.GONE);
                                DfrPeopleLikeFragment.tv12.setVisibility(View.GONE);
                            }
                            DfrPeopleLikeFragment.likeAdapter = new LikeAdapter(DfrPeopleLikeFragment.likesList, context, 1);
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false);
                            DfrPeopleLikeFragment.rycLike.setLayoutManager(gridLayoutManager);
                            DfrPeopleLikeFragment.rycLike.setAdapter(DfrPeopleLikeFragment.likeAdapter);
                            DfrPeopleLikeFragment.likeAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("có chạy vào đây ko ta", "đoán xem");
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

    public void getListOfRequestSend(Context context, String email) {

        MeLikeFragment.likesList = new ArrayList<>();

        AndroidNetworking.get("https://poly-dating.herokuapp.com/api/friends/list_of_requests_sent/{email}")
                .addPathParameter("email", email)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject arr = response.getJSONObject("data");
                            JSONArray usersJSON = arr.getJSONArray("friends");
                            for (int i = 0; i < usersJSON.length(); i++) {
                                JSONObject jo = usersJSON.getJSONObject(i).getJSONObject("friend");
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

                                MeLikeFragment.likesList.add(users);
                            }
                            if (MeLikeFragment.likesList.size() == 0) {
                                MeLikeFragment.tvCountFavorite.setText(MeLikeFragment.likesList.size() + " lượt đã thích");
                                MeLikeFragment.tv12.setVisibility(View.VISIBLE);
                                MeLikeFragment.progressBar.setVisibility(View.GONE);
                                MeLikeFragment.tv12.setText("Bạn chưa thích ai");
                            } else {
                                MeLikeFragment.tvCountFavorite.setText(MeLikeFragment.likesList.size() + " lượt đã thích");
                                MeLikeFragment.progressBar.setVisibility(View.GONE);
                                MeLikeFragment.tv12.setVisibility(View.GONE);
                            }
                            MeLikeFragment.likeAdapter = new LikeAdapter(MeLikeFragment.likesList, context, 2);
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false);
                            MeLikeFragment.rycLike.setLayoutManager(gridLayoutManager);
                            MeLikeFragment.rycLike.setAdapter(MeLikeFragment.likeAdapter);
                            MeLikeFragment.likeAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("có chạy vào đây ko ta", "đoán xem");
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

    public void getListFriends(Context context, String email) {

        ListFriendsFragment.likesList = new ArrayList<>();

        AndroidNetworking.get("https://poly-dating.herokuapp.com/api/friends/list_friends/{email}")
                .addPathParameter("email", email)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject arr = response.getJSONObject("data");
                            JSONArray usersJSON = arr.getJSONArray("friends");
                            for (int i = 0; i < usersJSON.length(); i++) {
                                JSONObject jo = usersJSON.getJSONObject(i).getJSONObject("myUser");
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

                                ListFriendsFragment.likesList.add(users);
                            }
                            if (ListFriendsFragment.likesList.size() == 0) {
                                ListFriendsFragment.tvCountListFriend.setText(ListFriendsFragment.likesList.size() + " bạn bè");
                                ListFriendsFragment.tv12.setVisibility(View.VISIBLE);
                                ListFriendsFragment.progressBar.setVisibility(View.GONE);
                                ListFriendsFragment.tv12.setText("Chưa có bạn bè");
                            } else {
                                ListFriendsFragment.tvCountListFriend.setText(ListFriendsFragment.likesList.size() + " bạn bè");
                                ListFriendsFragment.progressBar.setVisibility(View.GONE);
                                ListFriendsFragment.tv12.setVisibility(View.GONE);
                            }
                            ListFriendsFragment.likeAdapter = new LikeAdapter(ListFriendsFragment.likesList, context, 3);
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false);
                            ListFriendsFragment.rycListFriend.setLayoutManager(gridLayoutManager);
                            ListFriendsFragment.rycListFriend.setAdapter(ListFriendsFragment.likeAdapter);
                            ListFriendsFragment.likeAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("có chạy vào đây ko ta", "đoán xem");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        ListFriendsFragment.tv12.setVisibility(View.VISIBLE);
                        ListFriendsFragment.progressBar.setVisibility(View.GONE);
                        ListFriendsFragment.tv12.setText("Chưa có bạn bè");
                    }
                });
    }

    public void deleteFriends(Context context, String myEmail, String emailFriends, String message) {

        AndroidNetworking.post("https://poly-dating.herokuapp.com/api/friends/delete")
                .addBodyParameter("myEmail", myEmail)
                .addBodyParameter("emailFriend", emailFriends)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        getListFriends(context, HomeActivity.users.getEmail());
                        getListFriendsRequetst(context, HomeActivity.users.getEmail());
                        getListOfRequestSend(context, HomeActivity.users.getEmail());
                    }

                    @Override
                    public void onError(ANError anError) {
                        String firstStr = anError.getErrorBody().substring(29);
                        String lastStr = firstStr.substring(0, firstStr.length() - 2);
                        Toast.makeText(context, lastStr, Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
