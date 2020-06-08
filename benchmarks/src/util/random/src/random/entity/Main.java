package random.entity;

public class Main{
    public static void main(String[] args) {
        Random random = new Random();
        Random random1 = new Random(System.currentTimeMillis());
        random.setSeed(System.currentTimeMillis());
        random.nextInt();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        random.nextInt(1);
        random.nextLong();
        random.nextBoolean();
        random.nextFloat();
        random.nextDouble();
        random.nextGaussian();

    }
}