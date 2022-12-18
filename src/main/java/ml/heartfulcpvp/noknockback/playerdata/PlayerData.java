package ml.heartfulcpvp.noknockback.playerdata;

import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class PlayerData {
    private static final File folder = new File("./plugins/NoKnockback");
    private static final File playerDataFile = new File("./plugins/NoKnockback/playerdata.txt");

    private static List<Element> playerDataList;

    public PlayerData() throws IOException {
        playerDataList = fromFile();
    }

    private static PlayerData playerData;

    public static void initPlayerData() throws IOException {
        playerData = new PlayerData();
    }

    public static PlayerData getPlayerData() {
        return playerData;
    }

    public static void createPlayerDataFile() throws IOException {
        if (!folder.exists())
            folder.mkdir();
        if (!playerDataFile.exists())
            Files.createFile(playerDataFile.toPath());
    }

    public static List<Element> fromFile() throws IOException {
        var allLines = Files.readAllLines(playerDataFile.toPath());
        var elems = new ArrayList<Element>();

        for (var l : allLines) {
            elems.add(Element.fromLine(l));
        }
        return elems;
    }

    public boolean isEnabled(Player player) {
        for (var d : playerDataList) {
            if (d.getUuid().toString().equals(player.getUniqueId().toString())) {
                return d.isEnabled();
            }
        }
        return false;
    }

    public static void write() throws IOException {
        if (playerDataFile.delete()) {
            Files.createFile(playerDataFile.toPath());
            Files.write(playerDataFile.toPath(),
                    playerDataList.stream().map(Element::build).toList(),
                    StandardCharsets.UTF_8,
                    StandardOpenOption.WRITE
            );
        } else {
            throw new IOException("Failed in deleting old data file");
        }
    }

    public List<Element> getPlayerDataList() {
        return playerDataList;
    }

    public void setEnabled(Player player, boolean enabled) {
        for (var d : playerDataList) {
            if (d.getUuid().toString().equals(player.getUniqueId().toString())) {
                d.setEnabled(enabled);
                return;
            }
        }
        playerDataList.add(new Element(player.getUniqueId(), enabled));
    }

    public void setMinDamage(Player player, float minDamage) {
        // not implemented yet
    }
}
