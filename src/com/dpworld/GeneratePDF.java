package com.dpworld;

import com.navis.argo.webservice.types.v1_0.GenericInvokeResponseWsType;
import com.navis.argo.webservice.types.v1_0.ResponseType;
import com.navis.argo.webservice.types.v1_0.ScopeCoordinateIdsWsType;
import com.navis.www.services.argoservice.ArgoServiceLocator;
import com.navis.www.services.argoservice.ArgoServicePort;

import javax.xml.rpc.Stub;
import java.net.URL;


public class GeneratePDF {
    private static String ERRORS = "3";
    Credential _credential = null;
    BillingSetting billingSetting = null;
    public GeneratePDF(){
        this.billingSetting = new BillingSetting();
        this._credential = billingSetting.getValue();
    }

    public void generatePdf(Long draftNbr,String fileName){
        try{
            //DownloadManager myDownMan;
            GenericInvokeResponseWsType response;
            ResponseType commonResponse;
            String webserviceResponse;
            String reportUrl;
            String reportID;
            String xmlQuery;

            // Imprimo reporte 1
            xmlQuery = "<groovy class-name=\"DPWCAUWSGenerateInvoicePdf\" class-location=\"code-extension\">\n" +
                    "    <parameters>\n" +
                    "        <parameter id=\"draft\" value=\""+ draftNbr + "\"/>        \n" +
                    "        <parameter id=\"filename\" value=\""+ fileName + "\"/>        \n" +
                    "    </parameters>\n" +
                    " </groovy>";

            //myDownMan = new DownloadManager();
            response = callGenericWebservice(xmlQuery);
            commonResponse = response.getCommonResponse();
            if (commonResponse.getStatus().equals(ERRORS)) {
                System.err.println("Web service returned error:\n" + commonResponse.getStatusDescription());
            }

            // getting report URL with webservice response
            webserviceResponse = commonResponse.getQueryResults(0).getResult();
            reportUrl = webserviceResponse.substring(webserviceResponse.indexOf('>') + 1, webserviceResponse.lastIndexOf('<'));
            reportID = webserviceResponse.substring(webserviceResponse.indexOf('=') + 1, webserviceResponse.lastIndexOf('<'));

            System.out.println(" repurl " + reportUrl + " " + reportID);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Calls generic web service
     * @param inQueryXML
     * @return
     * @throws Exception
     */
    private GenericInvokeResponseWsType callGenericWebservice(String inQueryXML) throws Exception {
        GenericInvokeResponseWsType response = null;
        ScopeCoordinateIdsWsType scope = new ScopeCoordinateIdsWsType();
        scope.setOperatorId("DPW");
        scope.setComplexId("CAU");
        scope.setFacilityId("CAU");
        scope.setYardId("CAU");
        // Identify the Web Services host
        ArgoServiceLocator service = new ArgoServiceLocator();
        ArgoServicePort port = service.getArgoServicePort(new URL(ARGO_SERVICE_URL));
        Stub stub = (Stub) port;

        stub._setProperty(Stub.USERNAME_PROPERTY, this._credential.get_user());
        stub._setProperty(Stub.PASSWORD_PROPERTY, this._credential.get_pwd());

        response = port.genericInvoke(scope, inQueryXML);
        return response;
    }

    private static final String ARGO_SERVICE_URL ="http://192.168.6.219:11080/billing/services/argoservice";

}
