
public class myTests {

	public static void main(String[] args) {
		/** Interval class method tests **/
//		// testIntervalOverlaps();
//		testIntervalContains();
//		testIntervalCompareTo();
//		testIntervalToString();
		/** IntervalNode class method tests **/
//		testIntervalNodeGetSuccessor();
		/** IntervalTree class method tests **/
//		testIntervalTreeFindOverlapping();
//		testIntervalTreeSearchPoint();
//		testIntervalTreeGetSize();
//		testIntervalTreeGetHeight();
		testIntervalTreeDelete();
		testIntervalTreeInsert();
	}

	public static void testIntervalOverlaps() {
		// test 1
		Interval interval1 = new Interval(5, 10, "1");
		Interval interval2 = new Interval(7, 12, "2");
		System.out.println("Test 1: true = " + interval1.overlaps(interval2));
		// test 2
		Interval interval3 = new Interval(5, 10, "3");
		Interval interval4 = new Interval(11, 12, "4");
		System.out.println("Test 2: false = " + interval3.overlaps(interval4));
	}

	public static void testIntervalContains() {
		// test 1
		Interval interval1 = new Interval(5, 10, "1");
		System.out.println("Test 1: true = " + interval1.contains(6));
		// test 2
		Interval interval2 = new Interval(5, 10, "2");
		System.out.println("Test 2: false = " + interval2.contains(2));
		// test 3
		Interval interval3 = new Interval(5, 10, "3");
		System.out.println("Test 3: false = " + interval3.contains(11));
		// test 4
		Interval interval4 = new Interval(5, 10, "4");
		System.out.println("Test 4: true = " + interval4.contains(5));
	}

	public static void testIntervalCompareTo() {
		// test 1
		Interval interval1 = new Interval(5, 10, "1");
		Interval interval2 = new Interval(7, 12, "2");
		System.out.println("Test 1: -1 = " + interval1.compareTo(interval2));
		// test 2
		Interval interval3 = new Interval(12, 14, "3");
		Interval interval4 = new Interval(11, 12, "4");
		System.out.println("Test 2: 1 = " + interval3.compareTo(interval4));
		// test 3
		Interval interval5 = new Interval(12, 14, "5");
		Interval interval6 = new Interval(12, 16, "6");
		System.out.println("Test 3: -1 = " + interval5.compareTo(interval6));
		// test 4
		Interval interval7 = new Interval(12, 14, "7");
		Interval interval8 = new Interval(12, 14, "8");
		System.out.println("Test 4: 0 = " + interval7.compareTo(interval8));
	}

	public static void testIntervalToString() {
		Interval interval1 = new Interval(5, 10, "1");
		System.out.println(interval1.toString());
	}

	public static void testIntervalNodeGetSuccessor() {
		IntervalTree tree = new IntervalTree();
		Interval interval1 = new Interval(5, 10, "1");
		tree.insert(interval1);
		Interval interval2 = new Interval(7, 12, "2");
		tree.insert(interval2);
		Interval interval3 = new Interval(2, 12, "3");
		tree.insert(interval3);
		Interval interval4 = new Interval(9, 10, "4");
		tree.insert(interval4);
		System.out.println(tree.getRoot().getSuccessor().getSuccessor().getInterval().toString());
	}
	
	public static void testIntervalTreeFindOverlapping() {
		Interval interval1 = new Interval(5, 10, "1");
		Interval interval2 = new Interval(7, 12, "2");
		Interval interval3 = new Interval(2, 12, "3");
		Interval interval4 = new Interval(9, 10, "4");
		IntervalTree tree = new IntervalTree();
		tree.insert(interval1);
		tree.insert(interval2);
		tree.insert(interval3);
		tree.insert(interval4);
		Interval interval5 = new Interval(1, 6, "5");
		System.out.println("2: " + tree.findOverlapping(interval5));
		Interval interval6 = new Interval(11, 16, "6");
		System.out.println("2: " + tree.findOverlapping(interval6));
	}
	
	public static void testIntervalTreeSearchPoint() {
		Interval interval1 = new Interval(5, 10, "1");
		Interval interval2 = new Interval(7, 12, "2");
		Interval interval3 = new Interval(2, 12, "3");
		Interval interval4 = new Interval(9, 10, "4");
		IntervalTree tree = new IntervalTree();
		tree.insert(interval1);
		tree.insert(interval2);
		tree.insert(interval3);
		tree.insert(interval4);
		System.out.println("3: " + tree.searchPoint(8));
		System.out.println("2: " + tree.searchPoint(6));
		System.out.println("4: " + tree.searchPoint(10));
		System.out.println("0: " + tree.searchPoint(1));
	}
	
	public static void testIntervalTreeGetSize() {
		Interval interval1 = new Interval(5, 10, "1");
		Interval interval2 = new Interval(7, 12, "2");
		Interval interval3 = new Interval(2, 12, "3");
		Interval interval4 = new Interval(9, 10, "4");
		Interval interval5 = new Interval(12, 17, "5");
		Interval interval6 = new Interval(1, 10, "6");
		IntervalTree tree = new IntervalTree();
		tree.insert(interval1);
		tree.insert(interval2);
		tree.insert(interval3);
		tree.insert(interval4);
		tree.insert(interval5);
		tree.insert(interval6);
		System.out.println("4 = " + tree.getSize());
	}
	
	public static void testIntervalTreeGetHeight() {
		Interval interval1 = new Interval(5, 10, "1");
		Interval interval2 = new Interval(7, 12, "2");
		Interval interval3 = new Interval(2, 12, "3");
		Interval interval4 = new Interval(9, 10, "4");
		Interval interval5 = new Interval(13, 15, "5");
		IntervalTree tree = new IntervalTree();
		tree.insert(interval1);
		tree.insert(interval2);
		tree.insert(interval3);
		tree.insert(interval4);
		tree.insert(interval5);
		System.out.println("4 = " + tree.getHeight());
	}
	
	public static void testIntervalTreeDelete() {
		IntervalTree tree = new IntervalTree();
		Interval interval1 = new Interval(5, 10, "1");
		Interval interval2 = new Interval(7, 12, "2");
		Interval interval3 = new Interval(2, 12, "3");
		tree.insert(interval1);
		tree.insert(interval2);
		tree.insert(interval3);
		try {
			tree.delete(interval2);
		} catch (IllegalArgumentException e) {
			System.out.println("test 1");
		} catch (IntervalNotFoundException e) {
			System.out.println("test 2");
		}
		System.out.println("2 = " + tree.getSize());
	}
	
	public static void testIntervalTreeInsert() {
		IntervalTree tree = new IntervalTree();
		Interval interval1 = new Interval(5, 10, "1");
		Interval interval2 = new Interval(7, 12, "2");
		Interval interval3 = new Interval(2, 12, "3");
		tree.insert(interval1);
		tree.insert(interval2);
		tree.insert(interval3);
		System.out.println("3 = " + tree.getSize());
	}
	
}
