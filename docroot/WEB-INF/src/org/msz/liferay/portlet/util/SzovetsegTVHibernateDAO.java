package org.msz.liferay.portlet.util;

import java.util.Collection;
import java.util.LinkedList;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;
import org.msz.liferay.portlet.datatype.Program;
import org.msz.liferay.portlet.datatype.Timeframe;

import com.liferay.sampledao.util.ConnectionPool;

public class SzovetsegTVHibernateDAO implements IDAO
{
  private static SessionFactory sessionFactory;

  private Session getHibernateSession()
  {
    if (sessionFactory == null)
      sessionFactory = new Configuration().configure().buildSessionFactory();

    return sessionFactory.getCurrentSession();
  }

  private void closeSession(Session session)
  {
    if ((session != null) && (session.isOpen()))
    {
      session.close();
    }
  }

  public SzovetsegTVHibernateDAO()
  {
  }

  public void main(String[] args)
  {
    new SzovetsegTVHibernateDAO();

  }

  public Collection<Timeframe> getTimeframes()
  {
    Session session = null;

    try
    {
      Collection<Timeframe> returned = new LinkedList<Timeframe>();

      session = getHibernateSession();

      session.beginTransaction();

      returned.addAll(session.createQuery(
          "From Timeframe as t order by t.id desc").list());

      return returned;
    }
    finally
    {
      closeSession(session);
    }
  }

  public Timeframe getTimeframe(int timeframeID) throws Exception
  {
    Session session = null;

    try
    {
      session = getHibernateSession();

      session.beginTransaction();

      Timeframe timeframe = (Timeframe) session.createQuery(
          "From Timeframe as t where t.id = ?").setInteger(0, timeframeID)
          .uniqueResult();

      if (timeframe == null)
        throw new Exception("No timeframe is found with timeframeID: "
            + timeframeID);
      else
        return timeframe;

    }
    finally
    {
      closeSession(session);
    }
  }

  public void storeTimeframe(Timeframe timeframe) throws Exception
  {
    Session session = null;

    try
    {
      session = getHibernateSession();

      session.beginTransaction();

      session.saveOrUpdate(timeframe);

      session.getTransaction().commit();
    }
    finally
    {
      closeSession(session);
    }
  }

  public void updateTimeframe(Timeframe timeframe) throws Exception
  {
    Session session = null;

    try
    {
      session = getHibernateSession();

      session.beginTransaction();

      session.saveOrUpdate(timeframe);

      session.getTransaction().commit();
    }
    finally
    {
      closeSession(session);
    }
  }

  public void deleteTimeframe(int timeframeID) throws Exception
  {
    Session session = null;

    try
    {
      session = getHibernateSession();

      session.beginTransaction();

      session.delete(session.load(Timeframe.class, new Integer(timeframeID)));

      session.getTransaction().commit();
    }
    finally
    {
      closeSession(session);
    }
  }

  public int getNextTimeframeID() throws Exception
  {
    Session session = null;

    try
    {
      session = getHibernateSession();

      session.beginTransaction();

      Integer currentMaxvalue = (Integer) session.createQuery(
          "select max(id) From Timeframe").uniqueResult();
      return (currentMaxvalue == null ? 0 : currentMaxvalue) + 1;
    }
    finally
    {
      closeSession(session);
    }
  }

  public Collection<Program> getPrograms() throws Exception
  {
    Session session = null;

    try
    {
      Collection<Program> returned = new LinkedList<Program>();

      session = getHibernateSession();

      session.beginTransaction();

      returned.addAll(session
          .createQuery("From Program as p order by p.id asc").list());

      return returned;
    }
    finally
    {
      closeSession(session);
    }
  }

  public Collection<Program> getPrograms(int timeframeID) throws Exception
  {
    Session session = null;

    try
    {
      Collection<Program> returned = new LinkedList<Program>();

      session = getHibernateSession();

      session.beginTransaction();

      returned.addAll(session.createQuery(
          "From Program as p where p.timeframe.id = ? order by p.id asc")
          .setInteger(0, timeframeID).list());

      return returned;
    }
    finally
    {
      closeSession(session);
    }
  }

  public Program getProgram(int programID) throws Exception
  {
    Session session = null;

    try
    {
      session = getHibernateSession();

      session.beginTransaction();

      Program program = (Program) session.createQuery(
          "From Program as p where p.id = ?").setInteger(0, programID)
          .uniqueResult();

      if (program == null)
        throw new Exception("No program is found with programID: " + programID);
      else
        return program;
    }
    finally
    {
      closeSession(session);
    }
  }

  public void storeProgram(Program program) throws Exception
  {
    try
    {
      getProgram(program.id);
      throw new IllegalArgumentException(
          "M&aacute;r l&eacute;tez&ocirc; program sorsz&aacute;m: <b>"
              + program.id + "</b>");
    }
    catch (Exception e)
    {
    }

    Session session = null;
    try
    {

      session = getHibernateSession();

      session.beginTransaction();

      session.saveOrUpdate(program);

      session.getTransaction().commit();
    }
    finally
    {
      closeSession(session);
    }
  }

  public void updateProgram(int oldProgramID, Program program) throws Exception
  {
    Session session = null;

    try
    {
      session = getHibernateSession();

      session.beginTransaction();

      Program oldProgram = (Program) session.load(Program.class, new Integer(
          oldProgramID));
      oldProgram.update(program);

      session.delete(oldProgram);
      session.save(oldProgram);

      session.getTransaction().commit();
    }
    finally
    {
      closeSession(session);
    }
  }

  public void deleteProgram(int programID) throws Exception
  {
    Session session = null;

    try
    {
      session = getHibernateSession();

      session.beginTransaction();

      session.delete(session.load(Program.class, new Integer(programID)));

      session.getTransaction().commit();
    }
    finally
    {
      closeSession(session);
    }
  }

  public int getNextProgramID() throws Exception
  {
    Session session = null;

    try
    {
      session = getHibernateSession();

      session.beginTransaction();

      Integer currentMaxvalue = (Integer) session.createQuery(
          "select max(id) From Program").uniqueResult();
      return (currentMaxvalue == null ? 0 : currentMaxvalue) + 1;
    }
    finally
    {
      closeSession(session);
    }
  }

}
