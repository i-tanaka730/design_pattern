## 1. はじめに

GoFのデザインパターンにおける、**Prototypeパターン**についてまとめます。

## 2. Prototypeパターンとは
- Prototypeという英単語は、**原型**や**模範**という意味になります。
- Prototypeパターンは、new xxx()でクラスからインスタンスを生成するのではなく、**インスタンスから別のインスタンスを作り出す方式**です。
- 複製を作る操作を**clone**と呼びます。
- GoFのデザインパターンでは、**生成に関するデザインパターン**に分類されます。

## 3. サンプルクラス図
![Prototype.PNG](https://qiita-image-store.s3.amazonaws.com/0/247638/d9db27f4-f670-f9f5-e0ee-eddae4ad9c49.png)

## 4. サンプルプログラム
入力した文字列に対し、下線を引いたり、囲んだりするプログラムです。

#### 4-1. Productインターフェース
複製メソッドを定義するインターフェースです。javaのCloneableインターフェースを継承しています。

```java:Product.java
package framework;
import java.lang.Cloneable;

public interface Product extends Cloneable {
    public abstract void use(String s);
    public abstract Product createClone();
}
```

#### 4-2. Managerクラス
Productの作成指示や管理を行うクラスです。

```java:Manager.java
package framework;
import java.util.HashMap;

public class Manager {

	private HashMap showcase = new HashMap();

	public void register(String name, Product proto) {
        showcase.put(name, proto);
    }

	public Product create(String protoname) {
        Product p = (Product)showcase.get(protoname);
        return p.createClone();
    }
}
```

#### 4-3. UnderlinePenクラス
Productインターフェースを実装するクラスです。当クラスは「文字に下線を引く」クラスになります。

```java:UnderlinePen.java
import framework.Product;

public class UnderlinePen implements Product {

	private char ulchar;

	public UnderlinePen(char ulchar) {
        this.ulchar = ulchar;
    }

	public void use(String s) {

		int length = s.getBytes().length;

		System.out.println(s);

        for (int i = 0; i < length; i++) {
            System.out.print(ulchar);
        }

        System.out.println("");
    }

	public Product createClone() {
        Product p = null;
        try {
            p = (Product)clone();
        } catch (CloneNotSupportedException e) {
        	// オブジェクトのクラスがCloneableインタフェースを実装していない場合にスローされる例外
            e.printStackTrace();
        }
        return p;
    }
}
```

#### 4-4. MessageBoxクラス
Productインターフェースを実装するクラスです。当クラスは「文字を囲む」クラスになります。

```java:MessageBox.java
import framework.Product;

public class MessageBox implements Product {

	private char decochar;

	public MessageBox(char decochar) {
        this.decochar = decochar;
    }

	public void use(String s) {

		int length = s.getBytes().length;

		for (int i = 0; i < length + 2; i++) {
            System.out.print(decochar);
        }

		System.out.println("");
        System.out.println(decochar + s + decochar);

        for (int i = 0; i < length + 2; i++) {
            System.out.print(decochar);
        }

        System.out.println("");
    }

	public Product createClone() {
		Product p = null;
		try {
            p = (Product)clone();
        } catch (CloneNotSupportedException e) {
        	// オブジェクトのクラスがCloneableインタフェースを実装していない場合にスローされる例外
        	e.printStackTrace();
        }
        return p;
    }
}
```

#### 4-5. Mainクラス
メイン処理を行うクラスです。

```java:Main.java
import framework.Manager;
import framework.Product;

public class Main {
    public static void main(String[] args) {

        Manager manager = new Manager();
        UnderlinePen upen = new UnderlinePen('~');
        MessageBox mbox = new MessageBox('*');
        MessageBox pbox = new MessageBox('+');
        manager.register("strong message", upen);
        manager.register("warning box", mbox);
        manager.register("slash box", pbox);

        Product p1 = manager.create("strong message");
        p1.use("Hello world");
        Product p2 = manager.create("warning box");
        p2.use("Hello world");
        Product p3 = manager.create("slash box");
        p3.use("Hello world");
    }
}
```

#### 4-6. 実行結果
```
Hello World
===========
*************
*Hello World*
*************
+++++++++++++
+Hello World+
+++++++++++++
```

## 5. メリット
サンプルプログラムでは比較的シンプルなケースでしたが、インスタンスを生成するにあたり、複雑な処理が必要だったり、時間がかかる処理が必要だったりする場合があります。インスタンスを生成する度にnew xxx()としていては、とても非効率になります。
Prototype(雛形)を作っておいてコピーするだけで、簡単にインスタンスを生成することができます。

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
