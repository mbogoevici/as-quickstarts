package org.jboss.as.quickstarts.html5_mobile.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.as.quickstarts.html5_mobile.model.Country;
import org.jboss.as.quickstarts.html5_mobile.model.Member;

/**
 * @author Marius Bogoevici
 */
@Path("/countries")
@RequestScoped
@Stateful
public class CountryService {

    @Inject
    private EntityManager em;

    @Inject
    private Event<Member> memberEventSrc;

    @Inject
    private Validator validator;

    @GET
    @Path("/json")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Country> listAllCountriesJSON() {
        @SuppressWarnings("unchecked")
        final List<Country> results = em.createQuery("select c from Country c order by c.name").getResultList();
        return results;
    }

    @POST
    @Path("/json")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createJSON(Country country) {
        // this resource only creates new countries. Incoming entity must not have an ID
        if (country.getId() != null) {
            Map<String, Object> responseObj = new HashMap<String, Object>();
            responseObj.put("id", "Cannot create a new member if id is already specified");
            return Response.status(Response.Status.BAD_REQUEST).entity(responseObj).build();
        }
        Set<ConstraintViolation<Country>> constraintViolations = validator.validate(country);
        if (!constraintViolations.isEmpty()) {

        }
        try {
            Country existingCountry = (Country) em.createQuery("select c from Country c where c.name = :name").setParameter("name", country.getName()).getSingleResult();
            if (existingCountry != null) {
                Map<String, String> responseObj = new HashMap<String, String>();
                responseObj.put("code","A country with the same code already exists: " + existingCountry.getName());
                return Response.status(Response.Status.CONFLICT).entity(responseObj).build();
            }
        } catch (EntityNotFoundException e) {
            // ignore, this is correct
        }
        em.persist(country);
        return Response.ok().build();
    }

    @PUT
    @Path("/json/{id:[0-9][0-9]*}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateJSON(Country country, @PathParam("id") long id) {
        if (country.getId() == null) {
            // allow for requests with no member id?
            country.setId(id);
        } else if (country.getId() != id) {
            Map<String, Object> responseObj = new HashMap<String, Object>();
            responseObj.put("id", "Country id from request inconsistent with URL");
            return Response.status(Response.Status.BAD_REQUEST).entity(responseObj).build();
        }
        validator.validate(country);
        em.merge(country);
        return Response.ok().build();
    }

    @DELETE
    @Path("/json/{id:[0-9][0-9]*}")
    public Response deleteMember(@PathParam("id") long id) {
        Member member = em.find(Member.class, id);
        if (member == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        em.remove(member);
        return Response.ok().build();
    }

}
