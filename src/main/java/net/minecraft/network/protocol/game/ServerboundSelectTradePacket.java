package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ServerboundSelectTradePacket implements Packet<ServerGamePacketListener>
{
    private final int item;

    public ServerboundSelectTradePacket(int pItem)
    {
        this.item = pItem;
    }

    public ServerboundSelectTradePacket(FriendlyByteBuf pItem)
    {
        this.item = pItem.readVarInt();
    }

    public void write(FriendlyByteBuf pBuffer)
    {
        pBuffer.writeVarInt(this.item);
    }

    public void handle(ServerGamePacketListener pHandler)
    {
        pHandler.handleSelectTrade(this);
    }

    public int getItem()
    {
        return this.item;
    }
}
