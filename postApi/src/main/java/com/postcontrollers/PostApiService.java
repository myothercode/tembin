package com.postcontrollers;

import com.base.utils.cxfclient.CXFClientRequestVO;
import org.apache.http.Header;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

/**
 * Created by Administrtor on 2014/8/22.
 */
@WebService(serviceName = "postApiService",portName = "postApiPort",targetNamespace = "http://namespace.postApi/")
public interface PostApiService {

    @WebMethod(operationName = "postApi")
    String postApi(@WebParam(name = "CXFClientRequestVO") CXFClientRequestVO CXFClientRequestVO) throws Exception;
}
