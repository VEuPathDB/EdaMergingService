package org.veupathdb.service.eda.ms;

import jakarta.ws.rs.core.Context;
import org.glassfish.jersey.server.ContainerRequest;
import org.veupathdb.lib.container.jaxrs.server.annotations.Authenticated;
import org.veupathdb.lib.container.jaxrs.server.annotations.DisableJackson;
import org.veupathdb.service.eda.generated.model.DerivedVariableBulkMetadataRequest;
import org.veupathdb.service.eda.generated.model.MergedEntityTabularPostRequest;
import org.veupathdb.service.eda.generated.resources.MergingInternal;

import java.util.Map.Entry;

import static org.veupathdb.service.eda.ms.ServiceExternal.*;

@Authenticated(allowGuests = true)
public class ServiceInternal implements MergingInternal {

  @Context
  ContainerRequest _request;

  @Override
  public PostMergingInternalDerivedVariablesMetadataVariablesResponse postMergingInternalDerivedVariablesMetadataVariables(DerivedVariableBulkMetadataRequest entity) {
    Entry<String,String> authHeader = getAuthHeader(_request);
    // no need to check perms; only internal clients can access this endpoint
    return PostMergingInternalDerivedVariablesMetadataVariablesResponse.respond200WithApplicationJson(
        processDvMetadataRequest(entity, authHeader));
  }

  @DisableJackson
  @Override
  public PostMergingInternalQueryResponse postMergingInternalQuery(MergedEntityTabularPostRequest requestBody) {
    Entry<String,String> authHeader = getAuthHeader(_request);
    // no need to check perms; only internal clients can access this endpoint
    return PostMergingInternalQueryResponse.respond200WithTextTabSeparatedValues(
        processMergedTabularRequest(requestBody, authHeader));
  }

}
