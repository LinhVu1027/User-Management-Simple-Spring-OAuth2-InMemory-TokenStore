package com.cloud.common.interfaces.api;

import com.cloud.common.interfaces.domain.IWithName;

public interface IByNameApi<T extends IWithName> {

    T findByName(final String name);
}
