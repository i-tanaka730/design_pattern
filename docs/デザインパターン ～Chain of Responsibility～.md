## 1. はじめに

GoFのデザインパターンにおける、**Chain of Responsibilityパターン**についてまとめます。

## 2. Chain of Responsibilityパターンとは
- Chainという英単語は**鎖**、Responsibilityという英単語は**責任**、つまりChain of Responsibilityは、**責任の連鎖**という意味になります。実際には**たらい回し**を行う構造と考えた方が分かりやすいです。
- Chain of Responsibilityパターンは、**複数のオブジェクトを鎖で繋いでおき、そのオブジェクトの鎖を順次渡り歩いて目的のオブジェクトを決定する方式**です。
- 人に要求がやってくる、その人がそれを処理できるなら処理する。処理できないならその要求を「次の人」にたらい回しにする。以降繰り返し・・・。これがChain of Responsibilityパターンです。
- GoFのデザインパターンでは、**振る舞いに関するデザインパターン**に分類されます。

## 3. サンプルクラス図
![ChainofResponsibility.PNG](https://qiita-image-store.s3.amazonaws.com/0/247638/aa4dd89a-c522-9a02-32fa-dd72c49a9ac7.png)

## 4. サンプルプログラム
入力したトラブルを、いずれかのサポートが解決するプログラムです。

#### 4-1. Troubleクラス
発生したトラブルを表すを行うクラスです。

```java:Trouble.java
public class Trouble {

	// トラブル番号
	private int number;

	public Trouble(int number) {
		this.number = number;
	}

	public int getNumber() {
		return number;
	}

	public String toString() {
		return "[Trouble " + number + "]";
	}
}
```

#### 4-2. Supportクラス
トラブルを解決する抽象クラスです。

```java:Support.java
public abstract class Support {

	// このトラブル解決者の名前
	private String name;
	// たらい回し先
	private Support next;

	public Support(String name) {
		this.name = name;
	}

	public Support setNext(Support next) {
		this.next = next;
		return next;
	}

	// トラブル解決の手順
	public void support(Trouble trouble) {
		if (resolve(trouble)) {
			done(trouble);
		} else if (next != null) {
			next.support(trouble);
		} else {
			fail(trouble);
		}
	}

	public String toString() {
		return "[" + name + "]";
	}

	// 解決用メソッド
	protected abstract boolean resolve(Trouble trouble);

	// 解決
	protected void done(Trouble trouble) {
		System.out.println(trouble + " is resolved by " + this + ".");
	}

	// 未解決
	protected void fail(Trouble trouble) {
		System.out.println(trouble + " cannot be resolved.");
	}
}
```

#### 4-3. NoSupportクラス
トラブルを解決する具象クラスです。常に処理しません。

```java:NoSupport.java
public class NoSupport extends Support {

	public NoSupport(String name) {
		super(name);
	}

	protected boolean resolve(Trouble trouble) {
		return false;
	}
}
```

#### 4-4. LimitSupportクラス
トラブルを解決する具象クラスです。指定した番号未満のトラブルを解決できます。

```java:LimitSupport.java
public class LimitSupport extends Support {

	private int limit;

	public LimitSupport(String name, int limit) {
		super(name);
		this.limit = limit;
	}

	// limit未満なら解決可能
	protected boolean resolve(Trouble trouble) {
		if (trouble.getNumber() < limit) {
			return true;
		} else {
			return false;
		}
	}
}
```

#### 4-5. OddSupportクラス
トラブルを解決する具象クラスです。奇数番号のトラブルを解決できます。

```java:OddSupport.java
public class OddSupport extends Support {

	public OddSupport(String name) {
		super(name);
	}

	// 奇数番号の問題であれば解決可能
	protected boolean resolve(Trouble trouble) {
		if (trouble.getNumber() % 2 == 1) {
			return true;
		} else {
			return false;
		}
	}
}
```

#### 4-6. SpecialSupportクラス
トラブルを解決する具象クラスです。特定番号のトラブルを解決できます。

```java:SpecialSupport.java
public class SpecialSupport extends Support {

	private int number;

	public SpecialSupport(String name, int number) {
		super(name);
		this.number = number;
	}

	// numberであれば解決可能
	protected boolean resolve(Trouble trouble) {
		if (trouble.getNumber() == number) {
			return true;
		} else {
			return false;
		}
	}
}
```

#### 4-7. Mainクラス
メイン処理を行うクラスです。

```java:Main.java
public class Main {
	public static void main(String[] args) {

		Support alice = new NoSupport("Alice");
		Support bob = new LimitSupport("Bob", 100);
		Support charlie = new SpecialSupport("Charlie", 429);
		Support diana = new LimitSupport("Diana", 200);
		Support elmo = new OddSupport("Elmo");

		alice.setNext(bob).setNext(charlie).setNext(diana).setNext(elmo);

		// さまざまなトラブル発生
		for (int i = 0; i < 500; i += 33) {
			alice.support(new Trouble(i));
		}
	}
}
```

#### 4-8. 実行結果
```
[Trouble 0] is resolved by [Diana].
[Trouble 33] is resolved by [Diana].
[Trouble 66] is resolved by [Diana].
[Trouble 99] is resolved by [Diana].
[Trouble 132] is resolved by [Diana].
[Trouble 165] is resolved by [Diana].
[Trouble 198] is resolved by [Diana].
[Trouble 231] is resolved by [Elmo].
[Trouble 264] cannot be resolved.
[Trouble 297] is resolved by [Elmo].
[Trouble 330] cannot be resolved.
[Trouble 363] is resolved by [Elmo].
[Trouble 396] cannot be resolved.
[Trouble 429] is resolved by [Charlie].
[Trouble 462] cannot be resolved.
[Trouble 495] is resolved by [Elmo].
```

## 5. メリット
Chain of Responsibilityパターンのポイントは、**「要求を出す人(Main)」と「要求を処理する人(Support)」を緩やかに結びつける**ところにあります。「要求を出す人(Main)」は、最初の人に要求を出してしまうと、あとは連鎖の中をその要求が流れていき、適切な人によって要求が処理されます。
Chain of Responsibilityパターンを使わないと、「この要求はこの人が処理すべし」という知識を誰かが持っている必要があります。その知識を「要求を出す人(Main)」に持たせてしまうと、部品としての独立性が損なわれてしまいます。
注意点としては、**Chain of Responsibilityパターンは柔軟性が高くなりますが、たらい回しにする分、処理が遅くなってしまいます**。処理速度が非常に重要な場合は、Chain of Responsibilityパターンは使わない方がよいと言えます。(メリット欄に書くことではない！ｗ)

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
