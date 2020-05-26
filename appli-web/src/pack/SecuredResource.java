package pack;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("secured")
public class SecuredResource {
	
	@PersistenceContext(name="MaPU", unitName="MaPU")
	private EntityManager em;
	
	@GET
	@Path("login")
	@Produces("application/json")
	public String secureMethod() {
		return "Connected";
	}
}
