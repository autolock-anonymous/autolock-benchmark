package xyseries.entity;
import org.jfree.data.SeriesChangeEvent;
import org.jfree.data.SeriesChangeListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
/** 
 * Base class representing a data series.  Subclasses are left to implement the actual data structures. <P> The series has two properties ("Name" and "Description") for which you can register a PropertyChangeListener. <P> You can also register a SeriesChangeListener to receive notification of changes to the series data.
 * @author David Gilbert
 */
public class Series implements Cloneable, Serializable {
  /** 
 * The name of the series. 
 */
  private String name;
  /** 
 * A description of the series. 
 */
  private String description;
  /** 
 * Storage for registered change listeners. 
 */
  private List listeners;
  /** 
 * Object to support property change notification. 
 */
  private PropertyChangeSupport propertyChangeSupport;
  /** 
 * Constructs a series.
 * @param name The series name.
 */
  protected Series(  String name){
    this(name,null);
  }
  /** 
 * Constructs a series.
 * @param name  the series name.
 * @param description  the series description (<code>null</code> permitted).
 */
  protected Series(  String name,  String description){
    this.name=name;
    this.description=description;
    this.listeners=new java.util.ArrayList();
    propertyChangeSupport=new PropertyChangeSupport(this);
  }
  /** 
 * Returns the name of the series.
 * @return the name of the series.
 */
  public String getName(){
    Cloner cloner=new Cloner();
    name=cloner.deepClone(name);
    return this.name;
  }
  /** 
 * Sets the name of the series.
 * @param name the name.
 */
  public void setName(  String name){
    String old=this.name;
    this.name=name;
    propertyChangeSupport.firePropertyChange("Name",old,name);
  }
  /** 
 * Returns a description of the series.
 * @return A description of the series (possibly <code>null</code>).
 */
  public String getDescription(){
    Cloner cloner=new Cloner();
    description=cloner.deepClone(description);
    return this.description;
  }
  /** 
 * Sets the description of the series.
 * @param description  the description (<code>null</code> permitted).
 */
  public void setDescription(  String description){
    String old=this.description;
    this.description=description;
    propertyChangeSupport.firePropertyChange("Description",old,description);
  }
  /** 
 * Returns a clone of the series. <P> Notes: 1.  No need to clone the name or description, since String object is immutable. 2.  We set the listener list to empty, since the listeners did not register with the clone. 3.  Same applies to the PropertyChangeSupport instance.
 * @return a clone of the series.
 */
  public Object clone(){
    Object obj=null;
    try {
      obj=super.clone();
    }
 catch (    CloneNotSupportedException e) {
      System.err.println("Series.clone(): unexpected exception.");
    }
    Series clone=(Series)obj;
    clone.listeners=new java.util.ArrayList();
    clone.propertyChangeSupport=new PropertyChangeSupport(clone);
    return clone;
  }
  /** 
 * Registers an object with this series, to receive notification whenever the series changes. <P> Objects being registered must implement the SeriesChangeListener interface.
 * @param listener The object to register.
 */
  public void addChangeListener(  SeriesChangeListener listener){
    this.listeners.add(listener);
  }
  /** 
 * Deregisters an object, so that it not longer receives notification whenever the series changes. <P> Call this method when an object no longer needs to be notified of changes to the series.
 * @param listener The object to deregister.
 */
  public void removeChangeListener(  SeriesChangeListener listener){
    this.listeners.remove(listener);
  }
  /** 
 * General method for signalling to registered listeners that the series has been changed.
 */
  public void fireSeriesChanged(){
    notifyListeners(new SeriesChangeEvent(this));
  }
  /** 
 * Sends a change event to all registered listeners.
 * @param event Contains information about the event that triggered the notification.
 */
  protected void notifyListeners(  SeriesChangeEvent event){
    Iterator iterator=listeners.iterator();
    while (iterator.hasNext()) {
      SeriesChangeListener listener=(SeriesChangeListener)iterator.next();
      listener.seriesChanged(event);
    }
  }
  /** 
 * Adds a property change listener to the series.
 * @param listener The listener.
 */
  public void addPropertyChangeListener(  PropertyChangeListener listener){
    this.propertyChangeSupport.addPropertyChangeListener(listener);
  }
  /** 
 * Removes a property change listener from the series.
 * @param listener The listener.
 */
  public void removePropertyChangeListener(  PropertyChangeListener listener){
    this.propertyChangeSupport.removePropertyChangeListener(listener);
  }
  /** 
 * Fires a property change event.
 * @param property  the property key.
 * @param oldValue  the old value.
 * @param newValue  the new value.
 */
  protected void firePropertyChange(  String property,  Object oldValue,  Object newValue){
    this.propertyChangeSupport.firePropertyChange(property,oldValue,newValue);
  }
  public List getListeners(){
    Cloner cloner=new Cloner();
    listeners=cloner.deepClone(listeners);
    return listeners;
  }
  public PropertyChangeSupport getPropertyChangeSupport(){
    Cloner cloner=new Cloner();
    propertyChangeSupport=cloner.deepClone(propertyChangeSupport);
    return propertyChangeSupport;
  }
}
