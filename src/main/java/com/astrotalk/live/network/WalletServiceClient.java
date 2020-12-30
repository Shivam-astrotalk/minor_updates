package com.astrotalk.live.network;

import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WalletServiceClient {

    @POST("/AstroTalk/wallet/deduction")
    public Call<JSONObject> deductUserWallet(@Query("amount") double amount, @Query("comment") String comment, @Query("orderId") long eventId,
                                             @Query("secretKey") String key, @Query("serviceId") String serviceId, @Query("userId") long userId);



}
