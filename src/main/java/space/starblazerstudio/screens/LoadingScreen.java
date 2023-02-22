package space.starblazerstudio.screens;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.resources.ResourceLocation;

import java.util.Calendar;
import java.util.Date;

import com.mojang.blaze3d.vertex.PoseStack;

import org.lwjgl.system.Platform;


public class LoadingScreen{

    // adding loading image loading screen
    public String setLoadingImage() {

        String filelocal = "";

        // ALLOWS ME TO Swich the loading image based on day
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                filelocal = "blackburn/title/logo.png";

                break;

            case 2:
                filelocal = "blackburn/title/logo2.png";
                break;

            case 3:
                filelocal = "blackburn/title/logo3.png";
                break;

            case 4:
                filelocal = "blackburn/title/logo4.png";
                break;

            case 5:
                filelocal = "blackburn/title/logo5.png";
                break;

            case 6:
                filelocal = "blackburn/title/logo6.png";
                break;

            case 7:
                filelocal = "blackburn/title/logo7.png";
                break;

            default:
                filelocal = "blackburn/title/logo.png";
                break;
        }
        return filelocal;

    }

    // sets the default mojang logo local
    public void setLogoDefaultLogoLocal(PoseStack pose, GuiComponent component, int j2, int j1, int k2, int i1,
            int d1) {

        component.blit(pose, j2 - j1, k2 - i1, j1, (int) d1, -0.0625F, 0.0F, 120, 60, 120, 120);
        component.blit(pose, j2, k2 - i1, j1, (int) d1, 0.0625F, 60.0F, 120, 60, 120, 120);

    }

    // seting small logo pos
    public void setCustomLogoPosNonFull(LoadingOverlay component, PoseStack pMatrixStack, int px, int py, int UOffset,
            float VOffest, int pWidth, int pHight, int pTextureWidth, int pTextureHight, int screenWitdh,int j){
                
        int width = 0 ;
    if(px < 0 ){
        width =  screenWitdh / 2 -  Math.abs(px);
       }else{
        width = screenWitdh / 2 + px;
       }

       int y = 0;
   
       if(py < 0){
          y = j+72 - Math.abs(py);
       } else{
          y = j+72 + py;
       }
   
        component.blit(pMatrixStack, width, y, UOffset, VOffest, pWidth, pHight, pTextureWidth, pTextureHight);

    }

    // seting logo pos
    public void setCustomLogoPosFull(LoadingOverlay component, PoseStack pMatrixStack, int windowWidth,int px, int py, int UOffset,
            float VOffest, int pWidth, int pHight, int pTextureWidth, int pTextureHight) {

        component.blit(pMatrixStack, px, py, UOffset, VOffest, pWidth, pHight, pTextureWidth, pTextureHight);

    }
    
    

}