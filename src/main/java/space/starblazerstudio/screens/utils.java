package space.starblazerstudio.screens;

public class utils {
    
    // should help me register textures
    public void registerTextures(){
        LoadingScreen image  = new LoadingScreen();
        
        MOJANG_STUDIOS_LOGO_LOCATION = new ResourceLocation(image.setLoadingImage());
        pMc.getTextureManager().register(MOJANG_STUDIOS_LOGO_LOCATION, new LoadingOverlay.LogoTexture());
    }
}
