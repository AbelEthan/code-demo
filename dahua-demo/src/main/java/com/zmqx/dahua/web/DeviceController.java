package com.zmqx.dahua.web;

import com.dahuatech.hutool.http.Method;
import com.dahuatech.icc.exception.ClientException;
import com.dahuatech.icc.oauth.http.IClient;
import com.dahuatech.icc.oauth.model.v202010.GeneralRequest;
import com.dahuatech.icc.oauth.model.v202010.GeneralResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: {@link DeviceController}
 * @Author: AbelEthan
 * @Email AbelEthan@vip.qq.com
 * @Date 2023/12/29 17:18
 * @Describes
 */
@RestController
@RequestMapping("/device")
public class DeviceController {

    private final IClient defaultClient;

    public DeviceController(IClient defaultClient) {
        this.defaultClient = defaultClient;
    }


    @PostMapping("/page")
    public ResponseEntity<GeneralResponse> page(@RequestParam("bodyJson") String bodyJson) throws ClientException {
        GeneralRequest generalRequest = new GeneralRequest("/evo-apigw/evo-brm/1.2.0/device/channel/subsystem/page", Method.POST);
        generalRequest.body(bodyJson);
        // 发起请求处理应答
        GeneralResponse generalResponse = defaultClient.doAction(generalRequest, generalRequest.getResponseClass());
        return ResponseEntity.ok(generalResponse);
    }
}
