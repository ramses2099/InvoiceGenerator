package com.dpworld;

import java.util.Date;

public class InvoiceItem {

    private long _gkey;

    private long _extract_gkey;

    private String _event_id;
    private Double _amount;
    private int _quantity;
    private Date _from_date;
    private String _tariff;
    private String _description;
    private String _long_description;
    private Date _effective_date;


    public InvoiceItem() {
    }

    public InvoiceItem(long gkey, long extract_gkey, String event_id, Double amount, int quantity,
                       Date from_date, String tariff, String description, String long_description,
                       Date effective_date) {
        this._gkey = gkey;
        this._extract_gkey = extract_gkey;
        this._event_id = event_id;
        this._amount = amount;
        this._quantity = quantity;
        this._from_date = from_date;
        this._tariff = tariff;
        this._description = description;
        this._long_description = long_description;
        this._effective_date = effective_date;
    }

    public long get_gkey() {
        return _gkey;
    }

    public void set_gkey(long _gkey) {
        this._gkey = _gkey;
    }

    public String get_event_id() {
        return _event_id;
    }

    public void set_event_id(String _event_id) {
        this._event_id = _event_id;
    }

    public Double get_amount() {
        return _amount;
    }

    public void set_amount(Double _amount) {
        this._amount = _amount;
    }

    public int get_quantity() {
        return _quantity;
    }

    public void set_quantity(int _quantity) {
        this._quantity = _quantity;
    }

    public String get_tariff() {
        return _tariff;
    }

    public void set_tariff(String _tariff) {
        this._tariff = _tariff;
    }

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    public String get_long_description() {
        return _long_description;
    }

    public void set_long_description(String _long_description) {
        this._long_description = _long_description;
    }

    public Date get_from_date() {
        return _from_date;
    }

    public void set_from_date(Date _from_date) {
        this._from_date = _from_date;
    }

    public long get_extract_gkey() {
        return _extract_gkey;
    }

    public void set_extract_gkey(long _extract_gkey) {
        this._extract_gkey = _extract_gkey;
    }

    public Date get_effective_date() {
        return _effective_date;
    }

    public void set_effective_date(Date _effective_date) {
        this._effective_date = _effective_date;
    }
}
