package com.contact;


import com.contact.bean.Contact;
import com.contact.dao.ContactDAO;
import com.contact.dao.DAOFactory;
import com.sun.jersey.spi.resource.Singleton;

import javax.ws.rs.*;
import java.util.List;

@Singleton
@Path("/")
public class ContactResources {

    private static ContactDAO dao = DAOFactory.getContactDAO();

    @POST
    public Contact create(@FormParam("firstName") String firstName,
                          @FormParam("lastName") String lastName,
                          @FormParam("email") String email) {

        try {

            final Contact contact = new Contact();

            contact.setFirsName(firstName);
            contact.setLastName(lastName);
            contact.setEmail(email);


            return dao.create(contact);

        } catch (Exception e) {
            throw new WebApplicationException();
        }

    }

    @GET
    @Path("{id}")
    public Contact getById(@PathParam("id") Integer id) {

        try {

            return dao.readById(id);

        } catch (Exception e) {
            throw new WebApplicationException(404);
        }

    }

    @GET
    public List<Contact> getAll() {

        try {
            return dao.readAll();
        } catch (Exception e) {
            throw new WebApplicationException();
        }

    }


    @PUT
    public Contact update(@PathParam("id") Integer id,
                          @FormParam("firstName") String firstName,
                          @FormParam("lastName") String lastName,
                          @FormParam("email") String email) {

        try {

            final Contact contact = new Contact();
            contact.setId(id);
            contact.setFirsName(firstName);
            contact.setLastName(lastName);
            contact.setEmail(email);


            return dao.update(contact);
        } catch (Exception e) {
            throw new WebApplicationException();
        }

    }

    @DELETE
    @Path("{id}")
    public void deleteById(@PathParam("id") Integer id) {

        dao.deleteById(id);

    }


    @DELETE
    public void deleteAll() {

        dao.deleteAll();

    }


}