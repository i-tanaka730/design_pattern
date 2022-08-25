## 1. はじめに

GoFのデザインパターンにおける、**Abstract Factoryパターン**についてまとめます。

## 2. Abstract Factoryパターンとは
- Abstract Factoryは、**抽象的な工場**という意味になります。
- 抽象的とは、具体的にどのように実装されているかについては考えず、インターフェースだけに注目している状態のことです。
- Abstract Factoryパターンは、**部品の具体的な実装には注目せず、インターフェースに注目します。そしてそのインターフェースだけを使って、部品を組み立て、製品にまとめる方式**です。
- GoFのデザインパターンでは、**生成に関するデザインパターン**に分類されます。

## 3. サンプルクラス図
![AbstractFactory.PNG](https://qiita-image-store.s3.amazonaws.com/0/247638/8f5af214-53f8-1fd1-ebd2-7e78c90e1cd8.png)

## 4. サンプルプログラム
お気に入り一覧を、HTML形式で出力するプログラムです。

#### 4-1. Factoryクラス
抽象的な工場を表すを行うクラスです。Link、Tray、Pageを作成します。

```java:Factory.java
package factory;

public abstract class Factory {

	public abstract Link createLink(String caption, String url);
	public abstract Tray createTray(String caption);
	public abstract Page createPage(String title);

	public static Factory getFactory(String classname) {
		Factory factory = null;
		try {
			factory = (Factory) Class.forName(classname).newInstance();
		} catch (ClassNotFoundException e) {
			System.err.println("クラス " + classname + " が見つかりません。");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return factory;
	}
}
```

#### 4-2. Itemクラス
LinkとTrayを統一的に扱うクラスです。

```java:Item.java
package factory;

public abstract class Item {
	protected String caption;

	public Item(String caption) {
		this.caption = caption;
	}

	public abstract String makeHTML();
}
```

#### 4-3. Linkクラス
抽象的な部品：HTMLリンクを表すクラスです。

```java:Link.java
package factory;

public abstract class Link extends Item {
	protected String url;

	public Link(String caption, String url) {
		super(caption);
		this.url = url;
	}
}
```

#### 4-4. Trayクラス
抽象的な部品：LinkやTrayを集めたクラスです。

```java:Tray.java
package factory;

import java.util.ArrayList;

public abstract class Tray extends Item {
	protected ArrayList tray = new ArrayList();

	public Tray(String caption) {
		super(caption);
	}

	public void add(Item item) {
		tray.add(item);
	}
}
```

#### 4-5. Pageクラス
抽象的な部品：HTMLページを表すクラスです。

```java:Page.java
package factory;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

public abstract class Page {
	protected String title;
	protected ArrayList content = new ArrayList();

	public Page(String title) {
		this.title = title;
	}

	public void add(Item item) {
		content.add(item);
	}

	public void output() {
		try {
			String filename = title + ".html";
			Writer writer = new FileWriter(filename);
			writer.write(this.makeHTML());
			writer.close();
			System.out.println(filename + " を作成しました。");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public abstract String makeHTML();
}
```

#### 4-6. ListFactoryクラス
具体的な工場を表すクラスです。ListLink、ListTray、ListPageを作成します。

```java:ListFactory.java
package listfactory;

import factory.Factory;
import factory.Link;
import factory.Page;
import factory.Tray;

public class ListFactory extends Factory {
	public Link createLink(String caption, String url) {
		return new ListLink(caption, url);
	}

	public Tray createTray(String caption) {
		return new ListTray(caption);
	}

	public Page createPage(String title) {
		return new ListPage(title);
	}
}
```

#### 4-7. ListLinkクラス
具体的な部品：HTMLリンクを表すクラスです。

```java:ListLink.java
package listfactory;

import factory.Link;

public class ListLink extends Link {
	public ListLink(String caption, String url) {
		super(caption, url);
	}

	public String makeHTML() {
		return "  <li><a href=\"" + url + "\">" + caption + "</a></li>\n";
	}
}
```

#### 4-8. ListTrayクラス
具体的な部品：LinkやTrayを集めたクラスです。

```java:ListTray.java
package listfactory;

import java.util.Iterator;

import factory.Item;
import factory.Tray;

public class ListTray extends Tray {
	public ListTray(String caption) {
		super(caption);
	}

	public String makeHTML() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<li>\n");
		buffer.append(caption + "\n");
		buffer.append("<ul>\n");
		Iterator it = tray.iterator();
		while (it.hasNext()) {
			Item item = (Item) it.next();
			buffer.append(item.makeHTML());
		}
		buffer.append("</ul>\n");
		buffer.append("</li>\n");
		return buffer.toString();
	}
}
```

#### 4-9. ListPageクラス
具体的な部品：HTMLページを表すクラスです。

```java:ListPage.java
package listfactory;

import java.util.Iterator;

import factory.Item;
import factory.Page;

public class ListPage extends Page {
	public ListPage(String title) {
		super(title);
	}

	public String makeHTML() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<html><head><title>" + title + "</title></head>\n");
		buffer.append("<body>\n");
		buffer.append("<h1>" + title + "</h1>\n");
		buffer.append("<ul>\n");
		Iterator it = content.iterator();
		while (it.hasNext()) {
			Item item = (Item) it.next();
			buffer.append(item.makeHTML());
		}
		buffer.append("</ul>\n");
		buffer.append("</body></html>\n");
		return buffer.toString();
	}
}
```

#### 4-10. Mainクラス
メイン処理を行うクラスです。

```java:Main.java
import factory.Factory;
import factory.Link;
import factory.Page;
import factory.Tray;

public class Main {
	public static void main(String[] args) {

		Factory factory = Factory.getFactory("listfactory.ListFactory");

		Link qiita = factory.createLink("Qiita", "https://qiita.com//");
		Link dot = factory.createLink("ドットインストール", "https://dotinstall.com/");

		Link yahoo = factory.createLink("Yahoo!Japan", "http://www.yahoo.co.jp/");
		Link excite = factory.createLink("Excite", "http://www.excite.com/");
		Link google = factory.createLink("Google", "http://www.google.com/");

		Tray pgTray = factory.createTray("プログラミング");
		pgTray.add(qiita);
		pgTray.add(dot);

		Tray searchTray = factory.createTray("検索サイト");
		searchTray.add(yahoo);
		searchTray.add(excite);
		searchTray.add(google);

		Page page = factory.createPage("お気に入り");
		page.add(pgTray);
		page.add(searchTray);
		page.output();
	}
}
```

#### 4-11. 実行結果
![キャプチャ.PNG](https://qiita-image-store.s3.amazonaws.com/0/247638/48e97743-fb60-eac8-f4a8-c98803d39938.png)

## 5. メリット
例えば、サンプルプログラムに新たな具体的な工場を追加する場合、Factory、Link、Tray、Pageのサブクラス作り、それぞれの抽象メソッドを実装することになります。つまり、factoryパッケージのクラスが持っている抽象的な部分を具体化していくだけになります。このとき、いくら具体的な工場を追加しても、抽象的な工場を修正する必要がありません。

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
