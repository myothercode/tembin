import com.base.database.trading.model.TradingReturnpolicy;
import com.base.utils.common.ConvertPOJOUtil;
import com.base.xmlpojo.trading.addproduct.ReturnPolicy;
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
    TradingReturnpolicy tr = new TradingReturnpolicy();
    //公共属性值
    tr.setId(123l);
    tr.setCreateUser(123l);
    tr.setCreateTime(new Date());
    tr.setUuid(UUID.randomUUID().toString().replace("-",""));
    tr.setReturnsacceptedoption("dsfddg");

    tr.setExtendedholidayreturns("1");

    /*tr.setParentId(1324123l);
    tr.setParentUuid(UUID.randomUUID().toString().replace("-",""));*/

    ReturnPolicy pojo=new ReturnPolicy();





    ConvertPOJOUtil.convert(pojo,tr);
    }

}
