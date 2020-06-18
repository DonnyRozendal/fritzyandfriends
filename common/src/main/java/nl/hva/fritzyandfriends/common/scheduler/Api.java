package nl.hva.fritzyandfriends.common.scheduler;

import nl.hva.fritzyandfriends.common.InboxData;
import nl.hva.fritzyandfriends.common.TransactionConfirmation;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @POST("/fritzy/relayData")
    Call<InboxData> fritzyRelayData(
            @Query("kwh") int kwh
    );

    @POST("/batty/relayData")
    Call<InboxData> battyRelayData(
            @Query("kwh") int kwh
    );

    @POST("/sunny/relayData")
    Call<InboxData> sunnyRelayData(
            @Query("kwh") int kwh
    );

    @GET("/power/calculateTransaction")
    Call<TransactionConfirmation> executeTransaction();

}