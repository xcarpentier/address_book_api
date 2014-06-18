package com.contact;

import com.sun.jersey.spi.resource.Singleton;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Singleton
@Path("/contacts/")
public class ContactResources {


    private static final int MAX_RETURN = 10;
    private final AtomicInteger idCounter = new AtomicInteger();
    private final Map<Integer, Contact> contacts = new ConcurrentHashMap<Integer, Contact>();


    /* CREATE */
    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response addContact(@FormParam("fname") String firstName,
                               @FormParam("lname") String lastName,
                               @FormParam("email") String email) {

        final Contact contact = new Contact();
        contact.setEmail(email);
        contact.setFirstName(firstName);
        contact.setLastName(lastName);
        contact.setId(idCounter.getAndIncrement());

        contacts.put(contact.getId(), contact);

        return Response.created(URI.create(contact.getId() + "")).entity(contact).build();

    }


    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addContactJSON(Contact contact) {
        contact.setId(idCounter.getAndIncrement());
        contacts.put(contact.getId(), contact);
        return Response.created(URI.create(contact.getId() + "")).entity(contact).build();
    }


    /* READ */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response read(@PathParam("id") Integer id) {

        if (!contacts.containsKey(id)) {
            final Message message = new Message();
            message.setText("no contact : " + id);
            throw new WebApplicationException(Response.status(Status.NOT_FOUND).entity(message)
                    .build());
        }

        return Response.ok(contacts.get(id)).build();
    }


    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response readAll(@DefaultValue("0") @QueryParam("start") int start,
                            @QueryParam("end") int end) {

        if (start - end < 0) {
            final Message message = new Message();
            message.setText("Contacts is empty");
            throw new WebApplicationException(Response.status(Status.BAD_REQUEST).entity(message)
                    .build());
        }

        if (contacts.size() == 0) {
            final Message message = new Message();
            message.setText("Contacts is empty");
            throw new WebApplicationException(Response.status(Status.NOT_FOUND).entity(message)
                    .build());
        }

        if (contacts.size() < MAX_RETURN) {
            end = contacts.size();
        }

        if (start == 0 && end == 0) {
            end = MAX_RETURN;
        }


        List<Contact> contactList = Arrays.asList(contacts.values().toArray(new Contact[0]));

        return Response.ok(contactList.subList(start, end).toArray(new Contact[0])).build();
    }


    /* UPDATE */
    @PUT
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response update(@PathParam("id") Integer id, @FormParam("fname") String firstName,
                           @FormParam("lname") String lastName, @FormParam("email") String email) {

        if (!contacts.containsKey(id)) {
            final Message message = new Message();
            message.setText("Unknow contact : " + id);
            throw new WebApplicationException(Response.status(Status.NOT_FOUND).entity(message)
                    .build());
        }

        final Contact contact = contacts.get(id);
        contact.setFirstName(firstName);
        contact.setLastName(lastName);
        contact.setEmail(email);

        return Response.ok(contact).build();
    }


    /* DELETE */
    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response remove(@PathParam("id") Integer id) {

        if (!contacts.containsKey(id)) {
            final Message message = new Message();
            message.setText("Unknow contact : " + id);
            throw new WebApplicationException(Response.status(Status.NOT_FOUND).entity(message)
                    .build());
        }

        contacts.remove(id);
        return Response.noContent().build();
    }


    /* DELETE ALL */
    @DELETE
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response removeAll() {
        return Response.status(405).build();
    }
}
