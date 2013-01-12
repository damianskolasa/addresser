package pl.addresser.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import pl.addresser.model.Street;

import com.google.common.base.Strings;

@RequestScoped
public class StreetRESTServiceImpl implements StreetRESTService {
	
	@Inject
	private EntityManager em;

	public List<Street> listAllStreets() {
		final List<Street> results = em.createQuery("select s from Street s",
				Street.class).getResultList();
		return results;
	}

	public Street lookupById(long id) {
		return em.find(Street.class, id);
	}

	public Street lookupByDescription(String desc) {
		Street street = em
				.createQuery(
						"select s from Street s where lower(s.description) = :description",
						Street.class).setParameter("description", desc)
				.getSingleResult();
		return street;
	}

	public List<Street> suggestStreets(String pattern) {
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
