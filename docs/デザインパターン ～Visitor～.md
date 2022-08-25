## 1. はじめに

GoFのデザインパターンにおける、**Visitorパターン**についてまとめます。

## 2. Visitorパターンとは
- Visitorという英単語は、**訪問者**という意味になります。
- Visitorパターンは、**データ構造と処理を分離する方式**です。
- データ構造の中をめぐり歩く訪問者クラスを用意し、訪問者クラスに処理を任せます。すると、新しい処理を追加したいときは新しい訪問者を作ればよいことになります。そして、データ構造の方は、訪問者を受け入れてあげればよいのです。
- GoFのデザインパターンでは、**振る舞いに関するデザインパターン**に分類されます。

## 3. サンプルクラス図
![Visitor.PNG](https://qiita-image-store.s3.amazonaws.com/0/247638/867c488f-2069-f6e8-7d93-fd9ad7b656df.png)

## 4. サンプルプログラム
ディレクトリ、ファイルの一覧を表示するプログラムです。

#### 4-1. Elementインターフェース
Visitorクラスのインスタンスを受け入れるデータ構造を表すインターフェースです。

```java:Element.java
public interface Element {
	public abstract void accept(Visitor v);
}
```

#### 4-2. Entryクラス
FileやDirectoryの基底となるクラスです。Elementインターフェースを実装します。

```java:Entry.java
public abstract class Entry implements Element {

	public abstract String getName();

	public String toString() {
		return getName();
	}
}
```

#### 4-3. Fileクラス
ファイルを表すクラスです。Visitorの受け入れ役になります。

```java:File.java
public class File extends Entry {

	private String name;

	public File(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
```

#### 4-4. Directoryクラス
ディレクトリを表すクラスです。Visitorの受け入れ役になります。

```java:Directory.java
import java.util.ArrayList;
import java.util.Iterator;

public class Directory extends Entry {

	private String name;
	private ArrayList<Entry> dir = new ArrayList<Entry>();

	public Directory(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Entry add(Entry entry) {
		dir.add(entry);
		return this;
	}

	public Iterator<Entry> iterator() {
		return dir.iterator();
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
```

#### 4-5. Visitorクラス
ファイルやディレクトリを訪れる訪問者を表す抽象クラスです。

```java:Visitor.java
public abstract class Visitor {
	public abstract void visit(File file);
	public abstract void visit(Directory directory);
}
```

#### 4-6. ListVisitorクラス
ファイルやディレクトリの一覧を表示するクラスです。

```java:ListVisitor.java
import java.util.Iterator;

public class ListVisitor extends Visitor {

	// 現在注目しているディレクトリ名
	private String currentdir = "";

	// ファイルを訪問したときに呼ばれる
	public void visit(File file) {
		System.out.println(currentdir + "/" + file);
	}

	// ディレクトリを訪問したときに呼ばれる
	public void visit(Directory directory) {
		System.out.println(currentdir + "/" + directory);
		String savedir = currentdir;
		currentdir = currentdir + "/" + directory.getName();
		Iterator<Entry> it = directory.iterator();
		while (it.hasNext()) {
			Entry entry = (Entry) it.next();
			entry.accept(this);
		}
		currentdir = savedir;
	}
}
```

#### 4-7. Mainクラス
メイン処理を行うクラスです。

```java:Main.java
public class Main {
	public static void main(String[] args) {

		Directory workspaceDir = new Directory("workspace");
		Directory compositeDir = new Directory("Visitor");
		Directory testDir1 = new Directory("test1");
		Directory testDir2 = new Directory("test2");
		workspaceDir.add(compositeDir);
		workspaceDir.add(testDir1);
		workspaceDir.add(testDir2);

		File element = new File("Element.java");
		File entity = new File("Entity.java");
		File file = new File("file.java");
		File directory = new File("Directory.java");
		File visitor = new File("Visitor.java");
		File listVisitor = new File("ListVisitor.java");
		File main = new File("main.java");
		compositeDir.add(element);
		compositeDir.add(entity);
		compositeDir.add(file);
		compositeDir.add(directory);
		compositeDir.add(visitor);
		compositeDir.add(listVisitor);
		compositeDir.add(main);

		workspaceDir.accept(new ListVisitor());
	}
}
```

#### 4-8. 実行結果
```
/workspace
/workspace/Visitor
/workspace/Visitor/Element.java
/workspace/Visitor/Entity.java
/workspace/Visitor/file.java
/workspace/Visitor/Directory.java
/workspace/Visitor/Visitor.java
/workspace/Visitor/ListVisitor.java
/workspace/Visitor/main.java
/workspace/test1
/workspace/test2
```

## 5. メリット
Visitorパターンは処理を複雑にしているだけで、「繰り返しの処理が必要ならデータ構造の中にループ処理を書けばいいのではないか？」と感じます。
Visitorパターンの目的は、**データ構造と処理を分離すること**です。データ構造は、要素を集合としてまとめたり、要素間を繋いだりしてくれるものです。その構造を保持しておくことと、その構造を基礎とした処理を書くことは別のものです。
訪問者役(ListVisitor)は、受け入れ役(File、Directory)とは独立して開発することができます。つまり、Visitorパターンは、受け入れ役(File、Directory)クラスの**部品としての独立性を高めている**ことになります。もし、処理の内容をFileクラスやDirectoryクラスのメソッドとして実装してしまうと、新しい処理を追加して機能拡張するたびにFileクラスやDirectoryクラスを修正しなければならなくなります。

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
