package nl.groenier.android.capnow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements Callback<List<Capture>> {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8081/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CapNowService service = retrofit.create(CapNowService.class);

        Call<List<Capture>> captures = service.listCaptures();

        captures.enqueue(this);

    }


    @Override
    public void onResponse(Call<List<Capture>> call, Response<List<Capture>> response) {
        if(response.isSuccessful()) {
            Log.d(TAG, "Request successful");
            Log.d(TAG, "onResponse: " + response.body().toString());
        }
    }

    @Override
    public void onFailure(Call<List<Capture>> call, Throwable t) {
        Log.e(TAG, "Request NOT successful", t);
    }
}
