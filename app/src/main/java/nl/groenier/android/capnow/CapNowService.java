package nl.groenier.android.capnow;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Martijn on 06/12/2016.
 */

public interface CapNowService {

    @GET("captures")
    Call<List<Capture>> listCaptures();

}
