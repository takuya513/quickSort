package quickSort;

import java.util.concurrent.RecursiveAction;
/*
 * fork/join
 */
public class QuickSortTask5 <E extends Comparable> extends RecursiveAction{
	private E[] array;
	private int left,right;
	QuickSortTask5(E[] array,int left,int right){
		this.array = array;
		this.left = left;
		this.right = right;
	}

	public void compute(){

		if(right <= left)
			return;

		int i = partition();

		QuickSortTask5<E> leftTask = new QuickSortTask5(array,left,i - 1);
		QuickSortTask5<E> rightTask = new QuickSortTask5(array,i + 1,right);

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
