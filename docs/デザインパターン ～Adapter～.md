## 1. はじめに

GoFのデザインパターンにおける、**Adapterパターン**についてまとめます。

## 2. Adapterパターンとは
- Adapterという英単語は、**適合させるもの**という意味になります。
- Adapterパターンは、**既に提供されているがそのまま使えないものに対し、必要な形に変換して利用するための方式**です。
- **継承**を利用した方法と**委譲**を利用した方法があります。
- **Wrapperパターン**と呼ばれることもあります。Wrapperは**包むもの**という意味になります。
- GoFのデザインパターンでは、**構造に関するデザインパターン**に分類されます。

## 3. サンプルクラス図
### 3-1. ～継承を利用した方法～
![Adapter.PNG](https://qiita-image-store.s3.amazonaws.com/0/247638/e962c054-9cf0-7dff-3df6-af40ff93cd6a.png)

### 3-2. ～委譲を利用した方法～
![Adapter2.PNG](https://qiita-image-store.s3.amazonaws.com/0/247638/52735eb8-0893-6221-ac56-929cef6b6601.png)

## 4. サンプルプログラム
生徒の名前と年齢を表示するプログラムです。

### 4-1. ～継承を利用した方法～
#### 4-1-1. Humanクラス
元々提供されているクラスです。

```java:Human.java
public class Human {

	private String name;
	private int age;

	public Human(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public void printName() {
		System.out.println(name);
	}

	public void printAge() {
		System.out.println(age);
	}
}
```

#### 4-1-2. Studentインターフェース
必要とされているインターフェースです。

```java:Student.java
public interface Student {
	public abstract void showName();
	public abstract void showAge();
}
```

#### 4-1-3. HumanAdapterクラス
Adapter役となるクラスです。

```java:HumanAdapter.java
public class HumanAdapter extends Human implements Student {

	public HumanAdapter(String name, int age) {
		super(name, age);
	}

	public void showName() {
		printName();
	}

	public void showAge() {
		printAge();
	}
}
```

#### 4-1-4. Mainクラス
メイン処理を行うクラスです。

```java:Main.java
public class Main {
	public static void main(String[] args) {
		Student student = new HumanAdapter("田中", 25);
		student.showName();
		student.showAge();
	}
}
```

#### 4-1-5. 実行結果
```
田中
25
```
### 4-2. ～委譲を利用した方法～
#### 4-2-1. Humanクラス
元々提供されているクラスです。

```java:Human.java
public class Human {

	private String name;
	private int age;

	public Human(String name, int age) {
        this.name = name;
        this.age = age;
	}

	public void printName() {
        System.out.println(name);
    }

	public void printAge() {
        System.out.println(age);
    }
}
```

#### 4-2-2. Studentインターフェース
必要とされているインターフェースです。

```java:Student.java
public interface Student {
    public abstract void showName();
    public abstract void showAge();
}
```

#### 4-2-3. HumanAdapterクラス
Adapter役となるクラスです。

```java:HumanAdapter.java
public class HumanAdapter implements Student {

	private Human human;

	public HumanAdapter(String name, int age) {
		this.human = new Human(name, age);
    }

	public void showName() {
		human.printName();
    }

	public void showAge() {
		human.printAge();
    }
}
```

#### 4-2-4. Mainクラス
メイン処理を行うクラスです。

```java:Main.java
public class Main {
    public static void main(String[] args) {
    	Student student = new HumanAdapter("田中", 25);
    	student.showName();
    	student.showAge();
    }
}
```

#### 4-2-5. 実行結果
```
田中
25
```

## 5. メリット
Adapterパターンは、既存のクラスに一皮かぶせて必要とするクラスを作ります。このパターンによって、必要とするメソッド群を素早く作ることができます。もしバグが検出されたとしても、既存のクラスが十分にテストされているのであれば、Adapter役のクラスを重点的に調べればよいことになるので、プログラムのチェックが楽になります。
また、Adapterパターンでは既存のクラスには手を加えずに機能を実現できるので、既存のクラスをもう一度テストする手間を減らすことができます。

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
