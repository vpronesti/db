package util;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class DistanzaEuclidea {
    public static float distanza(float pAx, float pAy, float pBx, float pBy) {
        float d1 = pAx - pBx;
        float d2 = pAy - pBy;
        return (float) sqrt(pow(d1, 2) + pow(d2, 2));
    }
}
