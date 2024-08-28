import lombok.Getter;

@Getter
public enum Size {

    SMALL("SMALL"),
    MEDIUM("MEDIUM"),
    LARGE("LARGE");

    private final String value;

    Size(String value) {
        this.value = value;
    }
}
