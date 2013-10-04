package quickSort;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import tools.MyArrayUtil;

/*
 * producer-consumerを使ったクイックソート
 */
public class QuickSort6<E extends Comparable> extends QuickSort<E>{
	private final BlockingQueue<ArraysRange> tasks;
	private final AtomicInteger fixTimes;  //ソートした回数
	ExecutorService executor;
	int threadsNum;
	public QuickSort6(){

		tasks = new LinkedBlockingQueue<ArraysRange>(Integer.MAX_VALUE);
		fixTimes = new AtomicInteger(0);
		threadsNum = Runtime.getRuntime().availableProcessors(); //修正
		//threadsNum = 4;
		executor = Executors.newCachedThreadPool();
	}

	public void sort(E[] array){
		this.array = array;
		tasks.offer(new ArraysRange(0,array.length - 1));

		final List<Callable<Object>> workers = new ArrayList<Callable<Object>>(threadsNum);
		for(int i = 0; i< threadsNum;i++)
			workers.add(Executors.callable(new SortWorker()));

		try {
			executor.invokeAll(workers);
		} catch (InterruptedException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		executor.shutdown();
	}

	class SortWorker implements Runnable {
		
		
		public void run() {
			//p(Thread.currentThread().getName()+"start*******");
			while(fixTimes.get() < array.length){
				final ArraysRange arraysRange = tasks.poll();
				

				if (arraysRange.end > arraysRange.begin){
					fixTimes.incrementAndGet();
					final int pivot = partition(arraysRange.begin,arraysRange.end);
					
					tasks.offer(new ArraysRange(arraysRange.begin, pivot - 1));
					tasks.offer(new ArraysRange(pivot + 1, arraysRange.end));
				}
			}
		}
	}

	public synchronized void p(String st,int i,String st2 ,int j){
		System.out.print("            "+st+" : "+i);
		System.out.println("      "+st2+" : "+j);
	}
	public synchronized void p(String st,int i){
		System.out.println("            "+st+" : "+i);
	}

	public synchronized void p(String st){
		System.out.println("            "+st+":");
	}



	static class ArraysRange {
		final int begin; //修正
		final int end;

		ArraysRange(int begin, int end) {
			this.begin = begin;
			this.end = end;
		}
	}

	public synchronized int partition(int left,int right){
		return super.partition(left, right);

	}
}
