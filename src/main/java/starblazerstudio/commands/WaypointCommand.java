
package starblazerstudio.commands;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MessageSignature;

import net.minecraft.client.player.LocalPlayer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class WaypointCommand implements ICommandRegister {

    private static final File WAYPOINTS_FILE = new File("waypoints.json");
    private static final Gson GSON = new Gson();
    private Map<String, double[]> waypoints = new HashMap<>();

    public WaypointCommand() {
        loadWaypoints();
    }

    @Override
    public void register(List<String> command, Minecraft mc) {
            if (!command.isEmpty()) {
                if (command.get(0).equalsIgnoreCase(".waypoint")) {
                    if (command.size() == 1) {
                        mc.gui.getChat().addMessage(Component.translatable("blackburn.waypoint.usage"));
                    } else {
                        String subCommand = command.get(1).toLowerCase();
                        switch (subCommand) {
                            case "set":
                                if (command.size() == 2) {
                                    setWaypoint(command.get(2), mc);
                                } else {
                                    mc.gui.getChat().addMessage(Component.translatable("blackburn.waypoint.usage.set"));
                                }
                                break;
                            case "del":
                                if (command.size() == 3) {
                                    deleteWaypoint(command.get(2), mc);
                                } else {
                                    mc.gui.getChat().addMessage(Component.translatable("blackburn.waypoint.usage.del"));
                                }
                                break;
                            case "list":
                                listWaypoints(mc);
                                break;
                            default:
                                mc.gui.getChat().addMessage(Component.translatable("blackburn.waypoint.unknown_command"));
                                break;
                        }
                    }
                command.clear();
            }
        }
    }

    private void setWaypoint(String name, Minecraft mc) {
        LocalPlayer player = mc.player;
        if (player != null) {
            double[] pos = {player.getX(), player.getY(), player.getZ()};
            waypoints.put(name, pos);
            saveWaypoints();
            mc.gui.getChat().addMessage(Component.translatable(I18n.a("blackburn.waypoint.set")));
        }
    }

    private void deleteWaypoint(String name, Minecraft mc) {
        if (waypoints.remove(name) != null) {
            saveWaypoints();
            mc.gui.getChat().addMessage(Component.translatable(I18n.a("blackburn.waypoint.del")));
        } else {
            mc.gui.getChat().addMessage(Component.translatable(I18n.a("blackburn.waypoint.notfound")));
        }
    }

    private void listWaypoints(Minecraft mc) {
        if (waypoints.isEmpty()) {
            mc.gui.getChat().addMessage(Component.translatable(I18n.a("blackburn.waypoint.empty")));
        } else {
            for (Map.Entry<String, double[]> entry : waypoints.entrySet()) {
                double[] pos = entry.getValue();
                mc.gui.getChat().addMessage(Component.translatable(I18n.a("blackburn.waypoint.entry")));
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
