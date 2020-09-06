package ai.kitt.vnest.basedata.api;

import ai.kitt.vnest.basedata.api.model.ActiveCode;
import ai.kitt.vnest.basedata.api.model.ActiveResponse;
import ai.kitt.vnest.basedata.api.model.CarInfo;
import ai.kitt.vnest.basedata.api.model.CarResponse;

import ai.kitt.vnest.basedata.api.model.UpdateInfo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface API {
    @POST("search-api/car-info")
    Call<CarResponse> carInfo(@Body CarInfo carInfo);
    @POST("search-api/update-info")
    Call<String> carBks(@Body UpdateInfo updateInfo);

    //https://vnest.vn/
    @POST("search-api/activate")
    Call<ActiveResponse> activeCode(@Body ActiveCode activeCode);
}
