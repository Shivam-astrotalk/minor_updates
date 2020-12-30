package com.astrotalk.live.network;

import com.astrotalk.live.model.pojo.Response;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WalletServiceClient {

    @POST("/AstroTalk/wallet/deduction")
    public Call<Response> deductUserWallet(@Query("amount") double amount, @Query("comment") String comment, @Query("orderId") long eventId,
                                           @Query("secretKey") String key, @Query("serviceId") String serviceId, @Query("userId") long userId);



}
