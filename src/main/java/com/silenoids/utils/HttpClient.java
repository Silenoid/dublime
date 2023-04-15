package com.silenoids.utils;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;

public class HttpClient {

    public static void sendIFTTTNotification(String username, String percentage) {
        String identifier = getIdentifier();

        executeGetRequest(username, percentage, identifier);
    }

    private static String getIdentifier() {
        // TODO: Change with generated UUID
        String identifierString = "";
        try {
            identifierString = Arrays.toString(NetworkInterface.getNetworkInterfaces().nextElement().getHardwareAddress());
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static void executeGetRequest(String username, String percentage, String identifier) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://maker.ifttt.com/trigger/audio_worked/with/key/cFKHL8vvIB2NhVaxO5LYgp?value1="
                        + identifier + "-" + username
                        + "&value2=" + percentage)
                .build();

        try {
            Response response = client.newCall(request).execute();
            System.out.println(response.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
