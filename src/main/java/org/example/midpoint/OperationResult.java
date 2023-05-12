package org.example.midpoint;

public class OperationResult {
    public enum OPERATION_STATUS {FAILED, SUCCEED}

    private final OPERATION_STATUS status;
    private final String msg;

    public OperationResult(OPERATION_STATUS status, String msg){
        this.status = status;
        this.msg = msg;
    }

    public OPERATION_STATUS status() {
        return status;
    }

    public String msg() {
        return msg;
    }

}