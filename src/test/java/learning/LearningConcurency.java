package learning;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: uri
 * Date: 5/27/14
 * Time: 5:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class LearningConcurency {

    @Test
    public void findMaxLinear() {
        int[] vals = new int[]{10,3,-17,500,6};
        assertThat(findMax(vals),is(500));
    }

    private int findMax(int[] vals) {
        int maximum=vals[0];
        for (int i = 0; i < vals.length; i++) {
            int val = vals[i];
            if (val > maximum) {
                maximum = val;
            }
        }
        return maximum;  //To change body of created methods use File | Settings | File Templates.
    }

    @Test
       public void findMinLinear() {
           int[] vals = new int[]{10,3,-17,500,6};
           assertThat(findMin(vals),is(-17));
       }

       private int findMin(int[] vals) {
           int minimum=vals[0];
           for (int i = 0; i < vals.length; i++) {
               int val = vals[i];
               if (val < minimum) {
                   minimum = val;
               }
           }
           return minimum;  //To change body of created methods use File | Settings | File Templates.
       }

    @Test
    public void findMeanLinear(){
        double[] vals  = new double[]{10, 3, -17, 500, 6};
        assertThat(findMean(vals), is(100.4));
    }

    private Double findMean(double[] vals) {
        double sum =0;
        for (int i = 0; i< vals.length; i++){
            double val = vals[i];
            sum += val;
        }
        return Double.valueOf(sum/vals.length);

    }
}


