package org.msz.liferay.portlet.util;

import java.util.Collection;

import org.msz.liferay.portlet.datatype.Program;
import org.msz.liferay.portlet.datatype.Timeframe;

public interface IDAO
{

  public abstract Collection<Timeframe> getTimeframes() throws Exception;

  public abstract Timeframe getTimeframe(int timeframeID) throws Exception;

  public abstract void storeTimeframe(Timeframe timeframe) throws Exception;

  public abstract void updateTimeframe(Timeframe timeframe) throws Exception;

  public abstract void deleteTimeframe(int timeframeID) throws Exception;

  public abstract int getNextTimeframeID() throws Exception;

  public abstract Collection<Program> getPrograms() throws Exception;

  public abstract Collection<Program> getPrograms(int timeframeID)
      throws Exception;

  public abstract Program getProgram(int programID) throws Exception;

  public abstract void storeProgram(Program program) throws Exception;

  public abstract void updateProgram(int oldProgramID, Program program)
      throws Exception;

  public abstract void deleteProgram(int programID) throws Exception;

  public abstract int getNextProgramID() throws Exception;

}