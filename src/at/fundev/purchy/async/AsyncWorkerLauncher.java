package at.fundev.purchy.async;

import android.os.AsyncTask;

public class AsyncWorkerLauncher extends AsyncTask<Void, Void, Void> {
	private final IAsyncWorker worker;
	
	public AsyncWorkerLauncher(IAsyncWorker worker) {
		this.worker = worker;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		worker.processAsync();
		
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		worker.onAsyncFinish();
	}
	
	public static AsyncWorkerLauncher from(IAsyncWorker worker) {
		return new AsyncWorkerLauncher(worker);
	}
}
