package com.contact;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("/contacts/")
public class ContactResources {

    
    private static final int MAX_RETURN = 10;
    private final AtomicInteger idCounter = new AtomicInteger();
    private final Map<Integer, Contact> contacts = new ConcurrentHashMap<Integer, Contact>();
    private String lastName;

    


    
    /* CREATE */

    @POST
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response addContact(@FormParam("fname") String firstName,
        @FormParam("lname") String lastName, @FormParam("email") String email) {
        
        

        testMethod();
        
        
        
        
        
        return null;
    }

    private void testMethod() {
        for (int i = 0; i < 10; i++) {
            System.out.println(i);            
        }
    }

    /* READ */

    @GET
    @Path("{id}")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response read(@PathParam("id") Integer id) {

        if (!contacts.containsKey(id)) {
            final Message message = new Message();
            message.setText("Unknow contact : " + id);
            throw new WebApplicationException(Response.status(Status.NOT_FOUND).entity(message)
                .build());
        }

        return Response.ok(contacts.get(id)).build();
    }

    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response readAll(@DefaultValue("1") @QueryParam("start") int start,
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

        if (start == 0 && end == 0)
            end = MAX_RETURN;

        List<Contact> contactList = Arrays.asList(contacts.values().toArray(new Contact[0]));

        return Response.ok(contactList.subList(start, end).toArray(new Contact[0])).build();
    }

    /* UPDATE */

    @PUT
    @Path("{id}")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
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
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
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

    @DELETE
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response removeAll() {
        contacts.clear();
        return Response.noContent().build();
    }

    /* COUNT */

    @HEAD
    @Path("count")
    public Response count() {
        return Response.ok().header("X-COUNT", contacts.size()).build();
    }
}