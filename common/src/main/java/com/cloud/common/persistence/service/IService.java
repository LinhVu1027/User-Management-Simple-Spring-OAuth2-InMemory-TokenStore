package com.cloud.common.persistence.service;

import com.cloud.common.interfaces.api.IByNameApi;
import com.cloud.common.interfaces.domain.IWithName;

public interface IService<T extends IWithName> extends IRawService<T>, IByNameApi<T> {

}
