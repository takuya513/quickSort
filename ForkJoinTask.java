package quickSort;

import java.util.concurrent.RecursiveAction;
/*
 * fork/join
 */
public class ForkJoinTask <E extends Comparable> extends RecursiveAction{
	private E[] array;
	private int left,right;
	ForkJoinTask(E[] array,int left,int right){
		this.array = array;
		this.left = left;
		this.right = right;
	}

	public void compute(){

		if(right <= left)
			return;

		int i = partition();

		ForkJoinTask<E> leftTask = new ForkJoinTask(array,left,i - 1);
		ForkJoinTask<E> rightTask = new ForkJoinTask(array,i + 1,right);

		rightTask.fork();
		leftTask.compute();
		rightTask.join();
	}

	public int partition(){
		int i = left - 1, j = right;

		E pivot  = array[right];
		while(true){
			do{
				i++;
			}while(array[i].compareTo(pivot) < 0);
			do{
				j--;
				if(j < left) break;
			}while(pivot.compareTo(array[j]) < 0);
			if(i >= j) break;
			swap(i,j);
		}
		swap(i,right);
		return i;
	}

	public void swap(int i,int j){
		E temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
}
