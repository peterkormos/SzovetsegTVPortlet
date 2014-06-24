package org.msz.liferay.portlet.datatype;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.msz.liferay.portlet.SzovetsegTVPortlet;


public class Program
{
  public static String NO_PHOTOURL = "K&Eacute;P?";
  public static String NO_VIDEOURL = "VIDEO?";
  
  public int id;

  public String title;
  public String description;
  public String photoUrl;
  public String videoUrl;
  public Timeframe timeframe;
  public long length;

  public Program(int id, String title, String description, long length,
      Timeframe timeframe, String photoUrl, String videoUrl)
  {
    this.id = id;
    this.title = title;
    this.description = description;
    this.length = length;
    this.timeframe = timeframe;
    this.photoUrl = photoUrl;
    this.videoUrl = videoUrl;
  }

  @Override
  public String toString()
  {
    return "programID: " + id + " title: " + title + " description: "
        + description + " photoUrl: " + photoUrl + " videoUrl: " + videoUrl
        + " timeframe: " + timeframe + " lenght: " + length;
  }

  public Program()
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

  public String getTitle()
  {
    return title;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  public String getPhotoUrl()
  {
    return photoUrl;
  }

  public void setPhotoUrl(String photoUrl)
  {
    this.photoUrl = photoUrl;
  }

  public String getVideoUrl()
  {
    return videoUrl;
  }

  public void setVideoUrl(String videoUrl)
  {
    this.videoUrl = videoUrl;
  }

  public Timeframe getTimeframe()
  {
    return timeframe;
  }

  public void setTimeframe(Timeframe timeframe)
  {
    this.timeframe = timeframe;
  }

  public long getLength()
  {
    return length;
  }

  public void setLength(long length)
  {
    this.length = length;
  }

  public void update(Program program)
  {
    this.id = program.id;
    this.title = program.title;
    this.description = program.description;
    this.length = program.length;
    this.timeframe = program.timeframe;
    this.photoUrl = program.photoUrl;
    this.videoUrl = program.videoUrl;
  }
}
