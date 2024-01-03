package com.dpworld;

public class VesselVisit {
    private String _visit_id;
    private String _ib_vyg;
    private String _ob_vyg;
    private String _lloyds;
    private String _vessel_name;

    public VesselVisit(){}

    public VesselVisit(String visit_id,String ib_vyg,String ob_vyg, String _lloyds, String vessel_name ){
        this._visit_id = visit_id;
        this._ib_vyg = ib_vyg;
        this._ob_vyg = ob_vyg;
        this._lloyds = _lloyds;
        this._vessel_name = vessel_name;
    }

    public String get_visit_id() {
        return _visit_id;
    }

    public void set_visit_id(String _visit_id) {
        this._visit_id = _visit_id;
    }

    public String get_ib_vyg() {
        return _ib_vyg;
    }

    public void set_ib_vyg(String _ib_vyg) {
        this._ib_vyg = _ib_vyg;
    }

    public String get_ob_vyg() {
        return _ob_vyg;
    }

    public void set_ob_vyg(String _ob_vyg) {
        this._ob_vyg = _ob_vyg;
    }

    public String get_lloyds() {
        return _lloyds;
    }

    public void set_lloyds(String _lloyds) {
        this._lloyds = _lloyds;
    }

    public String get_vessel_name() {
        return _vessel_name;
    }

    public void set_vessel_name(String _vessel_name) {
        this._vessel_name = _vessel_name;
    }
}
