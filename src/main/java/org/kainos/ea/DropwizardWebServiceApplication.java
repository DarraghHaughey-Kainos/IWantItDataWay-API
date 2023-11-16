package org.kainos.ea;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.kainos.ea.api.JobRoleService;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.db.JobRoleDao;
import org.kainos.ea.resources.JobRoleController;

public class DropwizardWebServiceApplication extends Application<DropwizardWebServiceConfiguration> {
    private JobRoleService jobRoleService;

    public DropwizardWebServiceApplication() {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        jobRoleService = new JobRoleService(databaseConnector, new JobRoleDao());
    }

    public static void main(final String[] args) throws Exception {
        new DropwizardWebServiceApplication().run(args);
    }

    @Override
    public String getName() {
        return "IWantItDataWayAPI";
    }

    @Override
    public void initialize(final Bootstrap<DropwizardWebServiceConfiguration> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle<DropwizardWebServiceConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(DropwizardWebServiceConfiguration configuration){
                return configuration.getSwagger();
            }
        });
    }

    @Override
    public void run(final DropwizardWebServiceConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
        environment.jersey().register(new JobRoleController(jobRoleService));
    }
}
