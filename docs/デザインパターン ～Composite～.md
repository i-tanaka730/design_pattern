## 1. はじめに
GoFのデザインパターンにおける、**Compositeパターン**についてまとめます。

## 2. Compositeパターンとは
- Compositeという英単語は、**混合物**や**複合物**という意味になります。
- Compositeパターンは、**容器と中身を同一化し、再帰的な構造を作る方式**です。
- ディレクトリとファイルをまとめてディレクトリエントリとして扱うように、容器と中身を同じ種類のものとして扱うと便利な場合があります。例えば、容器の中には中身を入れてもいいですし、さらに容器を入れることもいいです。このようにして、再帰的な構造を作ることができます。
- GoFのデザインパターンでは、**構造に関するデザインパターン**に分類されます。

## 3. サンプルクラス図
![Composite.PNG](https://qiita-image-store.s3.amazonaws.com/0/247638/45c7556e-5701-5238-4a67-9a4ae71e593b.png)

## 4. サンプルプログラム
ディレクトリ、ファイルの一覧を表示するプログラムです。

#### 4-1. Entryクラス
FileとDirectoryの基底となるクラスです。

```java:Entry.java
public abstract class Entry {

	public abstract String getName();
	protected abstract void printList(String prefix);

	public void printList() {
		printList("");
	}
}
```

#### 4-2. Fileクラス
ファイルを表すクラスです。

```java:File.java
public class File extends Entry {

	private String name;

	public File(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	protected void printList(String prefix) {
		System.out.println(prefix + "/" + name);
	}
}
```

#### 4-3. Directoryクラス
ディレクトリを表すクラスです。

```java:Directory.java
import java.util.ArrayList;
import java.util.Iterator;

public class Directory extends Entry {

	private String name;
	private ArrayList<Entry> directory = new ArrayList<Entry>();

	public Directory(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Entry add(Entry entry) {
		directory.add(entry);
		return this;
	}

	protected void printList(String prefix) {
		System.out.println(prefix + "/" + name);
		Iterator<Entry> it = directory.iterator();
		while (it.hasNext()) {
			Entry entry = (Entry) it.next();
			entry.printList(prefix + "/" + name);
		}
	}
}
```

#### 4-4. Mainクラス
メイン処理を行うクラスです。

```java:Main.java
public class Main {
	public static void main(String[] args) {

		Directory workspaceDir = new Directory("workspace");
		Directory compositeDir = new Directory("composite");
		Directory testDir1 = new Directory("test1");
		Directory testDir2 = new Directory("test2");
		workspaceDir.add(compositeDir);
		workspaceDir.add(testDir1);
		workspaceDir.add(testDir2);

		File directory = new File("Directory.java");
		File entity = new File("Entity.java");
		File file = new File("file.java");
		File main = new File("main.java");
		compositeDir.add(directory);
		compositeDir.add(entity);
		compositeDir.add(file);
		compositeDir.add(main);
		workspaceDir.printList();
	}
}
```

#### 4-5. 実行結果
```
/workspace
/workspace/composite
/workspace/composite/Directory.java
/workspace/composite/Entity.java
/workspace/composite/file.java
/workspace/composite/main.java
/workspace/test1
/workspace/test2
```

## 5. メリット
全てのオブジェクト(File、Directory)は、共通の抽象クラスを持っているので、クライアントから見て、どれがFileなのかDirectoryなのか、中身を意識する必要がなく、一様に扱うことができます。
また、新たなクラス(例：SymbolicLink)を追加した場合でも、基底クラス(Entry)のインターフェースが変わらなければ、クライアントの処理には影響しません。

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
