
package com.base.utils.cxfclient.postapi.namespace;

import com.base.utils.cxfclient.CXFClientRequestVO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "postApi", propOrder = {
        "cxfClientRequestVO"
})
public class PostApi {

    @XmlElement(name = "CXFClientRequestVO")
    protected CXFClientRequestVO cxfClientRequestVO;

    /**
     * 获取cxfClientRequestVO属性的值。
     *
     * @return
     *     possible object is
     *     {@link  }
     *
     */
    public CXFClientRequestVO getCXFClientRequestVO() {
        return cxfClientRequestVO;
    }

    /**
     * 设置cxfClientRequestVO属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link  }
     *
     */
    public void setCXFClientRequestVO(CXFClientRequestVO value) {
        this.cxfClientRequestVO = value;
    }

}
