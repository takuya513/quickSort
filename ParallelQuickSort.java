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
public class ParallelQuickSort<E extends Comparable> extends QuickSort<E>{
	private BlockingQueue<ArraysRange> tasks;
	private AtomicInteger fixTimes;  //ソートした回数
	ExecutorService executor;
	int threadsNum;

	public ParallelQuickSort(){

		threadsNum = Runtime.getRuntime().availableProcessors(); //修正
		//threadsNum = 4;

	}

	public void sort(E[] array){
		this.array = array;
		executor = Executors.newFixedThreadPool(threadsNum);
		tasks = new LinkedBlockingQueue<ArraysRange>(Integer.MAX_VALUE);
		fixTimes = new AtomicInteger(0);

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
