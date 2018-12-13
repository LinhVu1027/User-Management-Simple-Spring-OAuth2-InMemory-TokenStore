package com.cloud.common.persistence.service.abstracts;

import com.cloud.common.persistence.model.INameableEntity;
import com.cloud.common.persistence.service.IService;

public abstract class AbstractService<T extends INameableEntity> extends AbstractRawService<T> implements IService<T> {

    public AbstractService() {
        super();
    }
}
