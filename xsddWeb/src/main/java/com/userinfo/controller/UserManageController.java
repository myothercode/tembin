package com.userinfo.controller;

import com.base.domains.CommonParmVO;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.threadpool.ApiCallable;
import com.base.utils.threadpool.TaskPool;
import com.common.base.web.BaseAction;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.concurrent.ListenableFutureTask;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrtor on 2014/8/6.
 * 用户管理
 */
@Controller
@RequestMapping("user")
//@Scope("")
public class UserManageController extends BaseAction {
    static Logger logger = Logger.getLogger(UserManageController.class);
    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping("bindEbayAccount.do")
    public ModelAndView bindEbayAccount(@ModelAttribute( "initSomeParmMap" )ModelMap modelMap,
                                        CommonParmVO commonParmVO) throws Exception {
      UsercontrollerDevAccountExtend d = userInfoService.getDevInfo(commonParmVO.getId());
        d.setApiSiteid("0");
        d.setApiCallName("GeteBayOfficialTime");

        ListenableFutureTask<String> task = new ListenableFutureTask<String>(new ApiCallable(d,"xml","url"));
        TaskPool.threadPoolTaskExecutor.submit(task);
        String res=task.get(5, TimeUnit.SECONDS);

        /*task.addCallback(new ListenableFutureCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("===success callback 1");
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });*/

      return   forword("userinfobindEbayAccount",modelMap);
    }

}
