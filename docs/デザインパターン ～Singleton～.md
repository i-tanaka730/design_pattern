## 1. はじめに

GoFのデザインパターンにおける、**Singletonパターン**についてまとめます。

## 2. Singletonパターンとは
- Singletonとは、**要素を1つしか持たない集合**という意味になります。
- Singletonパターンは、**インスタンスが1つしか存在しないことを保証する方式**です。
- 例えば、システム設定を表現したクラス、ウィンドウシステムを表現したクラス等があげられます。
- GoFのデザインパターンでは、**生成に関するデザインパターン**に分類されます。

## 3. サンプルクラス図
![Singleton.PNG](https://qiita-image-store.s3.amazonaws.com/0/247638/30c05b63-aadc-51bb-9f41-7b2c6ab565b7.png)

## 4. サンプルプログラム
シングルトンインスタンスを生成するプログラムです。

#### 4-1. Singletonクラス
唯一のインスタンスを返すクラスです。
Singletonクラスのコンストラクタはprivateになっています。これはSingletonクラス外からコンストラクタを呼び出すことを禁止するためです。

```java:Singleton.cs
public class Singleton {

	private static Singleton singleton = new Singleton();

	private Singleton() {
		System.out.println("インスタンスを生成しました。");
	}

	public static Singleton getInstance() {
		return singleton;
	}
}
```

#### 4-2. Mainクラス
メイン処理を行うクラスです。

```java:Main.cs
public class Main {
	public static void main(String[] args) {
		Singleton obj1 = Singleton.getInstance();
		Singleton obj2 = Singleton.getInstance();
		if (obj1 == obj2) {
			System.out.println("obj1とobj2は同じインスタンスです。");
		} else {
			System.out.println("obj1とobj2は同じインスタンスではありません。");
		}
	}
}
```

#### 4-3. 実行結果
```
インスタンスを生成しました。
obj1とobj2は同じインスタンスです。
```

## 5. メリット
Singletonパターンではインスタンスの数に制限を設けています。
インスタンスが複数存在すると、インスタンスたちが相互に影響し合って、思いがけないバグを生み出してしまうことがあります。
しかし、インスタンスが1つしかないという保証があれば、その前提条件でプログラミングすることができます。

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
