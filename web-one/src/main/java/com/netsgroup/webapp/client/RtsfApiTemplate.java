package com.netsgroup.webapp.client;

import org.springframework.cloud.openfeign.FeignClient;
import com.netsgroup.api.contract.rtsf.IUserContract;

@FeignClient(name = "rtsf-api", qualifier = "rtsf-for-user", contextId = "users")
public interface RtsfApiTemplate extends IUserContract {

}
