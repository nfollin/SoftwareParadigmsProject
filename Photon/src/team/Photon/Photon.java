package team.Photon;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import java.io.File;
import java.util.Date;
import java.io.IOException;
import java.text.SimpleDateFormat;
import android.content.Intent;
import android.os.Environment;
import android.os.Parcel;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class Photon extends Activity {

    private float lastX = 0;
    public static final String DEBUG_TAG = "Photon::";
    protected ViewFlipper gallery;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int RESULT_LOAD_IMAGE =2;
    static final int REQUEST_TAKE_PHOTO = 1;
    public static String mCurrentPhotoPath;
    private View [] views;

    public void loadFile(){
        gallery.removeAllViews();
        Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }
    private File createImageFile() throws IOException {

        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getResources().getConfiguration().orientation==Configuration.ORIENTATION_LANDSCAPE){
            setContentView(R.layout.main_landscape);
        }else {
            setContentView(R.layout.main);
        }
        gallery = (ViewFlipper) findViewById(R.id.gallery);
        final ImageView loadFile = (ImageView) findViewById(R.id.imageButton);
        loadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFile();
            }
        });
        gallery.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return onTouchEvent(v, event);

            }
        });

        if(savedInstanceState!=null){
            Log.d(DEBUG_TAG,"Setting active child from state");
            gallery.getChildAt(savedInstanceState.getInt("CURRENT_CHILD"));
        }
        final View[] views = (View []) getLastNonConfigurationInstance();
        if(views !=null) {
            Log.d(DEBUG_TAG, views.length + " views in array");

            for (int i = 0; i < views.length; i++) {

                gallery.addView(views[i]);
            }
        }
    }
    @Override
     public Object onRetainNonConfigurationInstance() {
        final View[] list = new View[gallery.getChildCount()];
        keepPhotos(list);
        return list;

    }
    public void keepPhotos(View [] views){

        int j =0;
        for (int i = gallery.getDisplayedChild(); i < views.length; i++) {
            views[j] = ((ImageView) gallery.getChildAt(i));
            j++;
        }
        for (int i = 0; i < gallery.getDisplayedChild(); i++){
            views[j]=((ImageView) gallery.getChildAt(i));
            j++;
        }
        gallery.removeAllViews();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        Log.d(DEBUG_TAG,"Saving Instance State");

        savedInstanceState.putInt("CURRENT_CHILD",gallery.getDisplayedChild());

    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
       // super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen for landscape and portrait
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.main_landscape);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            setContentView(R.layout.main);
        }
        Log.d(DEBUG_TAG,views.length + " views in array");

        for (int i = 0; i < views.length; i++) {

            gallery.addView(views[i]);
        }
    }

    public void onClick(View v) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        gallery.removeAllViews();


        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.d(DEBUG_TAG, "Failed to create image file");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        // store the data in the fragment


    }
    public boolean onTouchEvent(View v, MotionEvent touchevent) {
        if (v.getId() != gallery.getId())
            return false;

        switch (touchevent.getAction()) {
            case MotionEvent.ACTION_DOWN: {

                    lastX = touchevent.getX();

                break;
            }
            case MotionEvent.ACTION_UP: {

                float currentX = touchevent.getX();
                if (lastX < currentX) {
                    gallery.setInAnimation(this, R.anim.in_from_left);
                    gallery.setOutAnimation(this, R.anim.out_to_right);
                    gallery.showNext();

                }
                if (lastX > currentX) {

                    gallery.setInAnimation(this, R.anim.in_from_right);
                    gallery.setOutAnimation(this, R.anim.out_to_left);
                    gallery.showPrevious();

                }
                if (lastX == currentX) {
                    Log.d(DEBUG_TAG, "Trying to swipe during animation");
                }
                break;
            }
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            ImageView image = new ImageView(getApplicationContext());
            Drawable res = new BitmapDrawable(getResources(), mCurrentPhotoPath);
            image.setBackground(res);
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            gallery.addView(image);
            ImageView image2 = new ImageView(getApplicationContext());
            image2.setBackground(getResources().getDrawable(R.drawable.icon_256));
            image2.setScaleType(ImageView.ScaleType.CENTER_CROP);
            gallery.addView(image2);
            ImageView image3 = new ImageView(getApplicationContext());
            image3.setBackground(getResources().getDrawable(R.drawable.shutter));
            image3.setScaleType(ImageView.ScaleType.CENTER_CROP);
            gallery.addView(image3);
            ImageView image4 = new ImageView(getApplicationContext());
            image4.setBackground(getResources().getDrawable(R.drawable.ic_launcher));
            image4.setScaleType(ImageView.ScaleType.CENTER_CROP);
            gallery.addView(image4);


        }
        if(requestCode== RESULT_LOAD_IMAGE && resultCode==RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            ImageView image =new ImageView(getApplicationContext());
            image.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            gallery.addView(image);
            ImageView image2 = new ImageView(getApplicationContext());
            image2.setBackground(getResources().getDrawable(R.drawable.icon_256));
            image2.setScaleType(ImageView.ScaleType.CENTER_CROP);
            gallery.addView(image2);
            ImageView image3 = new ImageView(getApplicationContext());
            image3.setBackground(getResources().getDrawable(R.drawable.shutter));
            image3.setScaleType(ImageView.ScaleType.CENTER_CROP);
            gallery.addView(image3);
            ImageView image4 = new ImageView(getApplicationContext());
            image4.setBackground(getResources().getDrawable(R.drawable.ic_launcher));
            image4.setScaleType(ImageView.ScaleType.CENTER_CROP);
            gallery.addView(image4);
        }

    }

}
