package pl.addresser;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;

import pl.addresser.rest.StreetRESTService;

public class StreetRESTServiceTest {

	public static void main(String[] args) throws Exception {
		StreetRESTService streetRESTService = JAXRSClientFactory.create(
				"http://localhost:8081/addresser/rest",
				StreetRESTService.class);
		System.out.println(streetRESTService.suggestStreets("Szczygl"));
	}
}
