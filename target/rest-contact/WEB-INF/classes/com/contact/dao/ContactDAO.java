package com.contact.dao;


import com.contact.bean.Contact;

import java.util.List;

public interface ContactDAO {

    Contact create(Contact contact) throws Exception;
    Contact readById(Integer id)throws Exception;
    List<Contact> readAll() throws Exception;
    Contact update(Contact contact) throws Exception;
    void deleteById(Integer id);
    void deleteAll();
}
