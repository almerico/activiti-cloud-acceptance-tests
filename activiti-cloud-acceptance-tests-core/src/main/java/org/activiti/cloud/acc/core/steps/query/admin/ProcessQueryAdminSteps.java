package org.activiti.cloud.acc.core.steps.query.admin;

import net.thucydides.core.annotations.Step;
import org.activiti.cloud.acc.core.rest.feign.EnableRuntimeFeignContext;
import org.activiti.cloud.acc.core.services.query.admin.ProcessQueryAdminService;
import org.activiti.cloud.api.process.model.CloudProcessDefinition;
import org.activiti.cloud.api.process.model.CloudProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedResources;

import static org.assertj.core.api.Assertions.assertThat;

@EnableRuntimeFeignContext
public class ProcessQueryAdminSteps {

    @Autowired
    private ProcessQueryAdminService processQueryAdminService;

    @Step
    public void checkServicesHealth() {
        assertThat(processQueryAdminService.isServiceUp()).isTrue();
    }

    @Step
    public PagedResources<CloudProcessInstance> getAllProcessInstancesAdmin(){
        return processQueryAdminService.getProcessInstances();
    }

    @Step
    public PagedResources<CloudProcessDefinition> getAllProcessDefinitions(){
        return processQueryAdminService.getProcessDefinitions();
    }

}
