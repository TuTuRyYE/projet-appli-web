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
	public void addAddress(Film a) {
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
	public Collection<Film> listAddress() {
		return em.createQuery("from Address", Film.class).getResultList();	
	}
	
	@POST
	@Path("/associate")
    @Consumes({ "application/json" })
	public void associate(Association as) {
		System.out.println(as.getPersonId() +" "+ as.getAddressId());
		User p = em.find(User.class, as.getPersonId());
		Film a = em.find(Film.class, as.getAddressId());
		a.setUser(p);
	}
	
}
