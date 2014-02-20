package com.contact.dao;

public class DAOFactory {


    private static ContactDAO bibleDAO = null;

    public static ContactDAO getContactDAO() {
        if (bibleDAO == null)
            bibleDAO = ContactDAOImpl.getInstance();

        return bibleDAO;
    }

}
