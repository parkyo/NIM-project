import java.util.Arrays;

/**
 * This class provides a range of utility methods for computing
 * mean, median, max, min, variance, standard deviation and root mean square
 * values from arrays.
 * 
 * @author Grant Braught
 * @author Dickinson College
 * @version May 3, 2005
 */
public class BasicStatistics {

    /**
     * Compute the mean of the values in arr.
     * 
     * @param arr array of doubles.
     * @return the mean of the values in arr.
     */
    public static double getMean(double[] arr) {
        double total = 0;
        for (int i=0; i<arr.length; i++) {
            total = total + arr[i];
        }
        return total / arr.length;
    }

    /**
     * Find the maximum of the values in arr.
     * 
     * @param arr array of doubles.
     * @return the maximum value in arr.
     */
    public static double getMax(double[] arr) {
        int maxIndex = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > arr[maxIndex]) {
                maxIndex = i;
            }
        }
        return arr[maxIndex];
    }
    
    /**
     * Find the minimum of the values in arr.
     * 
     * @param arr array of doubles.
     * @return the minimum value in arr.
     */
    public static double getMin(double[] arr) {
        int minIndex = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[minIndex]) {
                minIndex = i;
            }
        }
        return arr[minIndex];
    }
    
    /**
     * Find the median of the values in arr.
     * 
     * @param arr array of doubles.
     * @return the medain of the values in arr.
     */
    public static double getMedian(double[] arr) {
        double[] arrClone = new double[arr.length];
        System.arraycopy(arr,0,arrClone,0,arr.length);
        Arrays.sort(arrClone);
        return arrClone[(int) Math.round(arrClone.length / 2.0)];
    }
    
    /**
     * Find the variance of the values in arr. If the mean of the values
     * is known use this method to avoid recalculating the mean when
     * computing the variance.
     * 
     * @param arr an array of doubles.
     * @param mean the mean of the values in arr.
     * @return the variance of the values in arr.
     */
    public static double getVariance(double[] arr, double mean) {
        double totalDev = 0;
        
        for (int i=0; i<arr.length; i++) {
            totalDev = totalDev + (mean - arr[i]) * (mean - arr[i]);
        }

        // Sample estimate of variance so divide by n-1.
        return totalDev / (arr.length - 1);
    }
    
    /**
     * Find the variance of the values in arr. This method first computes
     * the mean of the values in arr and then calls the two arg getVariance method.
     * So if the mean value is already known just call the two arg getVariance
     * method directly to prevent recalculation of the mean.
     * 
     * @param arr array of doubles.
     * @return the variance of the values in arr.
     */
    public static double getVariance(double[] arr) {
        return getVariance(arr, getMean(arr));
    }
    
    /**
     * Compute the standard deviation associated with the given variance.  Note
     * that this function just computes the square root of the variance and is
     * only included for the sake of completeness.
     * 
     * @param variance a variance
     * @return the associated standard deviation.
     */
    public static double getStdDev(double variance) {
        return Math.sqrt(variance);
    }
    
    /**
     * Compute the standard deviation of the values in arr. Note that this method
     * first computes the variance of the values in arr and then calls the
     * getStdDev(double) method with the result.  If the variance is already known
     * then call getStdDev(double) directly to avoid recomputing the variance.
     * 
     * @param arr array of doubles.
     * @return the standard deviation of the values in arr.
     */
    public static double getStdDev(double[] arr) {
        return getStdDev(getVariance(arr));
    }
    
    /**
     * Compute the RMS difference between the values in arr1 and arr2. The values 
     * in arr1 and arr2 are assumed to be paired. That is the values in arr1[j] 
     * corresponds to the value in aar2[j]. Thus the result of this method is the 
     * square root of the mean of the squared differences between aar1[j] and aar2[j]
     * for all j.  aar1 and aar2 must be the same length or this method will
     * throw an IllegalArgumentException.
     * 
     * @param arr1 array of doubles.
     * @param arr2 array of doubles.
     * @return the RMS difference between the values in arr1 and the values
     * in arr2.
     * @throws IllegalArgumentException if arr1.length != arr2.length. 
     */
    public static double getRMS(double[] arr1, double[] arr2) {
        if (arr1.length != arr2.length) {
            throw new IllegalArgumentException("getRMSDifference: aar1 and aar2 " +
                    "must be the same length.");
        }
        
        double totalSqDiff = 0;
        for (int i=0; i<arr1.length; i++) {
            double diff = (arr1[i] - arr2[i]);
            totalSqDiff += diff*diff;
        }
        
        return Math.sqrt(totalSqDiff / arr1.length);
    }
}
