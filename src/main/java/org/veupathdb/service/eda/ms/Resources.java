package org.veupathdb.service.eda.ms;

import org.gusdb.fgputil.client.ClientUtil;
import org.veupathdb.lib.container.jaxrs.config.Options;
import org.veupathdb.lib.container.jaxrs.server.ContainerResources;

import static org.gusdb.fgputil.runtime.Environment.getOptionalVar;
import static org.gusdb.fgputil.runtime.Environment.getRequiredVar;

/**
 * Service Resource Registration.
 *
 * This is where all the individual service specific resources and middleware
 * should be registered.
 */
public class Resources extends ContainerResources {

  private static final boolean DEVELOPMENT_MODE =
      Boolean.parseBoolean(getOptionalVar("DEVELOPMENT_MODE", "false"));

  public static final String SUBSETTING_SERVICE_URL = getRequiredVar("SUBSETTING_SERVICE_URL");
  public static final String COMPUTE_SERVICE_URL = getRequiredVar("COMPUTE_SERVICE_URL");
  public static final String DATASET_ACCESS_SERVICE_URL = getRequiredVar("DATASET_ACCESS_SERVICE_URL");

  public Resources(Options opts) {
    super(opts);
    if (DEVELOPMENT_MODE) {
      enableJerseyTrace();
      ClientUtil.LOG_RESPONSE_HEADERS = true;
    }
  }

  /**
   * Returns an array of JaxRS endpoints, providers, and contexts.
   *
   * Entries in the array can be either classes or instances.
   */
  @Override
  protected Object[] resources() {
    return new Object[] {
      ServiceInternal.class,
      ServiceExternal.class
    };
  }
}
