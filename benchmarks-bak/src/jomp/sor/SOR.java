package jomp.sor.withlock;
import jomp.jgfutil.JGFInstrumentor;
import jomp.jgfutil.*;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class SOR {
	public static ReentrantReadWriteLock GtotalLock = new ReentrantReadWriteLock();
	public static double Gtotal = 0.0;
	@Perm(requires = "full(Gtotal) in alive", ensures = "full(Gtotal) in alive")
	public static final void SORrun(int num_iterations, double G[][], double omega) {
		int M = G.length;
		int N = G[0].length;
		double omega_over_four = omega * 0.25;
		double one_minus_omega = 1.0 - omega;
		int Mm1 = M - 1;
		int Nm1 = N - 1;
		for (int p = 1; p < (2 * num_iterations + 1); p++) {
			for (int i = 1; i < M; i++) {
				if ((i % 2) == (p % 2)) {
					double[] Gi = G[i];
					double[] Gim1 = G[i - 1];
					if (i == 1) {
						double[] Gip1 = G[i + 1];
						for (int j = 1; j < Nm1; j = j + 2) {
							Gi[j] = omega_over_four * (Gim1[j] + Gip1[j] + Gi[j - 1] + Gi[j + 1])
									+ one_minus_omega * Gi[j];
						}
					} else if (i == Mm1) {
						double[] Gim2 = G[i - 2];
						for (int j = 1; j < Nm1; j = j + 2) {
							if ((j + 1) != Nm1) {
								Gim1[j + 1] = omega_over_four * (Gim2[j + 1] + Gi[j + 1] + Gim1[j] + Gim1[j + 2])
										+ one_minus_omega * Gim1[j + 1];
							}
						}
					} else {
						double[] Gip1 = G[i + 1];
						double[] Gim2 = G[i - 2];
						for (int j = 1; j < Nm1; j = j + 2) {
							Gi[j] = omega_over_four * (Gim1[j] + Gip1[j] + Gi[j - 1] + Gi[j + 1])
									+ one_minus_omega * Gi[j];
							if ((j + 1) != Nm1) {
								Gim1[j + 1] = omega_over_four * (Gim2[j + 1] + Gi[j + 1] + Gim1[j] + Gim1[j + 2])
										+ one_minus_omega * Gim1[j + 1];
							}
						}
					}
				}
			}
		}
		for (int i = 1; i < Nm1; i++) {
			for (int j = 1; j < Nm1; j++) {
				GtotalLock.writeLock().lock();
				Gtotal += G[i][j];
				GtotalLock.writeLock().unlock();
			}
		}
	}
}
