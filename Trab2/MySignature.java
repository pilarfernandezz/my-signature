import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import MySignatureStatus;

public class MySignature {
    private MySignatureStatus status;
    private String value;

    public MySignatureStatus getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MySignatureStatus getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public MySignature getInstance() {
        return new MySignature();
    }

    public void initSign() {
        throw NotImplementedException();
    }git

    public void update() {
        throw NotImplementedException();
    }

    public void initVerify() {
        throw NotImplementedException();
    }

    public void verify() {
        throw NotImplementedException();
    }

    public void sign() {
        throw NotImplementedException();
    }
}