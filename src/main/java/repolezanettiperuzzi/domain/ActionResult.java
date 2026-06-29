package repolezanettiperuzzi.domain;

/**
 * Named result codes returned by game actions and tool-card checks.
 */
public enum ActionResult {

    SUCCESS(1, null),
    FLUX_REMOVER_SECOND_STEP_REQUIRED(11, null),

    STARTING_OR_FINAL_POSITION_NOT_EXIST(-1, "startingFinalPositionNotExist"),
    CHOSEN_BOX_EMPTY(-2, "notMoveChosenEmptyBox"),
    POSITION_OCCUPIED(-3, "notMoveThereIsDieInside"),
    NO_ADJACENT_DIE(-4, "notMoveNotDiceNextToIt"),
    COLOUR_RESTRICTION_VIOLATED(-5, "notMoveNotRespectColourRestriction"),
    VALUE_RESTRICTION_VIOLATED(-6, "notMoveNotRespectValueRestriction"),
    BOX_RESTRICTION_VIOLATED(-7, "notMoveRespectRestriction"),
    NOT_ENOUGH_FAVOR_TOKENS(-8, "notNaveFavorTokens"),
    EMPTY_DRAFT_POSITION(-9, "emptyPositionDraft"),
    DIE_NEXT_TO_POSITION(-10, "notMoveThereisDieNextToIt"),
    WRONG_NUMBER(-11, "wrongNumber"),
    NOT_SECOND_TURN(-12, "notUseCardIsNotSecondTurn"),
    TOOL_CARD_AFTER_INSERTED_DIE(-13, "notUseCardHaveAlreadyInsertedDie"),
    CHOICE_NOT_EXIST(-14, "choiceNotExist"),
    DECREASE_IS_MINIMUM(-15, "notDecreaseIsMinimum"),
    INCREASE_IS_MAXIMUM(-16, "notIncreaseIsMaximum"),
    SECOND_DIE_START_EMPTY(-17, "notMoveDie2BoxEmpty"),
    SECOND_DIE_TARGET_OCCUPIED(-18, "notMoveDie2NotEmptyBox"),
    SECOND_DIE_NO_ADJACENT_DIE(-19, "notMoveDie2NotDiceNextToIt"),
    SECOND_DIE_RESTRICTION_VIOLATED(-20, "notMoveDie2NotRespectRestriction"),
    ROUND_TRACK_POSITION_EMPTY(-21, "notDieOnRoundTrack"),
    ROUND_TRACK_COLOUR_MISMATCH(-22, "notMoveDiceNotSameColourRoundTrack"),
    ADJACENT_SAME_COLOUR(-23, "notPutThereIsDieSameColorNear"),
    ADJACENT_SAME_VALUE(-24, "notPutThereIsDieSameValueNear"),
    ADJACENT_SAME_COLOUR_OR_VALUE(-25, "notPutDieThereIsDieSameColorValue"),
    SECOND_DIE_ADJACENT_SAME_COLOUR_OR_VALUE(-26, "notPutDie2ThereIsSameColorValue"),
    FIRST_PLACEMENT_NOT_BOUNDARY(-27, "notPutDieHereNotBoundaryPosition"),
    ALREADY_INSERTED_DIE(-28, "alreadyInsertedDie"),
    ALREADY_USED_TOOL_CARD(-29, "alreadyUsedToolCard"),
    NOT_FIRST_TURN(-30, "notFirstTurn"),
    DIE_NOT_INSERTED_IN_TURN(-31, "notInsertDieInTurn");

    private static final String ERROR_PREFIX = "error ";

    private final int code;
    private final String clientErrorCode;

    ActionResult(int code, String clientErrorCode) {
        this.code = code;
        this.clientErrorCode = clientErrorCode;
    }

    public int getCode() {
        return code;
    }

    public boolean matches(int code) {
        return this.code == code;
    }

    public boolean isSuccess() {
        return this == SUCCESS;
    }

    public boolean isError() {
        return clientErrorCode != null;
    }

    public String toClientErrorMessage() {
        ActionResult errorResult = isError() ? this : DIE_NOT_INSERTED_IN_TURN;
        return ERROR_PREFIX + errorResult.clientErrorCode;
    }

    public static boolean isErrorCode(int code) {
        return code < 0;
    }

    public static ActionResult fromCode(int code) {
        for (ActionResult result : values()) {
            if (result.matches(code)) {
                return result;
            }
        }

        return DIE_NOT_INSERTED_IN_TURN;
    }

    public static ActionResult fromErrorCode(int code) {
        ActionResult result = fromCode(code);
        return result.isError() ? result : DIE_NOT_INSERTED_IN_TURN;
    }
}
