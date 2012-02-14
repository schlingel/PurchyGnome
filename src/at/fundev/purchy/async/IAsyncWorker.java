package at.fundev.purchy.async;

public interface IAsyncWorker {
	public void processAsync();
	
	public void onAsyncFinish();
}
