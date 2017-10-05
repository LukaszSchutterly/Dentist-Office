package dentist.office.exception;

public class IllegalMergeException extends Exception {

    @Override
    public String getMessage() {
        return "Not allowed patient merge";
    }
}
