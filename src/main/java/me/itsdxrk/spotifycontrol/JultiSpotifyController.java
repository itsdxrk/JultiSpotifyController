package me.itsdxrk.spotifycontrol;

import com.google.common.io.Resources;
import me.itsdxrk.spotifycontrol.api.SpotifyControlAPI;
import me.itsdxrk.spotifycontrol.data.SpeedrunIGTEvents;
import me.itsdxrk.spotifycontrol.ui.SpotifyControllerGUI;
import org.apache.logging.log4j.Level;
import xyz.duncanruns.julti.Julti;
import xyz.duncanruns.julti.JultiAppLaunch;
import xyz.duncanruns.julti.plugin.PluginInitializer;
import xyz.duncanruns.julti.plugin.PluginManager;
import xyz.duncanruns.julti.util.ExceptionUtil;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class JultiSpotifyController implements PluginInitializer {
    private static final ScheduledExecutorService EXECUTOR = Executors.newSingleThreadScheduledExecutor();

    public static void main(String[] args) throws IOException {
        // This is only used to test the plugin in the dev environment
        // ExamplePlugin.main itself is never used when users run Julti

        JultiAppLaunch.launchWithDevPlugin(args, PluginManager.JultiPluginData.fromString(
                Resources.toString(Resources.getResource(JultiSpotifyController.class, "/julti.plugin.json"), Charset.defaultCharset())
        ), new JultiSpotifyController());
    }

    @Override
    public void initialize() {
        // This gets run once when Julti launches
        SpotifyControllerInit.init();
        SpotifyControlAPI.init();
        SpotifyControllerOptions options;
        try {
            options = SpotifyControllerOptions.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Julti.log(Level.INFO, "Julti Spotify Controller Initialized | Current Split: " + options.triggerEvent.split("\\.")[1]);
        
        SpeedrunIGTEvents srigtEvents = new SpeedrunIGTEvents();
        EXECUTOR.scheduleWithFixedDelay(() -> {
            try {
                if (options.enabled) srigtEvents.getEvent();
            } catch (Throwable t) {
                Julti.log(Level.ERROR, "Spotify Controller Error: " + ExceptionUtil.toDetailedString(t));
            }
        }, 5, 5, TimeUnit.SECONDS);
    }

    @Override
    public String getMenuButtonName() {
        return "Open Config";
    }

    @Override
    public void onMenuButtonPress() {
        SpotifyControllerGUI.open(null);
    }
}
