package ai.kitt.vnest.basedata.api.repository;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.gson.Gson;

import ai.kitt.vnest.basedata.api.ApiCall;
import ai.kitt.vnest.basedata.api.model.CarInfo;
import ai.kitt.vnest.basedata.api.model.CarResponse;
import ai.kitt.vnest.basedata.api.model.UpdateInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
@SuppressLint("LogNotTimber")
public class CarRepo {
    public void sendCarInfo(CarInfo carInfo, OnResponseListener onResponseListener) {
        ApiCall.getInstance().getApi().carInfo(carInfo).enqueue(new Callback<CarResponse>() {
            @Override
            public void onResponse(Call<CarResponse> call, Response<CarResponse> response) {
                Log.e("onResponse", new Gson().toJson(response.body()));
                onResponseListener.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<CarResponse> call, Throwable t) {
                Log.e("Error", t.getMessage(), t);
                onResponseListener.onError(t);
            }
        });
    }
    public void sendBks(UpdateInfo carInfo) {
        ApiCall.getInstance().getApi().carBks(carInfo).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e("onResponse", new Gson().toJson(response.body()));
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("Error", t.getMessage(), t);
            }
        });
    }

    public interface OnResponseBks{

    }

    public interface OnResponseListener {
        void onResponse(CarResponse carResponse);

        void onError(Throwable throwable);
    }
}
