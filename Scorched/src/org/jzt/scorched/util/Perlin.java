package org.jzt.scorched.util;

/**
 * Author: jon tucker
 * Date: 12/16/12
 * Time: 4:59 PM
 */
public class Perlin {

  private static double noise(int x) {
    x = (x << 13) ^ x;
    return ( 1.0 - ( (x * (x * x * 15731 + 789221) + 1376312589) & 0x7fffffff) / 1073741824.0);
  }

  private static double linearInterpolate(double x1, double x2, double x3, double x4) {
    return 0;
  }

  private static double cosineInterpolate(double a, double b, double x) {
    double ft = x * Math.PI;
    double f = (1 - Math.cos(ft)) * .5;
    return a * (1 - f) + b * f;
  }

  private static double cubicInterpolate(double v0, double v1, double v2, double v3, double x) {
    double P = (v3 - v2) - (v0 - v1);
    double Q = (v0 - v1) - P;
    double R = v2 - v0;
    return P*Math.pow(x, 3) + Q*Math.pow(x, 2) + R*x + v1;
  }

  private static double smoothNoise(int x) {
    return noise(x)/2 + noise(x-1)/4 + noise(x + 1)/4;
  }

  private static double interpolatedNoise(double x) {
    int ix = (int) x;
    double fx = x - ix;
    double v1 = smoothNoise(ix);
    double v2 = smoothNoise(ix + 1);
    return cosineInterpolate(v1, v2, fx);
  }

  public static float[] perlin(int width, int height, int delta, double persistence, int octaves) {

    float [] result = new float[width/delta];

    for (int i = 0; i < result.length; ++i) {
        float total = 0;
        for (int j = 0; j < octaves - 1; ++j) {
        double frequency = Math.pow(2, j);
        double amplitude = Math.pow(persistence, j);  // TODO: perhaps base this on height - check after testing
        total += interpolatedNoise(i * frequency) * amplitude;
      }
      result[i] = total;
    }
    return result;
  }
}
