package simon.hoefer.Aufgabe1;

public abstract class AbstractKomplexValidationBean {

    private String errorMsg;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    public abstract boolean isValid();
}
