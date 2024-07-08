package starblazerstudio.commands;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import starblazerstudio.utils.Consts;

public class WaypointCommand implements ICommandRegister {

    private static final File WAYPOINTS_FILE = new File("waypoints.json");
    private static final Gson GSON = new Gson();
    private Map<String, double[]> waypoints = new HashMap<>();

    public WaypointCommand() {
        loadWaypoints();
    }

    @Override
    public void register(List<String> commandArguments, Minecraft client) {
        if (commandArguments.isEmpty() || !commandArguments.get(0).equalsIgnoreCase(".waypoint")) {
            return;
        }
        String subCommand = commandArguments.size() > 1 ? commandArguments.get(1).toLowerCase() : "";
        switch (subCommand) {
            case "set":
                if (commandArguments.size() == 3) {
                    setWaypoint(commandArguments.get(2), client);
                } else {
                    displayUsageMessage(client, "set");
                }
                break;
            case "del":
                if (commandArguments.size() == 3) {
                    deleteWaypoint(commandArguments.get(2), client);
                } else {
                    displayUsageMessage(client, "del");
                }
                break;
            case "list":
                listWaypoints(client);
                break;
            default:
                displayUsageMessage(client);
                break;
        }
        commandArguments.clear();
    }

    private void displayUsageMessage(Minecraft client, String subCommand) {
        client.gui.getChat().addMessage(Component.translatable(I18n.a("blackburn.commands.waypoint.usage." + subCommand)));
    }

    private void displayUsageMessage(Minecraft client) {
        client.gui.getChat().addMessage(Component.translatable(I18n.a("blackburn.commands.waypoint.usage")));
    }

    private void setWaypoint(String name, Minecraft mc) {
        LocalPlayer player = mc.player;
        if (player != null) {
            double[] pos = {player.getX(), player.getY(), player.getZ()};
            waypoints.put(name, pos);
            saveWaypoints();
            mc.gui.getChat().addMessage(Component.translatable(I18n.a("blackburn.commands.waypoint.set"), name));
        }
    }

    private void deleteWaypoint(String name, Minecraft mc) {
        if (waypoints.remove(name) != null) {
            saveWaypoints();
            mc.gui.getChat().addMessage(Component.translatable(I18n.a("blackburn.commands.waypoint.del"), name));
        } else {
            mc.gui.getChat().addMessage(Component.translatable(I18n.a("blackburn.commands.waypoint.notfound"), name));
        }
    }

    private void listWaypoints(Minecraft mc) {
        if (waypoints.isEmpty()) {
            mc.gui.getChat().addMessage(Component.translatable(I18n.a("blackburn.commands.waypoint.empty")));
        } else {
            Set<String> keys = waypoints.keySet();
            for (String key : keys) {
                double[] pos = waypoints.get(key);
                mc.gui.getChat().addMessage(Component.translatable(I18n.a("blackburn.commands.waypoint.entry"), key, pos[0], pos[1], pos[2]));
            }
        }
    }

    private void saveWaypoints() {
        try (FileWriter writer = new FileWriter(WAYPOINTS_FILE)) {
            GSON.toJson(waypoints, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadWaypoints() {
        if (WAYPOINTS_FILE.exists()) {
            try {
                String json = new String(Files.readAllBytes(WAYPOINTS_FILE.toPath()));
                waypoints = GSON.fromJson(json, new TypeToken<Map<String, double[]>>() {}.getType());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getName() {
        return "blackburn.commands.waypoint.pre";
    }

    @Override
    public String getDesc() {
        return "blackburn.commands.waypoint.desc";
    }
}
