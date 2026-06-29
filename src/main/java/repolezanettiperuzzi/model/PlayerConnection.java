package repolezanettiperuzzi.model;

/**
 * Transport selected by a player client.
 */
public enum PlayerConnection {
    SOCKET("Socket"),
    RMI("RMI");

    private final String legacyName;

    PlayerConnection(String legacyName) {
        this.legacyName = legacyName;
    }

    public static PlayerConnection fromLegacyName(String legacyName) {
        if (legacyName == null) {
            return null;
        }

        for (PlayerConnection connection : values()) {
            if (connection.legacyName.equals(legacyName)) {
                return connection;
            }
        }

        return null;
    }

    public String legacyName() {
        return legacyName;
    }
}
