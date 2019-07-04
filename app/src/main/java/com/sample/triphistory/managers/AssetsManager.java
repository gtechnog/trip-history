package com.sample.triphistory.managers;

import android.content.res.AssetManager;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 *  AssetsManager is responsible for managing the assets in application.
 */
public class AssetsManager {

    private static final String TRIP_HISTORY_FILE_NAME = "trip_history.json";
    private final AssetManager mAssetManager;

    public AssetsManager(@NonNull AssetManager assetManager) {
        mAssetManager = assetManager;
    }

    /**
     * Reads the trip_history.json file from assets
     * @return json file as string value
     */
    public String readTripHistoryFile() {
        String jsonString = "";
        try {
            InputStream inputStream = mAssetManager.open(TRIP_HISTORY_FILE_NAME);
            int size = inputStream.available();
            byte[] buffer = new byte[size];

            if (inputStream.read(buffer) != -1) {
                inputStream.close();
                jsonString = new String(buffer, StandardCharsets.UTF_8);
            } else {
                inputStream.close();
                throw new Exception(" No data read from file");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
