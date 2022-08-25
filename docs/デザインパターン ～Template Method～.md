## 1. はじめに

GoFのデザインパターンにおける、**Template Methodパターン**についてまとめます。

## 2. Template Methodパターンとは
- Templateとは、**文字の形に穴が空いている薄いプラスチックの板**のことです。Templateを見ればどのような文字が書けるのかは分かりますが、実際にどういう文字になるかは具体的な筆記用具が決まらなければ分かりません。
- Template Methodパターンは、**スーパークラスで処理の枠組みを定め、サブクラスでその具体的内容を定める方式**です。
- GoFのデザインパターンでは、**振る舞いに関するデザインパターン**に分類されます。

## 3. サンプルクラス図
![TemplateMethod.PNG](https://qiita-image-store.s3.amazonaws.com/0/247638/7402c455-7e25-42ed-874d-553feee1f60b.png)

## 4. サンプルプログラム
モンスターの名前、攻撃力、守備力を表示するプログラムです。

#### 4-1. AbstractMonsterクラス
テンプレートとなるクラスです。

```java:AbstractMonster.java
public abstract class AbstractMonster {

	public String name;
	public abstract int getAttack();
	public abstract int getDefense();

	public final void showInfo() {
		System.out.print("名前：");
		System.out.println(name);
		System.out.print("攻撃力：");
		System.out.println(getAttack());
		System.out.print("守備力：");
		System.out.println(getDefense());
		System.out.println();
	}
}
```

#### 4-2. Slimeクラス
AbstractMonsterクラスで定義されたメソッドを実装するクラスです。

```java:Slime.java
public class Slime extends AbstractMonster {

	public Slime(String name) {
		this.name = name;
	}

	public int getAttack() {
		return 15;
	}

	public int getDefense() {
		return 10;
	}
}
```

#### 4-3. Dragonクラス
AbstractMonsterクラスで定義されたメソッドを実装するクラスです。

```java:Dragon.java
public class Dragon extends AbstractMonster {

	public Dragon(String name) {
		this.name = name;
	}

	public int getAttack() {
		return 60;
	}

	public int getDefense() {
		return 45;
	}
}
```

#### 4-4. Mainクラス
メイン処理を行うクラスです。

```java:Main.java
public class Main {
	public static void main(String[] args) {
		AbstractMonster slime = new Slime("スライムくん");
		AbstractMonster dragon = new Dragon("ドラゴンさん");
		slime.showInfo();
		dragon.showInfo();
	}
}
```

#### 4-5. 実行結果
```
名前：スライムくん
攻撃力：15
守備力：10

名前：ドラゴンさん
攻撃力：60
守備力：45
```

## 5. メリット
Template Methodパターンでは、スーパークラスのテンプレートメソッドでアルゴリズムが記載されているので、サブクラス側ではアルゴリズムをいちいち記述する必要がなくなります。
例えば、Template Methodパターンを使わず、似たようなクラス、Class1、Class2、Class3・・・を作っていた場合、Class1にバグが発見されると、そのバグをClass2、Class3・・・に反映させなくてはなりません。
Template Methodパターンで作成していれば、テンプレートメソッドにバグが発見された場合でも、テンプレートメソッドさえ修正すればよいことになります。

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
