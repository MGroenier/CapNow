package nl.groenier.android.capnow;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements Callback<List<Capture>> {

    private static final String TAG = "MainActivity";
    private static final int PICK_IMAGE = 1234;

    private List<Capture> captures = new ArrayList();
    private CapNowService client;

    private Button buttonGetAllCaptures;
    private Button buttonCreateCapture;

    private String selectedImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonGetAllCaptures = (Button) findViewById(R.id.buttonGetCaptures);
        buttonCreateCapture = (Button) findViewById(R.id.buttonCreateCapture);

        buttonGetAllCaptures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCaptures();
            }
        });

        buttonCreateCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

        client = ServiceGenerator.createService(CapNowService.class);



    }


    @Override
    public void onResponse(Call<List<Capture>> call, Response<List<Capture>> response) {
        if(response.isSuccessful()) {

            Log.d(TAG, "Request successful");

            for (Capture capture : response.body()){
                captures.add(capture);
            }

            for (Capture capture : captures) {
                Log.i(TAG, capture.toString());
            }

        }
    }

    @Override
    public void onFailure(Call<List<Capture>> call, Throwable t) {
        Log.e(TAG, "Request NOT successful", t);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGE) {
                Uri selectedImageUri = data.getData();
                Log.d(TAG, "selectedImageUri: " + selectedImageUri);
                createCapture(selectedImageUri);
            }
        }
    }

    private void getCaptures() {
        Call<List<Capture>> call = client.listCaptures();
        call.enqueue(this);
    }

    private void createCapture(Uri imageUri) {

        String imagePath = getRealPathFromUri(this, imageUri);
        File file = new File(imagePath);

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("capture-file", file.getName(), requestFile);

        // add another part within the multipart request
        String title = "This is a title!";
        RequestBody titlePart =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), title);


        Call<ResponseBody> call = client.createCapture(body, titlePart);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "onResponse() SUCCESS!");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("onFailure: FAILED!", t.getMessage());
            }
        });
    }

    public String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

}
