package com.dpworld;

public class ProcessInvoices {
    private long _drafNbr;
    private String _finalNbr;
    private String _invoiceNbrMsc;
    private String _filename;

    public ProcessInvoices(){}
    public ProcessInvoices(long drafNbr, String finalNbr, String invoiceNbrMsc, String filename){
        this._drafNbr = drafNbr;
        this._finalNbr = finalNbr;
        this._invoiceNbrMsc = invoiceNbrMsc;
        this._filename = filename;
    }

    public long get_drafNbr() {
        return _drafNbr;
    }

    public void set_drafNbr(long _drafNbr) {
        this._drafNbr = _drafNbr;
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

    public String get_filename() {
        return _filename;
    }

    public void set_filename(String _filename) {
        this._filename = _filename;
    }
}
