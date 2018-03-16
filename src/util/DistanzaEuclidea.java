package util;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class DistanzaEuclidea {
    public static double distanza(double pAx, double pAy, double pBx, double pBy) {
        double d1 = pAx - pBx;
        double d2 = pAy - pBy;
        return sqrt(pow(d1, 2) + pow(d2, 2));
    }
}
