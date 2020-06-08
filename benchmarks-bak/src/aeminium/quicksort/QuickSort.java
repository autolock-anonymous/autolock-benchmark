package aeminium.quicksort.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class QuickSort {
	public final static int DEFAULT_SIZE = 10000;
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static int partition(long[] data, int left, int right) {
		int i = left;
		int j = right;
		long tmp;
		long pivot = data[(left + right) / 2];
		while (i <= j) {
			while (data[i] < pivot)
				i++;
			while (data[j] > pivot)
				j--;
			if (i <= j) {
				tmp = data[i];
				data[i] = data[j];
				data[j] = tmp;
				i++;
				j--;
			}
		}
		return i;
	}
}
