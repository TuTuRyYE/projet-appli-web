package pack;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("secured")
@Stateless
public class SecuredResource {
	
	@PersistenceContext(name="MaPU", unitName="MaPU")
	private EntityManager em;
	
	@GET
	@Path("login")
	@Produces("application/json")
	public String secureMethod() {
		return "Connected";
	}
	
	@POST
	@Path("infoFilm")
	@Consumes("application/json")
	@Produces("application/json")
	public Film infoFilm(Film film) {
		System.out.println(film.getImdbID());
		TypedQuery<Film> queryFilm = em.createNamedQuery("searchImdbID", Film.class);
		queryFilm.setParameter("imdbID", film.getImdbID());
		List<Film> lfilm = queryFilm.getResultList();
		if(lfilm.isEmpty()) {
			System.out.println("true");
			film.setNbVue(film.getNbVue()+1);
			em.persist(film);
			return film;
		} else {
			lfilm.get(0).addVue();
			return lfilm.get(0);
		}
	}
	
	@POST
	@Path("ajoutListeFilm")
	@Consumes("application/json")
	public void ajoutListeFilm(AjoutListe al) {
		System.out.println(al.getImdbID() +" "+ al.getUsername());
		
		User p = em.find(User.class, al.getUsername());
		Film a = em.find(Film.class, al.getImdbID());
		//a.setUser(p);
	}
}
