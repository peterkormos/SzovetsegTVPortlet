package org.msz.liferay.portlet.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import org.msz.liferay.portlet.datatype.Program;
import org.msz.liferay.portlet.datatype.Timeframe;

import com.liferay.sampledao.util.ConnectionPool;

public class SzovetsegTVJDBCDAO implements IDAO
{
  /*
   * (non-Javadoc)
   * 
   * @see org.msz.liferay.portlet.util.IDAO#getTimeframes()
   */
  public Collection<Timeframe> getTimeframes() throws Exception
  {
    Collection<Timeframe> returned = new LinkedList<Timeframe>();

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try
    {
      con = ConnectionPool.getConnection();

      ps = con
          .prepareStatement("SELECT * FROM sztv_timeframes  order by timeframeID desc");
      rs = ps.executeQuery();

      while (rs.next())
        returned.add(new Timeframe(rs));
    }
    finally
    {
      ConnectionPool.cleanUp(con, ps, rs);
    }

    return returned;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.msz.liferay.portlet.util.IDAO#getTimeframe(int)
   */
  public Timeframe getTimeframe(int timeframeID) throws Exception
  {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try
    {
      con = ConnectionPool.getConnection();

      ps = con
          .prepareStatement("SELECT * FROM sztv_timeframes where timeframeID="
              + timeframeID);
      rs = ps.executeQuery();

      if (rs.next())
        return new Timeframe(rs);
      else
        throw new Exception("No timeframe is found with timeframeID: "
            + timeframeID);
    }
    finally
    {
      ConnectionPool.cleanUp(con, ps, rs);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.msz.liferay.portlet.util.IDAO#storeTimeframe(org.msz.liferay.portlet
   * .datatype.Timeframe)
   */
  public void storeTimeframe(Timeframe timeframe) throws Exception
  {
    Connection con = null;
    PreparedStatement ps = null;

    try
    {
      con = ConnectionPool.getConnection();

      ps = con
          .prepareStatement("insert into sztv_timeframes "
              + "(timeframeID, timeframe_closed, timeframe_from, timeframe_to, timeframe_editor, timeframe_cycle) "
              + " VALUES (" + timeframe.id + ", " + timeframe.closed + ", "
              + timeframe.from + ", " + timeframe.to + ", '" + timeframe.editor
              + "', '" + timeframe.cycle + "')");

      ps.executeUpdate();
    }
    finally
    {
      ConnectionPool.cleanUp(con, ps, null);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.msz.liferay.portlet.util.IDAO#updateTimeframe(org.msz.liferay.portlet
   * .datatype.Timeframe)
   */
  public void updateTimeframe(Timeframe timeframe) throws Exception
  {
    Connection con = null;
    PreparedStatement ps = null;

    try
    {
      con = ConnectionPool.getConnection();

      ps = con.prepareStatement("UPDATE sztv_timeframes SET "
          + "timeframe_closed=" + timeframe.closed + ", " + "timeframe_from="
          + timeframe.from + ", " + "timeframe_to=" + timeframe.to + ", "
          + "timeframe_editor='" + timeframe.editor + "', "
          + "timeframe_cycle='" + timeframe.cycle + "' where timeframeID="
          + timeframe.id);

      ps.executeUpdate();
    }
    finally
    {
      ConnectionPool.cleanUp(con, ps, null);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.msz.liferay.portlet.util.IDAO#deleteTimeframe(int)
   */
  public void deleteTimeframe(int timeframeID) throws Exception
  {
    Connection con = null;
    PreparedStatement ps = null;

    try
    {
      con = ConnectionPool.getConnection();

      ps = con
          .prepareStatement("delete from sztv_timeframes where timeframeID="
              + timeframeID);
      ps.executeUpdate();
    }
    finally
    {
      ConnectionPool.cleanUp(con, ps, null);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.msz.liferay.portlet.util.IDAO#getNextTimeframeID()
   */
  public int getNextTimeframeID() throws Exception
  {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try
    {
      con = ConnectionPool.getConnection();

      ps = con.prepareStatement("SELECT max(timeframeID) FROM sztv_timeframes");
      rs = ps.executeQuery();

      if (rs.next())
        return rs.getInt(1) + 1;
      else
        throw new Exception("WTF?");
    }
    finally
    {
      ConnectionPool.cleanUp(con, ps, rs);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.msz.liferay.portlet.util.IDAO#getPrograms()
   */
  public Collection<Program> getPrograms() throws Exception
  {
    Collection<Program> returned = new LinkedList<Program>();

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try
    {
      con = ConnectionPool.getConnection();

      ps = con.prepareStatement("SELECT * FROM sztv_programs");
      rs = ps.executeQuery();

      while (rs.next())
        returned.add(getProgram(rs));
    }
    finally
    {
      ConnectionPool.cleanUp(con, ps, rs);
    }

    return returned;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.msz.liferay.portlet.util.IDAO#getPrograms(int)
   */
  public Collection<Program> getPrograms(int timeframeID) throws Exception
  {
    Collection<Program> returned = new LinkedList<Program>();

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try
    {
      con = ConnectionPool.getConnection();

      ps = con
          .prepareStatement("SELECT * FROM sztv_programs WHERE timeframeID = "
              + timeframeID + " order by programID asc");
      rs = ps.executeQuery();

      while (rs.next())
        returned.add(getProgram(rs));
    }
    finally
    {
      ConnectionPool.cleanUp(con, ps, rs);
    }

    return returned;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.msz.liferay.portlet.util.IDAO#getProgram(int)
   */
  public Program getProgram(int programID) throws Exception
  {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try
    {
      con = ConnectionPool.getConnection();

      ps = con.prepareStatement("SELECT * FROM sztv_programs where programID="
          + programID);
      rs = ps.executeQuery();

      if (rs.next())
        return getProgram(rs);
      else
        throw new Exception("No program is found with programID: " + programID);
    }
    finally
    {
      ConnectionPool.cleanUp(con, ps, rs);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.msz.liferay.portlet.util.IDAO#storeProgram(org.msz.liferay.portlet.
   * datatype.Program)
   */
  public void storeProgram(Program program) throws Exception
  {
    Connection con = null;
    PreparedStatement ps = null;

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

    try
    {
      con = ConnectionPool.getConnection();

      ps = con
          .prepareStatement("insert into sztv_programs "
              + "(programID, title, description, photoUrl, videoUrl, timeframeID, program_length) "
              + " VALUES (" + program.id + ", '" + program.title + "', '"
              + program.description + "', '" + program.photoUrl + "', '"
              + program.videoUrl + "', " + program.timeframe.id + ", "
              + program.length + ")");
      ps.executeUpdate();
    }
    finally
    {
      ConnectionPool.cleanUp(con, ps, null);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.msz.liferay.portlet.util.IDAO#updateProgram(int,
   * org.msz.liferay.portlet.datatype.Program)
   */
  public void updateProgram(int oldProgramID, Program program) throws Exception
  {
    Connection con = null;
    PreparedStatement ps = null;

    try
    {
      con = ConnectionPool.getConnection();

      ps = con.prepareStatement("UPDATE sztv_programs SET " + "programID="
          + program.id + ", " + "title='" + program.title + "', "
          + "description='" + program.description + "', " + "photoUrl='"
          + program.photoUrl + "', " + "videoUrl='" + program.videoUrl + "', "
          + " timeframeID=" + program.timeframe.id + ", " + " program_length="
          + program.length + " where programID=" + oldProgramID);

      ps.executeUpdate();
    }
    finally
    {
      ConnectionPool.cleanUp(con, ps, null);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.msz.liferay.portlet.util.IDAO#deleteProgram(int)
   */
  public void deleteProgram(int programID) throws Exception
  {
    Connection con = null;
    PreparedStatement ps = null;

    try
    {
      con = ConnectionPool.getConnection();

      ps = con.prepareStatement("delete from sztv_programs where programID="
          + programID);
      ps.executeUpdate();
    }
    finally
    {
      ConnectionPool.cleanUp(con, ps, null);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.msz.liferay.portlet.util.IDAO#getNextProgramID()
   */
  public int getNextProgramID() throws Exception
  {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try
    {
      con = ConnectionPool.getConnection();

      ps = con.prepareStatement("SELECT max(programID) FROM sztv_programs");
      rs = ps.executeQuery();

      if (rs.next())
        return rs.getInt(1) + 1;
      else
        throw new Exception("WTF?");
    }
    finally
    {
      ConnectionPool.cleanUp(con, ps, rs);
    }
  }

  private Program getProgram(ResultSet rs) throws Exception
  {
    return new Program(rs.getInt("programID"), rs.getString("title"), rs
        .getString("description"), rs.getLong("program_length"),
        getTimeframe(rs.getInt("timeframeID")), rs.getString("photoUrl"), rs
            .getString("videoUrl"));
  }
}
