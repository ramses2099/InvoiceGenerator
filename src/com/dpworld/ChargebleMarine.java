package com.dpworld;

import java.util.Date;

public class ChargebleMarine {
    private Long _gkey;
    private String _event_type;
    private String _vv_id;
     private String _ib_id;
    private String _ob_id;
    private String _lloyds_id;
    private String _vls_name;

    public ChargebleMarine(){}
    public ChargebleMarine(Long gkey, String event_type, String vv_id, String ib_id, String ob_id, String lloyds_id, String vls_name){
        this._gkey = gkey;
        this._event_type = event_type;
        this._vv_id = vv_id;
        this._ib_id = ib_id;
        this._ob_id = ob_id;
        this._lloyds_id = lloyds_id;
        this._vls_name = vls_name;
    }

    public Long get_gkey() {
        return _gkey;
    }

    public void set_gkey(Long _gkey) {
        this._gkey = _gkey;
    }

    public String get_event_type() {
        return _event_type;
    }

    public void set_event_type(String _event_type) {
        this._event_type = _event_type;
    }

    public String get_vv_id() {
        return _vv_id;
    }

    public void set_vv_id(String _vv_id) {
        this._vv_id = _vv_id;
    }

    public String get_ib_id() {
        return _ib_id;
    }

    public void set_ib_id(String _ib_id) {
        this._ib_id = _ib_id;
    }

    public String get_ob_id() {
        return _ob_id;
    }

    public void set_ob_id(String _ob_id) {
        this._ob_id = _ob_id;
    }

    public String get_lloyds_id() {
        return _lloyds_id;
    }

    public void set_lloyds_id(String _lloyds_id) {
        this._lloyds_id = _lloyds_id;
    }

    public String get_vls_name() {
        return _vls_name;
    }

    public void set_vls_name(String _vls_name) {
        this._vls_name = _vls_name;
    }
}
