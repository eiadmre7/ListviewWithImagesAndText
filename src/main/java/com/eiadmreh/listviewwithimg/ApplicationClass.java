package com.eiadmreh.listviewwithimg;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import androidx.annotation.NonNull;

public class ApplicationClass extends Application {
    public static FirebaseDatabase database;
    public static DatabaseReference myRef ;
    public static StorageReference mStorageRef;
    public static Bitmap[] bitmaps;
    public static ArrayList<Frouit> tools;
    public static int SIZE=0;

    @Override
    public void onCreate() {
        super.onCreate();
        database= FirebaseDatabase.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference("Frouits");

    }



    }
