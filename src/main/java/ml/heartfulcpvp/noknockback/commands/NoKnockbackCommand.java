package ml.heartfulcpvp.noknockback.commands;

import ml.heartfulcpvp.noknockback.NoKnockback;
import ml.heartfulcpvp.noknockback.playerdata.PlayerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NoKnockbackCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            var player = (Player) sender;
            if (PlayerData.getPlayerData().isEnabled(player)) {
                PlayerData.getPlayerData().setEnabled(player, false);
                NoKnockback.sendInfoMessage(player, "Disabled NoKnock-back.");
            } else {
                PlayerData.getPlayerData().setEnabled(player, true);
                NoKnockback.sendInfoMessage(player, "Enabled NoKnock-back.");
            }
            return true;
        }
        return false;
    }
}
