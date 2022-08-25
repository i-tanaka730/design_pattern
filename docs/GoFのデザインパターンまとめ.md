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
|[**Chain of Responsibility**](https://qiita.com/i-tanaka730/items/073c106c58d7c74c1706)  |責任のたらい回し    |
|[**Command**](https://qiita.com/i-tanaka730/items/6f8c3423eda95c230f3b)                  |命令をクラスにする|
|[**Interpreter**](https://qiita.com/i-tanaka730/items/adf5090cdbfd55cbc9b5)              |文法規則をクラスで表現する    |
|[**Iterator**](https://qiita.com/i-tanaka730/items/7c178409a4d5c1e4e42b)                 |1つ1つ数え上げる    |
|[**Mediator**](https://qiita.com/i-tanaka730/items/9f96d28d32ab3c9099d9)                 |相手は相談役1人だけ    |
|[**Memento**](https://qiita.com/i-tanaka730/items/c664aeec431da492f5de)                  |状態を保存する    |
|[**Observer**](https://qiita.com/i-tanaka730/items/b69cd773d81e044a2ec9)                 |状態の変化を通知する    |
|[**State**](https://qiita.com/i-tanaka730/items/49ee4e3daa3aeaf6e0b5)                    |状態をクラスとして表現する    |
|[**Strategy**](https://qiita.com/i-tanaka730/items/4d00c884b7ce1594f42a)                 |アルゴリズムをごっそり切り替える    |
|[**Template Method**](https://qiita.com/i-tanaka730/items/dbeb82ac44c031fe8df9)          |具体的な処理をサブクラスに任せる    |
|[**Visitor**](https://qiita.com/i-tanaka730/items/2e2d4fac2075b3e45ef7)                  |構造を渡り歩きながら仕事する    |

## 3. GitHub
- https://github.com/i-tanaka730/design_pattern

## 4. 参考
今回の記事、及びサンプルプログラムは、以下の書籍を元に作成させて頂きました。

- [**Java言語で学ぶデザインパターン入門**](
https://www.amazon.co.jp/%E5%A2%97%E8%A3%9C%E6%94%B9%E8%A8%82%E7%89%88Java%E8%A8%80%E8%AA%9E%E3%81%A7%E5%AD%A6%E3%81%B6%E3%83%87%E3%82%B6%E3%82%A4%E3%83%B3%E3%83%91%E3%82%BF%E3%83%BC%E3%83%B3%E5%85%A5%E9%96%80-%E7%B5%90%E5%9F%8E-%E6%B5%A9/dp/4797327030/ref=sr_1_1?ie=UTF8&qid=1549628781)

大変分かりやすく、勉強になりました。感謝申し上げます。
デザインパターンやサンプルプログラムについての説明が詳細に書かれていますので、是非書籍の方もご覧ください。
