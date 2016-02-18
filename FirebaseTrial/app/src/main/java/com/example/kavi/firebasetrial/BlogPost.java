package com.example.kavi.firebasetrial;

import com.firebase.client.Firebase;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.shaded.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Kavi on 2/17/16.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class BlogPost {


    private String filePath;

    public BlogPost(){}

    public BlogPost(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }




}
