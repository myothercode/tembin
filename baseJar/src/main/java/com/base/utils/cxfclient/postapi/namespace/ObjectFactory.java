
package com.base.utils.cxfclient.postapi.namespace;

import com.base.utils.cxfclient.CXFClientRequestVO;
import org.apache.http.message.BasicHeader;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.base.utils.cxfclient.postapi.namespace package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _PostApi_QNAME = new QName("http://namespace.postApi/", "postApi");
    private final static QName _PostApiResponse_QNAME = new QName("http://namespace.postApi/", "postApiResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.base.utils.cxfclient.postapi.namespace
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link com.base.utils.cxfclient.postapi.namespace.PostApi }
     * 
     */
    public PostApi createPostApi() {
        return new PostApi();
    }

    /**
     * Create an instance of {@link PostApiResponse }
     * 
     */
    public PostApiResponse createPostApiResponse() {
        return new PostApiResponse();
    }

    /**
     * Create an instance of {@link  }
     * 
     */
    public CXFClientRequestVO createCxfClientRequestVO() {
        return new CXFClientRequestVO();
    }

    /**
     * Create an instance of {@link  }
     * 
     */
    public BasicHeader createBasicHeader() {
        return new BasicHeader("","");
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link com.base.utils.cxfclient.postapi.namespace.PostApi }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://namespace.postApi/", name = "postApi")
    public JAXBElement<PostApi> createPostApi(PostApi value) {
        return new JAXBElement<PostApi>(_PostApi_QNAME, PostApi.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link PostApiResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://namespace.postApi/", name = "postApiResponse")
    public JAXBElement<PostApiResponse> createPostApiResponse(PostApiResponse value) {
        return new JAXBElement<PostApiResponse>(_PostApiResponse_QNAME, PostApiResponse.class, null, value);
    }

}
