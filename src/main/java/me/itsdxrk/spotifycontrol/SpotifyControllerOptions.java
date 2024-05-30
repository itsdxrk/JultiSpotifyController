package me.itsdxrk.spotifycontrol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import xyz.duncanruns.julti.JultiOptions;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SpotifyControllerOptions {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path SAVE_PATH = JultiOptions.getJultiDir().resolve("spotifycontroloptions.json");
    private static SpotifyControllerOptions instance = null;

    public int triggerID = 1;
    public String triggerEvent = "rsg.enter_bastion";
    public boolean enabled = true;

    public static void save() throws IOException {
        FileWriter writer = new FileWriter(SAVE_PATH.toFile());
        GSON.toJson(instance, writer);
        writer.close();
    }

    public static SpotifyControllerOptions load() throws IOException {
        if (Files.exists(SAVE_PATH)) {
            instance = GSON.fromJson(new String(Files.readAllBytes(SAVE_PATH)), SpotifyControllerOptions.class);
        } else {
            instance = new SpotifyControllerOptions();
        }
        return instance;
    }

    public static SpotifyControllerOptions getInstance() {
        return instance;
    }
}
