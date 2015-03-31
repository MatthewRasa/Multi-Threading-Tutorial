import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DriverFinal {
	
	private final static int N = 10000000; // 10 million elements(adjust according to computer)
	private static List<Integer> list, sub0, sub1, sub2;
	private static byte doneCount;
	
	public static void main(String[] args) {
		list = new ArrayList<Integer>();
		sub0 = new ArrayList<Integer>();
		sub1 = new ArrayList<Integer>();
		sub2 = new ArrayList<Integer>();
		doneCount = 0;
		
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
		new Thread(new Runnable() {
			public void run() {sub0 = mergeSort(list.subList(0, list.size()/4));doneCount++;}
		}).start();
		new Thread(new Runnable() {
			public void run() {sub1 = mergeSort(list.subList(list.size()/4, list.size()/2));doneCount++;}
		}).start();
		new Thread(new Runnable() {
			public void run() {sub2 = mergeSort(list.subList(list.size()/2, 3*list.size()/4));doneCount++;}
		}).start();
		list = (ArrayList<Integer>)mergeSort(list.subList(3*list.size()/4, list.size()));
		doneCount++;
		
		while(doneCount < 4) { // Wait for the other 3 threads to catch up
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		list = merge(merge(sub0,sub1),merge(sub2,list)); // Merge all sublists
		System.out.println("done in " + (System.currentTimeMillis()-timeLast)/1000.0 + " sec.");
		System.out.println(list.subList(0, 100)); // Print first 100 elements
		System.out.println(list.subList(list.size()/4, list.size()/4 + 100)); // Print 100 mid elements
		System.out.println(list.subList(list.size()/2, list.size()/2 + 100)); // Print more mid elements
		System.out.println(list.subList(list.size() - 100, list.size())); // Print last 100 elements
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
