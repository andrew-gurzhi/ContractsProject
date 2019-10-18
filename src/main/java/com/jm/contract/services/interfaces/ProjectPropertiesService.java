package com.jm.contract.services.interfaces;


import com.jm.contract.models.ProjectProperties;

public interface ProjectPropertiesService extends CommonService<ProjectProperties> {

    ProjectProperties get();

    ProjectProperties getOrCreate();

    ProjectProperties saveAndFlash(ProjectProperties entity);
}
