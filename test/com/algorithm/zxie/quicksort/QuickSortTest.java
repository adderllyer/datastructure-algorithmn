package com.algorithm.zxie.quicksort;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class QuickSortTest {

	QuickSort qs;
	long[] array = {49, 38, 65, 97, 76, 13, 27, 49};
	
	@Before
	public void setUp() throws Exception {		
		qs = new QuickSort(array, 0, array.length-1);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testSort() {
		qs.sort(array, 0, array.length-1);
		System.out.println(Arrays.toString(array));
		assertTrue(checkSorted(array)); 
	}
	
	boolean checkSorted(long[] a) {
		for (int i = 0; i < a.length - 1; i++) {
			if (a[i] > (a[i + 1])) {
				return false;
			}
		}
		return true;
	}

}
