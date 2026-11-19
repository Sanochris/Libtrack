package enums;


public enum BookType {
    PHYSICAL("Physical"),
    DIGITAL("Digital");

    private final String displayName;

    BookType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    
    public static BookType fromString(String text) {
        if (text == null) {
            return null;
        }
        String cleaned = text.trim();
        for (BookType type : values()) {
            if (type.name().equalsIgnoreCase(cleaned)) {
                return type;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return displayName;
    }
}