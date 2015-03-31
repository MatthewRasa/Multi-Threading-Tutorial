import java.util.*;

public class Driver {
	
	private final static int N = 10000000; // 10 million elements(adjust according to computer)
	
	public static void main(String[] args) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		//---Fill list with random elements---//
		
		System.out.print("Loading...");
		long timeLast = System.currentTimeMillis(); // Record start time
		Random rand = new Random();
		for (int index = 0; index < N; index++)
			list.add(rand.nextInt(N));
		System.out.println("done in " + (System.currentTimeMillis()-timeLast)/1000.0 + " sec.");
		
		//--Sort list---//
		
		System.out.print("Sorting...");
		timeLast = System.currentTimeMillis(); // Record start time
		list = (ArrayList<Integer>)mergeSort(list);
		System.out.println("done in " + (System.currentTimeMillis()-timeLast)/1000.0 + " sec.");
		System.out.println(list.subList(0, 100)); // Print first 100 elements
	}
	
	/**
	 * Fast, nlog_2(n) sorting algorithm.
	 * @param list - List of Comparable elements to sort
	 * @return the sorted list of Comparable objects
	 */
	private static <T extends Comparable<T>> List<T> mergeSort(List<T> list) {
		int mid = list.size()/2;
		return list.size() == 1 ? list : merge(mergeSort(list.subList(0, mid)),
			mergeSort(list.subList(mid, list.size())));
	}
	
	private static <T extends Comparable<T>> List<T> merge(List<T> list0, List<T> list1) {
		List<T> result = new ArrayList<T>();
		int index0 = 0,
			index1 = 0;
		while(true) {
			if (index0 == list0.size() && index1 == list1.size())
				break;
			else if (index1 == list1.size()) {
				result.add(list0.get(index0));
				index0++;
			} else if (index0 == list0.size()) {
				result.add(list1.get(index1));
				index1++;
			} else if (list0.get(index0).compareTo(list1.get(index1)) <= 0) {
				result.add(list0.get(index0));
				index0++;
			} else {
				result.add(list1.get(index1));
				index1++;
			}
		}
		return result;
	}
	
}
