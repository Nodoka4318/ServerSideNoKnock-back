package ml.heartfulcpvp.noknockback.playerdata;

import java.util.UUID;

public class Element {
    // 我流フォーマットゆるして:pleading_face:
    private UUID uuid;
    private boolean isEnabled;

    public Element(UUID uuid, boolean isEnabled) {
        this.uuid = uuid;
        this.isEnabled = isEnabled;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public String build() {
        return uuid + ":" + isEnabled;
    }

    public static Element fromLine(String line) {
        var elems = line.split(":");
        return new Element(UUID.fromString(elems[0]), Boolean.getBoolean(elems[1]));
    }
}
