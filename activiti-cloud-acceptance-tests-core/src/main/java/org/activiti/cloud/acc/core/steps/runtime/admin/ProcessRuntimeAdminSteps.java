package org.activiti.cloud.acc.core.steps.runtime.admin;

import net.thucydides.core.annotations.Step;
import org.activiti.cloud.acc.core.rest.feign.EnableRuntimeFeignContext;
import org.activiti.cloud.acc.core.services.runtime.admin.ProcessRuntimeAdminService;
import org.activiti.cloud.api.process.model.CloudProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedResources;

import static org.assertj.core.api.Assertions.assertThat;

@EnableRuntimeFeignContext
public class ProcessRuntimeAdminSteps {

    @Autowired
    private ProcessRuntimeAdminService processRuntimeAdminService;

    @Step
    public void checkServicesHealth() {
        assertThat(processRuntimeAdminService.isServiceUp()).isTrue();
    }

    @Step
    public PagedResources<CloudProcessInstance> getProcessInstances(){
        return processRuntimeAdminService.getProcessInstances();
    }
}
