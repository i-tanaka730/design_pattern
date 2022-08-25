## 1. はじめに

GoFのデザインパターンにおける、**Factory Methodパターン**についてまとめます。

## 2. Factory Methodパターンとは
- Factoryという英単語は、**工場**という意味になります。
- Factory Methodパターンは、**インスタンスの作り方をスーパークラスで定め、具体的な生成処理はサブクラス側で行う方式**です。
- インスタンスを生成する工場をTmplate Methodパターンで構成したものがFactory Methodパターンになります。
- GoFのデザインパターンでは、**生成に関するデザインパターン**に分類されます。

## 3. サンプルクラス図
![FactoryMethod.PNG](https://qiita-image-store.s3.amazonaws.com/0/247638/b310b76b-6145-8ad8-a76e-13e3888238b3.png)

## 4. サンプルプログラム
IDカード工場から、IDカードを作成するプログラムです。

#### 4-1. Factoryクラス
Factoryの基底となるクラスです。インスタンスの作り方を定めます。

```java:Factory.java
package framework;

public abstract class Factory {

	public final Product create(String owner) {
        Product product = createProduct(owner);
        registerProduct(product);
        return product;
    }

	protected abstract Product createProduct(String owner);
    protected abstract void registerProduct(Product product);
}
```

#### 4-2. Productクラス
Factoryにて生成されるオブジェクトの基底となるクラスです。

```java:Product.java
package framework;

public abstract class Product {
    public abstract void use();
}
```

#### 4-3. IDCardFactoryクラス
Factoryクラスで定義されたメソッドを実装する具象クラスです。

```java:IDCardFactory.java
package idcard;
import java.util.ArrayList;

import framework.Factory;
import framework.Product;

public class IDCardFactory extends Factory {

	private ArrayList<String> owners = new ArrayList<String>();

	protected Product createProduct(String owner) {
        return new IDCard(owner);
    }

	protected void registerProduct(Product product) {
		IDCard icCard = (IDCard)product;
		String owner = icCard.getOwner();
		owners.add(owner);
    }

	public ArrayList<String> getOwners() {
        return owners;
    }
}
```

#### 4-4. IDCardクラス
Productクラスで定義されたメソッドを実装する具象クラスです。

```java:IDCard.java
package idcard;
import framework.Product;

public class IDCard extends Product {

	private String owner;

	IDCard(String owner) {
        System.out.println(owner + "のカードを作ります。");
        this.owner = owner;
    }

	public void use() {
        System.out.println(owner + "のカードを使います。");
    }

	public String getOwner() {
        return owner;
    }
}
```

#### 4-5. Mainクラス
メイン処理を行うクラスです。

```java:Main.java
import framework.*;
import idcard.*;

public class Main {
    public static void main(String[] args) {
        Factory factory = new IDCardFactory();
        Product card1 = factory.create("山田");
        Product card2 = factory.create("鈴木");
        Product card3 = factory.create("佐藤");
        card1.use();
        card2.use();
        card3.use();
    }
}
```

#### 4-6. 実行結果
```
山田のカードを作ります。
鈴木のカードを作ります。
佐藤のカードを作ります。
山田のカードを使います。
鈴木のカードを使います。
佐藤のカードを使います。
```

## 5. メリット
Factory/Productはframeworkパッケージ、IDCardFactory/IDCardはidcardパッケージに存在します。
frameworkパッケージでは、idcardパッケージをインポートしていません。つまり、**frameworkパッケージでは、idcardパッケージに依存しない**形になっています。
全く別の「製品」と「工場」を作ろうとした場合、frameworkパッケージの中身を修正する必要がなくなります。

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
