## 1. はじめに

GoFのデザインパターンにおける、**Stateパターン**についてまとめます。

## 2. Stateパターンとは
- Stateという英単語は、**状態**という意味になります。
- Stateパターンは、**状態をクラスとして表現し、クラスを切り替えることによって「状態の変化」を表す方式**です。
- GoFのデザインパターンでは、**振る舞いに関するデザインパターン**に分類されます。

## 3. サンプルクラス図
![State.PNG](https://qiita-image-store.s3.amazonaws.com/0/247638/b8cea485-287c-14ec-b919-32ee2d9c83f5.png)

## 4. サンプルプログラム
昼、夜の状態によって、ボタンの動作を変える金庫管理プログラムです。

#### 4-1. Contextインターフェース
金庫の状態変化を管理し、警備センターとの連絡をとるインターフェースです。

```java:Context.java
public interface Context {
	// 時刻の設定
	public abstract void setClock(int hour);
	// 状態変化
	public abstract void changeState(State state);
	// 警備センター警備員呼び出し
	public abstract void callSecurityCenter(String msg);
	// 警備センター記録
	public abstract void recordLog(String msg);
}
```

#### 4-2. SafeFrameクラス
Contextインターフェースを実装するクラスです。ボタンや画面表示などのUIを持ちます。

```java:SafeFrame.java
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SafeFrame extends Frame implements ActionListener, Context {

	private TextField textClock = new TextField(60);
	private TextArea textScreen = new TextArea(10, 60);
	private Button buttonUse = new Button("金庫使用");
	private Button buttonAlarm = new Button("非常ベル");
	private Button buttonPhone = new Button("通常通話");
	private Button buttonExit = new Button("終了");

	private State state = DayState.getInstance();

	public SafeFrame(String title) {
		super(title);

		setBackground(Color.lightGray);
		setLayout(new BorderLayout());
		add(textClock, BorderLayout.NORTH);
		textClock.setEditable(false);
		add(textScreen, BorderLayout.CENTER);
		textScreen.setEditable(false);

		Panel panel = new Panel();
		panel.add(buttonUse);
		panel.add(buttonAlarm);
		panel.add(buttonPhone);
		panel.add(buttonExit);
		add(panel, BorderLayout.SOUTH);

		pack();
		show();

		buttonUse.addActionListener(this);
		buttonAlarm.addActionListener(this);
		buttonPhone.addActionListener(this);
		buttonExit.addActionListener(this);
	}

	// ボタンが押されたらここに来る
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.toString());
		if (e.getSource() == buttonUse) {
			// 金庫使用ボタン
			state.doUse(this);
		} else if (e.getSource() == buttonAlarm) {
			// 非常ベルボタン
			state.doAlarm(this);
		} else if (e.getSource() == buttonPhone) {
			// 通常通話ボタン
			state.doPhone(this);
		} else if (e.getSource() == buttonExit) {
			// 終了ボタン
			System.exit(0);
		} else {
			System.out.println("?");
		}
	}

	// 時刻の設定
	public void setClock(int hour) {
		String clockstring = "現在時刻は";
		if (hour < 10) {
			clockstring += "0" + hour + ":00";
		} else {
			clockstring += hour + ":00";
		}
		System.out.println(clockstring);
		textClock.setText(clockstring);
		state.doClock(this, hour);
	}

	// 状態変化
	public void changeState(State state) {
		System.out.println(this.state + "から" + state + "へ状態が変化しました。");
		this.state = state;
	}

	// 警備センター警備員呼び出し
	public void callSecurityCenter(String msg) {
		textScreen.append("call! " + msg + "\n");
	}

	// 警備センター記録
	public void recordLog(String msg) {
		textScreen.append("record ... " + msg + "\n");
	}
}
```

#### 4-3. Stateインターフェース
金庫の状態を表すインターフェースです。

```java:State.java
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
```

#### 4-4. DayStateクラス
Stateインターフェースを実装するクラスです。昼間の状態を表します。

```java:DayState.java
public class DayState implements State {
	private static DayState singleton = new DayState();

	private DayState() {
	}

	public static State getInstance() {
		return singleton;
	}

	public void doClock(Context context, int hour) {
		if (hour < 9 || 17 <= hour) {
			context.changeState(NightState.getInstance());
		}
	}

	public void doUse(Context context) {
		context.recordLog("金庫使用(昼間)");
	}

	public void doAlarm(Context context) {
		context.callSecurityCenter("非常ベル(昼間)");
	}

	public void doPhone(Context context) {
		context.callSecurityCenter("通常の通話(昼間)");
	}

	public String toString() {
		return "[昼間]";
	}
}
```

#### 4-5. NightStateクラス
Stateインターフェースを実装するクラスです。夜間の状態を表します

```java:NightState.java
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
```

#### 4-6. Mainクラス
メイン処理を行うクラスです。

```java:Main.java
public class Main {
	public static void main(String[] args) {
		SafeFrame frame = new SafeFrame("State Sample");
		while (true) {
			for (int hour = 0; hour < 24; hour++) {
				frame.setClock(hour);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
			}
		}
	}
}
```

#### 4-7. 実行結果
![pg.png](https://qiita-image-store.s3.amazonaws.com/0/247638/01ba7266-4696-832e-8420-18498405b24e.png)

## 5. メリット
**分割して統合せよ**という方針は、プログラミングによく登場します。
Stateパターンでは、状態をクラスとして表現しています。個々の具体的な状態を別々のクラスとして表現して、分割を行っています。
DayStateを実装している最中、プログラマは他のクラス(NightState)を意識する必要はなくなります。
サンプルプログラムでは状態が2つしかありませんでしたが、状態がもっと増えた場合にStateパターンは強みを発揮します。

## 6. GitHub
- https://github.com/i-tanaka730/design_pattern

## 7. デザインパターン一覧
- [**GoFのデザインパターンまとめ**](https://qiita.com/i-tanaka730/items/c63c6c22abd1477e0ba0)

## 8. 参考
今回の記事、及びサンプルプログラムは、以下の書籍を元に作成させて頂きました。

- [**Java言語で学ぶデザインパターン入門**](
https://www.amazon.co.jp/%E5%A2%97%E8%A3%9C%E6%94%B9%E8%A8%82%E7%89%88Java%E8%A8%80%E8%AA%9E%E3%81%A7%E5%AD%A6%E3%81%B6%E3%83%87%E3%82%B6%E3%82%A4%E3%83%B3%E3%83%91%E3%82%BF%E3%83%BC%E3%83%B3%E5%85%A5%E9%96%80-%E7%B5%90%E5%9F%8E-%E6%B5%A9/dp/4797327030/ref=sr_1_1?ie=UTF8&qid=1549628781)

大変分かりやすく、勉強になりました。感謝申し上げます。
デザインパターンやサンプルプログラムについての説明が詳細に書かれていますので、是非書籍の方もご覧ください。
