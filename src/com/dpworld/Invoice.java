package com.dpworld;

public class Invoice {
    private Long _gkey;
    private Long _draft_nbr;
    private String _final_nbr;
    private String _status;
    private String _payeeid;
    private String _payee;
    private String _vessel_visit_id;
    private String _customer_references;
    public Invoice(){}

    public Invoice(Long gkey, Long _draft_nbr, String final_nbr, String status,String payeeid,  String payee, String vessel_visit_id,
                   String customer_references){
        this._gkey = gkey;
        this._draft_nbr = _draft_nbr;
        this._final_nbr = final_nbr;
        this._status = status;
        this._payeeid = payeeid;
        this._payee = payee;
        this._vessel_visit_id = vessel_visit_id;
        this._customer_references =customer_references;
    }

    public Long get_draft_nbr() {
        return _draft_nbr;
    }

    public void set_draft_nbr(Long _draft_nbr) {
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

    public String get_payee() {
        return _payee;
    }

    public void set_payee(String payee) {
        this._payee = payee;
    }

    public String get_vessel_visit_id() {
        return _vessel_visit_id;
    }

    public void set_vessel_visit_id(String _vessel_visit_id) {
        this._vessel_visit_id = _vessel_visit_id;
    }

    public String get_customer_references() {
        return _customer_references;
    }

    public void set_customer_references(String _customer_references) {
        this._customer_references = _customer_references;
    }
    public Long get_gkey() {
        return _gkey;
    }
    public void set_gkey(Long _gkey) {
        this._gkey = _gkey;
    }

    public String get_payeeid() {
        return _payeeid;
    }

    public void set_payeeid(String _payeeid) {
        this._payeeid = _payeeid;
    }
}
