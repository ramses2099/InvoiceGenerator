package com.dpworld;

public class ProcessInvoicesLog {
    private long _draftNbr;
    private String _finalNbr;
    private String _invoiceNbrMsc;
    private long _status;
    private String _message;
    public ProcessInvoicesLog(){}

    public ProcessInvoicesLog(long draftNbr, String finalNbr, String invoiceNbrMsc, long status, String message){
        this._draftNbr = draftNbr;
        this._finalNbr = finalNbr;
        this._invoiceNbrMsc = invoiceNbrMsc;
        this._status = status;
        this._message = message;
    }

    public long get_draftNbr() {
        return _draftNbr;
    }

    public void set_draftNbr(long _draftNbr) {
        this._draftNbr = _draftNbr;
    }

    public String get_finalNbr() {
        return _finalNbr;
    }

    public void set_finalNbr(String _finalNbr) {
        this._finalNbr = _finalNbr;
    }

    public String get_invoiceNbrMsc() {
        return _invoiceNbrMsc;
    }

    public void set_invoiceNbrMsc(String _invoiceNbrMsc) {
        this._invoiceNbrMsc = _invoiceNbrMsc;
    }

    public long get_status() {
        return _status;
    }

    public void set_status(long _status) {
        this._status = _status;
    }

    public String get_message() {
        return _message;
    }

    public void set_message(String _message) {
        this._message = _message;
    }
}
