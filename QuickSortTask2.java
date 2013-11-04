package quickSort;

import tools.MyArrayUtil;
import tools.Sortable;

public class QuickSortTask2<E extends Comparable> extends QuickSort<E> implements Runnable{

	protected int left;
	protected int right;
	protected static int minThreadFactor = 3;

	public QuickSortTask2(E[] array,int left,int right){
//		super(array);
		this.array = array;
		this.left = left;
		this.right = right;
		//minThreadFactor = array.length / 4; //この変数以上の要素のﾀｽｸがある場合子スレッドを作成する
	}

	@Override
	public void run(){
		sort2();
	}

	public synchronized void sort2(){
		int i,leftArrayNum,rightArrayNum;
		System.out.println("現在アクティブなスレッド数　： "+Thread.currentThread().activeCount()); //修正
		try {
			if(right > left){
				i = partition(left,right);
				leftArrayNum = i - left; rightArrayNum = right - i;

				if(rightArrayNum > minThreadFactor && leftArrayNum > minThreadFactor){
					Thread leftSort = new Thread(new QuickSortTask2(array,left,i - 1));
					Thread rightSort = new Thread(new QuickSortTask2(array,i + 1,right));
					leftSort.start();
					rightSort.start();

					leftSort.join();
					rightSort.join();

				}else if(leftArrayNum > minThreadFactor){
					Thread leftSort = new Thread(new QuickSortTask2(array,left,i - 1));
					leftSort.start();
					quickSort(i + 1, right);

					leftSort.join();

				}else if(rightArrayNum > minThreadFactor){
					Thread rightSort = new Thread(new QuickSortTask2(array,i + 1,right));
					rightSort.start();
					quickSort(left, i - 1);

					rightSort.join();

				}else{
					quickSort(left, i - 1);
					quickSort(i + 1 , right);
				}
			}
		} catch (InterruptedException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	@Override
	public synchronized void swap(int i,int j){
		super.swap(i, j);
	}
	@Override
	public synchronized void quickSort(int left,int right){
		super.quickSort(left, right);
	}
	@Override
	public synchronized int partition(int left,int right){
		return super.partition(left, right);
	}

}
