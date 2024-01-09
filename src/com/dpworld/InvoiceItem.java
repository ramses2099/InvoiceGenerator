package com.dpworld;

import java.util.Date;

public class InvoiceItem {

    private Double _amount;
    private int _quantity;
    private String _tariff;
    private String _description;
    private String _long_description;

    public InvoiceItem() {
    }

    public InvoiceItem(Double amount, int quantity, String tariff, String description, String long_description) {
        this._amount = amount;
        this._quantity = quantity;
        this._tariff = tariff;
        this._description = description;
        this._long_description = long_description;
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

}