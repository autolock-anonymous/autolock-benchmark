package edu.cmu.cs.crystal.analysis.alias;
import org.eclipse.jdt.core.dom.ITypeBinding;
public interface ObjectLabel {
  public boolean isSummary();
  public ITypeBinding getType();
}
