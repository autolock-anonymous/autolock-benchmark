package hashtablejava167.entity.withlock;
import java.lang.reflect.*;
import java.util.*;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class Arrays {
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private Arrays() {
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void sort(long[] a) {
		sort1(a, 0, a.length);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void sort(long[] a, int fromIndex, int toIndex) {
		rangeCheck(a.length, fromIndex, toIndex);
		sort1(a, fromIndex, toIndex - fromIndex);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void sort(int[] a) {
		sort1(a, 0, a.length);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void sort(int[] a, int fromIndex, int toIndex) {
		rangeCheck(a.length, fromIndex, toIndex);
		sort1(a, fromIndex, toIndex - fromIndex);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void sort(short[] a) {
		sort1(a, 0, a.length);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void sort(short[] a, int fromIndex, int toIndex) {
		rangeCheck(a.length, fromIndex, toIndex);
		sort1(a, fromIndex, toIndex - fromIndex);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void sort(char[] a) {
		sort1(a, 0, a.length);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void sort(char[] a, int fromIndex, int toIndex) {
		rangeCheck(a.length, fromIndex, toIndex);
		sort1(a, fromIndex, toIndex - fromIndex);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void sort(byte[] a) {
		sort1(a, 0, a.length);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void sort(byte[] a, int fromIndex, int toIndex) {
		rangeCheck(a.length, fromIndex, toIndex);
		sort1(a, fromIndex, toIndex - fromIndex);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void sort(double[] a) {
		sort2(a, 0, a.length);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void sort(double[] a, int fromIndex, int toIndex) {
		rangeCheck(a.length, fromIndex, toIndex);
		sort2(a, fromIndex, toIndex);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void sort(float[] a) {
		sort2(a, 0, a.length);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void sort(float[] a, int fromIndex, int toIndex) {
		rangeCheck(a.length, fromIndex, toIndex);
		sort2(a, fromIndex, toIndex);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static void sort2(double a[], int fromIndex, int toIndex) {
		final long NEG_ZERO_BITS = Double.doubleToLongBits(-0.0d);
		int numNegZeros = 0;
		int i = fromIndex, n = toIndex;
		while (i < n) {
			if (a[i] != a[i]) {
				double swap = a[i];
				a[i] = a[--n];
				a[n] = swap;
			} else {
				if (a[i] == 0 && Double.doubleToLongBits(a[i]) == NEG_ZERO_BITS) {
					a[i] = 0.0d;
					numNegZeros++;
				}
				i++;
			}
		}
		sort1(a, fromIndex, n - fromIndex);
		if (numNegZeros != 0) {
			int j = binarySearch0(a, fromIndex, n, 0.0d);
			do {
				j--;
			} while (j >= 0 && a[j] == 0.0d);
			for (int k = 0; k < numNegZeros; k++)
				a[++j] = -0.0d;
		}
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static void sort2(float a[], int fromIndex, int toIndex) {
		final int NEG_ZERO_BITS = Float.floatToIntBits(-0.0f);
		int numNegZeros = 0;
		int i = fromIndex, n = toIndex;
		while (i < n) {
			if (a[i] != a[i]) {
				float swap = a[i];
				a[i] = a[--n];
				a[n] = swap;
			} else {
				if (a[i] == 0 && Float.floatToIntBits(a[i]) == NEG_ZERO_BITS) {
					a[i] = 0.0f;
					numNegZeros++;
				}
				i++;
			}
		}
		sort1(a, fromIndex, n - fromIndex);
		if (numNegZeros != 0) {
			int j = binarySearch0(a, fromIndex, n, 0.0f);
			do {
				j--;
			} while (j >= 0 && a[j] == 0.0f);
			for (int k = 0; k < numNegZeros; k++)
				a[++j] = -0.0f;
		}
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static void sort1(long x[], int off, int len) {
		if (len < 7) {
			for (int i = off; i < len + off; i++)
				for (int j = i; j > off && x[j - 1] > x[j]; j--)
					swap(x, j, j - 1);
			return;
		}
		int m = off + (len >> 1);
		if (len > 7) {
			int l = off;
			int n = off + len - 1;
			if (len > 40) {
				int s = len / 8;
				l = med3(x, l, l + s, l + 2 * s);
				m = med3(x, m - s, m, m + s);
				n = med3(x, n - 2 * s, n - s, n);
			}
			m = med3(x, l, m, n);
		}
		long v = x[m];
		int a = off, b = a, c = off + len - 1, d = c;
		while (true) {
			while (b <= c && x[b] <= v) {
				if (x[b] == v)
					swap(x, a++, b);
				b++;
			}
			while (c >= b && x[c] >= v) {
				if (x[c] == v)
					swap(x, c, d--);
				c--;
			}
			if (b > c)
				break;
			swap(x, b++, c--);
		}
		int s, n = off + len;
		s = Math.min(a - off, b - a);
		vecswap(x, off, b - s, s);
		s = Math.min(d - c, n - d - 1);
		vecswap(x, b, n - s, s);
		if ((s = b - a) > 1)
			sort1(x, off, s);
		if ((s = d - c) > 1)
			sort1(x, n - s, s);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static void swap(long x[], int a, int b) {
		long t = x[a];
		x[a] = x[b];
		x[b] = t;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static void vecswap(long x[], int a, int b, int n) {
		for (int i = 0; i < n; i++, a++, b++)
			swap(x, a, b);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static int med3(long x[], int a, int b, int c) {
		return (x[a] < x[b] ? (x[b] < x[c] ? b : x[a] < x[c] ? c : a) : (x[b] > x[c] ? b : x[a] > x[c] ? c : a));
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static void sort1(int x[], int off, int len) {
		if (len < 7) {
			for (int i = off; i < len + off; i++)
				for (int j = i; j > off && x[j - 1] > x[j]; j--)
					swap(x, j, j - 1);
			return;
		}
		int m = off + (len >> 1);
		if (len > 7) {
			int l = off;
			int n = off + len - 1;
			if (len > 40) {
				int s = len / 8;
				l = med3(x, l, l + s, l + 2 * s);
				m = med3(x, m - s, m, m + s);
				n = med3(x, n - 2 * s, n - s, n);
			}
			m = med3(x, l, m, n);
		}
		int v = x[m];
		int a = off, b = a, c = off + len - 1, d = c;
		while (true) {
			while (b <= c && x[b] <= v) {
				if (x[b] == v)
					swap(x, a++, b);
				b++;
			}
			while (c >= b && x[c] >= v) {
				if (x[c] == v)
					swap(x, c, d--);
				c--;
			}
			if (b > c)
				break;
			swap(x, b++, c--);
		}
		int s, n = off + len;
		s = Math.min(a - off, b - a);
		vecswap(x, off, b - s, s);
		s = Math.min(d - c, n - d - 1);
		vecswap(x, b, n - s, s);
		if ((s = b - a) > 1)
			sort1(x, off, s);
		if ((s = d - c) > 1)
			sort1(x, n - s, s);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static void swap(int x[], int a, int b) {
		int t = x[a];
		x[a] = x[b];
		x[b] = t;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static void vecswap(int x[], int a, int b, int n) {
		for (int i = 0; i < n; i++, a++, b++)
			swap(x, a, b);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static int med3(int x[], int a, int b, int c) {
		return (x[a] < x[b] ? (x[b] < x[c] ? b : x[a] < x[c] ? c : a) : (x[b] > x[c] ? b : x[a] > x[c] ? c : a));
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static void sort1(short x[], int off, int len) {
		if (len < 7) {
			for (int i = off; i < len + off; i++)
				for (int j = i; j > off && x[j - 1] > x[j]; j--)
					swap(x, j, j - 1);
			return;
		}
		int m = off + (len >> 1);
		if (len > 7) {
			int l = off;
			int n = off + len - 1;
			if (len > 40) {
				int s = len / 8;
				l = med3(x, l, l + s, l + 2 * s);
				m = med3(x, m - s, m, m + s);
				n = med3(x, n - 2 * s, n - s, n);
			}
			m = med3(x, l, m, n);
		}
		short v = x[m];
		int a = off, b = a, c = off + len - 1, d = c;
		while (true) {
			while (b <= c && x[b] <= v) {
				if (x[b] == v)
					swap(x, a++, b);
				b++;
			}
			while (c >= b && x[c] >= v) {
				if (x[c] == v)
					swap(x, c, d--);
				c--;
			}
			if (b > c)
				break;
			swap(x, b++, c--);
		}
		int s, n = off + len;
		s = Math.min(a - off, b - a);
		vecswap(x, off, b - s, s);
		s = Math.min(d - c, n - d - 1);
		vecswap(x, b, n - s, s);
		if ((s = b - a) > 1)
			sort1(x, off, s);
		if ((s = d - c) > 1)
			sort1(x, n - s, s);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static void swap(short x[], int a, int b) {
		short t = x[a];
		x[a] = x[b];
		x[b] = t;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static void vecswap(short x[], int a, int b, int n) {
		for (int i = 0; i < n; i++, a++, b++)
			swap(x, a, b);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static int med3(short x[], int a, int b, int c) {
		return (x[a] < x[b] ? (x[b] < x[c] ? b : x[a] < x[c] ? c : a) : (x[b] > x[c] ? b : x[a] > x[c] ? c : a));
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static void sort1(char x[], int off, int len) {
		if (len < 7) {
			for (int i = off; i < len + off; i++)
				for (int j = i; j > off && x[j - 1] > x[j]; j--)
					swap(x, j, j - 1);
			return;
		}
		int m = off + (len >> 1);
		if (len > 7) {
			int l = off;
			int n = off + len - 1;
			if (len > 40) {
				int s = len / 8;
				l = med3(x, l, l + s, l + 2 * s);
				m = med3(x, m - s, m, m + s);
				n = med3(x, n - 2 * s, n - s, n);
			}
			m = med3(x, l, m, n);
		}
		char v = x[m];
		int a = off, b = a, c = off + len - 1, d = c;
		while (true) {
			while (b <= c && x[b] <= v) {
				if (x[b] == v)
					swap(x, a++, b);
				b++;
			}
			while (c >= b && x[c] >= v) {
				if (x[c] == v)
					swap(x, c, d--);
				c--;
			}
			if (b > c)
				break;
			swap(x, b++, c--);
		}
		int s, n = off + len;
		s = Math.min(a - off, b - a);
		vecswap(x, off, b - s, s);
		s = Math.min(d - c, n - d - 1);
		vecswap(x, b, n - s, s);
		if ((s = b - a) > 1)
			sort1(x, off, s);
		if ((s = d - c) > 1)
			sort1(x, n - s, s);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static void swap(char x[], int a, int b) {
		char t = x[a];
		x[a] = x[b];
		x[b] = t;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static void vecswap(char x[], int a, int b, int n) {
		for (int i = 0; i < n; i++, a++, b++)
			swap(x, a, b);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static int med3(char x[], int a, int b, int c) {
		return (x[a] < x[b] ? (x[b] < x[c] ? b : x[a] < x[c] ? c : a) : (x[b] > x[c] ? b : x[a] > x[c] ? c : a));
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static void sort1(byte x[], int off, int len) {
		if (len < 7) {
			for (int i = off; i < len + off; i++)
				for (int j = i; j > off && x[j - 1] > x[j]; j--)
					swap(x, j, j - 1);
			return;
		}
		int m = off + (len >> 1);
		if (len > 7) {
			int l = off;
			int n = off + len - 1;
			if (len > 40) {
				int s = len / 8;
				l = med3(x, l, l + s, l + 2 * s);
				m = med3(x, m - s, m, m + s);
				n = med3(x, n - 2 * s, n - s, n);
			}
			m = med3(x, l, m, n);
		}
		byte v = x[m];
		int a = off, b = a, c = off + len - 1, d = c;
		while (true) {
			while (b <= c && x[b] <= v) {
				if (x[b] == v)
					swap(x, a++, b);
				b++;
			}
			while (c >= b && x[c] >= v) {
				if (x[c] == v)
					swap(x, c, d--);
				c--;
			}
			if (b > c)
				break;
			swap(x, b++, c--);
		}
		int s, n = off + len;
		s = Math.min(a - off, b - a);
		vecswap(x, off, b - s, s);
		s = Math.min(d - c, n - d - 1);
		vecswap(x, b, n - s, s);
		if ((s = b - a) > 1)
			sort1(x, off, s);
		if ((s = d - c) > 1)
			sort1(x, n - s, s);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static void swap(byte x[], int a, int b) {
		byte t = x[a];
		x[a] = x[b];
		x[b] = t;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static void vecswap(byte x[], int a, int b, int n) {
		for (int i = 0; i < n; i++, a++, b++)
			swap(x, a, b);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static int med3(byte x[], int a, int b, int c) {
		return (x[a] < x[b] ? (x[b] < x[c] ? b : x[a] < x[c] ? c : a) : (x[b] > x[c] ? b : x[a] > x[c] ? c : a));
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static void sort1(double x[], int off, int len) {
		if (len < 7) {
			for (int i = off; i < len + off; i++)
				for (int j = i; j > off && x[j - 1] > x[j]; j--)
					swap(x, j, j - 1);
			return;
		}
		int m = off + (len >> 1);
		if (len > 7) {
			int l = off;
			int n = off + len - 1;
			if (len > 40) {
				int s = len / 8;
				l = med3(x, l, l + s, l + 2 * s);
				m = med3(x, m - s, m, m + s);
				n = med3(x, n - 2 * s, n - s, n);
			}
			m = med3(x, l, m, n);
		}
		double v = x[m];
		int a = off, b = a, c = off + len - 1, d = c;
		while (true) {
			while (b <= c && x[b] <= v) {
				if (x[b] == v)
					swap(x, a++, b);
				b++;
			}
			while (c >= b && x[c] >= v) {
				if (x[c] == v)
					swap(x, c, d--);
				c--;
			}
			if (b > c)
				break;
			swap(x, b++, c--);
		}
		int s, n = off + len;
		s = Math.min(a - off, b - a);
		vecswap(x, off, b - s, s);
		s = Math.min(d - c, n - d - 1);
		vecswap(x, b, n - s, s);
		if ((s = b - a) > 1)
			sort1(x, off, s);
		if ((s = d - c) > 1)
			sort1(x, n - s, s);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static void swap(double x[], int a, int b) {
		double t = x[a];
		x[a] = x[b];
		x[b] = t;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static void vecswap(double x[], int a, int b, int n) {
		for (int i = 0; i < n; i++, a++, b++)
			swap(x, a, b);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static int med3(double x[], int a, int b, int c) {
		return (x[a] < x[b] ? (x[b] < x[c] ? b : x[a] < x[c] ? c : a) : (x[b] > x[c] ? b : x[a] > x[c] ? c : a));
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static void sort1(float x[], int off, int len) {
		if (len < 7) {
			for (int i = off; i < len + off; i++)
				for (int j = i; j > off && x[j - 1] > x[j]; j--)
					swap(x, j, j - 1);
			return;
		}
		int m = off + (len >> 1);
		if (len > 7) {
			int l = off;
			int n = off + len - 1;
			if (len > 40) {
				int s = len / 8;
				l = med3(x, l, l + s, l + 2 * s);
				m = med3(x, m - s, m, m + s);
				n = med3(x, n - 2 * s, n - s, n);
			}
			m = med3(x, l, m, n);
		}
		float v = x[m];
		int a = off, b = a, c = off + len - 1, d = c;
		while (true) {
			while (b <= c && x[b] <= v) {
				if (x[b] == v)
					swap(x, a++, b);
				b++;
			}
			while (c >= b && x[c] >= v) {
				if (x[c] == v)
					swap(x, c, d--);
				c--;
			}
			if (b > c)
				break;
			swap(x, b++, c--);
		}
		int s, n = off + len;
		s = Math.min(a - off, b - a);
		vecswap(x, off, b - s, s);
		s = Math.min(d - c, n - d - 1);
		vecswap(x, b, n - s, s);
		if ((s = b - a) > 1)
			sort1(x, off, s);
		if ((s = d - c) > 1)
			sort1(x, n - s, s);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static void swap(float x[], int a, int b) {
		float t = x[a];
		x[a] = x[b];
		x[b] = t;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static void vecswap(float x[], int a, int b, int n) {
		for (int i = 0; i < n; i++, a++, b++)
			swap(x, a, b);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static int med3(float x[], int a, int b, int c) {
		return (x[a] < x[b] ? (x[b] < x[c] ? b : x[a] < x[c] ? c : a) : (x[b] > x[c] ? b : x[a] > x[c] ? c : a));
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void sort(Object[] a) {
		Object[] aux = (Object[]) a.clone();
		mergeSort(aux, a, 0, a.length, 0);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void sort(Object[] a, int fromIndex, int toIndex) {
		rangeCheck(a.length, fromIndex, toIndex);
		Object[] aux = copyOfRange(a, fromIndex, toIndex);
		mergeSort(aux, a, fromIndex, toIndex, -fromIndex);
	}
	private static final int INSERTIONSORT_THRESHOLD = 7;
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static void mergeSort(Object[] src, Object[] dest, int low, int high, int off) {
		int length = high - low;
		if (length < INSERTIONSORT_THRESHOLD) {
			for (int i = low; i < high; i++)
				for (int j = i; j > low && ((Comparable) dest[j - 1]).compareTo(dest[j]) > 0; j--)
					swap(dest, j, j - 1);
			return;
		}
		int destLow = low;
		int destHigh = high;
		low += off;
		high += off;
		int mid = (low + high) >>> 1;
		mergeSort(dest, src, low, mid, -off);
		mergeSort(dest, src, mid, high, -off);
		if (((Comparable) src[mid - 1]).compareTo(src[mid]) <= 0) {
			System.arraycopy(src, low, dest, destLow, length);
			return;
		}
		for (int i = destLow, p = low, q = mid; i < destHigh; i++) {
			if (q >= high || p < mid && ((Comparable) src[p]).compareTo(src[q]) <= 0)
				dest[i] = src[p++];
			else
				dest[i] = src[q++];
		}
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static void swap(Object[] x, int a, int b) {
		Object t = x[a];
		x[a] = x[b];
		x[b] = t;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static <T> void sort(T[] a, Comparator<? super T> c) {
		T[] aux = (T[]) a.clone();
		if (c == null)
			mergeSort(aux, a, 0, a.length, 0);
		else
			mergeSort(aux, a, 0, a.length, 0, c);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static <T> void sort(T[] a, int fromIndex, int toIndex, Comparator<? super T> c) {
		rangeCheck(a.length, fromIndex, toIndex);
		T[] aux = (T[]) copyOfRange(a, fromIndex, toIndex);
		if (c == null)
			mergeSort(aux, a, fromIndex, toIndex, -fromIndex);
		else
			mergeSort(aux, a, fromIndex, toIndex, -fromIndex, c);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static void mergeSort(Object[] src, Object[] dest, int low, int high, int off, Comparator c) {
		int length = high - low;
		if (length < INSERTIONSORT_THRESHOLD) {
			for (int i = low; i < high; i++)
				for (int j = i; j > low && c.compare(dest[j - 1], dest[j]) > 0; j--)
					swap(dest, j, j - 1);
			return;
		}
		int destLow = low;
		int destHigh = high;
		low += off;
		high += off;
		int mid = (low + high) >>> 1;
		mergeSort(dest, src, low, mid, -off, c);
		mergeSort(dest, src, mid, high, -off, c);
		if (c.compare(src[mid - 1], src[mid]) <= 0) {
			System.arraycopy(src, low, dest, destLow, length);
			return;
		}
		for (int i = destLow, p = low, q = mid; i < destHigh; i++) {
			if (q >= high || p < mid && c.compare(src[p], src[q]) <= 0)
				dest[i] = src[p++];
			else
				dest[i] = src[q++];
		}
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static void rangeCheck(int arrayLen, int fromIndex, int toIndex) {
		if (fromIndex > toIndex)
			throw new IllegalArgumentException("fromIndex(" + fromIndex + ") > toIndex(" + toIndex + ")");
		if (fromIndex < 0)
			throw new ArrayIndexOutOfBoundsException(fromIndex);
		if (toIndex > arrayLen)
			throw new ArrayIndexOutOfBoundsException(toIndex);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static int binarySearch(long[] a, long key) {
		return binarySearch0(a, 0, a.length, key);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static int binarySearch(long[] a, int fromIndex, int toIndex, long key) {
		rangeCheck(a.length, fromIndex, toIndex);
		return binarySearch0(a, fromIndex, toIndex, key);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static int binarySearch0(long[] a, int fromIndex, int toIndex, long key) {
		int low = fromIndex;
		int high = toIndex - 1;
		while (low <= high) {
			int mid = (low + high) >>> 1;
			long midVal = a[mid];
			if (midVal < key)
				low = mid + 1;
			else if (midVal > key)
				high = mid - 1;
			else
				return mid;
		}
		return -(low + 1);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static int binarySearch(int[] a, int key) {
		return binarySearch0(a, 0, a.length, key);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static int binarySearch(int[] a, int fromIndex, int toIndex, int key) {
		rangeCheck(a.length, fromIndex, toIndex);
		return binarySearch0(a, fromIndex, toIndex, key);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static int binarySearch0(int[] a, int fromIndex, int toIndex, int key) {
		int low = fromIndex;
		int high = toIndex - 1;
		while (low <= high) {
			int mid = (low + high) >>> 1;
			int midVal = a[mid];
			if (midVal < key)
				low = mid + 1;
			else if (midVal > key)
				high = mid - 1;
			else
				return mid;
		}
		return -(low + 1);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static int binarySearch(short[] a, short key) {
		return binarySearch0(a, 0, a.length, key);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static int binarySearch(short[] a, int fromIndex, int toIndex, short key) {
		rangeCheck(a.length, fromIndex, toIndex);
		return binarySearch0(a, fromIndex, toIndex, key);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static int binarySearch0(short[] a, int fromIndex, int toIndex, short key) {
		int low = fromIndex;
		int high = toIndex - 1;
		while (low <= high) {
			int mid = (low + high) >>> 1;
			short midVal = a[mid];
			if (midVal < key)
				low = mid + 1;
			else if (midVal > key)
				high = mid - 1;
			else
				return mid;
		}
		return -(low + 1);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static int binarySearch(char[] a, char key) {
		return binarySearch0(a, 0, a.length, key);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static int binarySearch(char[] a, int fromIndex, int toIndex, char key) {
		rangeCheck(a.length, fromIndex, toIndex);
		return binarySearch0(a, fromIndex, toIndex, key);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static int binarySearch0(char[] a, int fromIndex, int toIndex, char key) {
		int low = fromIndex;
		int high = toIndex - 1;
		while (low <= high) {
			int mid = (low + high) >>> 1;
			char midVal = a[mid];
			if (midVal < key)
				low = mid + 1;
			else if (midVal > key)
				high = mid - 1;
			else
				return mid;
		}
		return -(low + 1);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static int binarySearch(byte[] a, byte key) {
		return binarySearch0(a, 0, a.length, key);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static int binarySearch(byte[] a, int fromIndex, int toIndex, byte key) {
		rangeCheck(a.length, fromIndex, toIndex);
		return binarySearch0(a, fromIndex, toIndex, key);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static int binarySearch0(byte[] a, int fromIndex, int toIndex, byte key) {
		int low = fromIndex;
		int high = toIndex - 1;
		while (low <= high) {
			int mid = (low + high) >>> 1;
			byte midVal = a[mid];
			if (midVal < key)
				low = mid + 1;
			else if (midVal > key)
				high = mid - 1;
			else
				return mid;
		}
		return -(low + 1);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static int binarySearch(double[] a, double key) {
		return binarySearch0(a, 0, a.length, key);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static int binarySearch(double[] a, int fromIndex, int toIndex, double key) {
		rangeCheck(a.length, fromIndex, toIndex);
		return binarySearch0(a, fromIndex, toIndex, key);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static int binarySearch0(double[] a, int fromIndex, int toIndex, double key) {
		int low = fromIndex;
		int high = toIndex - 1;
		while (low <= high) {
			int mid = (low + high) >>> 1;
			double midVal = a[mid];
			int cmp;
			if (midVal < key) {
				cmp = -1;
			} else if (midVal > key) {
				cmp = 1;
			} else {
				long midBits = Double.doubleToLongBits(midVal);
				long keyBits = Double.doubleToLongBits(key);
				cmp = (midBits == keyBits ? 0 : (midBits < keyBits ? -1 : 1));
			}
			if (cmp < 0)
				low = mid + 1;
			else if (cmp > 0)
				high = mid - 1;
			else
				return mid;
		}
		return -(low + 1);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static int binarySearch(float[] a, float key) {
		return binarySearch0(a, 0, a.length, key);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static int binarySearch(float[] a, int fromIndex, int toIndex, float key) {
		rangeCheck(a.length, fromIndex, toIndex);
		return binarySearch0(a, fromIndex, toIndex, key);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static int binarySearch0(float[] a, int fromIndex, int toIndex, float key) {
		int low = fromIndex;
		int high = toIndex - 1;
		while (low <= high) {
			int mid = (low + high) >>> 1;
			float midVal = a[mid];
			int cmp;
			if (midVal < key) {
				cmp = -1;
			} else if (midVal > key) {
				cmp = 1;
			} else {
				int midBits = Float.floatToIntBits(midVal);
				int keyBits = Float.floatToIntBits(key);
				cmp = (midBits == keyBits ? 0 : (midBits < keyBits ? -1 : 1));
			}
			if (cmp < 0)
				low = mid + 1;
			else if (cmp > 0)
				high = mid - 1;
			else
				return mid;
		}
		return -(low + 1);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static int binarySearch(Object[] a, Object key) {
		return binarySearch0(a, 0, a.length, key);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static int binarySearch(Object[] a, int fromIndex, int toIndex, Object key) {
		rangeCheck(a.length, fromIndex, toIndex);
		return binarySearch0(a, fromIndex, toIndex, key);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static int binarySearch0(Object[] a, int fromIndex, int toIndex, Object key) {
		int low = fromIndex;
		int high = toIndex - 1;
		while (low <= high) {
			int mid = (low + high) >>> 1;
			Comparable midVal = (Comparable) a[mid];
			int cmp = midVal.compareTo(key);
			if (cmp < 0)
				low = mid + 1;
			else if (cmp > 0)
				high = mid - 1;
			else
				return mid;
		}
		return -(low + 1);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static <T> int binarySearch(T[] a, T key, Comparator<? super T> c) {
		return binarySearch0(a, 0, a.length, key, c);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static <T> int binarySearch(T[] a, int fromIndex, int toIndex, T key, Comparator<? super T> c) {
		rangeCheck(a.length, fromIndex, toIndex);
		return binarySearch0(a, fromIndex, toIndex, key, c);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static <T> int binarySearch0(T[] a, int fromIndex, int toIndex, T key, Comparator<? super T> c) {
		if (c == null) {
			return binarySearch0(a, fromIndex, toIndex, key);
		}
		int low = fromIndex;
		int high = toIndex - 1;
		while (low <= high) {
			int mid = (low + high) >>> 1;
			T midVal = a[mid];
			int cmp = c.compare(midVal, key);
			if (cmp < 0)
				low = mid + 1;
			else if (cmp > 0)
				high = mid - 1;
			else
				return mid;
		}
		return -(low + 1);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static boolean equals(long[] a, long[] a2) {
		if (a == a2)
			return true;
		if (a == null || a2 == null)
			return false;
		int length = a.length;
		if (a2.length != length)
			return false;
		for (int i = 0; i < length; i++)
			if (a[i] != a2[i])
				return false;
		return true;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static boolean equals(int[] a, int[] a2) {
		if (a == a2)
			return true;
		if (a == null || a2 == null)
			return false;
		int length = a.length;
		if (a2.length != length)
			return false;
		for (int i = 0; i < length; i++)
			if (a[i] != a2[i])
				return false;
		return true;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static boolean equals(short[] a, short a2[]) {
		if (a == a2)
			return true;
		if (a == null || a2 == null)
			return false;
		int length = a.length;
		if (a2.length != length)
			return false;
		for (int i = 0; i < length; i++)
			if (a[i] != a2[i])
				return false;
		return true;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static boolean equals(char[] a, char[] a2) {
		if (a == a2)
			return true;
		if (a == null || a2 == null)
			return false;
		int length = a.length;
		if (a2.length != length)
			return false;
		for (int i = 0; i < length; i++)
			if (a[i] != a2[i])
				return false;
		return true;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static boolean equals(byte[] a, byte[] a2) {
		if (a == a2)
			return true;
		if (a == null || a2 == null)
			return false;
		int length = a.length;
		if (a2.length != length)
			return false;
		for (int i = 0; i < length; i++)
			if (a[i] != a2[i])
				return false;
		return true;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static boolean equals(boolean[] a, boolean[] a2) {
		if (a == a2)
			return true;
		if (a == null || a2 == null)
			return false;
		int length = a.length;
		if (a2.length != length)
			return false;
		for (int i = 0; i < length; i++)
			if (a[i] != a2[i])
				return false;
		return true;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static boolean equals(double[] a, double[] a2) {
		if (a == a2)
			return true;
		if (a == null || a2 == null)
			return false;
		int length = a.length;
		if (a2.length != length)
			return false;
		for (int i = 0; i < length; i++)
			if (Double.doubleToLongBits(a[i]) != Double.doubleToLongBits(a2[i]))
				return false;
		return true;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static boolean equals(float[] a, float[] a2) {
		if (a == a2)
			return true;
		if (a == null || a2 == null)
			return false;
		int length = a.length;
		if (a2.length != length)
			return false;
		for (int i = 0; i < length; i++)
			if (Float.floatToIntBits(a[i]) != Float.floatToIntBits(a2[i]))
				return false;
		return true;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static boolean equals(Object[] a, Object[] a2) {
		if (a == a2)
			return true;
		if (a == null || a2 == null)
			return false;
		int length = a.length;
		if (a2.length != length)
			return false;
		for (int i = 0; i < length; i++) {
			Object o1 = a[i];
			Object o2 = a2[i];
			if (!(o1 == null ? o2 == null : o1.equals(o2)))
				return false;
		}
		return true;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void fill(long[] a, long val) {
		fill(a, 0, a.length, val);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void fill(long[] a, int fromIndex, int toIndex, long val) {
		rangeCheck(a.length, fromIndex, toIndex);
		for (int i = fromIndex; i < toIndex; i++)
			a[i] = val;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void fill(int[] a, int val) {
		fill(a, 0, a.length, val);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void fill(int[] a, int fromIndex, int toIndex, int val) {
		rangeCheck(a.length, fromIndex, toIndex);
		for (int i = fromIndex; i < toIndex; i++)
			a[i] = val;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void fill(short[] a, short val) {
		fill(a, 0, a.length, val);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void fill(short[] a, int fromIndex, int toIndex, short val) {
		rangeCheck(a.length, fromIndex, toIndex);
		for (int i = fromIndex; i < toIndex; i++)
			a[i] = val;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void fill(char[] a, char val) {
		fill(a, 0, a.length, val);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void fill(char[] a, int fromIndex, int toIndex, char val) {
		rangeCheck(a.length, fromIndex, toIndex);
		for (int i = fromIndex; i < toIndex; i++)
			a[i] = val;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void fill(byte[] a, byte val) {
		fill(a, 0, a.length, val);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void fill(byte[] a, int fromIndex, int toIndex, byte val) {
		rangeCheck(a.length, fromIndex, toIndex);
		for (int i = fromIndex; i < toIndex; i++)
			a[i] = val;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void fill(boolean[] a, boolean val) {
		fill(a, 0, a.length, val);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void fill(boolean[] a, int fromIndex, int toIndex, boolean val) {
		rangeCheck(a.length, fromIndex, toIndex);
		for (int i = fromIndex; i < toIndex; i++)
			a[i] = val;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void fill(double[] a, double val) {
		fill(a, 0, a.length, val);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void fill(double[] a, int fromIndex, int toIndex, double val) {
		rangeCheck(a.length, fromIndex, toIndex);
		for (int i = fromIndex; i < toIndex; i++)
			a[i] = val;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void fill(float[] a, float val) {
		fill(a, 0, a.length, val);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void fill(float[] a, int fromIndex, int toIndex, float val) {
		rangeCheck(a.length, fromIndex, toIndex);
		for (int i = fromIndex; i < toIndex; i++)
			a[i] = val;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void fill(Object[] a, Object val) {
		fill(a, 0, a.length, val);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void fill(Object[] a, int fromIndex, int toIndex, Object val) {
		rangeCheck(a.length, fromIndex, toIndex);
		for (int i = fromIndex; i < toIndex; i++)
			a[i] = val;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static <T> T[] copyOf(T[] original, int newLength) {
		return (T[]) copyOf(original, newLength, original.getClass());
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static <T, U> T[] copyOf(U[] original, int newLength, Class<? extends T[]> newType) {
		T[] copy = ((Object) newType == (Object) Object[].class)
				? (T[]) new Object[newLength]
				: (T[]) Array.newInstance(newType.getComponentType(), newLength);
		System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
		return copy;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static byte[] copyOf(byte[] original, int newLength) {
		byte[] copy = new byte[newLength];
		System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
		return copy;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static short[] copyOf(short[] original, int newLength) {
		short[] copy = new short[newLength];
		System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
		return copy;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static int[] copyOf(int[] original, int newLength) {
		int[] copy = new int[newLength];
		System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
		return copy;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static long[] copyOf(long[] original, int newLength) {
		long[] copy = new long[newLength];
		System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
		return copy;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static char[] copyOf(char[] original, int newLength) {
		char[] copy = new char[newLength];
		System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
		return copy;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static float[] copyOf(float[] original, int newLength) {
		float[] copy = new float[newLength];
		System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
		return copy;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static double[] copyOf(double[] original, int newLength) {
		double[] copy = new double[newLength];
		System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
		return copy;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static boolean[] copyOf(boolean[] original, int newLength) {
		boolean[] copy = new boolean[newLength];
		System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
		return copy;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static <T> T[] copyOfRange(T[] original, int from, int to) {
		return copyOfRange(original, from, to, (Class<T[]>) original.getClass());
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static <T, U> T[] copyOfRange(U[] original, int from, int to, Class<? extends T[]> newType) {
		int newLength = to - from;
		if (newLength < 0)
			throw new IllegalArgumentException(from + " > " + to);
		T[] copy = ((Object) newType == (Object) Object[].class)
				? (T[]) new Object[newLength]
				: (T[]) Array.newInstance(newType.getComponentType(), newLength);
		System.arraycopy(original, from, copy, 0, Math.min(original.length - from, newLength));
		return copy;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static byte[] copyOfRange(byte[] original, int from, int to) {
		int newLength = to - from;
		if (newLength < 0)
			throw new IllegalArgumentException(from + " > " + to);
		byte[] copy = new byte[newLength];
		System.arraycopy(original, from, copy, 0, Math.min(original.length - from, newLength));
		return copy;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static short[] copyOfRange(short[] original, int from, int to) {
		int newLength = to - from;
		if (newLength < 0)
			throw new IllegalArgumentException(from + " > " + to);
		short[] copy = new short[newLength];
		System.arraycopy(original, from, copy, 0, Math.min(original.length - from, newLength));
		return copy;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static int[] copyOfRange(int[] original, int from, int to) {
		int newLength = to - from;
		if (newLength < 0)
			throw new IllegalArgumentException(from + " > " + to);
		int[] copy = new int[newLength];
		System.arraycopy(original, from, copy, 0, Math.min(original.length - from, newLength));
		return copy;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static long[] copyOfRange(long[] original, int from, int to) {
		int newLength = to - from;
		if (newLength < 0)
			throw new IllegalArgumentException(from + " > " + to);
		long[] copy = new long[newLength];
		System.arraycopy(original, from, copy, 0, Math.min(original.length - from, newLength));
		return copy;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static char[] copyOfRange(char[] original, int from, int to) {
		int newLength = to - from;
		if (newLength < 0)
			throw new IllegalArgumentException(from + " > " + to);
		char[] copy = new char[newLength];
		System.arraycopy(original, from, copy, 0, Math.min(original.length - from, newLength));
		return copy;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static float[] copyOfRange(float[] original, int from, int to) {
		int newLength = to - from;
		if (newLength < 0)
			throw new IllegalArgumentException(from + " > " + to);
		float[] copy = new float[newLength];
		System.arraycopy(original, from, copy, 0, Math.min(original.length - from, newLength));
		return copy;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static double[] copyOfRange(double[] original, int from, int to) {
		int newLength = to - from;
		if (newLength < 0)
			throw new IllegalArgumentException(from + " > " + to);
		double[] copy = new double[newLength];
		System.arraycopy(original, from, copy, 0, Math.min(original.length - from, newLength));
		return copy;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static boolean[] copyOfRange(boolean[] original, int from, int to) {
		int newLength = to - from;
		if (newLength < 0)
			throw new IllegalArgumentException(from + " > " + to);
		boolean[] copy = new boolean[newLength];
		System.arraycopy(original, from, copy, 0, Math.min(original.length - from, newLength));
		return copy;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static <T> List<T> asList(T... a) {
		return new ArrayList<T>(a);
	}
	private static class ArrayList<E> extends AbstractList<E> implements RandomAccess, java.io.Serializable {
		private static final long serialVersionUID = -2764017481108945198L;
		private final E[] a;
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		ArrayList(E[] array) {
			if (array == null)
				throw new NullPointerException();
			a = array;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public int size() {
			return a.length;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Object[] toArray() {
			return a.clone();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public <T> T[] toArray(T[] a) {
			int size = size();
			if (a.length < size)
				return Arrays.copyOf(this.a, size, (Class<? extends T[]>) a.getClass());
			System.arraycopy(this.a, 0, a, 0, size);
			if (a.length > size)
				a[size] = null;
			return a;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public E get(int index) {
			return a[index];
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public E set(int index, E element) {
			E oldValue = a[index];
			a[index] = element;
			return oldValue;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public int indexOf(Object o) {
			if (o == null) {
				for (int i = 0; i < a.length; i++)
					if (a[i] == null)
						return i;
			} else {
				for (int i = 0; i < a.length; i++)
					if (o.equals(a[i]))
						return i;
			}
			return -1;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean contains(Object o) {
			return indexOf(o) != -1;
		}
	}
	public static int hashCode(long a[]) {
		if (a == null)
			return 0;
		int result = 1;
		for (long element : a) {
			int elementHash = (int) (element ^ (element >>> 32));
			result = 31 * result + elementHash;
		}
		return result;
	}
	public static int hashCode(int a[]) {
		if (a == null)
			return 0;
		int result = 1;
		for (int element : a)
			result = 31 * result + element;
		return result;
	}
	public static int hashCode(short a[]) {
		if (a == null)
			return 0;
		int result = 1;
		for (short element : a)
			result = 31 * result + element;
		return result;
	}
	public static int hashCode(char a[]) {
		if (a == null)
			return 0;
		int result = 1;
		for (char element : a)
			result = 31 * result + element;
		return result;
	}
	public static int hashCode(byte a[]) {
		if (a == null)
			return 0;
		int result = 1;
		for (byte element : a)
			result = 31 * result + element;
		return result;
	}
	public static int hashCode(boolean a[]) {
		if (a == null)
			return 0;
		int result = 1;
		for (boolean element : a)
			result = 31 * result + (element ? 1231 : 1237);
		return result;
	}
	public static int hashCode(float a[]) {
		if (a == null)
			return 0;
		int result = 1;
		for (float element : a)
			result = 31 * result + Float.floatToIntBits(element);
		return result;
	}
	public static int hashCode(double a[]) {
		if (a == null)
			return 0;
		int result = 1;
		for (double element : a) {
			long bits = Double.doubleToLongBits(element);
			result = 31 * result + (int) (bits ^ (bits >>> 32));
		}
		return result;
	}
	public static int hashCode(Object a[]) {
		if (a == null)
			return 0;
		int result = 1;
		for (Object element : a)
			result = 31 * result + (element == null ? 0 : element.hashCode());
		return result;
	}
	public static int deepHashCode(Object a[]) {
		if (a == null)
			return 0;
		int result = 1;
		for (Object element : a) {
			int elementHash = 0;
			if (element instanceof Object[])
				elementHash = deepHashCode((Object[]) element);
			else if (element instanceof byte[])
				elementHash = hashCode((byte[]) element);
			else if (element instanceof short[])
				elementHash = hashCode((short[]) element);
			else if (element instanceof int[])
				elementHash = hashCode((int[]) element);
			else if (element instanceof long[])
				elementHash = hashCode((long[]) element);
			else if (element instanceof char[])
				elementHash = hashCode((char[]) element);
			else if (element instanceof float[])
				elementHash = hashCode((float[]) element);
			else if (element instanceof double[])
				elementHash = hashCode((double[]) element);
			else if (element instanceof boolean[])
				elementHash = hashCode((boolean[]) element);
			else if (element != null)
				elementHash = element.hashCode();
			result = 31 * result + elementHash;
		}
		return result;
	}
	public static boolean deepEquals(Object[] a1, Object[] a2) {
		if (a1 == a2)
			return true;
		if (a1 == null || a2 == null)
			return false;
		int length = a1.length;
		if (a2.length != length)
			return false;
		for (int i = 0; i < length; i++) {
			Object e1 = a1[i];
			Object e2 = a2[i];
			if (e1 == e2)
				continue;
			if (e1 == null)
				return false;
			boolean eq;
			if (e1 instanceof Object[] && e2 instanceof Object[])
				eq = deepEquals((Object[]) e1, (Object[]) e2);
			else if (e1 instanceof byte[] && e2 instanceof byte[])
				eq = equals((byte[]) e1, (byte[]) e2);
			else if (e1 instanceof short[] && e2 instanceof short[])
				eq = equals((short[]) e1, (short[]) e2);
			else if (e1 instanceof int[] && e2 instanceof int[])
				eq = equals((int[]) e1, (int[]) e2);
			else if (e1 instanceof long[] && e2 instanceof long[])
				eq = equals((long[]) e1, (long[]) e2);
			else if (e1 instanceof char[] && e2 instanceof char[])
				eq = equals((char[]) e1, (char[]) e2);
			else if (e1 instanceof float[] && e2 instanceof float[])
				eq = equals((float[]) e1, (float[]) e2);
			else if (e1 instanceof double[] && e2 instanceof double[])
				eq = equals((double[]) e1, (double[]) e2);
			else if (e1 instanceof boolean[] && e2 instanceof boolean[])
				eq = equals((boolean[]) e1, (boolean[]) e2);
			else
				eq = e1.equals(e2);
			if (!eq)
				return false;
		}
		return true;
	}
	public static String toString(long[] a) {
		if (a == null)
			return "null";
		int iMax = a.length - 1;
		if (iMax == -1)
			return "[]";
		StringBuilder b = new StringBuilder();
		b.append('[');
		for (int i = 0;; i++) {
			b.append(a[i]);
			if (i == iMax)
				return b.append(']').toString();
			b.append(", ");
		}
	}
	public static String toString(int[] a) {
		if (a == null)
			return "null";
		int iMax = a.length - 1;
		if (iMax == -1)
			return "[]";
		StringBuilder b = new StringBuilder();
		b.append('[');
		for (int i = 0;; i++) {
			b.append(a[i]);
			if (i == iMax)
				return b.append(']').toString();
			b.append(", ");
		}
	}
	public static String toString(short[] a) {
		if (a == null)
			return "null";
		int iMax = a.length - 1;
		if (iMax == -1)
			return "[]";
		StringBuilder b = new StringBuilder();
		b.append('[');
		for (int i = 0;; i++) {
			b.append(a[i]);
			if (i == iMax)
				return b.append(']').toString();
			b.append(", ");
		}
	}
	public static String toString(char[] a) {
		if (a == null)
			return "null";
		int iMax = a.length - 1;
		if (iMax == -1)
			return "[]";
		StringBuilder b = new StringBuilder();
		b.append('[');
		for (int i = 0;; i++) {
			b.append(a[i]);
			if (i == iMax)
				return b.append(']').toString();
			b.append(", ");
		}
	}
	public static String toString(byte[] a) {
		if (a == null)
			return "null";
		int iMax = a.length - 1;
		if (iMax == -1)
			return "[]";
		StringBuilder b = new StringBuilder();
		b.append('[');
		for (int i = 0;; i++) {
			b.append(a[i]);
			if (i == iMax)
				return b.append(']').toString();
			b.append(", ");
		}
	}
	public static String toString(boolean[] a) {
		if (a == null)
			return "null";
		int iMax = a.length - 1;
		if (iMax == -1)
			return "[]";
		StringBuilder b = new StringBuilder();
		b.append('[');
		for (int i = 0;; i++) {
			b.append(a[i]);
			if (i == iMax)
				return b.append(']').toString();
			b.append(", ");
		}
	}
	public static String toString(float[] a) {
		if (a == null)
			return "null";
		int iMax = a.length - 1;
		if (iMax == -1)
			return "[]";
		StringBuilder b = new StringBuilder();
		b.append('[');
		for (int i = 0;; i++) {
			b.append(a[i]);
			if (i == iMax)
				return b.append(']').toString();
			b.append(", ");
		}
	}
	public static String toString(double[] a) {
		if (a == null)
			return "null";
		int iMax = a.length - 1;
		if (iMax == -1)
			return "[]";
		StringBuilder b = new StringBuilder();
		b.append('[');
		for (int i = 0;; i++) {
			b.append(a[i]);
			if (i == iMax)
				return b.append(']').toString();
			b.append(", ");
		}
	}
	public static String toString(Object[] a) {
		if (a == null)
			return "null";
		int iMax = a.length - 1;
		if (iMax == -1)
			return "[]";
		StringBuilder b = new StringBuilder();
		b.append('[');
		for (int i = 0;; i++) {
			b.append(String.valueOf(a[i]));
			if (i == iMax)
				return b.append(']').toString();
			b.append(", ");
		}
	}
	public static String deepToString(Object[] a) {
		if (a == null)
			return "null";
		int bufLen = 20 * a.length;
		if (a.length != 0 && bufLen <= 0)
			bufLen = Integer.MAX_VALUE;
		StringBuilder buf = new StringBuilder(bufLen);
		deepToString(a, buf, new HashSet());
		return buf.toString();
	}
	private static void deepToString(Object[] a, StringBuilder buf, Set<Object[]> dejaVu) {
		if (a == null) {
			buf.append("null");
			return;
		}
		dejaVu.add(a);
		buf.append('[');
		for (int i = 0; i < a.length; i++) {
			if (i != 0)
				buf.append(", ");
			Object element = a[i];
			if (element == null) {
				buf.append("null");
			} else {
				Class eClass = element.getClass();
				if (eClass.isArray()) {
					if (eClass == byte[].class)
						buf.append(toString((byte[]) element));
					else if (eClass == short[].class)
						buf.append(toString((short[]) element));
					else if (eClass == int[].class)
						buf.append(toString((int[]) element));
					else if (eClass == long[].class)
						buf.append(toString((long[]) element));
					else if (eClass == char[].class)
						buf.append(toString((char[]) element));
					else if (eClass == float[].class)
						buf.append(toString((float[]) element));
					else if (eClass == double[].class)
						buf.append(toString((double[]) element));
					else if (eClass == boolean[].class)
						buf.append(toString((boolean[]) element));
					else {
						if (dejaVu.contains(element))
							buf.append("[...]");
						else
							deepToString((Object[]) element, buf, dejaVu);
					}
				} else {
					buf.append(element.toString());
				}
			}
		}
		buf.append(']');
		dejaVu.remove(a);
	}
}
