package pack;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("secured")
public class SecuredResource {
	
	@GET
	@Path("login")
	@Produces("application/json")
	public String secureMethod() {
		return "Connected";
	}
}
