package com.silenoids.utils;

import com.squareup.okhttp.*;

import java.io.IOException;

public class HttpClient {

    private static String token = "cFKHL8vvIB2NhVaxO5LYgp";

    public static void sendIFTTTProgressionNotification(String username, String percentage) {
        try {
            executeNoBodyGetRequest(username, percentage, token);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendIFTTTCrashReport(String crashReport) {
        try {
            executeWithBodyGetRequest(token, crashReport);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void executeNoBodyGetRequest(String token, String value1, String value2) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://maker.ifttt.com/trigger/audio_worked/with/key/" + token + "?" +
                        "value1=" + value1 +
                        "&value2=" + value2)
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(response.toString());
    }

    private static void executeWithBodyGetRequest(String token, String bodyString) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://maker.ifttt.com/trigger/error_occurred/json/with/key/" + token)
                .post(RequestBody.create(MediaType.parse("application/json"), bodyString))
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(response.toString());
    }

}
