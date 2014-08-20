package com.algorithm.zxie.tree.heap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BinaryHeapTest {
	BinaryHeap<Integer> bh;

	@Before
	public void setUp() throws Exception {
		/*
		bh = new BinaryHeap<>();

		bh.add(new Integer(99));
		bh.add(new Integer(80));
		bh.add(new Integer(40));
		bh.add(new Integer(78));
		bh.add(new Integer(3));
		bh.add(new Integer(45));
		bh.add(new Integer(34));
		bh.add(new Integer(55));
		bh.add(new Integer(80));
		bh.add(new Integer(31));
		bh.add(new Integer(21));*/
		Integer[] heap = {new Integer(4), new Integer(1) ,new Integer(3), new Integer(2),new Integer(16),new Integer(9),new Integer(10),new Integer(14),new Integer(8),new Integer(7)};
		bh = new BinaryHeap<>(heap);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testAdd() {
		bh.add(2);
		org.junit.Assert.assertEquals(bh.peek(), new Integer(1));
	}

	@Test
	public final void testPeek() {
		org.junit.Assert.assertEquals(bh.peek(), new Integer(1));
	}

	@Test
	public final void testRemove() {
		org.junit.Assert.assertEquals(bh.remove(), new Integer(1));
		org.junit.Assert.assertEquals(bh.peek(), new Integer(2));
	}

}
