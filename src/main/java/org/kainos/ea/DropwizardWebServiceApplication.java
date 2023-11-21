package org.kainos.ea;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.kainos.ea.api.*;
import org.kainos.ea.db.*;
import org.kainos.ea.resources.*;
import org.kainos.ea.client.ActionFailedException;
import org.kainos.ea.core.CredentialValidator;
import org.kainos.ea.api.JobRoleService;

public class DropwizardWebServiceApplication extends Application<DropwizardWebServiceConfiguration> {
    private AuthService authService;
    private JobRoleService jobRoleService;
    private CapabilityService capabilityService;
    private SpecificationService specificationService;

    public DropwizardWebServiceApplication() {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        jobRoleService = new JobRoleService(databaseConnector, new JobRoleDao());
        capabilityService = new CapabilityService(databaseConnector, new CapabilityDao());
        specificationService = new SpecificationService(databaseConnector, new SpecificationDao());
        try {
            authService = new AuthService(databaseConnector, new AuthDao(), new CredentialValidator());
        } catch (ActionFailedException e) {
            System.err.println(e.getMessage());
        }
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
        environment.jersey().register(new CapabilityController(capabilityService));
        environment.jersey().register(new HelloWorldController());
        environment.jersey().register(new AuthController(authService));
        environment.jersey().register(new JobRoleController(jobRoleService));
        environment.jersey().register(new SpecificationController(specificationService));
    }
}
