package util;

public enum Keyword {

    DEALER("딜러"),
    NAME_SPLITTER(","),
    YES("y"),
    NO("n"),
    WIN("승"),
    SAME("무"),
    LOSE("패"),
    ACE("A");

    private final String value;

    Keyword(final String value) {
        this.value = value;
    }

    public static boolean isValidCommand(final String command) {
        return command.equals(YES.getValue()) || command.equals(NO.getValue());
    }

    public static boolean isCommandYes(final String command) {
        return command.equals(YES.getValue());
    }

    public static boolean isDealer(final String value) {
        return value.equals(DEALER.getValue());
    }

    public String getValue() {
        return value;
    }

    public int length() {
        return value.length();
    }
}
