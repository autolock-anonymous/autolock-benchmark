package edu.cmu.cs.crystal.internal;
import java.util.List;
import java.util.concurrent.CancellationException;
/** 
 * A Crystal job is an abstraction of an analysis or analyses that we want to run on a file or many files. This interface was created in anticipation of our need to have testing jobs, regular crystal analysis jobs, and maybe even other kinds of jobs.
 * @author Nels E. Beckman
 */
public interface ICrystalJob {
  public void runJobs() throws CancellationException ;
  public List<ISingleCrystalJob> analysisJobs();
}
