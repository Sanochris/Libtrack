package enums;

public enum BookStatus {
    AVAILABLE,
    BORROWED,
    RESERVED;

    public static BookStatus fromString(String text) {
        if (text == null) {
            return null;
        }
        String cleaned = text.trim();
        for (BookStatus status : values()) {
            if (status.name().equalsIgnoreCase(cleaned)) {
                return status;
            }
        }
        return null;
    }
}