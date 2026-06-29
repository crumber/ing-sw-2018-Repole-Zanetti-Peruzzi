package repolezanettiperuzzi.model;

/**
 * Restriction mode used when placing or moving a die in a box.
 */
public enum BoxRestriction {
    BOTH("both"),
    NONE("none"),
    VALUE("value"),
    COLOUR("colour");

    private final String legacyName;

    BoxRestriction(String legacyName) {
        this.legacyName = legacyName;
    }

    public static BoxRestriction fromLegacyName(String legacyName) {
        if (legacyName == null) {
            return null;
        }

        for (BoxRestriction restriction : values()) {
            if (restriction.legacyName.equals(legacyName)) {
                return restriction;
            }
        }

        return null;
    }

    public String legacyName() {
        return legacyName;
    }
}
