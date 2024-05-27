package starblazerstudio.discord;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import starblazerstudio.utils.Consts;

public class RichPresence {

    private static final DiscordRPC lib = DiscordRPC.INSTANCE;
    private static final String APPLICATION_ID = "886991053121519657";

    // Sets up the Discord RPC for use
    public static void setup() {
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.ready = (user) -> {
            Consts.warn("Welcome " + user.username + "#" + user.discriminator + "!");
            startingPresence();
        };

        lib.Discord_Initialize(APPLICATION_ID, handlers, true, null);

        // Start a new thread to handle Discord RPC callbacks
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                lib.Discord_RunCallbacks();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "Discord-RPC-Callback-Handler").start();
    }

    // Creates a rich presence when the game is starting
    public static void startingPresence() {
        DiscordRichPresence presence = new DiscordRichPresence();
        presence.state = "Minecraft is Starting....";
        presence.details = "Loading the UwU's....";
        presence.largeImageKey = "projeto_14_6";
        presence.largeImageText = "Cum on me ~";
        lib.Discord_UpdatePresence(presence);
    }

    // Creates a rich presence when lurking in the main menu
    public void lurkingPresence() {
        DiscordRichPresence presence = new DiscordRichPresence();
        presence.state = "Lurking in the Main Menu~~";
        presence.details = "Hehe~ I see you";
        presence.largeImageKey = "projeto_14_6";
        presence.largeImageText = "OwO you looked";
        lib.Discord_UpdatePresence(presence);
    }

    // Creates a custom rich presence with title and details
    public void customPresence(String title, String details) {
        DiscordRichPresence presence = new DiscordRichPresence();
        presence.state = title;
        presence.details = details;
        lib.Discord_UpdatePresence(presence);
    }

    // Creates a custom rich presence with title, details, image, and image text
    public void customPresenceWithImage(String title, String details, String image, String imageText) {
        DiscordRichPresence presence = new DiscordRichPresence();
        presence.state = title;
        presence.details = details;
        presence.largeImageKey = image;
        presence.largeImageText = imageText;
        lib.Discord_UpdatePresence(presence);
    }

    // Creates a rich presence with status, image, and player count without description
    public void imageWithoutDescPresence(String status, String image, int currentPlayers, int maxPlayers) {
        DiscordRichPresence presence = new DiscordRichPresence();
        presence.state = status;
        presence.largeImageKey = image;
        presence.partySize = currentPlayers;
        presence.partyMax = maxPlayers;
        lib.Discord_UpdatePresence(presence);
    }

    // Creates a rich presence with status, description, image, and player count
    public static void imageWithDescPresence(String status, String desc, String image, int currentPlayers, int maxPlayers) {
        Consts.warn("Sending update to Discord...");
        DiscordRichPresence presence = new DiscordRichPresence();
        presence.state = status;
        presence.details = desc;
        presence.largeImageKey = image;
        presence.partySize = currentPlayers;
        presence.partyMax = maxPlayers;
        lib.Discord_UpdatePresence(presence);
        Consts.error("Sent update to Discord...");
    }
}