# Julti Spotify Controller
 A Julti Plugin that allows the control of a Spotify Client using SpeedrunIGT events.

# NOTICE
This plugin is *very badly coded* and has many issues that I have tried to remove, a major one being a warning on startup saying "Failed to load class 'module-info'! Julti may crash if this is needed by a plugin...". This does not currently affect the plugin, but if you do come across any issues please reach out to me on Discord or GitHub Issues.

Unintentionally, the plugin sometimes works when other media is playing while the Spotify Client is open in the background idly, such as YouTube. This plugin was made with Spotify in mind, so any other media working with the plugin is unintentional.

A delay may occur when pausing/playing the media. This is due to how the check is performed every 5 seconds, and so the delay in pausing/playing after a certain event may sometimes be longer than other times. I would make the delay between checks shorter, however this would hinder the performance of Julti far too much than is already being done.

Starting Julti while Spotify is **minimised in the system tray** will make it unable to detect Spotify, so please launch Julti while Spotify is unminimised. You are able to minimise it once the initialisation of Julti has finished.

# SETUP
* In Julti, go to the Plugins section and Open Plugins Folder
* Place the [plugin](https://github.com/itsdxrk/JultiSpotifyController/releases/latest) in the folder.
* Restart Julti
* Go back to the plugins menu to find the new Spotify Controller section and press Open Config.
* Set which split you'd like Spotify to pause upon reaching.
* **Save** any changes made before closing the menu.
