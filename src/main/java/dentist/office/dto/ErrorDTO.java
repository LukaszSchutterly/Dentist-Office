package dentist.office.dto;

public class ErrorDTO {

    private String Message;

    ErrorDTO() {

    }

    public ErrorDTO(String message) {
        Message = message;
    }

    public String getMessage() {
        return Message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ErrorDTO that = (ErrorDTO) o;

        return Message != null ? Message.equals(that.Message) : that.Message == null;
    }

    @Override
    public int hashCode() {
        return Message != null ? Message.hashCode() : 0;
    }
}
