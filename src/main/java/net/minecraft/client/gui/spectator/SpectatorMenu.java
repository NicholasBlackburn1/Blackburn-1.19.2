package net.minecraft.client.gui.spectator;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.spectator.SpectatorGui;
import net.minecraft.client.gui.spectator.categories.SpectatorPage;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class SpectatorMenu
{
    private static final SpectatorMenuItem CLOSE_ITEM = new SpectatorMenu.CloseSpectatorItem();
    private static final SpectatorMenuItem SCROLL_LEFT = new SpectatorMenu.ScrollMenuItem(-1, true);
    private static final SpectatorMenuItem SCROLL_RIGHT_ENABLED = new SpectatorMenu.ScrollMenuItem(1, true);
    private static final SpectatorMenuItem SCROLL_RIGHT_DISABLED = new SpectatorMenu.ScrollMenuItem(1, false);
    private static final int MAX_PER_PAGE = 8;
    static final Component CLOSE_MENU_TEXT = Component.translatable("spectatorMenu.close");
    static final Component PREVIOUS_PAGE_TEXT = Component.translatable("spectatorMenu.previous_page");
    static final Component NEXT_PAGE_TEXT = Component.translatable("spectatorMenu.next_page");
    public static final SpectatorMenuItem EMPTY_SLOT = new SpectatorMenuItem()
    {
        public void selectItem(SpectatorMenu p_101812_)
        {
        }
        public Component getName()
        {
            return CommonComponents.EMPTY;
        }
        public void renderIcon(PoseStack p_101808_, float p_101809_, int p_101810_)
        {
        }
        public boolean isEnabled()
        {
            return false;
        }
    };
    private final SpectatorMenuListener listener;
    private SpectatorMenuCategory category;
    private int selectedSlot = -1;
    int page;

    public SpectatorMenu(SpectatorMenuListener pListener)
    {
        this.category = new RootSpectatorMenuCategory();
        this.listener = pListener;
    }

    public SpectatorMenuItem getItem(int pIndex)
    {
        int i = pIndex + this.page * 6;

        if (this.page > 0 && pIndex == 0)
        {
            return SCROLL_LEFT;
        }
        else if (pIndex == 7)
        {
            return i < this.category.getItems().size() ? SCROLL_RIGHT_ENABLED : SCROLL_RIGHT_DISABLED;
        }
        else if (pIndex == 8)
        {
            return CLOSE_ITEM;
        }
        else
        {
            return i >= 0 && i < this.category.getItems().size() ? MoreObjects.firstNonNull(this.category.getItems().get(i), EMPTY_SLOT) : EMPTY_SLOT;
        }
    }

    public List<SpectatorMenuItem> getItems()
    {
        List<SpectatorMenuItem> list = Lists.newArrayList();

        for (int i = 0; i <= 8; ++i)
        {
            list.add(this.getItem(i));
        }

        return list;
    }

    public SpectatorMenuItem getSelectedItem()
    {
        return this.getItem(this.selectedSlot);
    }

    public SpectatorMenuCategory getSelectedCategory()
    {
        return this.category;
    }

    public void selectSlot(int pSlot)
    {
        SpectatorMenuItem spectatormenuitem = this.getItem(pSlot);

        if (spectatormenuitem != EMPTY_SLOT)
        {
            if (this.selectedSlot == pSlot && spectatormenuitem.isEnabled())
            {
                spectatormenuitem.selectItem(this);
            }
            else
            {
                this.selectedSlot = pSlot;
            }
        }
    }

    public void exit()
    {
        this.listener.onSpectatorMenuClosed(this);
    }

    public int getSelectedSlot()
    {
        return this.selectedSlot;
    }

    public void selectCategory(SpectatorMenuCategory pCategory)
    {
        this.category = pCategory;
        this.selectedSlot = -1;
        this.page = 0;
    }

    public SpectatorPage getCurrentPage()
    {
        return new SpectatorPage(this.getItems(), this.selectedSlot);
    }

    static class CloseSpectatorItem implements SpectatorMenuItem
    {
        public void selectItem(SpectatorMenu pMenu)
        {
            pMenu.exit();
        }

        public Component getName()
        {
            return SpectatorMenu.CLOSE_MENU_TEXT;
        }

        public void renderIcon(PoseStack p_101819_, float p_101820_, int p_101821_)
        {
            RenderSystem.setShaderTexture(0, SpectatorGui.SPECTATOR_LOCATION);
            GuiComponent.blit(p_101819_, 0, 0, 128.0F, 0.0F, 16, 16, 256, 256);
        }

        public boolean isEnabled()
        {
            return true;
        }
    }

    static class ScrollMenuItem implements SpectatorMenuItem
    {
        private final int direction;
        private final boolean enabled;

        public ScrollMenuItem(int pDirection, boolean pEnabled)
        {
            this.direction = pDirection;
            this.enabled = pEnabled;
        }

        public void selectItem(SpectatorMenu pMenu)
        {
            pMenu.page += this.direction;
        }

        public Component getName()
        {
            return this.direction < 0 ? SpectatorMenu.PREVIOUS_PAGE_TEXT : SpectatorMenu.NEXT_PAGE_TEXT;
        }

        public void renderIcon(PoseStack p_101832_, float p_101833_, int p_101834_)
        {
            RenderSystem.setShaderTexture(0, SpectatorGui.SPECTATOR_LOCATION);

            if (this.direction < 0)
            {
                GuiComponent.blit(p_101832_, 0, 0, 144.0F, 0.0F, 16, 16, 256, 256);
            }
            else
            {
                GuiComponent.blit(p_101832_, 0, 0, 160.0F, 0.0F, 16, 16, 256, 256);
            }
        }

        public boolean isEnabled()
        {
            return this.enabled;
        }
    }
}
