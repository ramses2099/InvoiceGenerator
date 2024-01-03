package com.dpworld;

import java.util.Date;

public class ChargebleUnit {
    private Long _gkey;
    private String _event_type;
    private Long _ib_vessel_lloyds_id;
    private String _ib_id;
    private String _ib_visit_id;
    private String _ib_carrier_name;
    private Long _ob_vessel_lloyds_id;
    private String _ob_id;
    private String _ob_visit_id;
    private String _ob_carrier_name;
    private String _booking_nbr;
    private String _bl_nbr;
    private Date _event_start_time;
    private Date _event_end_time;

    public ChargebleUnit(){}
    public ChargebleUnit(Long gkey, String event_type,Long ib_vessel_lloyds_id, String ib_id, String ib_visit_id, String ib_carrier_name
            ,Long ob_vessel_lloyds_id, String ob_id, String ob_visit_id, String ob_carrier_name,String booking_nbr, String bl_nbr
            ,Date event_start_time, Date _event_end_time){
        this._gkey = gkey;
        this._event_type = event_type;
        this._ib_vessel_lloyds_id =ib_vessel_lloyds_id;
        this._ib_id = ib_id;
        this._ib_visit_id = ib_visit_id;
        this._ib_carrier_name =ib_carrier_name;
        this._ob_vessel_lloyds_id = ob_vessel_lloyds_id;
        this._ob_id =ob_id;
        this._ob_visit_id = ob_visit_id;
        this._ob_carrier_name = ob_carrier_name;
        this._booking_nbr = booking_nbr;
        this._bl_nbr = bl_nbr;
        this._event_start_time =event_start_time;
        this._event_end_time =_event_end_time;
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

    public Long get_ib_vessel_lloyds_id() {
        return _ib_vessel_lloyds_id;
    }

    public void set_ib_vessel_lloyds_id(Long _ib_vessel_lloyds_id) {
        this._ib_vessel_lloyds_id = _ib_vessel_lloyds_id;
    }

    public String get_ib_id() {
        return _ib_id;
    }

    public void set_ib_id(String _ib_id) {
        this._ib_id = _ib_id;
    }

    public String get_ib_visit_id() {
        return _ib_visit_id;
    }

    public void set_ib_visit_id(String _ib_visit_id) {
        this._ib_visit_id = _ib_visit_id;
    }

    public String get_ib_carrier_name() {
        return _ib_carrier_name;
    }

    public void set_ib_carrier_name(String _ib_carrier_name) {
        this._ib_carrier_name = _ib_carrier_name;
    }

    public Long get_ob_vessel_lloyds_id() {
        return _ob_vessel_lloyds_id;
    }

    public void set_ob_vessel_lloyds_id(Long _ob_vessel_lloyds_id) {
        this._ob_vessel_lloyds_id = _ob_vessel_lloyds_id;
    }

    public String get_ob_id() {
        return _ob_id;
    }

    public void set_ob_id(String _ob_id) {
        this._ob_id = _ob_id;
    }

    public String get_ob_visit_id() {
        return _ob_visit_id;
    }

    public void set_ob_visit_id(String _ob_visit_id) {
        this._ob_visit_id = _ob_visit_id;
    }

    public String get_ob_carrier_name() {
        return _ob_carrier_name;
    }

    public void set_ob_carrier_name(String _ob_carrier_name) {
        this._ob_carrier_name = _ob_carrier_name;
    }

    public String get_booking_nbr() {
        return _booking_nbr;
    }

    public void set_booking_nbr(String _booking_nbr) {
        this._booking_nbr = _booking_nbr;
    }

    public String get_bl_nbr() {
        return _bl_nbr;
    }

    public void set_bl_nbr(String _bl_nbr) {
        this._bl_nbr = _bl_nbr;
    }

    public Date get_event_start_time() {
        return _event_start_time;
    }

    public void set_event_start_time(Date _event_start_time) {
        this._event_start_time = _event_start_time;
    }

    public Date get_event_end_time() {
        return _event_end_time;
    }

    public void set_event_end_time(Date _event_end_time) {
        this._event_end_time = _event_end_time;
    }
}
