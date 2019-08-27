package com.pdl.graghqlexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.pdl.graphqlexample.AllPostQuery;
import com.pdl.graphqlexample.CreatePostMutation;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    TextView postTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button submit = findViewById(R.id.submitBtn);
        postTv = findViewById(R.id.postTv);
        Button getPostBtn = findViewById(R.id.getPostBtn);
        getPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllPost();

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPost();
            }
        });
    }

    //Implementing get request
    private void getAllPost(){

       MyApoloClient.getMyApoloClient().query(AllPostQuery.builder().build()).
               enqueue(new ApolloCall.Callback<AllPostQuery.Data>() {
           @Override
           public void onResponse(@NotNull final Response<AllPostQuery.Data> response) {
               MainActivity.this.runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
//                       StringBuilder stringBuilder = new StringBuilder();
                     /* postTv.setText(stringBuilder.append(response.data().allPosts().get(0).title() + "\n"));
                      postTv.setText(stringBuilder.append(response.data().allPosts().get(0).description() + "\n"));*/
                       Log.d(TAG, "onResponse: " + response.data().toString());
                       postTv.setText(response.data().toString());
                   }
               });

           }

           @Override
           public void onFailure(@NotNull ApolloException e) {

           }
       });
    }

    private void createPost(){
        EditText title = findViewById(R.id.title);
        EditText description = findViewById(R.id.description);
        MyApoloClient.getMyApoloClient().mutate(CreatePostMutation.builder()
                        .title(title.getText().toString())
                        .description(description.getText().toString()).build()
                ).enqueue(new ApolloCall.Callback<CreatePostMutation.Data>() {
            @Override
            public void onResponse(@NotNull Response<CreatePostMutation.Data> response) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Added successffully", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {

            }
        });
    }
}
