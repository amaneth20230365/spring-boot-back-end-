package com.amaneth.ticketsystem.service;

import com.amaneth.ticketsystem.model.Configuration;

public interface ConfigurationService {
    public Configuration saveConfiguration(Configuration configuration);

    Configuration getCurrentConfiguration();
}
