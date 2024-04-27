  package com.example.uts.ui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uts.R;
import com.example.uts.data.response.GithubSearchResponse;
import com.example.uts.data.response.GithubUser;
import com.example.uts.data.retrofit.ApiConfig;
import com.example.uts.data.retrofit.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

  public class MainActivity extends AppCompatActivity {
      private RecyclerView recyclerView;
      private GithubUserAdapter adapter;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(com.example.uts.R.layout.activity_main);

          recyclerView = findViewById(R.id.rvUser);

          ApiService apiService = ApiConfig.getApiService();
          Call<GithubSearchResponse> call = apiService.searchUsers("ikhsansan");

          call.enqueue(new Callback<GithubSearchResponse>() {
              @Override
              public void onResponse(Call<GithubSearchResponse> call, Response<GithubSearchResponse> response) {
                  if (response.isSuccessful() && response.body() != null) {
                      List<GithubUser> users = response.body().getUsers();
                      adapter = new GithubUserAdapter(users);
                      recyclerView.setAdapter(adapter);
                      recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                  } else {
                      Toast.makeText(MainActivity.this, "Failed to get users", Toast.LENGTH_SHORT).show();
                  }
              }

              @Override
              public void onFailure(Call<GithubSearchResponse> call, Throwable t) {
                  Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
              }
          });
      }

  }