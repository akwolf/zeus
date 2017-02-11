package org.zunpeng.math;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.junit.Test;

/**
 * Created by dapeng on 2016/11/24.
 */
public class MathTest {

	@Test
	public void demo(){
		double[] inputArray = new double[]{0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9};

		DescriptiveStatistics stats = new DescriptiveStatistics();

		for( int i = 0; i < inputArray.length; i++) {
			stats.addValue(inputArray[i]);
		}

		double mean = stats.getMean();
		double std = stats.getStandardDeviation();
		double median = stats.getPercentile(50);

		System.out.println(mean);
		System.out.println(std);
		System.out.println(median);
	}
}
