package com.Training.BackEnd.Soap;

import com.Training.BackEnd.Constants;
import com.Training.BackEnd.wsdl.AddBundleRequest;
import com.Training.BackEnd.wsdl.BundleSoap;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class SoapClient extends WebServiceGatewaySupport {

    public void addBundlesSoap(BundleSoap bundle) {
        AddBundleRequest request = new AddBundleRequest();
        request.setBundle(bundle);
       getWebServiceTemplate().marshalSendAndReceive(Constants.SoapUri, request,
                        new SoapActionCallback(Constants.SoapNamespace + "/addBundleRequest"));
    }
}
