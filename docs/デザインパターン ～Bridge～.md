## 1. はじめに

GoFのデザインパターンにおける、**Bridgeパターン**についてまとめます。

## 2. Bridgeパターンとは
- Bridgeという英単語は、**橋**という意味になります。
- 現実世界の橋が、川のこちら側とあちら側を結びつけるように、Bridgeパターンも**2つの場所を結びつける方式**です。
- Bridgeパターンが橋渡しをしている2つの場所は、**機能のクラス階層**と**実装のクラス階層**になります。
  - **機能のクラスの階層**・・・スーパークラスで基本的な機能を持っていて、サブクラスで新しい機能を追加する場合の階層です。
  - **実装クラスの階層**・・・スーパークラスで抽象メソッドによってインターフェースを規定していて、サブクラスで具象メソッドによってそのインターフェースを実装する場合の階層です。
- GoFのデザインパターンでは、**構造に関するデザインパターン**に分類されます。

## 3. サンプルクラス図
![Bridge.PNG](https://qiita-image-store.s3.amazonaws.com/0/247638/de5c1a21-a37c-371d-7ef4-f305eba30bad.png)

## 4. サンプルプログラム
入力した文字列を指定回数、及びランダム回数表示するプログラムです。

#### 4-1. Displayクラス
機能のクラス階層になります。「表示」を行うクラスです。
このクラスが持つimplフィールドが2つのクラス階層の「橋」になります。

```java:Display.java
public class Display {

	private DisplayImpl impl;

	public Display(DisplayImpl impl) {
		this.impl = impl;
	}

	public void open() {
		impl.rawOpen();
	}

	public void print() {
		impl.rawPrint();
	}

	public void close() {
		impl.rawClose();
	}

	public final void display() {
		open();
		print();
		close();
	}
}
```

#### 4-2. CountDisplayクラス
機能のクラス階層になります。「指定回数表示」という機能を追加したクラスです。

```java:CountDisplay.java
public class CountDisplay extends Display {

	public CountDisplay(DisplayImpl impl) {
		super(impl);
	}

	public void multiDisplay(int times) {
		open();
		for (int i = 0; i < times; i++) {
			print();
		}
		close();
	}
}
```

#### 4-3. RandomCountDisplayクラス
機能のクラス階層になります。「ランダム回数表示」という機能を追加したクラスです。

```java:RandomCountDisplay.java
import java.util.Random;

public class RandomCountDisplay extends CountDisplay {

    private Random random = new Random();

	public RandomCountDisplay(DisplayImpl impl) {
		super(impl);
	}

	public void randomDisplay(int times) {
        multiDisplay(random.nextInt(times));
	}
}
```

#### 4-4. DisplayImplクラス
実装のクラス階層になります。「表示」用のメソッドを定義したクラスです。

```java:DisplayImpl.java
public abstract class DisplayImpl {
	public abstract void rawOpen();
	public abstract void rawPrint();
	public abstract void rawClose();
}
```

#### 4-5. StringDisplayImplクラス
実装のクラス階層になります。「文字列を使って表示する」クラスです。

```java:StringDisplayImpl.java
public class StringDisplayImpl extends DisplayImpl {

	private String string;
	private int width;

	public StringDisplayImpl(String string) {
		this.string = string;
		this.width = string.getBytes().length;
	}

	public void rawOpen() {
		printLine();
	}

	public void rawPrint() {
		System.out.println("|" + string + "|");
	}

	public void rawClose() {
		printLine();
	}

	private void printLine() {
		System.out.print("+");
		for (int i = 0; i < width; i++) {
			System.out.print("-");
		}
		System.out.println("+");
	}
}
```

#### 4-6. Mainクラス
メイン処理を行うクラスです。

```java:Main.java
public class Main {

	public static void main(String[] args) {
		Display d = new Display(new StringDisplayImpl("Display Test"));
		CountDisplay cd = new CountDisplay(new StringDisplayImpl("CountDisplay Test"));
		RandomCountDisplay rcd = new RandomCountDisplay(new StringDisplayImpl("RandomCountDisplay Test"));
		d.display();
		cd.multiDisplay(5);
		rcd.randomDisplay(10);
	}
}
```

#### 4-7. 実行結果
```
+------------+
|Display Test|
+------------+
+-----------------+
|CountDisplay Test|
|CountDisplay Test|
|CountDisplay Test|
|CountDisplay Test|
|CountDisplay Test|
+-----------------+
+-----------------------+
|RandomCountDisplay Test|
|RandomCountDisplay Test|
|RandomCountDisplay Test|
|RandomCountDisplay Test|
|RandomCountDisplay Test|
|RandomCountDisplay Test|
+-----------------------+
```

## 5. メリット
前述の通り、Bridgeパターンの特徴は**機能クラスの階層**と**実装クラスの階層**を分けている点にあります。この2つのクラス階層を分けておけば、それぞれのクラス階層を独立に拡張することができます。
機能を追加したければ、機能のクラス階層にクラスを追加します。このとき、実装クラス階層は全く修正する必要はありません。しかも、**追加した機能はすべての実装で利用できる**ことになります。
サンプルプログラムでは、CountDisplayクラスやRandomCountDisplayクラスを追加することが、機能追加にあたります。
このように、Bridgeパターンではクラスの拡張を見通しよく行うことができます。

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
