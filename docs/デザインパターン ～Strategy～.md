## 1. はじめに

GoFのデザインパターンにおける、**Strategyパターン**についてまとめます。

## 2. Strategyパターンとは
- Strategyという英単語は、**戦略**という意味になります。プログラミングの場合は**アルゴリズム**と考えていいみたいです。
- どんなプログラムも問題を解くために書かれています。問題を解くために特定のアルゴリズムが実装されています。Strategyパターンは、**アルゴリズムを実装した部分をごっそりと交換できる方式**です。
- GoFのデザインパターンでは、**振る舞いに関するデザインパターン**に分類されます。

## 3. サンプルクラス図
![Strategy.PNG](https://qiita-image-store.s3.amazonaws.com/0/247638/8d5cab02-fef1-5c7e-e3c3-8cb2dd2d5ec2.png)

## 4. サンプルプログラム
じゃんけんを行うプログラムです。ランダムに手を出す戦略とグーのみの手を出す戦略があります。

#### 4-1. Handクラス
じゃんけんの手を表すクラスです。

```java:Hand.java
public class Hand {

	public static final int HANDVALUE_GUU = 0;
	public static final int HANDVALUE_CHO = 1;
	public static final int HANDVALUE_PAA = 2;

	public static final Hand[] hand = {
			new Hand(HANDVALUE_GUU),
			new Hand(HANDVALUE_CHO),
			new Hand(HANDVALUE_PAA),
	};

	private int handvalue;

	private Hand(int handvalue) {
		this.handvalue = handvalue;
	}

	public static Hand getHand(int handvalue) {
		return hand[handvalue];
	}

	public boolean isStrongerThan(Hand h) {
		// thisがhより強いときtrue
		return fight(h) == 1;
	}

	private int fight(Hand h) {
		if (this == h) {
			// 引き分け
			return 0;
		} else if ((this.handvalue + 1) % 3 == h.handvalue) {
			// thisの勝ち
			return 1;
		} else {
			// hの勝ち
			return -1;
		}
	}
}
```

#### 4-2. Playerクラス
じゃんけんを行うプレイヤーを表すクラスです。

```java:Player.java
public class Player {

	private String name;
	private Strategy strategy;
	private int wincount;
	private int losecount;
	private int gamecount;

	public Player(String name, Strategy strategy) {
		this.name = name;
		this.strategy = strategy;
	}

	public String getName() {
		return name;
	}

	public Hand nextHand() {
		return strategy.nextHand();
	}

	public void win() {
		wincount++;
		gamecount++;
	}

	public void lose() {
		losecount++;
		gamecount++;
	}

	public void even() {
		gamecount++;
	}

	public String toString() {
		return "[" + name + "] " + gamecount + " gemes, " + wincount + " win, " + losecount + " lose";
	}
}
```

#### 4-3. Strategyインターフェース
じゃんけんの「戦略」を表すインターフェースです。

```java:Strategy.java
public interface Strategy {
	public abstract Hand nextHand();
}
```

#### 4-4. RandomStrategyクラス
ランダムに手を出す戦略を表すクラスです。

```java:RandomStrategy.java
import java.util.Random;

public class RandomStrategy implements Strategy {

	public Hand nextHand() {
		Random random = new Random();
		return Hand.getHand(random.nextInt(3));
	}
}
```

#### 4-5. GuuStrategyクラス
グーのみの手を出す戦略を表すクラスです。

```java:GuuStrategy.java
public class GuuStrategy implements Strategy {

	public Hand nextHand() {
		return Hand.getHand(Hand.HANDVALUE_GUU);
	}
}
```

#### 4-6. Mainクラス
メイン処理を行うクラスです。

```java:Main.java
public class Main {

	public static void main(String[] args) {

		Player player1 = new Player("Taro", new RandomStrategy());
		Player player2 = new Player("Hana", new GuuStrategy());
		for (int i = 0; i < 5; i++) {
			Hand nextHand1 = player1.nextHand();
			Hand nextHand2 = player2.nextHand();
			if (nextHand1.isStrongerThan(nextHand2)) {
				System.out.println("Winner:" + player1.getName());
				player1.win();
				player2.lose();
			} else if (nextHand2.isStrongerThan(nextHand1)) {
				System.out.println("Winner:" + player2.getName());
				player1.lose();
				player2.win();
			} else {
				System.out.println("Even...");
				player1.even();
				player2.even();
			}
		}

		System.out.println("----- Total result -----");
		System.out.println(player1.toString());
		System.out.println(player2.toString());
	}
}
```

#### 4-7. 実行結果
```
Winner:Taro
Winner:Hana
Even...
Winner:Hana
Even...
----- Total result -----
[Taro] 5 gemes, 1 win, 2 lose
[Hana] 5 gemes, 2 win, 1 lose
```

## 5. メリット
Strategyパターンでは、アルゴリズムの部分をほかの部分と意識的に分離します。そしてアルゴリズムとのインターフェースの部分だけを規定し、委譲によってアルゴリズムを利用します。
これは、プログラムを複雑にしているように見えますが、そうではありません。例えば、アルゴリズムを改良してもっと高速にしたい場合、Strategyパターンを使っていれば、Strategy役のインターフェースを変更しないようにして、アルゴリズムをだけを追加、修正すればいいのです。委譲というゆるやかな結びつきを使っているのため、アルゴリズムを用意に切り替えることができます。
また、ゲームプログラム等では、ユーザーの選択に合わせて難易度を切り替えたりすることにも使えます。

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
