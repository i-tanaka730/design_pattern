## 1. はじめに

GoFのデザインパターンにおける、**Mediatorパターン**についてまとめます。

## 2. Mediatorパターンとは
- Mediatorという英単語は、**仲介者**という意味になりますが、実際には**相談役**と考えた方が分かりやすいです。
- メンバーが10人いたとして、互いに指示しあっていたら、作業は大混乱します。そんなとき、立場の違う**相談役**がいれば、メンバーは相談役だけに報告し、メンバーへの支持は相談役だけから受けるようにすればよくなります。
- Mediatorパターンは、**相談役を通して行動を起こすようにしてもらう方式**です。
- GoFのデザインパターンでは、**振る舞いに関するデザインパターン**に分類されます。

## 3. サンプルクラス図
![Mediator.PNG](https://qiita-image-store.s3.amazonaws.com/0/247638/707c7aa6-170b-795f-15f1-dcb806c4de3a.png)

## 4. サンプルプログラム
ログインダイアログを表示し、テキストやボタンの有効/無効状態を制御するプログラムです。

#### 4-1. Mediatorインターフェース
相談役となるインターフェースです。

```java:Mediator.java
public interface Mediator {
	public abstract void createColleagues();
	public abstract void colleagueChanged();
}
```

#### 4-2. LoginFrameクラス
ログインダイアログを表すクラスです。Mediatorインターフェースを実装します。

```java:LoginFrame.java
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends Frame implements ActionListener, Mediator {

	private ColleagueCheckbox checkGuest;
	private ColleagueCheckbox checkLogin;
	private ColleagueTextField textUser;
	private ColleagueTextField textPass;
	private ColleagueButton buttonOk;
	private ColleagueButton buttonCancel;

	public LoginFrame(String title) {
		super(title);
		setBackground(Color.lightGray);
		setLayout(new GridLayout(4, 2));

		createColleagues();
		add(checkGuest);
		add(checkLogin);
		add(new Label("Username:"));
		add(textUser);
		add(new Label("Password:"));
		add(textPass);
		add(buttonOk);
		add(buttonCancel);
		colleagueChanged();

		pack();
		setVisible(true);
	}

	public void createColleagues() {

		CheckboxGroup g = new CheckboxGroup();
		checkGuest = new ColleagueCheckbox("Guest", g, true);
		checkLogin = new ColleagueCheckbox("Login", g, false);
		textUser = new ColleagueTextField("", 10);
		textPass = new ColleagueTextField("", 10);
		textPass.setEchoChar('*');
		buttonOk = new ColleagueButton("OK");
		buttonCancel = new ColleagueButton("Cancel");

		checkGuest.setMediator(this);
		checkLogin.setMediator(this);
		textUser.setMediator(this);
		textPass.setMediator(this);
		buttonOk.setMediator(this);
		buttonCancel.setMediator(this);

		checkGuest.addItemListener(checkGuest);
		checkLogin.addItemListener(checkLogin);
		textUser.addTextListener(textUser);
		textPass.addTextListener(textPass);
		buttonOk.addActionListener(this);
		buttonCancel.addActionListener(this);
	}

	// Colleageからの通知で各Colleageの有効/無効を判定する。
	public void colleagueChanged() {
		if (checkGuest.getState()) {
			// Guest mode
			textUser.setColleagueEnabled(false);
			textPass.setColleagueEnabled(false);
			buttonOk.setColleagueEnabled(true);
		} else {
			// Login mode
			textUser.setColleagueEnabled(true);
			userpassChanged();
		}
	}

	private void userpassChanged() {
		if (textUser.getText().length() > 0) {
			textPass.setColleagueEnabled(true);
			if (textPass.getText().length() > 0) {
				buttonOk.setColleagueEnabled(true);
			} else {
				buttonOk.setColleagueEnabled(false);
			}
		} else {
			textPass.setColleagueEnabled(false);
			buttonOk.setColleagueEnabled(false);
		}
	}

	public void actionPerformed(ActionEvent e) {
		System.out.println(e.toString());
		System.exit(0);
	}
}
```

#### 4-3. Colleagueインターフェース
メンバーとなるインターフェースです。

```java:Colleague.java
public interface Colleague {
	public abstract void setMediator(Mediator mediator);
	public abstract void setColleagueEnabled(boolean enabled);
}
```

#### 4-4. ColleagueButtonクラス
ボタンを表すクラスです。Colleagueインターフェースを実装します。

```java:ColleagueButton.java
import java.awt.Button;

public class ColleagueButton extends Button implements Colleague {

	private Mediator mediator;

	public ColleagueButton(String caption) {
		super(caption);
	}

	public void setMediator(Mediator mediator) {
		this.mediator = mediator;
	}

	public void setColleagueEnabled(boolean enabled) {
		// Mediatorから有効/無効が指示される
		setEnabled(enabled);
	}
}
```

#### 4-5. ColleagueCheckboxクラス
チェックボックス(ここではラジオボタン)を表すクラスです。Colleagueインターフェースを実装します。

```java:ColleagueCheckbox.java
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ColleagueCheckbox extends Checkbox implements ItemListener, Colleague {

	private Mediator mediator;

	public ColleagueCheckbox(String caption, CheckboxGroup group, boolean state) {
		super(caption, group, state);
	}

	public void setMediator(Mediator mediator) {
		this.mediator = mediator;
	}

	public void setColleagueEnabled(boolean enabled) {
		// Mediatorから有効/無効が指示される
		setEnabled(enabled);
	}

	public void itemStateChanged(ItemEvent e) {
		// 状態が変化したらMediatorに通知
		mediator.colleagueChanged();
	}
}
```

#### 4-6. ColleagueTextFieldクラス
テキストボックスを表すクラスです。Colleagueインターフェースを実装します。

```java:ColleagueTextField.java
import java.awt.Color;
import java.awt.TextField;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;

public class ColleagueTextField extends TextField implements TextListener, Colleague {

	private Mediator mediator;

	public ColleagueTextField(String text, int columns) {
		super(text, columns);
	}

	public void setMediator(Mediator mediator) {
		this.mediator = mediator;
	}

	public void setColleagueEnabled(boolean enabled) {
		// Mediatorから有効/無効が指示される
		setEnabled(enabled);
		setBackground(enabled ? Color.white : Color.lightGray);
	}

	public void textValueChanged(TextEvent e) {
		// 文字列が変化したらMediatorに通知
		mediator.colleagueChanged();
	}
}
```

#### 4-7. Mainクラス
メイン処理を行うクラスです。

```java:Main.java
public class Main {
	static public void main(String args[]) {
		new LoginFrame("Mediator Sample");
	}
}
```

#### 4-8. 実行結果
![f.PNG](https://qiita-image-store.s3.amazonaws.com/0/247638/80f21734-a8bc-0f11-2b2b-24c9af602fc1.png)

## 5. メリット
表示の有効/無効に関するロジックは複雑になりますが、LoginFrameクラスに仲介されています。
表示に関する仕様を変更したり、バグを見つけた場合はLoginFrameクラスのみ修正・デバッグすればよいことになります。
ロジックがColleagueButton、ColleagueCheckbox、ColleagueTextFieldに分散されいたら、書くのも、デバッグするのも、修正するのも大変になります。

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
