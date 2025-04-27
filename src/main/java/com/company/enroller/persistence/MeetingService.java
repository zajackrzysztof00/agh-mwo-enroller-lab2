package com.company.enroller.persistence;

import java.util.Collection;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import com.company.enroller.model.Meeting;

@Component("meetingService")
public class MeetingService {

	DatabaseConnector connector;

	public MeetingService() {
		connector = DatabaseConnector.getInstance();
	}

	public Collection<Meeting> getAll(String sortBy, String orderBy, String key) {
		String hql = "From Meeting ";
		if (key.equals("")) {
			;
		} else {
			hql = hql + " where title like '%" + key + "%'";
			System.out.println(hql);
		}
		hql = hql + " order by " + sortBy;
		hql = hql + " " + orderBy;
		Query query = connector.getSession().createQuery(hql);
		return query.list();
	}

	public Meeting findById(Long id) {
		Session session = connector.getSession();
		session = HibernateUtil.getSessionFactory().openSession();
		Meeting meeting = session.get(Meeting.class, id);
		return meeting;
	}

}
