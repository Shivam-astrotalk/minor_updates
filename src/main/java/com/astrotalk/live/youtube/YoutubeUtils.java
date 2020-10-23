package com.astrotalk.live.youtube;

import com.astrotalk.live.model.LiveEvent;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleOAuthConstants;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URLDecoder;
import java.security.GeneralSecurityException;
import java.util.*;

@Service
public class YoutubeUtils {

    private static final String CLIENT_ID = "139146714941-3mqo8cemv3h4lg9v9u6id9f5bjfte85j.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "OKVz-kAB2oH4YAVwWcPHWKLq";
    private static final Collection<String> SCOPES =
            Arrays.asList("https://www.googleapis.com/auth/youtube");

    private static final String APPLICATION_NAME = "youtubeLive";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    static String token = "ya29.A0AfH6SMAxxuAjl4329rULaqQw91rHXLjlREJ4aCuRNhkNhmiTqQfKRw_FRpqlBG7tliyNYFJYx_tf8dPWNjoXlixwk7VxCcEnQ65op2elSOEUR0uW6UweYjamPDpNpGtsqSKfOg2G1OCcAnOGzk8FDGmTs-OFWvfhGAHt1QDb43_X";
    /**
     * Create an authorized Credential object.
     *
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize(final NetHttpTransport httpTransport, String authCode) throws IOException, GeneralSecurityException {

        GoogleAuthorizationCodeTokenRequest request = new GoogleAuthorizationCodeTokenRequest(httpTransport,JSON_FACTORY,CLIENT_ID,CLIENT_SECRET,authCode,"https://www.astrotalk.com");
        GoogleTokenResponse response = request.execute();
        Credential credential = new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(response.getAccessToken());
        return credential;
    }

    /**
     * Build and return an authorized API client service.
     *
     * @return an authorized API client service
     * @throws GeneralSecurityException, IOException
     */
    public static YouTube getService(String authCode) throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Credential credential = authorize(httpTransport,authCode);
        return new YouTube.Builder(httpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();

    }




    public static void goLive(String authCode, LiveEvent liveEvent) throws IOException, GeneralSecurityException {
       // String authCode = URLDecoder.decode("4%2F5QHpTtdpvvBN6BEJrCnqfI-EahJyB4KznvXPFnrOzPsvrEmyM3IOX0JU86n6BFbGWE3UQm-M28O6rnIyBM4_JOc");
        YouTube youtubeService = getService(authCode);

        LiveBroadcast liveBroadcast = new LiveBroadcast();
        LiveBroadcastContentDetails contentDetails = new LiveBroadcastContentDetails();
        contentDetails.setEnableContentEncryption(true);
        contentDetails.setEnableDvr(true);
        contentDetails.setEnableEmbed(true);
        contentDetails.setEnableAutoStart(true);
        contentDetails.setRecordFromStart(true);
        contentDetails.setStartWithSlate(true);
        liveBroadcast.setContentDetails(contentDetails);

        LiveBroadcastSnippet snippet = new LiveBroadcastSnippet();
        snippet.setScheduledEndTime(new DateTime(System.currentTimeMillis() + 24*3600*1000));
        snippet.setScheduledStartTime(new DateTime(System.currentTimeMillis() + 48*3600*1000));
        snippet.setTitle(liveEvent.getAstrologerName() + " live");
       // snippet.setChannelId("UCtzwI9wcMhw4RJH2VRTnhkQ"); //
        liveBroadcast.setSnippet(snippet);

        LiveBroadcastStatus status = new LiveBroadcastStatus();
        status.setPrivacyStatus("unlisted");
        liveBroadcast.setStatus(status);
        YouTube.LiveBroadcasts.Insert request = youtubeService.liveBroadcasts().insert("snippet,contentDetails,status", liveBroadcast);
        liveBroadcast = request.execute();


        LiveStream liveStream = new LiveStream();
        CdnSettings cdn = new CdnSettings();
        cdn.setFrameRate("60fps");
        cdn.setIngestionType("rtmp");
        cdn.setResolution("1080p");
        liveStream.setCdn(cdn);
        LiveStreamContentDetails contentDetails1 = new LiveStreamContentDetails();
        contentDetails1.setIsReusable(true);
        liveStream.setContentDetails(contentDetails1);

        LiveStreamSnippet snippet1 = new LiveStreamSnippet();
        snippet1.setDescription("Astrotalk Live");
        snippet1.setTitle("Astrotalk Live");
        liveStream.setSnippet(snippet1);
        YouTube.LiveStreams.Insert request1 = youtubeService.liveStreams()
                .insert("snippet,cdn,contentDetails,status", liveStream);
        liveStream = request1.execute();
        YouTube.LiveBroadcasts.Bind request2 = youtubeService.liveBroadcasts()
                .bind(liveBroadcast.getId(), "id");
        LiveBroadcast response = request2.setStreamId(liveStream.getId())
                .execute();
        System.out.println("yay");
        liveEvent.setIngestionUrl(liveStream.getCdn().getIngestionInfo().getIngestionAddress());
        liveEvent.setConsumptionUrl("https://www.youtube.com/watch?v=" + liveBroadcast.getId());
    }

}
