package me.itsdxrk.spotifycontrol.api;

import de.labystudio.spotifyapi.SpotifyAPI;
import de.labystudio.spotifyapi.SpotifyAPIFactory;
import de.labystudio.spotifyapi.model.MediaKey;
import org.apache.logging.log4j.Level;
import xyz.duncanruns.julti.Julti;

public class SpotifyControlAPI {

    private static SpotifyAPI api;
    private static int errorCount = 0;
    public static void init() {
        api = SpotifyAPIFactory.create();
        api.initialize();
    }
    public static void play() {
        try {
            if (!api.isPlaying()) {
                api.pressMediaKey(MediaKey.PLAY_PAUSE);
            }
        } catch (IllegalStateException e) {
            if (errorCount < 1) {
                Julti.log(Level.ERROR,"Unable to connect to Spotify Client, make sure it is open!");
                errorCount++;
            }
        }
    }

    public static void pause() {
        try {
            if (api.isPlaying()) {
                api.pressMediaKey(MediaKey.PLAY_PAUSE);
            }
        } catch (IllegalStateException e) {
            if (errorCount < 1) {
                Julti.log(Level.ERROR,"Unable to connect to Spotify Client, make sure it is open!");
                errorCount++;
            }
        }
    }

    public static boolean getState() {
        return api.isPlaying();
    }
}
