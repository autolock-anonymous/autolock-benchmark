package edu.cmu.cs.crystal.util;
import static edu.cmu.cs.crystal.util.ConsList.cons;
import static edu.cmu.cs.crystal.util.ConsList.list;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.Iterator;
import edu.cmu.cs.crystal.util.ConsList;
import edu.cmu.cs.crystal.util.Lambda2;
public class ConsListTest {
  ConsList<Integer> l=list(1,2,3,4,5);
  ConsList<Integer> front=ConsList.list(1,2,3,4,5);
  ConsList<Integer> back=ConsList.list(6,7,8,9,10);
  Object obj[]=new Object[10];
  public void testList(){
    l.toArray();
    l.tl();
    l.toArray(obj);
    l.isEmpty();
    l.hd().intValue();
    l=cons(2,l);
    ConsList.concat(front,back);
    ConsList<Integer> l2=cons(2,l);
    l2.tl().size();
    l.removeElement(1).isEmpty();
    l=l.removeElement(1);
    Iterator<Integer> iter=l.iterator();
    iter.hasNext();
    iter.next().intValue();
    l.contains(6);
    l.contains(9);
  }
  public ConsList<Integer> getFront(){
    Cloner cloner=new Cloner();
    front=cloner.deepClone(front);
    Cloner cloner=new Cloner();
    front=cloner.deepClone(front);
    return front;
  }
  public Object getObj(){
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    return obj;
  }
  public ConsList<Integer> getBack(){
    Cloner cloner=new Cloner();
    back=cloner.deepClone(back);
    Cloner cloner=new Cloner();
    back=cloner.deepClone(back);
    return back;
  }
  public ConsList<Integer> getL(){
    Cloner cloner=new Cloner();
    l=cloner.deepClone(l);
    Cloner cloner=new Cloner();
    l=cloner.deepClone(l);
    return l;
  }
}
