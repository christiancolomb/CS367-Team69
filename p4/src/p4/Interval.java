
///////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2017 
// PROJECT:          p4
// FILE:             Interval.java
//						
// Authors: Albert Liu, Christian Colomb, Alonso Del Rio, Kyra Dahl,
//			Weidi Dai, Tavishi Gupta
//
// Author1: Albert Liu, 		liu668@wisc.edu,	liu668, 	002
// Author2: Christian Colomb, 		ccolomb@wisc.edu, 	ccolomb, 	002
// Author3: Alonso Del Rio, 		adelrio@wisc.edu, 	adelrio, 	002
// Author4: Kyra Dahl, 			krdahl2@wisc.edu, 	krdahl2, 	002
// Author5: Weidi Dai, 			wdai38@wisc.edu, 	wdai38, 	002
// Author6: Tavishi Gupta, 		tgupta24@wisc.edu, 	tgupta24, 	002
// 
///////////////////////////////////////////////////////////////////////////////

public class Interval<T extends Comparable<T>> implements IntervalADT<T> {

	// start, end, and label of the interval
	private T start;
	private T end;
	private String label;

	/**
	 * Constructor for an interval given the start, end, and label of the
	 * interval
	 * 
	 * @param start
	 *            of the interval
	 * @param end
	 *            of the interval
	 * @param label
	 *            of the interval
	 */
	public Interval(T start, T end, String label) {
		this.start = start;
		this.end = end;
		this.label = label;
	}

	@Override
	/** Returns the start value (must be Comparable<T>) of the interval. */
	public T getStart() {
		return start;
	}

	@Override
	/** Returns the end value (must be Comparable<T>) of the interval. */
	public T getEnd() {
		return end;
	}

	@Override
	/** Returns the label for the interval. */
	public String getLabel() {
		return label;
	}

	@Override
	/**
	 * Return true if this interval overlaps with the other interval.
	 * 
	 * @param other
	 *            target interval to compare for overlap
	 * @return true if it overlaps, false otherwise.
	 * @throws IllegalArgumentException
	 *             if the other interval is null.
	 */
	public boolean overlaps(IntervalADT<T> other) {
		if (start.compareTo(other.getEnd()) > 0 || end.compareTo(other.getStart()) < 0)
			return false;
		return true;
	}

	@Override
	/**
	 * Returns true if given point lies inside the interval.
	 * 
	 * @param point
	 *            to search
	 * @return true if it contains the point
	 */
	public boolean contains(T point) {
		return (point.compareTo(start) >= 0) && (point.compareTo(end) <= 0);
	}

	@Override
	/**
	 * Compares this interval with the other and return a negative value if this
	 * interval comes before the "other" interval. Intervals are compared first
	 * on their start time. The end time is only considered if the start time is
	 * the same.
	 *
	 * @param other
	 *            the second interval to which compare this interval with
	 * 
	 * @return negative if this interval's comes before the other interval,
	 *         positive if this interval comes after the other interval, and 0
	 *         if the intervals are the same. See above for details.
	 */
	public int compareTo(IntervalADT<T> other) {
		if (start.compareTo(other.getStart()) == 0)
			return end.compareTo(other.getEnd());
		return start.compareTo(other.getStart());
	}

	@Override
	/**
	 * Returns a specific string representation of the interval. It must return
	 * the interval in this form.
	 * 
	 * @return a string representation of the interval
	 */
	public String toString() {
		return label + " [" + start + ", " + end + "]";
	}
}
