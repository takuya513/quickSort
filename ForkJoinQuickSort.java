package quickSort;

import java.util.concurrent.ForkJoinPool;

public class ForkJoinQuickSort<E extends Comparable> extends QuickSort<E>{
	ForkJoinPool pool;
	public ForkJoinQuickSort() {
		
	}

	public void sort(E[] array){
		this.array = array;
		pool = new ForkJoinPool();
		pool.invoke(new ForkJoinTask(array,0,array.length-1));
	}


}
