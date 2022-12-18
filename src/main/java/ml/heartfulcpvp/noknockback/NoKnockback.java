package ml.heartfulcpvp.noknockback;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import ml.heartfulcpvp.noknockback.commands.NoKnockbackCommand;
import ml.heartfulcpvp.noknockback.playerdata.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class NoKnockback extends JavaPlugin {

    @Override
    public void onEnable() {
        try {
            PlayerData.createPlayerDataFile();
            PlayerData.initPlayerData();
            getLogger().info("Loaded " + PlayerData.getPlayerData().getPlayerDataList().size() + " players' data.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        var protocol = ProtocolLibrary.getProtocolManager();

        protocol.addPacketListener(new PacketAdapter(this,
                ListenerPriority.NORMAL,
                PacketType.Play.Server.ENTITY_VELOCITY) {
            @Override
            public void onPacketSending(PacketEvent e) {
                if (PlayerData.getPlayerData().isEnabled(e.getPlayer())) {
                    sendInfoMessage(e.getPlayer(), "Cancelling packet..");
                    e.setCancelled(true);
                }
            }
        });

        getCommand("noknockback").setExecutor(new NoKnockbackCommand());
    }

    @Override
    public void onDisable() {
        try {
            PlayerData.write();
            getLogger().info("Saved " + PlayerData.getPlayerData().getPlayerDataList().size() + " players' data.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendInfoMessage(Player player, String message) {
        // player.sendMessage(ChatColor.BOLD + "" + ChatColor.RED + "[" + ChatColor.WHITE + "AutoCrystal" + ChatColor.RED + "] " + ChatColor.WHITE + message);
        player.sendMessage("§c§l[§f§lNoKnock-back§c§l] §f§l" + message);
    }
}
