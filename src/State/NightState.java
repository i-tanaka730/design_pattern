public class NightState implements State {
	private static NightState singleton = new NightState();

	private NightState() {
	}

	public static State getInstance() {
		return singleton;
	}

	public void doClock(Context context, int hour) {
		if (9 <= hour && hour < 17) {
			context.changeState(DayState.getInstance());
		}
	}

	public void doUse(Context context) {
		context.callSecurityCenter("非常：夜間の金庫使用！");
	}

	public void doAlarm(Context context) {
		context.callSecurityCenter("非常ベル(夜間)");
	}

	public void doPhone(Context context) {
		context.recordLog("夜間の通話録音");
	}

	public String toString() {
		return "[夜間]";
	}
}
