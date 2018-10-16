package daggerok.filters;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class CrossOriginResourceFilter implements ContainerResponseFilter {

  @Override
  public void filter(final ContainerRequestContext request, final ContainerResponseContext response) {
    response.getHeaders().putSingle("Access-Control-Allow-Origin", "*");
    response.getHeaders().putSingle("Access-Control-Expose-Headers", "Location");
    response.getHeaders().putSingle("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
    response.getHeaders()
            .putSingle("Access-Control-Allow-Headers",
                       "Content-Type, User-Agent, X-Requested-With, X-Requested-By, Cache-Control");
    response.getHeaders().putSingle("Access-Control-Allow-Credentials", "true");
  }
}
