package simon.hoefer.Aufgabe1;

public abstract class AbstractKomplexValidationBean {

    private String errorMsg;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public void resetErrorMsg(){
        this.errorMsg = null;
    }
    public abstract boolean isValid();
}
