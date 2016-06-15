package at.dafnik.ragemode.API;

import java.lang.reflect.Field;

import net.minecraft.server.v1_10_R1.IChatBaseComponent;
import net.minecraft.server.v1_10_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_10_R1.PacketPlayOutChat;
import net.minecraft.server.v1_10_R1.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_10_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_10_R1.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_10_R1.PlayerConnection;

import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;


public class Title {
	
	public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String message) {
		sendTitle(player, fadeIn, stay, fadeOut, message, null);
	}

	public static void sendSubtitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String message) {
		sendTitle(player, fadeIn, stay, fadeOut, null, message);
	}

	public static void sendFullTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle) {
		sendTitle(player, fadeIn, stay, fadeOut, title, subtitle);
	}

	public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle) {
		PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;

		PacketPlayOutTitle packetPlayOutTimes = new PacketPlayOutTitle(EnumTitleAction.TIMES, null, fadeIn.intValue(), stay.intValue(), fadeOut.intValue());
		connection.sendPacket(packetPlayOutTimes);

		if (subtitle != null) {
			IChatBaseComponent titleSub = ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
			PacketPlayOutTitle packetPlayOutSubTitle = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, titleSub);
			connection.sendPacket(packetPlayOutSubTitle);
		}

		if (title != null) {
			IChatBaseComponent titleMain = ChatSerializer.a("{\"text\": \"" + title + "\"}");
			PacketPlayOutTitle packetPlayOutTitle = new PacketPlayOutTitle(EnumTitleAction.TITLE, titleMain);
			connection.sendPacket(packetPlayOutTitle);
		}
	}

	static public void sendActionBar(Player player, String message) {
		if (message == null)
			message = "";

		PlayerConnection con = ((CraftPlayer) player).getHandle().playerConnection;

		IChatBaseComponent chat = ChatSerializer.a("{\"text\": \"" + message + "\"}");
		PacketPlayOutChat packet = new PacketPlayOutChat(chat, (byte) 2);

		con.sendPacket(packet);
	}
		
	static public void sendTabList(Player player, String headerstring) {
		if (headerstring == null)
			headerstring = "";

		PlayerConnection con = ((CraftPlayer) player).getHandle().playerConnection;

		IChatBaseComponent header = ChatSerializer.a("{\"text\": \"" + headerstring + "\"}");
		IChatBaseComponent footer = ChatSerializer.a("{\"text\": \"" + "§7Gamemode made by §3Dafnik" + "\"}");

		PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter(header);

		try {
			Field b = packet.getClass().getDeclaredField("b");
			b.setAccessible(true);
			b.set(packet, footer);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.sendPacket(packet);
		}
	}
}