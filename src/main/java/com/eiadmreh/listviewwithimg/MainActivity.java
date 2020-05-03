package com.eiadmreh.listviewwithimg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lst;
    //String[] fruitname = {"Mnago", "Banana", "WaterMilon", "Grapes", "Kiwi", "Apple"};
    //String[] disc = {"This is Mnago", "This is Banana", "This is WaterMilon", "This is Grapes", "This is Kiwi", "This is Apple"};
    //int[] imgid = {R.drawable.mango, R.drawable.banana, R.drawable.watermilon, R.drawable.grapes, R.drawable.kiwi, R.drawable.apple};

    public static DatabaseReference myRef ;
    public static StorageReference mStorageRef;
    public static ArrayList<Frouit> frouits;
    public static String[] fruitname;
    public static String[] disc;
    public static String[] Urls;
    public static int SIZE=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lst = (ListView) findViewById(R.id.listview);
        mStorageRef = FirebaseStorage.getInstance().getReference("Frouits");
        frouits=new ArrayList<Frouit>();
        myRef= FirebaseDatabase.getInstance().getReference("Frouits");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                frouits.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Frouit frouit = new Frouit();
                        frouit = snapshot.getValue(Frouit.class);
                        frouits.add(frouit);
                        SIZE++;
                    }
                    fruitname=new String[SIZE];
                    disc=new String[SIZE];
                    Urls=new String[SIZE];
                    //init Prices
                    for(int i=0;i<SIZE;i++){
                        disc[i]=String.valueOf(frouits.get(i).gettPrice());
                    }
                    //init Names
                    for(int i=0;i<SIZE;i++){
                        fruitname[i]=frouits.get(i).gettName();
                    }
                    //get Urls for images..
                    for(int i=0;i<SIZE;i++){

                        try {
                            StorageReference riversRef = mStorageRef.child(frouits.get(i).getImageUrl());
                            final File localFile = File.createTempFile("Tools", getImageType(frouits.get(i).getImageUrl()));
                            final int finalJ = i;
                            //Urls[i]=String.valueOf(riversRef);
                            Urls[i]=localFile.getAbsolutePath();
                            riversRef.getFile(localFile)
                                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                            // Successfully downloaded data to local file
                                            // ...
                                            //Urls[finalJ]=localFile.getAbsolutePath();
                                            //Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                            //bitmaps[finalJ]=bitmap;


                                        }
                                    }).addOnFailureListener(new OnFailureListener() {

                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle failed download
                                    // ...

                                }
                            });
                        }

                        catch (IOException e) {
                            e.printStackTrace();

                        }

                    }

                   /*ArrayAdapter<String> myAdapter =
                            new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,Urls );
                    lst.setAdapter(myAdapter);*/

                   CustomListview customListview=new CustomListview(MainActivity.this,fruitname,disc,Urls);
                   lst.setAdapter(customListview);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


    }

    public static String getImageType(String name){
        String[] type = name.split( "\\." );
        return type[1];
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType( cR.getType( uri ) );
    }

}
