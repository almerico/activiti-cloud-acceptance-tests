package org.activiti.cloud.acc.core.services.query;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.activiti.api.task.model.Task;
import org.activiti.cloud.api.model.shared.CloudVariableInstance;
import org.activiti.cloud.api.task.model.CloudTask;
import org.activiti.cloud.acc.shared.service.BaseService;
import org.springframework.hateoas.PagedResources;

public interface TaskQueryService extends BaseService {

    @RequestLine("GET /v1/tasks?status={status}&id={taskId}")
    PagedResources<CloudTask> queryTasksByIdAnsStatus(@Param("taskId") String taskId,
                                                      @Param("status") Task.TaskStatus taskStatus);

    @RequestLine("GET /v1/tasks?id={taskId}")
    PagedResources<CloudTask> getTask(@Param("taskId") String taskId);

    @RequestLine("GET /v1/tasks?sort=createdDate,desc&sort=id,desc")
    @Headers("Content-Type: application/json")
    PagedResources<CloudTask> getTasks();

    @RequestLine("GET /v1/tasks/{taskId}/variables")
    @Headers("Content-Type: application/json")
    PagedResources<CloudVariableInstance> getTaskVariables(@Param("taskId") String taskId);

}
