package jomp.search.withlock;
import jomp.jgfutil.*;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class SearchGame extends TransGame {
  public static ReentrantReadWriteLock colthr_columns_height_history_msecs_nodes_plycnt_posedLock=new ReentrantReadWriteLock();
  int history[][]={{-1,-1,-1,-1,-1,-1,-1,-1,-1,0,1,2,4,2,1,0,-1,1,3,5,7,5,3,1,-1,2,5,8,10,8,5,2,-1,2,5,8,10,8,5,2,-1,1,3,5,7,5,3,1,-1,0,1,2,4,2,1,0},{-1,-1,-1,-1,-1,-1,-1,-1,-1,0,1,2,4,2,1,0,-1,1,3,5,7,5,3,1,-1,2,5,8,10,8,5,2,-1,2,5,8,10,8,5,2,-1,1,3,5,7,5,3,1,-1,0,1,2,4,2,1,0}};
  long nodes, msecs;
  @Perm(requires="no permission in alive",ensures="no permission in alive") public SearchGame(){
  }
  @Perm(requires="share(nodes) * share(msecs) * pure(plycnt) * pure(height) * immutable(colthr) * pure(columns) * pure(posed) in alive",ensures="share(nodes) * share(msecs) * pure(plycnt) * pure(height) * immutable(colthr) * pure(columns) * pure(posed) in alive") int solve(){
    int i, side;
    int x, work, score;
    long poscnt;
    try {
      colthr_columns_height_history_msecs_nodes_plycnt_posedLock.writeLock().lock();
      nodes=0L;
      msecs=1L;
      side=(plycnt + 1) & 1;
      for (i=0; ++i <= 7; )       if (height[i] <= 6) {
        if (wins(i,height[i],1 << side) || colthr[columns[i]] == (1 << side))         return (side != 0 ? WIN : LOSE) << 5;
      }
      if ((x=transpose()) != ABSENT) {
        if ((x & 32) == 0)         return x;
      }
      poscnt=posed;
      for (work=1; (poscnt>>=1) != 0; work++)       ;
      score=ab(LOSE,WIN);
    }
  finally {
      colthr_columns_height_history_msecs_nodes_plycnt_posedLock.writeLock().unlock();
    }
    return score << 5 | work;
  }
  @Perm(requires="share(nodes) * share(plycnt) * share(height) * immutable(colthr) * share(columns) * share(posed) * unique(history) in alive",ensures="share(nodes) * share(plycnt) * share(height) * immutable(colthr) * share(columns) * share(posed) * unique(history) in alive") int ab(  int alpha,  int beta){
    int besti, i, j, h, k, l, val, score;
    int x, v, work;
    int nav=0, av[]=new int[8];
    long poscnt;
    int side=0, otherside;
    try {
      colthr_columns_height_history_msecs_nodes_plycnt_posedLock.writeLock().lock();
      nodes++;
      if (plycnt == 41)       return DRAW;
      side=(otherside=plycnt & 1) ^ 1;
      for (i=nav=0; ++i <= 7; ) {
        if ((h=height[i]) <= 6) {
          if (wins(i,h,3) || colthr[columns[i]] != 0) {
            if (h + 1 <= 6 && wins(i,h + 1,1 << otherside))             return LOSE;
            av[0]=i;
            while (++i <= 7)             if ((h=height[i]) <= 6 && (wins(i,h,3) || colthr[columns[i]] != 0))             return LOSE;
            nav=1;
            break;
          }
          if (!(h + 1 <= 6 && wins(i,h + 1,1 << otherside)))           av[nav++]=i;
        }
      }
      if (nav == 0)       return LOSE;
      if (nav == 1) {
        makemove(av[0]);
        score=-ab(-beta,-alpha);
        backmove();
        return score;
      }
      if ((x=transpose()) != ABSENT) {
        score=x >> 5;
        if (score == DRAWLOSE) {
          if ((beta=DRAW) <= alpha)           return score;
        }
 else         if (score == DRAWWIN) {
          if ((alpha=DRAW) >= beta)           return score;
        }
 else         return score;
      }
      poscnt=posed;
      l=besti=0;
      score=Integer.MIN_VALUE;
      for (i=0; i < nav; i++) {
        for (j=i, val=Integer.MIN_VALUE; j < nav; j++) {
          k=av[j];
          v=history[side][height[k] << 3 | k];
          if (v > val) {
            val=v;
            l=j;
          }
        }
        j=av[l];
        if (i != l) {
          av[l]=av[i];
          av[i]=j;
        }
        makemove(j);
        val=-ab(-beta,-alpha);
        backmove();
        if (val > score) {
          besti=i;
          if ((score=val) > alpha && (alpha=val) >= beta) {
            if (score == DRAW && i < nav - 1)             score=DRAWWIN;
            break;
          }
        }
      }
      if (besti > 0) {
        for (i=0; i < besti; i++)         history[side][height[av[i]] << 3 | av[i]]--;
        history[side][height[av[besti]] << 3 | av[besti]]+=besti;
      }
      poscnt=posed - poscnt;
    }
  finally {
      colthr_columns_height_history_msecs_nodes_plycnt_posedLock.writeLock().unlock();
    }
    for (work=1; (poscnt>>=1) != 0; work++)     ;
    if (x != ABSENT) {
      if (score == -(x >> 5))       score=DRAW;
      transrestore(score,work);
    }
 else     transtore(score,work);
    return score;
  }
  public static ReentrantReadWriteLock getColthr_columns_height_history_msecs_nodes_plycnt_posedLock(){
    Cloner cloner=new Cloner();
    colthr_columns_height_history_msecs_nodes_plycnt_posedLock=cloner.deepClone(colthr_columns_height_history_msecs_nodes_plycnt_posedLock);
    return colthr_columns_height_history_msecs_nodes_plycnt_posedLock;
  }
}
