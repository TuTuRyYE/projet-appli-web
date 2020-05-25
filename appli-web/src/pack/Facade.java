package pack;

import java.util.Collection;
import java.util.List;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NamedQuery;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Singleton
@Path("/")
public class Facade {

	@PersistenceContext(name="MaPU", unitName="MaPU")
	private EntityManager em;
	
	@POST
	@Path("/adduser")
    @Consumes({ "application/json" })
	public String addUser(User u) {
		//on check qu'un champ n'est pas vide 
		if(u.getUsername() == "" || u.getEmail() == "" || u.getPassword() == "") {
			return "badInput";
		}
		//on check si l'utilisateur n'a pas déjà le même email et pseudo
		TypedQuery<User> queryPseudo = em.createNamedQuery("searchUsername", User.class);
		queryPseudo.setParameter("username", u.getUsername());
		List<User> lpseudo = queryPseudo.getResultList();
		TypedQuery<User> queryEmail = em.createNamedQuery("searchEmail", User.class);
		queryEmail.setParameter("email", u.getEmail());
		List<User> lemail = queryEmail.getResultList();
		if(!lpseudo.isEmpty()) {
			return "pseudoAlreadyUsed";
		} else if(!lemail.isEmpty()) {
			return "emailAlreadyUsed";
		} else {
			em.persist(u);
			return "newUserAdded";
		}
	}
	
	@Path("/authentification")
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	public Response authentificateUser(Credentials cre) {
		try {
			System.out.println("authentification");
			String username = cre.getUsername();
			String password = cre.getPassword();
			//AuthentificationEndpoint aep = new AuthentificationEndpoint(em);
			//aep.authentificate(username, password);
			//String token = aep.issueToken(username);
			//System.out.println(token);
			return Response.ok("token").build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.FORBIDDEN).build();
		}
	}
	
	@POST
	@Path("/addperson")
    @Consumes({ "application/json" })
	public void addPerson(Person p) {
		System.out.println("coucou");
		em.persist(p);
	}
	
	@POST
	@Path("/addaddress")
    @Consumes({ "application/json" })
	public void addAddress(Address a) {
		em.persist(a);
	}
	
	@GET
	@Path("/listpersons")
    @Produces({ "application/json" })
	public Collection<Person> listPersons() {
		return em.createQuery("from Person", Person.class).getResultList();
	}
	
	@GET
	@Path("/listaddresses")
    @Produces({ "application/json" })
	public Collection<Address> listAddress() {
		return em.createQuery("from Address", Address.class).getResultList();	
	}
	
	@POST
	@Path("/associate")
    @Consumes({ "application/json" })
	public void associate(Association as) {
		System.out.println(as.getPersonId() +" "+ as.getAddressId());
		Person p = em.find(Person.class, as.getPersonId());
		Address a = em.find(Address.class, as.getAddressId());
		a.setOwner(p);
	}
	
}
