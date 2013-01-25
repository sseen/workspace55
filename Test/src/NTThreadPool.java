import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class NTThreadPool {
	public static List<String> daemonList = new ArrayList<String>(); 
	public static BlockingQueue<String> bq = new LinkedBlockingQueue<String>();
	static {
		int i = 0;
		while(i++<20000) {
//			daemonList.add(i+"");
			bq.add(i+"");
		}
	}
	public static void main(String[] args) {
		long S = System.currentTimeMillis();
		// 创建一个可重用固定线程数的线程池
		ExecutorService pool = Executors.newFixedThreadPool(8);
		MyThread tt1 = new NTThreadPool().new MyThread("");
		String _tmp ;
		while(!bq.isEmpty()) {		
			_tmp = bq.poll();
			tt1 = new NTThreadPool().new MyThread(_tmp);
			pool.execute(tt1);
		}
		
		// 关闭线程池
		pool.shutdown();
		while (!pool.isTerminated()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println("mmm" + daemonList.size());
		System.out.println(System.currentTimeMillis() - S);
	}
	
	class MyThread extends Thread {
		public List<String> willPush = new ArrayList<String>();
		public String name;
		public MyThread(String o) {
			name = o;
		}
	    @Override
	    public void run() {
	    	System.out.println(
	    			Thread.currentThread().getName() 
	    			+ "正在执行。。。"
	    			+ name
	    			);
	    	if (Integer.valueOf(name) > 10) {
				NTThreadPool.daemonList.add(name);
			}
	    }
	}
}


