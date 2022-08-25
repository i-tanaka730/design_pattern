## 1. はじめに

GoFのデザインパターンにおける、**Flyweightパターン**についてまとめます。

## 2. Flyweightパターンとは
- Flyweightという英単語は、**フライ級**という意味になります。ボクシングで最も体重が軽い階級を表します。
- Flyweightパターンにおいての重さとは、メモリ使用量のことになります。
- Flyweightパターンは、**インスタンスをできるだけ共有させて、メモリ使用量を軽くする方式**です。
- GoFのデザインパターンでは、**構造に関するデザインパターン**に分類されます。

## 3. サンプルクラス図
![Flyweight.PNG](https://qiita-image-store.s3.amazonaws.com/0/247638/2deca306-42be-4653-0166-5218f3bbc2dc.png)

## 4. サンプルプログラム
ファイルから大きな文字のテキストを読み込んで、それを表示するプログラムです。

#### 4-1. BigCharクラス
大きな文字を表すクラスです。

```java:BigChar.java
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BigChar {

	// 文字の名前
	private char charname;
	// 大きな文字を表現する文字列('#' '.' '\n'の列)
	private String fontdata;

	public BigChar(char charname) {
		this.charname = charname;
		try {
			BufferedReader reader = new BufferedReader(
					new FileReader("big" + charname + ".txt"));
			String line;
			StringBuffer buf = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				buf.append(line);
				buf.append("\n");
			}
			reader.close();
			this.fontdata = buf.toString();
		} catch (IOException e) {
			this.fontdata = charname + "?";
		}
	}

	// 大きな文字を表示する
	public void print() {
		System.out.print(fontdata);
	}
}
```

#### 4-2. BigCharFactoryクラス
BigCharのインスタンスを共有しながら生成するクラスです。

```java:BigCharFactory.java
import java.util.HashMap;

public class BigCharFactory {

	// すでに作ったBigCharのインスタンスを管理
	private HashMap pool = new HashMap();

	private static BigCharFactory singleton = new BigCharFactory();

	private BigCharFactory() {
	}

	// 唯一のインスタンスを得る
	public static BigCharFactory getInstance() {
		return singleton;
	}

	// BigCharのインスタンス生成(共有)
	public synchronized BigChar getBigChar(char charname) {
		BigChar bc = (BigChar) pool.get("" + charname);
		if (bc == null) {
			bc = new BigChar(charname);
			pool.put("" + charname, bc);
		}
		return bc;
	}
}
```

#### 4-3. BigStringクラス
BigCharを集めて作った「大きな文字列」を表すクラスです。

```java:BigString.java
public class BigString {

	private BigChar[] bigchars;

	public BigString(String string) {
		bigchars = new BigChar[string.length()];
		BigCharFactory factory = BigCharFactory.getInstance();
		for (int i = 0; i < bigchars.length; i++) {
			bigchars[i] = factory.getBigChar(string.charAt(i));
		}
	}

	public void print() {
		for (int i = 0; i < bigchars.length; i++) {
			bigchars[i].print();
		}
	}
}
```

#### 4-4. Mainクラス
メイン処理を行うクラスです。

```java:Main.java
public class Main {
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Usage: java Main digits");
			System.out.println("Example: java Main 1212123");
			System.exit(0);
		}
		BigString bs = new BigString(args[0]);
		bs.print();
	}
}
```

#### 4-5. データ
以下のtxtファイルをご参照ください。
https://github.com/i-tanaka730/design_pattern/tree/master/src/Flyweight

#### 4-6. 実行結果
```
..##########....
..##......##....
..........##....
........##......
......##........
......##........
......##........
................
......##........
..######........
......##........
......##........
......##........
......##........
..##########....
................
..##########....
..##......##....
..........##....
........##......
......##........
......##........
......##........
................
..##########....
..##............
..##............
..########......
..........##....
..##......##....
....######......
................。
```

## 5. メリット
インスタンスを共有すると、毎回newする必要がなく、メモリ使用量が少なくなります。より一般的に言えば、インスタンスを共有すると、インスタンスを生成するのに必要な**リソース**の量を減らすことができます。リソースとはコンピュータ上の資源であり、メモリはリソースの一種です。
時間もリソースの一種になります。インスタンスをnewするとき、一定の時間がかかるとすると、Flyweightパターンを使ってインスタンスを共有すれば、インスタンスをnewする数を減らすことができます。これによってプログラムのスピードを上げることができます。

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
