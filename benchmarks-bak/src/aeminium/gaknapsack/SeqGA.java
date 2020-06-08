package aeminium.gaknapsack.withlock;
import java.util.Arrays;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class SeqGA {
	public static ReentrantReadWriteLock bestLimitLock = new ReentrantReadWriteLock();
	public static ReentrantReadWriteLock popSizeLock = new ReentrantReadWriteLock();
	public static ReentrantReadWriteLock numGenLock = new ReentrantReadWriteLock();
	public static Indiv ind = new Indiv(Knapsack.cromSize);
	@Perm(requires = "none(popSize) * none(numGen) * none(bestLimit) in alive", ensures = "none(popSize) * none(numGen) * none(bestLimit) in alive")
	public static void main(String[] args) {
		Knapsack.resetSeed();
		Indiv ind = new Indiv(Knapsack.cromSize);
		Indiv[] pop = new Indiv[Knapsack.popSize];
		Indiv[] next = new Indiv[Knapsack.popSize];
		for (int i = 0; i < Knapsack.popSize; i++) {
			pop[i] = Knapsack.createRandomIndiv(ind);
		}
		for (int g = 0; g < Knapsack.numGen; g++) {
			for (int i = 0; i < Knapsack.popSize; i++) {
				Knapsack.evaluate(pop[i]);
			}
			Arrays.sort(pop);
			for (int i = 0; i < Knapsack.elitism; i++) {
				next[Knapsack.popSize - i - 1] = pop[i];
			}
			for (int i = 0; i < Knapsack.popSize - Knapsack.elitism; i++) {
				Indiv other = (i < Knapsack.bestLimit) ? pop[i + 1] : pop[i - Knapsack.bestLimit];
				next[i] = Knapsack.recombine(ind, pop[i], other);
			}
			for (int i = 0; i < Knapsack.popSize - Knapsack.elitism; i++) {
				Knapsack.mutate(next[i]);
			}
			Indiv[] tmp = pop;
			pop = next;
			next = tmp;
		}
		System.out.println("Done");
	}
}
