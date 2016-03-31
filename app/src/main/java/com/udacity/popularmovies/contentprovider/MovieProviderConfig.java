package com.udacity.popularmovies.contentprovider;

import ckm.simple.sql_provider.UpgradeScript;
import ckm.simple.sql_provider.annotation.ProviderConfig;
import ckm.simple.sql_provider.annotation.SimpleSQLConfig;

/**
 * Created by vaibhav on 31/3/16.
 */
@SimpleSQLConfig(
        name = "MovieProvider",
        authority = "com.udacity.popularmovies.authority",
        database = "movie.db",
        version = 1)
class MovieProviderConfig implements ProviderConfig {

    @Override
    public UpgradeScript[] getUpdateScripts() {
        return new UpgradeScript[0];
    }
}
