package com.contact.dao;

import com.contact.bean.Contact;
import com.contact.util.DataSourceUtil;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactDAOImpl implements ContactDAO {

    private Map<Integer, Contact> mapContact = new HashMap<Integer, Contact>();

    private ContactDAOImpl() {
    }

    public static ContactDAO getInstance() {
        return new ContactDAOImpl();
    }

    @Override
    public Contact create(Contact contact) throws Exception {
        contact.setId(mapContact.size());
        mapContact.put(contact.getId(), contact);
        return contact;
    }

    @Override
    public Contact readById(Integer id) throws Exception {

        if (!mapContact.containsKey(id))
            throw new Exception("Unknow id");

        return mapContact.get(id);
    }

    @Override
    public List<Contact> readAll() throws Exception {
        return (List<Contact>) mapContact.values();
    }

    @Override
    public Contact update(Contact contact) throws Exception {


        Contact contactToUpdate = mapContact.get(contact.getId());
        contactToUpdate = contact;

        return readById(contact.getId());
    }

    @Override
    public void deleteById(Integer id) {
     mapContact.remove(id);
    }

    @Override
    public void deleteAll() {
        mapContact.clear();
    }
}
