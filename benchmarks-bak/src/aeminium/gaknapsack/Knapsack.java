package aeminium.gaknapsack.withlock;
import edu.cmu.cs.plural.annot.Perm;
import aeminium.gaknapsack.MersenneTwisterFast;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class Knapsack {
	public ReentrantReadWriteLock fitnessLock = new ReentrantReadWriteLock();
	public static ReentrantReadWriteLock SIZE_LIMITLock = new ReentrantReadWriteLock();
	public static ReentrantReadWriteLock itemsLock = new ReentrantReadWriteLock();
	public ReentrantReadWriteLock sizeLock = new ReentrantReadWriteLock();
	public ReentrantReadWriteLock valueLock = new ReentrantReadWriteLock();
	public ReentrantReadWriteLock weightLock = new ReentrantReadWriteLock();
	public static ReentrantReadWriteLock has_randLock = new ReentrantReadWriteLock();
	public static int SIZE_LIMIT = 10000;
	public final static int numberOfItems = 10000;
	public static MersenneTwisterFast rand = new MersenneTwisterFast(1L);
	public static Item[] items;
	public static int popSize = 5000;
	public static int numGen = 100;
	public final static int cromSize = numberOfItems;
	public final static double prob_mut = 0.2;
	public final static double prob_rec = 0.2;
	public static final int elitism = 10;
	public static final int DEFAULT_THRESHOLD = popSize / 100;
	public static int bestLimit = elitism;
	@Perm(requires = "unique(items) in alive", ensures = "unique(items) in alive")
	Knapsack() {
		itemsLock.writeLock().lock();
		items = new Item[numberOfItems];
		for (int i = 0; i < numberOfItems; i++) {
			items[i] = new Item("obj" + i, 100, 100);
		}
		itemsLock.writeLock().unlock();
	}
	@Perm(requires = "unique(rand) in alive", ensures = "unique(rand) in alive")
	public static void resetSeed() {
		has_randLock.writeLock().lock();
		rand = null;
		has_randLock.writeLock().unlock();
	}
	@Perm(requires = "pure(rand) in alive", ensures = "pure(rand) in alive")
	public static Indiv createRandomIndiv(Indiv ind) {
		boolean hasSth = false;
		has_randLock.readLock().lock();
		for (int i = 0; i < cromSize; i++) {
			boolean b = (Knapsack.rand.nextDouble() < 0.01);
			ind.set(i, b);
			hasSth = hasSth || b;
		}
		if (!hasSth) {
			ind.set(rand.nextInt(cromSize), true);
		}
		has_randLock.readLock().unlock();
		return ind;
	}
	@Perm(requires = "pure(rand) * share(has) in alive", ensures = "pure(rand) * share(has) in alive")
	public static Indiv recombine(Indiv ind, Indiv p1, Indiv p2) {
		try {
			has_randLock.writeLock().lock();
			if (rand.nextFloat() > Knapsack.prob_rec)
				return p1;
			int cutpoint = rand.nextInt(cromSize);
			for (int i = 0; i < cromSize; i++) {
				if (i < cutpoint)
					ind.set(i, p1.has[i]);
				else
					ind.set(i, p2.has[i]);
			}
		} finally {
			has_randLock.writeLock().unlock();
		}
		return ind;
	}
	@Perm(requires = "none(SIZE_LIMIT) * full(fitness) in alive", ensures = "none(SIZE_LIMIT) * full(fitness) in alive")
	public static void evaluate(Indiv indiv) {
		int[] ph = phenotype(indiv);
		int value = ph[0];
		int weight = ph[1];
		fitnessLock.writeLock().lock();
		if (weight >= Knapsack.SIZE_LIMIT || value == 0) {
			indiv.fitness = 2.0;
		} else {
			indiv.fitness = 1.0 / (value);
		}
		fitnessLock.writeLock().unlock();
	}
	@Perm(requires = "none(size) * pure(has) * none(value) * none(items) * none(weight) in alive", ensures = "none(size) * pure(has) * none(value) * none(items) * none(weight) in alive")
	public static int[] phenotype(Indiv indiv) {
		int value = 0;
		int weight = 0;
		for (int i = 0; i < indiv.size; i++) {
			has_randLock.readLock().lock();
			if (indiv.has[i]) {
				value = value + Knapsack.items[i].value;
				weight = value + Knapsack.items[i].weight;
			}
			has_randLock.readLock().unlock();
		}
		return new int[]{value, weight};
	}
	@Perm(requires = "pure(rand) * share(has) in alive", ensures = "pure(rand) * share(has) in alive")
	public static void mutate(Indiv indiv) {
		has_randLock.readLock().lock();
		has_randLock.writeLock().lock();
		if (rand.nextFloat() < Knapsack.prob_mut) {
			int p = rand.nextInt(cromSize);
			indiv.set(p, !indiv.has[p]);
		}
		has_randLock.writeLock().unlock();
		has_randLock.readLock().unlock();
	}
}
