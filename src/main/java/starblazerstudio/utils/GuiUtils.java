package starblazerstudio.utils;
/**
 * this is a class to store most of my gui stuff buttons and etc functiosn 
 * JSON STRING FOR MENU {'id':id,'image':str(basedir)+str(id)+str(fileextention),'sp_posX': addToFile(id,"sp_posX"), 'sp_posY':addToFile(id,"sp_posY"),'mp_posX':addToFile(id,"mp_posX"),'mp_posY':addToFile(id,"mp_posY"),'settings_posX':addToFile(id,"settings_posX"),
        'settings_posY':addToFile(id,"settings_posY"), 'quit_posX':addToFile(id,"quit_posX"), 'quit_posY':addToFile(id,"quit_posY"),'splash_posX':addToFile(id,"splash_posX"), 
        'splash_posY':addToFile(id,"splash_posY"),'edition_posX':addToFile(id,"edition_posX"),'edition_posY':addToFile(id,"edition_posY"),
        'lang_posX':addToFile(id,"lang_posX"), 'lang_posY':addToFile(id,"lang_posY")}
 */

 import net.minecraft.client.Minecraft;
 import net.minecraft.client.gui.Font;
 import net.minecraft.client.gui.components.ImageButton;
 import net.minecraft.client.gui.screens.Screen;
 import net.minecraft.network.chat.Component;
 import net.minecraftforge.api.distmarker.OnlyIn;
 import net.optifine.gui.GuiScreenOF;
 
 import com.google.common.util.concurrent.Runnables;
 import com.google.gson.Gson;
 import com.google.gson.JsonArray;
 import com.google.gson.JsonElement;
 import com.google.gson.JsonObject;
 import com.google.gson.JsonParseException;
 import com.mojang.blaze3d.platform.GlStateManager;
 import com.mojang.blaze3d.systems.RenderSystem;
 import com.mojang.blaze3d.vertex.PoseStack;
 import com.mojang.datafixers.kinds.Const;
 import com.mojang.math.Vector3f;
 import com.mojang.realmsclient.RealmsMainScreen;
 import com.mojang.realmsclient.gui.screens.RealmsNotificationsScreen;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.InputStreamReader;
 import java.io.Reader;
 import java.net.URL;
 import java.nio.charset.StandardCharsets;
 import java.nio.file.Files;
 import java.nio.file.Paths;
 import java.util.Calendar;
 import java.util.Date;
 import java.util.Random;
 import java.util.Map.Entry;
 import java.util.concurrent.CompletableFuture;
 import java.util.concurrent.Executor;
 import java.util.function.BiConsumer;
 import java.util.function.Consumer;
 import javax.annotation.Nullable;
 import javax.imageio.ImageIO;
 
 import net.minecraft.SharedConstants;
 import net.minecraft.Util;
 
 import net.minecraft.client.gui.components.AbstractWidget;
 import net.minecraft.client.gui.components.Button;
 import net.minecraft.client.gui.components.ImageButton;
 import net.minecraft.client.gui.components.events.GuiEventListener;
 import net.minecraft.client.gui.components.toasts.SystemToast;
 import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
 import net.minecraft.client.gui.screens.multiplayer.SafetyScreen;
 import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
 import net.minecraft.client.renderer.CubeMap;
 import net.minecraft.client.renderer.GameRenderer;
 import net.minecraft.client.renderer.PanoramaRenderer;
 import net.minecraft.client.renderer.texture.TextureManager;
 import net.minecraft.client.resources.language.I18n;
 import net.minecraft.core.RegistryAccess;
 import net.minecraft.network.chat.CommonComponents;
 
 import net.minecraft.resources.ResourceLocation;
 import net.minecraft.server.MinecraftServer;
 import net.minecraft.util.Mth;
 import net.minecraft.world.level.levelgen.WorldGenSettings;
 import net.minecraft.world.level.storage.LevelStorageSource;
 import net.minecraft.world.level.storage.LevelSummary;
 import net.minecraftforge.api.distmarker.Dist;
 
 public class GuiUtils {

  // loads data from json fiels 
  public static void loadFromJson(InputStream p_128109_) {
    Gson json = new Gson();
    Random random = new Random();
 
    JsonArray jsonobject = json.fromJson(new InputStreamReader(p_128109_, StandardCharsets.UTF_8), JsonArray.class);
    int i  = 0;
 
    for(Entry<String, JsonElement> entry : jsonobject.get(getBackgroundnum(random,0,26)).getAsJsonObject().entrySet()) {
       Consts.debug(entry.getKey().toString());
       Consts.keys.add(entry.getKey().toString());
       Consts.background.add(entry.getValue());
 
       Consts.log("Linked list for keys"+ " "+ entry.getKey().toString()+ " "+ entry.getValue().toString());
       Consts.log("Linked List for data"+ " "+ Consts.background.toString());
       
    }
 
 }
 
  // Dumps json so i cam then hope fully get daya from it 
  public void dumpLayoutJson(){
    try{
    InputStream inputstream = GuiUtils.class.getResourceAsStream("/assets/minecraft/blackburn/backgrounds.json");
 
    try {
       loadFromJson(inputstream);
 
    } catch (Throwable throwable1) {
 
       if (inputstream != null) {
          try {
             inputstream.close();
          } catch (Throwable throwable) {
             throwable1.addSuppressed(throwable);
          }
       }
 
       throw throwable1;
    }
 
    if (inputstream != null) {
       inputstream.close();
    }
 } catch (JsonParseException | IOException ioexception) {
    Consts.error("Couldn't read strings from {}"+"/assets/blackburn/backgrounds.json" +" "+ioexception.toString());
 }
  }
 
}
 