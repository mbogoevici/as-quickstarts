package org.jboss.as.quickstarts.html5_mobile.rest;

import java.util.logging.Logger;

import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.validation.Validator;
import javax.ws.rs.Path;

import org.jboss.as.quickstarts.html5_mobile.model.Member;

/**
 * @author Marius Bogoevici
 */
@Path("/countries")
@RequestScoped
@Stateful
public class CountryService {
    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    @Inject
    private Event<Member> memberEventSrc;

    @Inject
    private Validator validator;


}
