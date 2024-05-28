package me.itsdxrk.spotifycontrol;

import org.apache.logging.log4j.Level;
import xyz.duncanruns.julti.Julti;
import xyz.duncanruns.julti.plugin.PluginEvents;

public class SpotifyControllerInit {
    public static void init() {
        PluginEvents.RunnableEventType.RELOAD.register(() -> {
            // This gets run when Julti launches and every time the profile is switched
            Julti.log(Level.INFO, "Spotify Controller Plugin Reloaded!");
        });

        PluginEvents.RunnableEventType.STOP.register(() -> {
            // This gets run when Julti is shutting down
            Julti.log(Level.INFO, "Spotify Controller Plugin shutting down...");
        });
    }
}
