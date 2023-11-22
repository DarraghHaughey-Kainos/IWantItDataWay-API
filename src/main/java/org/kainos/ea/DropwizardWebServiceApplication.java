package org.kainos.ea;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.kainos.ea.api.CapabilityService;
import org.kainos.ea.db.CapabilityDao;
import org.kainos.ea.resources.CapabilityController;
import org.kainos.ea.api.JobRoleService;
import org.kainos.ea.api.AuthService;
import org.kainos.ea.client.ActionFailedException;
import org.kainos.ea.core.CredentialValidator;
import org.kainos.ea.api.SpecificationService;
import org.kainos.ea.db.SpecificationDao;
import org.kainos.ea.db.AuthDao;
import org.kainos.ea.api.BandService;
import org.kainos.ea.db.BandDao;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.resources.AuthController;
import org.kainos.ea.db.JobRoleDao;
import org.kainos.ea.resources.HelloWorldController;
import org.kainos.ea.resources.JobRoleController;
import org.kainos.ea.resources.SpecificationController;
import org.kainos.ea.resources.BandController;

public class DropwizardWebServiceApplication extends Application<DropwizardWebServiceConfiguration> {
    private AuthService authService;
    private JobRoleService jobRoleService;
    private CapabilityService capabilityService;
    private BandService bandService;
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
        jobRoleService = new JobRoleService(databaseConnector, new JobRoleDao());
        bandService = new BandService(databaseConnector, new BandDao());
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
        environment.jersey().register(new CapabilityController(capabilityService, authService));
        environment.jersey().register(new HelloWorldController(authService));
        environment.jersey().register(new AuthController(authService));
        environment.jersey().register(new SpecificationController(specificationService));
        environment.jersey().register(new BandController(bandService));
        environment.jersey().register(new JobRoleController(jobRoleService, authService));
    }
}
