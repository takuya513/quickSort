package quickSort;

import java.util.concurrent.ForkJoinPool;

public class QuickSort5<E extends Comparable> extends QuickSort<E>{
	ForkJoinPool pool;
	public QuickSort5() {
		pool = new ForkJoinPool();
	}

	public void sort(E[] array){
		this.array = array;
		pool.invoke(new QuickSortTask5(array,0,array.length-1));
	}


}
