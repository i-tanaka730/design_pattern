## 1. はじめに

GoFのデザインパターンにおける、**Builderパターン**についてまとめます。

## 2. Builderパターンとは
- Buildという英単語は、**構造を持っている大きなものを構築したりすること**の意味になります。
- 複雑な構造をもったものを作り上げるとき、一気に完成させるのは困難です。まず全体を構築している各部分を作り、段階を踏んで組み上げていくことになります。
- Builderパターンは、**構造を持ったインスタンスを組み上げていく方式**です。
- GoFのデザインパターンでは、**生成に関するデザインパターン**に分類されます。

## 3. サンプルクラス図
![Builder.PNG](https://qiita-image-store.s3.amazonaws.com/0/247638/15843f32-8de1-e959-3acb-5f6217d79c62.png)

## 4. サンプルプログラム
文書をプレーンテキスト、及びHTML形式で出力するプログラムです。

#### 4-1. Builderクラス
文書を構成するためのメソッドを定義した抽象クラスです。

```java:Builder.java
public abstract class Builder {
	public abstract void makeTitle(String title);
	public abstract void makeString(String str);
	public abstract void makeItems(String[] items);
	public abstract void close();
}
```

#### 4-2. Guideクラス
1つの文書を作成するクラスです。

```java:Guide.java
public class Guide {

	private Builder builder;

	public Guide(Builder builder) {
		this.builder = builder;
	}

	public void construct() {
		builder.makeTitle("バーベキューについて");
		builder.makeString("日時");
		builder.makeItems(new String[]{
			"2019/2/28 (木)",
			"11:00～",
		});
		builder.makeString("場所");
		builder.makeItems(new String[]{
			"xxx市 xxxバーベキュー場",
		});
		builder.makeString("持ち物");
		builder.makeItems(new String[]{
			"タオル",
			"肉",
			"飲み物",
			"(略)",
		});
		builder.close();
	}
}
```

#### 4-3. TextBuilderクラス
プレーンテキストで文書を作成するクラスです。

```java:TextBuilder.java
public class TextBuilder extends Builder {

	private StringBuffer buffer = new StringBuffer();

	public void makeTitle(String title) {
		buffer.append("==============================\n");
		buffer.append("『" + title + "』\n");
		buffer.append("\n");
	}

	public void makeString(String str) {
		buffer.append('■' + str + "\n");
		buffer.append("\n");
	}

	public void makeItems(String[] items) {
		for (int i = 0; i < items.length; i++) {
			buffer.append("　・" + items[i] + "\n");
		}
		buffer.append("\n");
	}

	public void close() {
		buffer.append("==============================\n");
	}

	public String getResult() {
		return buffer.toString();
	}
}
```

#### 4-3. HTMLBuilderクラス
HTMLファイルで文書を作成するクラスです。

```java:HTMLBuilder.java
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class HTMLBuilder extends Builder {

	private String filename;
	private PrintWriter writer;

	public void makeTitle(String title) {
		filename = title + ".html";
		try {
			writer = new PrintWriter(new FileWriter(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		writer.println("<html><head><title>" + title + "</title></head><body>");
		writer.println("<h1>" + title + "</h1>");
	}

	public void makeString(String str) {
		writer.println("<p>" + str + "</p>");
	}

	public void makeItems(String[] items) {
		writer.println("<ul>");
		for (int i = 0; i < items.length; i++) {
			writer.println("<li>" + items[i] + "</li>");
		}
		writer.println("</ul>");
	}

	public void close() {
		writer.println("</body></html>");
		writer.close();
	}

	public String getResult() {
		return filename;
	}
}
```

#### 4-5. Mainクラス
メイン処理を行うクラスです。

```java:Main.java
public class Main {
	public static void main(String[] args) {

		if (args.length != 1) {
			System.exit(0);
		}
		if (args[0].equals("plain")) {
			TextBuilder textbuilder = new TextBuilder();
			Guide guide = new Guide(textbuilder);
			guide.construct();
			String result = textbuilder.getResult();
			System.out.println(result);
		} else if (args[0].equals("html")) {
			HTMLBuilder htmlbuilder = new HTMLBuilder();
			Guide guide = new Guide(htmlbuilder);
			guide.construct();
			String filename = htmlbuilder.getResult();
			System.out.println(filename + "が作成されました。");
		} else {
			System.exit(0);
		}
	}
}
```

#### 4-6. 実行結果
##### 4-6-1. テキスト
```
==============================
『バーベキューについて』

■日時

　・2019/2/28 (木)
　・11:00～

■場所

　・xxx市 xxxバーベキュー場

■持ち物

　・タオル
　・肉
　・飲み物
　・(略)

==============================
```

##### 4-6-2. HTML
![bbq.PNG](https://qiita-image-store.s3.amazonaws.com/0/247638/0f66aadd-219a-be42-c277-5958a16595e1.png)

## 5. メリット
サンプルプログラムを見てみると、Mainクラスは文書の構築方法(Builderクラスのメソッド)を知りません。MainクラスはGuideクラスのconstructメソッドを呼び出すだけで、文章を構築できます。
また、GuideクラスはBuilderクラスを使って文書を構築しますが、Guideクラスは自分が実際に利用しているクラスが何なのか(TextBuilderなのかHTMLBuilderなのか)を知りません。
TextBuilderのインスタンスをGuideに与えても、HTMLBuilderのインスタンスをGuideに与えても正しく機能するのは、GuideクラスがBuilderクラスの具体的なクラスを知らないからになります。
**知らないからこそ、入れ替えができる、入れ替えられるからこそ、部品としての価値が高まります。**

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
