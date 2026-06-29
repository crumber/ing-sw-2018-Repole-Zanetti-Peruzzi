package repolezanettiperuzzi.model;

/**
 * User interface selected by a player client.
 */
public enum PlayerInterface {
    GUI("GUI"),
    CLI("CLI");

    private final String legacyName;

    PlayerInterface(String legacyName) {
        this.legacyName = legacyName;
    }

    public static PlayerInterface fromLegacyName(String legacyName) {
        if (legacyName == null) {
            return null;
        }

        for (PlayerInterface playerInterface : values()) {
            if (playerInterface.legacyName.equals(legacyName)) {
                return playerInterface;
            }
        }

        return null;
    }

    public String legacyName() {
        return legacyName;
    }
}
