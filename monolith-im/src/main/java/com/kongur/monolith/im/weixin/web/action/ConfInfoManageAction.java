package com.kongur.monolith.im.weixin.web.action;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kongur.monolith.im.domain.ServiceResult;
import com.kongur.monolith.im.serivce.AccessTokenService;

/**
 * ����ǰ΢��ƽ̨�����ʺ���Ϣ
 * 
 * @author zhengwei
 * @date 2014-2-18
 */
@Controller
@RequestMapping("weixin")
public class ConfInfoManageAction {

    @Resource(name = "defaultAccessTokenService")
    private AccessTokenService accessTokenService;

    /**
     * �鿴��ǰaccess token
     * 
     * @param model
     * @return
     */
    @RequestMapping("get_access_token.htm")
    public String viewAccessToken(Model model) {

        String accessToken = accessTokenService.getAccessToken();

        model.addAttribute("accessToken", accessToken);

        return "weixin/get_access_token";
    }

    /**
     * ˢ�µ�ǰaccess token
     * 
     * @param model
     * @return
     */
    @RequestMapping("refresh_access_token.htm")
    public String refreshAccessToken(Model model) {

        ServiceResult<String> result = accessTokenService.refresh();

        model.addAttribute("result", result);

        return "weixin/refresh_access_token";
    }

}
