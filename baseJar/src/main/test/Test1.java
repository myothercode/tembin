import com.base.database.trading.model.TradingReturnpolicy;
import com.base.utils.common.ConvertPOJOUtil;
import com.base.utils.httpclient.HttpClientUtil;
import com.base.xmlpojo.trading.addproduct.ReturnPolicy;
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
    String x="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
            "<GeteBayOfficialTimeRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">" +
            "<RequesterCredentials>" +
            "<eBayAuthToken>AgAAAA**AQAAAA**aAAAAA**QM20Uw**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6wFk4GhCpGGoA+dj6x9nY+seQ**Sd0CAA**AAMAAA**a8K8fmx7s3vurAo/SGFiTl1PTp5pI/+JwbMuDqmBGpz/3++lJUPnHvIVgzjMBQtP89Qx/RDRY0s2Y10elagAew81uMb4EkWRIZIYw7YZdpp+ExQ5BUNnopAPRXeWz9ODtU2ujdk7m1y+wGLeNS8iFwP3bmHjcGyMLHDWvKkyBsky/y9kbpTmMbcry3SlUVykFG5cYeQFgpEjqJohfH6mX2T7NcD0L0eVWzrU4/Wh7NFpmGfxsgzN1e3FA89sLG6HZfJhSg+SBRT+dR29BAf2A9oVI6+yIctnfGPnHL68UrGzmgh+EgUf8aQW8n17zLEZEatUjpwlrAoRIH/CbG+gVZkSkGQyxI/WoqWtwAZKyAApvOSqGNYVRwef61GHMmAyf7eXojMBP3na1wMMAHpde6APji+3QiDlGT49T6wzcUA8TPRTTIQCYuqsEBY0tAjVrTEwcbsPUW9/533q8MNsVywH99VDme1fsKKLK4v93mZg9JzAMbdeNIfrtcH8CVQYQzv4n+xGNvchUD8pwGtZ98RxGk/8dZr0pEMZpcM70YNGLAtzbNEsPvfPdQfDUV6bgsHRBzySa+jAeCDesslrC3fanWJFFa/7YjLnNqdcVpWsC0V/uRWbdzOJ9mo2+sJelL5mPCgWS+YdFHYgdxYRnJCb/VBkkrm7IuSHUuBXWVdlaqs5Miu0fWIj0CZ/KYjBZK7XZyAN7LP1spAiFQJ2vo8/UCqcoay4ftMT68QgNcAyucVEr8gAF7k7RFDFEYSf</eBayAuthToken>\n" +
            "</RequesterCredentials>" +
            "</GeteBayOfficialTimeRequest>​";
    HttpClient client = HttpClientUtil.getHttpsClient();
    String res = HttpClientUtil.post(client, "https://api.sandbox.ebay.com/ws/api.dll", x,"gb2312");
    System.out.println(res);
    }

}
