package me.itsdxrk.spotifycontrol.data;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import me.itsdxrk.spotifycontrol.SpotifyControllerOptions;
import me.itsdxrk.spotifycontrol.api.SpotifyControlAPI;
import org.apache.logging.log4j.Level;
import xyz.duncanruns.julti.Julti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.*;

public class SpeedrunIGTEvents {
    private Path worldPath = null;
    private Path latestWorld = Paths.get(System.getProperty("user.home")).resolve("speedrunigt").resolve("latest_world.json").toAbsolutePath();
    private Path eventLog;
    private static final List<String> END_EVENTS = Arrays.asList("common.leave_world", "common.multiplayer", "common.old_world", "common.open_to_lan", "common.enable_cheats", "common.view_seed", "rsg.credits");
    private static final List<String> IMPORTANT_EVENTS = Arrays.asList("rsg.enter_nether", "rsg.enter_bastion", "rsg.enter_fortress", "rsg.first_portal", "rsg.enter_stronghold", "rsg.enter_end");
    private void getEventLog() {
        eventLog = this.worldPath.resolve("speedrunigt").resolve("events.log");
    }
    private static FileTime lastModifiedTime = null;
    private boolean isImportant;
    private static String thisWorldPaused;

    private void getLatestWorld() throws IOException {
        if (this.latestWorld != null) {
            String readLatestWorld = new String(Files.readAllBytes(this.latestWorld));
            JsonObject json;
            try {
                json = new Gson().fromJson(readLatestWorld, JsonObject.class);
            } catch (JsonSyntaxException e) {
                Julti.log(Level.ERROR, "Couldn't convert latest world to JSON file. : " + e);
                return;
            }
            this.worldPath = Paths.get(json.get("world_path").getAsString());
            getEventLog();
        }
    }

    private String getWorldName() {
        return this.worldPath == null ? null : this.worldPath.getFileName().toString();
    }


    public void getEvent() throws IOException {
        getLatestWorld();
        ArrayList<String> events = new ArrayList<>();
        try {
            List<String> allLines = Files.readAllLines(eventLog);
            for (String line : allLines) {
                String event = line.split("\\s+")[0];
                if (IMPORTANT_EVENTS.contains(event) || END_EVENTS.contains(event)) {
                    events.add(line.split("\\s+")[0]);
                    isImportant = true;
                } else {
                    isImportant = false;
                }
            }
        } catch (IOException e) {
            Julti.log(Level.ERROR, "Could not read event log. : " + e);
        }

//        ButtonGroup eventButtons = SpotifyControllerGUI.buttonGroup;
        SpotifyControllerOptions options = SpotifyControllerOptions.getInstance();
        String triggerEvent = options.triggerEvent;

        boolean triggerEventPresent = events.contains(triggerEvent);
        boolean endEventPresent = !Collections.disjoint(events, END_EVENTS);

        FileTime currentModifiedTime = Files.getLastModifiedTime(eventLog);
        if ((lastModifiedTime != null && currentModifiedTime.equals(lastModifiedTime)) || !isImportant) {
            return;
        }

        lastModifiedTime = currentModifiedTime;
        boolean isPlaying = SpotifyControlAPI.getState();

        if (triggerEventPresent && isPlaying && !Objects.equals(getWorldName(), thisWorldPaused) && !endEventPresent) {
            SpotifyControlAPI.pause();
            thisWorldPaused = getWorldName();
        } else if (endEventPresent && !isPlaying) {
            SpotifyControlAPI.play();
        }
    }
}
