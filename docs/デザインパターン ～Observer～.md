## 1. はじめに

GoFのデザインパターンにおける、**Observerパターン**についてまとめます。

## 2. Observerパターンとは
- Observerという英単語は、**観察者**という意味になります。
- Observerパターンは、**観察対象の状態が変化すると、観察者に対して通知が行われる方式**です。
- Observerパターンは、状態変化に応じた処理を記述するときに有効です。
- GoFのデザインパターンでは、**振る舞いに関するデザインパターン**に分類されます。

## 3. サンプルクラス図
![Observer.PNG](https://qiita-image-store.s3.amazonaws.com/0/247638/af79bbd1-c6dd-98b0-f6c8-f768204c240c.png)

## 4. サンプルプログラム
ランダムに数を生成し、その変化に応じて「数字」及び「*」を表示するプログラムです。

#### 4-1. Observerインターフェース
観察者を表すインターフェースです。

```java:Observer.java
public interface Observer {
	public abstract void update(NumberGenerator generator);
}
```

#### 4-2. DigitObserverクラス
数字で数を表す行うクラスです。Observerインターフェースを実装します。

```java:DigitObserver.java
public class DigitObserver implements Observer {

	public void update(NumberGenerator generator) {
		System.out.println("DigitObserver:" + generator.getNumber());
	}
}
```

#### 4-3. GraphObserverクラス
簡易グラフで数を表す行うクラスです。Observerインターフェースを実装します。

```java:GraphObserver.java
public class GraphObserver implements Observer {

	public void update(NumberGenerator generator) {
		System.out.print("GraphObserver:");
		int count = generator.getNumber();
		for (int i = 0; i < count; i++) {
			System.out.print("*");
		}
		System.out.println("");
	}
}
```

#### 4-4. NumberGeneratorクラス
数を生成するオブジェクトを表す抽象クラスです。

```java:NumberGenerator.java
import java.util.ArrayList;
import java.util.Iterator;

public abstract class NumberGenerator {

	private ArrayList<Observer> observers = new ArrayList<Observer>();

	public void addObserver(Observer observer) {
		observers.add(observer);
	}

	public void deleteObserver(Observer observer) {
		observers.remove(observer);
	}

	public void notifyObservers() {
		Iterator it = observers.iterator();
		while (it.hasNext()) {
			Observer o = (Observer) it.next();
			o.update(this);
		}
	}

	public abstract int getNumber();
	public abstract void execute();
}
```

#### 4-5. RandomNumberGeneratorクラス
ランダムに数を生成するクラスです。

```java:RandomNumberGenerator.java
import java.util.Random;

public class RandomNumberGenerator extends NumberGenerator {

	private Random random = new Random();
	private int number;

	public int getNumber() {
		return number;
	}

	public void execute() {
		for (int i = 0; i < 10; i++) {
			number = random.nextInt(50);
			notifyObservers();
		}
	}
}
```

#### 4-6. Mainクラス
メイン処理を行うクラスです。

```java:Main.java
public class Main {
	public static void main(String[] args) {

		NumberGenerator generator = new RandomNumberGenerator();
		Observer observer1 = new DigitObserver();
		Observer observer2 = new GraphObserver();
		generator.addObserver(observer1);
		generator.addObserver(observer2);
		generator.execute();
	}
}
```

#### 4-7. 実行結果
```
DigitObserver:35
GraphObserver:***********************************
DigitObserver:33
GraphObserver:*********************************
DigitObserver:40
GraphObserver:****************************************
DigitObserver:28
GraphObserver:****************************
DigitObserver:4
GraphObserver:****
DigitObserver:45
GraphObserver:*********************************************
DigitObserver:7
GraphObserver:*******
DigitObserver:30
GraphObserver:******************************
DigitObserver:31
GraphObserver:*******************************
DigitObserver:22
GraphObserver:**********************
```

## 5. メリット
Observerパターンでは、状態を持っているRandomNumberGeneratorクラスと、状態変化を通知してもらうDigitObserverクラス、GraphObserverクラスが登場します。そしてその2つの役目を繋いでいるものが、ObserverインターフェースとNumberGeneratorクラスになります。
RandomNumberGeneratorクラスは、自分が現在監視しているのが、DigitObserverインスタンスなのか、GraphObserverインスタンスなのかを知りません。しかし、observersフィールドに格納されているインスタンスが、Observerインターフェースを継承していることは知っており、updateメソッドを呼び出せることが保証されています。
一方、DigitObserverクラス、GraphObserverクラスは、自分を観察しているのがRandomNumberGeneratorインスタンスなのか、他のXXXNumberGeneratorインスタンスなのかを知りません。ただ、NumberGeneratorのサブクラスのインスタンスであり、getNumberメソッドを持っていることは知っています。
- **抽象クラスやインターフェースを使って、具象クラスから抽象メソッドを引きはがす**
- **引数でインスタンスを渡すときや、フィールドでインスタンスを保持するときは、抽象クラスやインターフェースの型にしておく**
このようにすることで、具象クラスの部分をカチッと交換することができます。

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
