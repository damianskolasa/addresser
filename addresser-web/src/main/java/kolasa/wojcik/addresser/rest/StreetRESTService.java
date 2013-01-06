package kolasa.wojcik.addresser.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import kolasa.wojcik.addresser.model.Street;

import com.google.common.base.Strings;

@Path("/streets")
@RequestScoped
public class StreetRESTService {
   @Inject
   private EntityManager em;

   @GET
   @Produces("text/xml")
   public List<Street> listAllStreets() {
      final List<Street> results = em.createQuery("select s from Street s", Street.class).getResultList();
      return results;
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces("text/xml")
   public Street lookupMemberById(@PathParam("id") long id) {
      return em.find(Street.class, id);
   }
   
   
   
   @GET
   @Path("/desc/{desc}")
   @Produces("text/xml")
   public Street lookupMemberById(@PathParam("desc") String desc) {
	   Street street = em
				.createQuery(
						"select s from Street s where lower(s.description) = :description",
						Street.class).setParameter("description", desc)
				.getSingleResult();
		return street;
   }
   @GET
   @Path("/suggest/{suggest}")
   @Produces("text/xml")
   public List<Street> suggestStreets(@PathParam("suggest")String pattern) {
		List<Street> matchingStreets = em
				.createQuery(
						"select s from Street s where lower(s.description) like :description",
						Street.class)
				.setParameter("description", getSearchPattern(pattern))
				.getResultList();
		
		return matchingStreets;
	}

	private String getSearchPattern(String pattern) {
		if (Strings.isNullOrEmpty(pattern)) {
			return "%";
		} else {
			return "%" + pattern.toLowerCase().replace('*', '%') + "%";
		}
	}
}
