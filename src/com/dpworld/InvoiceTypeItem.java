package com.dpworld;

public class InvoiceTypeItem {
    private String _gkey;
    private String _id;

    public InvoiceTypeItem(){}
    public InvoiceTypeItem(String gkey, String id){
        this._gkey = gkey;
        this._id = id;
    }

    public String get_gkey() {
        return _gkey;
    }

    public void set_gkey(String _gkey) {
        this._gkey = _gkey;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @Override
    public String toString(){
        return this._id;
    }
}
