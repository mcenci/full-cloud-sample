package com.netsgroup.webapp.client;

import org.springframework.cloud.openfeign.FeignClient;
import com.netsgroup.api.contract.rtsf.IApplicationContract;

@FeignClient(name = "rtsf-api", qualifier = "rtsf-for-application", contextId = "application")
public interface RtsfApiTemplate2 extends IApplicationContract {

}
