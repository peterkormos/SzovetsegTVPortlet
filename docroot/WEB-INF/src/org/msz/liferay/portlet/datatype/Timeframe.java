package org.msz.liferay.portlet.datatype;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Timeframe
{
  public int id;

  public boolean closed;
  public long from;
  public long to;
  public String editor;
  public String cycle;

  public Timeframe(int id, boolean closed, long from, long to, String editor, String cycle)
  {
    super();
    this.id = id;
    this.closed = closed;
    this.from = from;
    this.to = to;
    this.editor = editor;
    this.cycle = cycle;
  }

  // sztv_timeframess
  public Timeframe(ResultSet rs) throws SQLException
  {
    id = rs.getInt("timeframeID");
    closed = rs.getBoolean("timeframe_closed");
    from = rs.getLong("timeframe_from");
    to = rs.getLong("timeframe_to");
    editor = rs.getString("timeframe_editor");
    cycle =  rs.getString("timeframe_cycle");
  }

  @Override
  public String toString()
  {
    return "timeframeID: " + id + " closed: " + closed + " from: " + from
        + " to: " + to + " editor: " + editor + " cycle: " + cycle;
  }
  
  public Timeframe()
  {
    
  }

  public int getId()
  {
    return id;
  }

  public void setId(int id)
  {
    this.id = id;
  }

  public boolean isClosed()
  {
    return closed;
  }

  public void setClosed(boolean closed)
  {
    this.closed = closed;
  }

  public long getFrom()
  {
    return from;
  }

  public void setFrom(long from)
  {
    this.from = from;
  }

  public long getTo()
  {
    return to;
  }

  public void setTo(long to)
  {
    this.to = to;
  }

  public String getEditor()
  {
    return editor;
  }

  public void setEditor(String editor)
  {
    this.editor = editor;
  }

  public String getCycle()
  {
    return cycle;
  }

  public void setCycle(String cycle)
  {
    this.cycle = cycle;
  }
}
