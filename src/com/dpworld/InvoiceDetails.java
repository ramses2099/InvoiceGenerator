package com.dpworld;

public class InvoiceDetails {

    private String _draft_nbr;
    private String _final_nbr;
    private String _status;
    private String _name;
    private String _applied;

    public InvoiceDetails(){}

    public InvoiceDetails(String draft_nbr, String final_nbr, String status, String name, String applied){
        this._draft_nbr = draft_nbr;
        this._final_nbr = final_nbr;
        this._status = status;
        this._name = name;
        this._applied = applied;
    }

    public String get_draft_nbr() {
        return _draft_nbr;
    }

    public void set_draft_nbr(String _draft_nbr) {
        this._draft_nbr = _draft_nbr;
    }

    public String get_final_nbr() {
        return _final_nbr;
    }

    public void set_final_nbr(String _final_nbr) {
        this._final_nbr = _final_nbr;
    }

    public String get_status() {
        return _status;
    }

    public void set_status(String _status) {
        this._status = _status;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_applied() {
        return _applied;
    }

    public void set_applied(String _applied) {
        this._applied = _applied;
    }
}
