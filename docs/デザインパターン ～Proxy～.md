## 1. はじめに

GoFのデザインパターンにおける、**Proxyパターン**についてまとめます。

## 2. Proxyパターンとは
- Proxyという英単語は、**代理人**という意味になります。
- オブジェクト指向では、「本人」も「代理人」もオブジェクトとなります。
- Proxyパターンは、**忙しくて仕事ができない本人オブジェクトの代わりに、代理人オブジェクトが一部の仕事をこなす方式**です。
- GoFのデザインパターンでは、**構造に関するデザインパターン**に分類されます。

## 3. サンプルクラス図
![Proxy.PNG](https://qiita-image-store.s3.amazonaws.com/0/247638/4af33b52-61cc-5c7e-3784-be7892b6542b.png)

## 4. サンプルプログラム
画面に文字を表示する「名前付きプリンタ」のプログラムです。

#### 4-1. Printableインターフェース
PrinterとPrinterProxy共通のインターフェースです。

```java:Printable.java
public interface Printable {
	// 名前の設定
	public abstract void setPrinterName(String name);
	// 名前の取得
	public abstract String getPrinterName();
	// 文字列表示(プリントアウト)
	public abstract void print(String string);
}
```

#### 4-2. PrinterProxyクラス
名前つきのプリンタを表すクラスです。(代理人)

```java:PrinterProxy.java
public class PrinterProxy implements Printable {

	private String name;
	private Printer real;

	public PrinterProxy() {
	}

	public PrinterProxy(String name) {
		this.name = name;
	}

	public synchronized void setPrinterName(String name) {
		if (real != null) {
			real.setPrinterName(name);
		}
		this.name = name;
	}

	public String getPrinterName() {
		return name;
	}

	public void print(String string) {
		realize();
		real.print(string);
	}

	private synchronized void realize() {
		if (real == null) {
			real = new Printer(name);
		}
	}
}
```

#### 4-3. Printerクラス
名前つきのプリンタを表すクラスです。(本人)

```java:Printer.java
public class Printer implements Printable {

	private String name;

	public Printer() {
		heavyJob("Printerのインスタンスを生成中");
	}

	public Printer(String name) {
		this.name = name;
		heavyJob("Printerのインスタンス(" + name + ")を生成中");
	}

	public void setPrinterName(String name) {
		this.name = name;
	}

	public String getPrinterName() {
		return name;
	}

	public void print(String string) {
		System.out.println("=== " + name + " ===");
		System.out.println(string);
	}

	private void heavyJob(String msg) {
		System.out.print(msg);
		for (int i = 0; i < 5; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			System.out.print(".");
		}
		System.out.println("完了。");
	}
}
```

#### 4-4. Mainクラス
メイン処理を行うクラスです。

```java:Main.java
public class Main {
	public static void main(String[] args) {
		Printable p = new PrinterProxy("Alice");
		System.out.println("名前は現在" + p.getPrinterName() + "です。");
		p.setPrinterName("Bob");
		System.out.println("名前は現在" + p.getPrinterName() + "です。");
		p.print("Hello, world.");
	}
}
```

#### 4-5. 実行結果
```
名前は現在Aliceです。
名前は現在Bobです。
Printerのインスタンス(Bob)を生成中.....完了。
=== Bob ===
Hello, world.
```

## 5. メリット
Proxyパターンでは、Proxyが代理人となって、できるだけ処理を肩代わりします。
サンプルプログラムでは、Proxy役を使うことによって、実際にprintするときまで、重い処理(インスタンス生成)を遅らせることができました。
例えば、初期化に時間がかかる機能がたくさん存在するようなシステムの場合、起動の時点では利用しない機能まで全部初期化してしまったら、アプリケーションの起動に時間がかかってしまうことになります。
PrinterProxyクラスとPrinterクラスの2つに分けず、Printerクラスの中に最初から遅延機能を入れておくこともできますが、クラスを分けることで、プログラムの部品化が進み、個別に機能を加えることができます。

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
