## 1. はじめに

GoFのデザインパターンにおける、**Facadeパターン**についてまとめます。

## 2. Facadeパターンとは
- Facadeという英単語は、**正面**という意味になります。
- 大きなプログラムを使って処理を行うためには、関係しあっているたくさんのクラスを適切に制御しなければなりません。その処理を行うための窓口を用意しておけば、たくさんのクラスを個別に制御しなくてよくなります。
- Facadeパターンは、**複雑なシステムに対してシンプルな窓口を用意する方式**です。
- GoFのデザインパターンでは、**構造に関するデザインパターン**に分類されます。

## 3. サンプルクラス図
![Facade.PNG](https://qiita-image-store.s3.amazonaws.com/0/247638/ef055942-1dee-3909-0c18-6ad02f248d62.png)

## 4. サンプルプログラム
ユーザーのWebページを作成するプログラムです。

#### 4-1. PageMakerクラス
メールアドレスからユーザーのWebページを作成するクラスです。このクラスがFacadeになります。

```java:PageMaker.java
package pagemaker;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class PageMaker {

	private PageMaker() {}

	public static void makeWelcomePage(String mailaddr, String filename) {
		try {
			Properties mailprop = Database.getProperties("maildata");
			String username = mailprop.getProperty(mailaddr);
			HtmlWriter writer = new HtmlWriter(new FileWriter(filename));
			writer.title("Welcome to " + username + "'s page!");
			writer.paragraph(username + "のページへようこそ。");
			writer.paragraph("メールまっていますね。");
			writer.mailto(mailaddr, username);
			writer.close();
			System.out.println(filename + " is created for " + mailaddr + " (" + username + ")");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
```

#### 4-2. HtmlWriterクラス
HTMLファイルを作成するクラスです。

```java:v.java
package pagemaker;

import java.io.IOException;
import java.io.Writer;

public class HtmlWriter {

	private Writer writer;

	public HtmlWriter(Writer writer) {
        this.writer = writer;
    }

	public void title(String title) throws IOException {
        writer.write("<html>");
        writer.write("<head>");
        writer.write("<title>" + title + "</title>");
        writer.write("</head>");
        writer.write("<body>\n");
        writer.write("<h1>" + title + "</h1>\n");
    }

	public void paragraph(String msg) throws IOException {
        writer.write("<p>" + msg + "</p>\n");
    }

	public void link(String href, String caption) throws IOException {
        paragraph("<a href=\"" + href + "\">" + caption + "</a>");
    }

	public void mailto(String mailaddr, String username) throws IOException {
        link("mailto:" + mailaddr, username);
    }

	public void close() throws IOException {
        writer.write("</body>");
        writer.write("</html>\n");
        writer.close();
    }
}
```

#### 4-3. Databaseクラス
メールアドレスからユーザー名を得るうクラスです。

```java:Database.java
package pagemaker;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Database {

	private Database() {}

	public static Properties getProperties(String dbname) {
		String filename = dbname + ".txt";
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(filename));
		} catch (IOException e) {
			System.out.println("Warning: " + filename + " is not found.");
		}
		return prop;
	}
}
```

#### 4-4. maildataデータベース
データベースファイルです。

```java:maildata.txt
tanaka@test.com=Taro Tanaka
yamada@test.com=Hanako Yamada
suzuki@test.com=Daisuke Suzuki
```

#### 4-5. Mainクラス
メイン処理を行うクラスです。

```java:Main.java
import pagemaker.PageMaker;

public class Main {
	public static void main(String[] args) {
		PageMaker.makeWelcomePage("tanaka@test.com", "welcome.html");
	}
}
```

#### 4-6. 実行結果
![f.PNG](https://qiita-image-store.s3.amazonaws.com/0/247638/3aebd09e-4e02-4ede-bb40-3d8900943f8f.png)

## 5. メリット
クラスやメソッドがたくさんあると、プログラマはどれを使ってよいか迷ったり、呼び出し順に注意したりしなければならなくなります。注意しなければならないということはその分、間違えやすいということになります。
Facadeパターンを使うことで、**インターフェースを少なくし、複雑なものを単純に見せる**ことができます。
インターフェースの数が少ないというのは、外部との結合が疎であるという表現もできます。つまり、ゆるやかな結合となり、パッケージを部品として再利用しやすくしてくれます。

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
