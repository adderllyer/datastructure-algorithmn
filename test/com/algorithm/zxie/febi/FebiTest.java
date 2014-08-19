package com.algorithm.zxie.febi;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FebiTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testCalcFebi() {
//		for(int i=0; i<=100; i++)
			org.junit.Assert.assertEquals(Febi.calcFebi(100), Febi.calcFebiDP(100));
	}

	/*
	@Test
	public final void testCalcFebiDP() {
		long t1,t2,t3,t4;
		for(int i=0; i<=100; i++){
			t1 = System.currentTimeMillis();
			Febi.calcFebi(i);
			t2 = System.currentTimeMillis();
			t3 = System.currentTimeMillis();
			Febi.calcFebiDP(i);			
			t4 = System.currentTimeMillis();
			org.junit.Assert.assertTrue((t2-t1) >= (t4 - t3));
		}
	}
	*/

}
