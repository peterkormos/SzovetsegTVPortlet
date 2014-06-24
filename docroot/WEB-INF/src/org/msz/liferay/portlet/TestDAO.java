package org.msz.liferay.portlet;

import java.util.Collection;

import junit.framework.TestCase;

import org.msz.liferay.portlet.datatype.Program;
import org.msz.liferay.portlet.datatype.Timeframe;
import org.msz.liferay.portlet.util.IDAO;
import org.msz.liferay.portlet.util.SzovetsegTVHibernateDAO;

public class TestDAO extends TestCase
{
  public void testAll() throws Exception
  {
//    IDAO dao = new SzovetsegTVDAO();
    IDAO dao = new SzovetsegTVHibernateDAO();
    
    Timeframe t = new Timeframe(dao.getNextTimeframeID(), true,
        System.currentTimeMillis(), System.currentTimeMillis() + 6000000,
        "editor", "cycle");
    dao.storeTimeframe(t);
    
    assertEquals(t.toString(), dao.getTimeframe(t.id).toString());

    System.out.println("Query timeframes:");
    Collection<Timeframe> timeframes = dao.getTimeframes();
    for (Timeframe timeframe : timeframes)
      System.out.println(timeframe);

    t.closed = false;
    t.cycle = "cycle";
    t.from = 1;
    t.to = 2;
    t.editor = "e";
    dao.updateTimeframe(t);
    assertEquals(t.toString(), dao.getTimeframe(t.id).toString());

    Program p = new Program(dao.getNextProgramID(), "t", "d", 123,
        t, "pppu", "vvvu");

    dao.storeProgram(p);
    assertEquals(p.toString(), dao.getProgram(p.id).toString());

    System.out.println("Query programs:");
    Collection<Program> programs = dao.getPrograms();
    for (Program program : programs)
      System.out.println(program);

    p.title = "t";
    p.description = "d";
    p.photoUrl = "p";
    p.videoUrl = "v";
    p.length = 1;

    dao.updateProgram(p.id++, p);
    assertEquals(p.toString(), dao.getProgram(p.id).toString());

    dao.deleteProgram(p.id);
    try
    {
      dao.getProgram(p.id);
      fail();
    }
    catch (Exception e)
    {
    }

    assertEquals(programs.size()-1, dao.getPrograms().size());

    dao.deleteTimeframe(t.id);
    try
    {
      dao.getTimeframe(t.id);
      fail();
    }
    catch (Exception e)
    {
    }

    assertEquals(timeframes.size()-1, dao.getTimeframes().size());

    
    dao.storeTimeframe(new Timeframe(dao.getNextTimeframeID(), true,
        System.currentTimeMillis(), System.currentTimeMillis() + 6000000,
        "editor", "cycle"));
    dao.storeProgram(new Program(dao.getNextProgramID(), "t", "d", 123,
        t, "pppu", "vvvu"));
    dao.storeProgram(new Program(dao.getNextProgramID(), "t", "d", 123,
        t, "pppu", "vvvu"));
    assertEquals(2, dao.getPrograms(t.id).size());
  }

}
