package quickSort;

import sort.Sort;
import tools.MyArrayUtil;
import tools.Sortable;
/*
 *
 */
public class QuickSort<E extends Comparable> implements Sort<E>{
	protected E[] array;
	private final int SMALL = 10;
	public QuickSort(){
	}

	public  void sort(E[] array){
		this.array = array;
		quickSort(0,array.length - 1);
	}
	@SuppressWarnings("unchecked")
	public int partition(int left,int right){
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

	public void insertSort(int left,int right){
		int i,j;
		E temp;
		for(i = left + 1; i <=right;i++) {
			temp = array[i];
			j = i;
			while(j > left && temp.compareTo(array[j-1])< 0){
				array[j] = array[j-1];
				j--;
			}
			array[j] = temp;
		}
	}

	public void quickSort(int left,int right){
		int i;
		if(right <= left) return;

		if(right <= left + SMALL)
			insertSort(left,right);
		else{
			i = partition(left,right);
			quickSort(left, i - 1);
			quickSort(i + 1 , right);
		}
	}


}
