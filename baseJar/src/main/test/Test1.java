import com.base.database.trading.model.TradingReturnpolicy;
import com.base.utils.common.ConvertPOJOUtil;
import com.base.utils.cxfclient.CXFClientRequestVO;
import com.base.utils.cxfclient.postapi.namespace.PostApiService;
import com.base.utils.httpclient.ApiHeader;
import com.base.utils.httpclient.HttpClientUtil;
import com.base.xmlpojo.trading.addproduct.ReturnPolicy;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.http.client.HttpClient;
import org.apache.http.message.BasicHeader;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrtor on 2014/7/22.
 */
public class Test1 {
    public static void main(String[] args) {

    }

@Test
    public void test1() throws Exception {
    JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
    factory.setServiceClass(PostApiService.class);
    factory.setAddress("http://localhost:8081/postApi/ws/postApiService?wsdl");
    PostApiService client= (PostApiService) factory.create();
    CXFClientRequestVO cxfClientRequestVO=new CXFClientRequestVO();
    cxfClientRequestVO.setXml("dfdfd");


    List<BasicHeader> headers = new ArrayList<BasicHeader>();
    headers.add(new BasicHeader("X-EBAY-SOA-OPERATION-NAME", "kh"));
    headers.add(new BasicHeader("X-EBAY-SOA-SECURITY-TOKEN ", "kh"));
    headers.add(new BasicHeader("X-EBAY-SOA-REQUEST-DATA-FORMAT", "hk"));
    headers.add(new BasicHeader("X-EBAY-SOA-SERVICE-NAME", "kh"));
    headers.add(new BasicHeader("X-EBAY-SOA-SERVICE-VERSION", "hk"));
    headers.add(new BasicHeader("X-EBAY-SOA-GLOBAL-ID", "khh"));
    cxfClientRequestVO.setHeaders(ApiHeader.header2Map(headers) );


    String result =client.postApi(cxfClientRequestVO);
    System.out.println(result);
    }

}
