## 1. はじめに
GoFのデザインパターン(23種)をまとめました。

## 2. デザインパターン一覧
#### 2-1. 生成に関するパターン
| デザインパターン        |概要|
|-------------------------|----|
|[**Abstract Factory**](https://github.com/i-tanaka730/design_pattern/blob/master/docs/デザインパターン%20～Abstract%20Factory～.md)         |関連する部品を組み合わせて製品を作る|
|[**Builder**](https://github.com/i-tanaka730/design_pattern/blob/master/docs/デザインパターン%20～Builder～.md)                  |複雑なインスタンスを組み立てる|
|[**Factory Method**](https://github.com/i-tanaka730/design_pattern/blob/master/docs/デザインパターン%20～Factory%20Method～.md)           |インスタンス作成をサブクラスにまかせる|
|[**Prototype**](https://github.com/i-tanaka730/design_pattern/blob/master/docs/デザインパターン%20～Prototype～.md)                |コピーしてインスタンスを作る|
|[**Singleton**](https://github.com/i-tanaka730/design_pattern/blob/master/docs/デザインパターン%20～Singleton～.md)                |たった1つのインスタンス|

#### 2-2. 構造に関するパターン
| デザインパターン        |概要|
|-------------------------|----|
|[**Adapter**](https://github.com/i-tanaka730/design_pattern/blob/master/docs/デザインパターン%20～Adapter～.md)                  |一皮かぶせて再利用    |
|[**Bridge**](https://github.com/i-tanaka730/design_pattern/blob/master/docs/デザインパターン%20～Bridge～.md)                   |機能の階層と実装の階層を分ける    |
|[**Composite**](https://github.com/i-tanaka730/design_pattern/blob/master/docs/デザインパターン%20～Composite～.md)                |容器と中身の同一視    |
|[**Decorator**](https://github.com/i-tanaka730/design_pattern/blob/master/docs/デザインパターン%20～Decorator～.md)                |飾り枠と中身の同一視    |
|[**Facade**](https://github.com/i-tanaka730/design_pattern/blob/master/docs/デザインパターン%20～Facade～.md)                   |シンプルな窓口    |
|[**Flyweight**](https://github.com/i-tanaka730/design_pattern/blob/master/docs/デザインパターン%20～Flyweight～.md)                |同じものを共有して無駄をなくす    |
|[**Proxy**](https://github.com/i-tanaka730/design_pattern/blob/master/docs/デザインパターン%20～Proxy～.md)                    |必要になってから作る    |

#### 2-3. 振る舞いに関するパターン
| デザインパターン        |概要|
|-------------------------|----|
|[**Chain of Responsibility**](https://github.com/i-tanaka730/design_pattern/blob/master/docs/デザインパターン%20～Chain%20of%20Responsibility～.md)  |責任のたらい回し    |
|[**Command**](https://github.com/i-tanaka730/design_pattern/blob/master/docs/デザインパターン%20～Command～.md)                  |命令をクラスにする|
|[**Interpreter**](https://github.com/i-tanaka730/design_pattern/blob/master/docs/デザインパターン%20～Interpreter～.md)              |文法規則をクラスで表現する    |
|[**Iterator**](https://github.com/i-tanaka730/design_pattern/blob/master/docs/デザインパターン%20～Iterator～.md)                 |1つ1つ数え上げる    |
|[**Mediator**](https://github.com/i-tanaka730/design_pattern/blob/master/docs/デザインパターン%20～Mediator～.md)                 |相手は相談役1人だけ    |
|[**Memento**](https://github.com/i-tanaka730/design_pattern/blob/master/docs/デザインパターン%20～Memento～.md)                  |状態を保存する    |
|[**Observer**](https://github.com/i-tanaka730/design_pattern/blob/master/docs/デザインパターン%20～Observer～.md)                 |状態の変化を通知する    |
|[**State**](https://github.com/i-tanaka730/design_pattern/blob/master/docs/デザインパターン%20～State～.md)                    |状態をクラスとして表現する    |
|[**Strategy**](https://github.com/i-tanaka730/design_pattern/blob/master/docs/デザインパターン%20～Strategy～.md)                 |アルゴリズムをごっそり切り替える    |
|[**Template Method**](https://github.com/i-tanaka730/design_pattern/blob/master/docs/デザインパターン%20～Template%20Method～.md)          |具体的な処理をサブクラスに任せる    |
|[**Visitor**](https://github.com/i-tanaka730/design_pattern/blob/master/docs/デザインパターン%20～Visitor～.md)                  |構造を渡り歩きながら仕事する    |

## 3. GitHub
- https://github.com/i-tanaka730/design_pattern

## 4. 参考
今回の記事、及びサンプルプログラムは、以下の書籍を元に作成させて頂きました。

- [**Java言語で学ぶデザインパターン入門**](
https://www.amazon.co.jp/%E5%A2%97%E8%A3%9C%E6%94%B9%E8%A8%82%E7%89%88Java%E8%A8%80%E8%AA%9E%E3%81%A7%E5%AD%A6%E3%81%B6%E3%83%87%E3%82%B6%E3%82%A4%E3%83%B3%E3%83%91%E3%82%BF%E3%83%BC%E3%83%B3%E5%85%A5%E9%96%80-%E7%B5%90%E5%9F%8E-%E6%B5%A9/dp/4797327030/ref=sr_1_1?ie=UTF8&qid=1549628781)

大変分かりやすく、勉強になりました。感謝申し上げます。
デザインパターンやサンプルプログラムについての説明が詳細に書かれていますので、是非書籍の方もご覧ください。
