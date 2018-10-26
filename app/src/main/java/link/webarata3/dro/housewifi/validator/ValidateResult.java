package link.webarata3.dro.housewifi.validator;

public class ValidateResult {
    private boolean valid;
    private String errorMessage;

    public ValidateResult() {
        valid = true;
    }

    public ValidateResult(String errorMessage) {
        valid = false;
        this.errorMessage = errorMessage;
    }

    public boolean isValid() {
        return valid;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
