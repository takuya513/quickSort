package quickSort;

import tools.Sortable;

/*
 * 二つのスレッドのみ使用
 */
public class QuickSort2<E extends Comparable> extends QuickSort<E> {

	public QuickSort2(){
	}


	@Override
	public void quickSort(int left,int right){

		int i = partition(left,right);
		Thread leftSort = new Thread(new QuickSortTask<E>(array,left,i-1));
		Thread rightSort = new Thread(new QuickSortTask<E>(array,i+1,right));
		leftSort.start();
		rightSort.start();

//		System.out.println("現在アクティブなスレッド数　： "+leftSort.activeCount());
		try {
			leftSort.join();
			rightSort.join();
		} catch (InterruptedException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

}
