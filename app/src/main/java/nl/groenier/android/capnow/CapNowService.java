package nl.groenier.android.capnow;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Martijn on 06/12/2016.
 */

public interface CapNowService {

    // Retrieve al existing Captures.
    @GET("captures")
    Call<List<Capture>> listCaptures();

    // Create a new Capture
    @Multipart
    @POST("captures")
    Call<ResponseBody> createCapture(@Part MultipartBody.Part file,
                                     @Part("title") RequestBody title);

}
