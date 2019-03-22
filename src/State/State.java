public interface State {
	// 時刻設定
	public abstract void doClock(Context context, int hour);
	// 金庫使用
	public abstract void doUse(Context context);
	// 非常ベル
	public abstract void doAlarm(Context context);
	// 通常通話
	public abstract void doPhone(Context context);
}
