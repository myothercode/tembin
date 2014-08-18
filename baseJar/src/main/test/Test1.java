import com.base.database.trading.model.TradingReturnpolicy;
import com.base.utils.common.ConvertPOJOUtil;
import com.base.utils.httpclient.HttpClientUtil;
import com.base.xmlpojo.trading.addproduct.ReturnPolicy;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.http.client.HttpClient;
import org.junit.Test;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Administrtor on 2014/7/22.
 */
public class Test1 {
    public static void main(String[] args) {

    }

@Test
    public void test1() throws Exception {
    String x= StringEscapeUtils.escapeXml("<script src=\"http://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js\" type=\"text/javascript\"></script>");
    System.out.println(x);
    }

}
