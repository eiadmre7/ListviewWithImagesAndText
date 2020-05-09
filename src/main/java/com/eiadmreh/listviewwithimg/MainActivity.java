package com.eiadmreh.listviewwithimg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnSuccessListener;
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
    public static Bitmap[] Urls;
    public static int SIZE=0;
    private int index;

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
                    fruitname = new String[SIZE];
                    disc = new String[SIZE];
                    Urls=new Bitmap[SIZE];
                    //init Prices
                    for (int i = 0; i < SIZE; i++) {
                        disc[i] = String.valueOf(frouits.get(i).gettPrice());
                    }
                    //init Names
                    for (int i = 0; i < SIZE; i++) {
                        fruitname[i] = frouits.get(i).gettName();
                    }
                    //get Urls for images..
                    index = 0;
                    loadProductsImages();
                    for (int i = 0; i < SIZE; i++) {
                        Urls[i] = frouits.get(i).getImage();
                    }
                }
                   /*ArrayAdapter<Bitmap> myAdapter =
                            new ArrayAdapter<Bitmap>(MainActivity.this, android.R.layout.simple_list_item_1,Urls );
                    lst.setAdapter(myAdapter);
                   CustomListview customListview = new CustomListview(MainActivity.this, fruitname,disc,Urls);*/



            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText( MainActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT ).show();
            }
        } );
    }

    private void loadProductsImages() {

        try{
            StorageReference mStorageRef = FirebaseStorage.getInstance().getReference("Frouits");
            StorageReference riversRef = mStorageRef.child( frouits.get( index ).getImageUrl() );
            final File localFile = File.createTempFile( "Frouits", getImageType( frouits.get( index ).getImageUrl() ) );
            riversRef.getFile( localFile ).addOnSuccessListener( new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile( localFile.getAbsolutePath() );
                    frouits.get( index ).setImage( bitmap );
                    nextProduct();

                }
            } );


        }catch (Exception e){
            Toast.makeText( MainActivity.this, e.getMessage(),Toast.LENGTH_LONG ).show();
        }

    }
    private void nextProduct(){
        index++;
        if(index<frouits.size()){
            loadProductsImages();
        }else
        {
            FrouitsListView adapter = new FrouitsListView( MainActivity.this, frouits );
            lst.setAdapter(adapter);
           // CustomListview customListview=new CustomListview(MainActivity.this,fruitname,disc,Urls);
           // lst.setAdapter(customListview);
           // progressBarProducts.setVisibility( View.GONE );
        }
    }

    private String getImageType(String name){
        String [] type = name.split( "\\." );
        return type[1];
    }
}
   /* private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType( cR.getType( uri ) );
    }*/


