package com.company.enroller.persistence;

import java.util.Collection;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import com.company.enroller.model.Participant;

@Component("participantService")
public class ParticipantService {

    DatabaseConnector connector;

    public ParticipantService() {
        connector = DatabaseConnector.getInstance();
    }

    public Collection<Participant> getAll(String sortBy, String orderBy, String key) {
        String hql = "From Participant ";
        if (key.equals("")) {
            ;
        } else {
            hql = hql + " where login like '%" + key + "%'";
            System.out.println(hql);
        }
        hql = hql + " order by " + sortBy;
        hql = hql + " " + orderBy;
        Query query = connector.getSession().createQuery(hql);
        return query.list();
    }

    public Participant findByLogin(String login) {
        Session session = connector.getSession();
        session = HibernateUtil.getSessionFactory().openSession();
        org.hibernate.Query query = session.createQuery("from Participant where login = :login");
        Participant participant = (Participant) query.setParameter("login", login).uniqueResult();
        return participant;
    }

    public Participant add(Participant participant) {
        Session session = connector.getSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(participant);
        transaction.commit();
        return participant;
    }

    public void remove(String login) {
        Session session = connector.getSession();
        session = HibernateUtil.getSessionFactory().openSession();
        Participant participant = findByLogin(login);
        Transaction transaction = session.beginTransaction();
        session.remove(participant);
        transaction.commit();
    }

    public void update(String login, Participant participant, Participant participantToUpdate) {
        String newPassward = participant.getPassword();
        participantToUpdate.setPassword(newPassward);
        Session session = connector.getSession();
        Transaction transaction = session.beginTransaction();
        session.merge(participantToUpdate);
        transaction.commit();
    }
}
