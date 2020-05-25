package pack;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.StringTokenizer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.Provider;

@Provider
public class SecurityFilter implements ContainerRequestFilter{
	
	private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
	private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";
	private static final String SECURED_URL_PREFIX = "secured";
	
	@PersistenceContext(name="MaPU", unitName="MaPU")
	private EntityManager em;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		if(requestContext.getUriInfo().getPath().contains(SECURED_URL_PREFIX)) {
			List<String> authHeader = requestContext.getHeaders().get(AUTHORIZATION_HEADER_KEY);
			if(authHeader != null && authHeader.size() > 0) {
				String authToken = authHeader.get(0);
				authToken = authToken.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
				String decodedString = new String(Base64.getDecoder().decode(authToken));
				StringTokenizer tokenizer = new StringTokenizer(decodedString, ":");
				String username = tokenizer.nextToken();
				String password = tokenizer.nextToken();
				System.out.println(username + password);
				
				TypedQuery<User> queryPseudo = em.createNamedQuery("searchUsername", User.class);
				queryPseudo.setParameter("username", username);
				List<User> lpseudo = queryPseudo.getResultList();
				if(lpseudo.size() > 0) {
					if(username.equals("tuturyye") && password.equals("toto")) {
						return;
					}
					Response unauthorizedStatus = Response.status(Response.Status.UNAUTHORIZED).entity("User cannot access the ressource.").build();
					requestContext.abortWith(unauthorizedStatus);
				} else {
					Response noUserStatus = Response.status(Response.Status.NOT_ACCEPTABLE).entity("User not found").build();
					requestContext.abortWith(noUserStatus);
				}
			}
		}
		
		
	}

}
