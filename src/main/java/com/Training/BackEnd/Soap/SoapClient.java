package Soap;

import com.Training.BackEnd.Constants;
import com.Training.BackEnd.wsdl.AddBundleRequest;
import com.Training.BackEnd.wsdl.AddBundleResponse;
import com.Training.BackEnd.wsdl.BundleDtoSoap;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class SoapClient extends WebServiceGatewaySupport {

    public AddBundleResponse addBundlesSoap(BundleDtoSoap bundle) {
        AddBundleRequest request = new AddBundleRequest();
        request.setBundle(bundle);
        AddBundleResponse response = (AddBundleResponse) getWebServiceTemplate()
                .marshalSendAndReceive(Constants.SoapUri, request,
                        new SoapActionCallback(Constants.SoapNamespace + "/addBundleRequest"));
        return response;
    }
}
