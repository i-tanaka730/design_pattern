## 1. はじめに

GoFのデザインパターンにおける、**Interpreterパターン**についてまとめます。

## 2. Interpreterパターンとは
- Interpreterという英単語は、**通訳**という意味になります。
- Interpreterパターンは、**何らかの形式で書かれたファイルの中身を、「通訳」の役目を果たすプログラムで解析・表現する方式**です。
- GoFのデザインパターンでは、**振る舞いに関するデザインパターン**に分類されます。

## 3. サンプルクラス図
![Interpreter.PNG](https://qiita-image-store.s3.amazonaws.com/0/247638/7df23001-af26-f747-2a33-71bac0d8d238.png)

## 4. サンプルプログラム
テキストファイルに書かれた言語を構文解析するプログラムです。
構文解析対象テキストで使う言語の文法ではBNF記法を使います。

```
<program> ::= program <command list>
<command list> ::= <command>* end
<command> ::= <repeat command> | <primitive command>
<repeat command> ::= repeat <number> <command list>
<primitive command> ::= go | right | left
```
- `<program>`・・・トークン program の後にコマンドの列<command list>が続いたもの、になります。
- `<command list>`・・・<command>が0個以上繰り返したあとトークン end が続いたもの、になります。
- `<command>`・・・繰り返しコマンド<repeat command>または基本コマンド<primitive command>のいずれか、になります。( | は or を表す)
- `<repeat command>`・・・トークン repeat のあとに繰り返し回数<number>が続き，さらにコマンドの列<command list>が続いたもの、になります。
- `<primitive command>`・・・go または right または left 、になります。

#### 4-1. Contextクラス
構文解析のための前後関係を表すクラスです。

```java:Context.java
import java.util.StringTokenizer;

public class Context {

	private StringTokenizer tokenizer;
	private String currentToken;

	public Context(String text) {
		tokenizer = new StringTokenizer(text);
		nextToken();
	}

	public String nextToken() {
		if (tokenizer.hasMoreTokens()) {
			currentToken = tokenizer.nextToken();
		} else {
			currentToken = null;
		}
		return currentToken;
	}

	public String currentToken() {
		return currentToken;
	}

	public void skipToken(String token) throws Exception {
		if (!token.equals(currentToken)) {
			throw new Exception("Warning: " + token + " is expected, but " + currentToken + " is found.");
		}
		nextToken();
	}

	public int currentNumber() throws Exception {
		int number = 0;
		try {
			number = Integer.parseInt(currentToken);
		} catch (NumberFormatException e) {
			throw new Exception("Warning: " + e);
		}
		return number;
	}
}
```

#### 4-2. Nodeクラス
構文木の「ノード」となるクラスです。

```java:Node.java
public abstract class Node {
	public abstract void parse(Context context) throws Exception;
}
```

#### 4-3. ProgramNodeクラス
<program>に対応するクラスです。

```java:ProgramNode.java
// <program> ::= program <command list>
public class ProgramNode extends Node {

	private Node commandListNode;

	public void parse(Context context) throws Exception {
		context.skipToken("program");
		commandListNode = new CommandListNode();
		commandListNode.parse(context);
	}

	public String toString() {
		return "[program " + commandListNode + "]";
	}
}
```

#### 4-4. CommandNodeクラス
<command>に対応するクラスです。

```java:CommandNode.java
// <command> ::= <repeat command> | <primitive command>
public class CommandNode extends Node {

	private Node node;

	public void parse(Context context) throws Exception {
		if (context.currentToken().equals("repeat")) {
			node = new RepeatCommandNode();
			node.parse(context);
		} else {
			node = new PrimitiveCommandNode();
			node.parse(context);
		}
	}

	public String toString() {
		return node.toString();
	}
}
```

#### 4-5. RepeatCommandNodeクラス
<repeat command>に対応するクラスです。

```java:RepeatCommandNode.java
// <repeat command> ::= repeat <number> <command list>
public class RepeatCommandNode extends Node {

	private int number;
	private Node commandListNode;

	public void parse(Context context) throws Exception {
		context.skipToken("repeat");
		number = context.currentNumber();
		context.nextToken();
		commandListNode = new CommandListNode();
		commandListNode.parse(context);
	}

	public String toString() {
		return "[repeat " + number + " " + commandListNode + "]";
	}
}
```

#### 4-6. CommandListNodeクラス
<command list>に対応するクラスです。

```java:CommandListNode.java
import java.util.ArrayList;

// <command list> ::= <command>* end
public class CommandListNode extends Node {

	private ArrayList list = new ArrayList();

	public void parse(Context context) throws Exception {
		while (true) {
			if (context.currentToken() == null) {
				throw new Exception("Missing 'end'");
			} else if (context.currentToken().equals("end")) {
				context.skipToken("end");
				break;
			} else {
				Node commandNode = new CommandNode();
				commandNode.parse(context);
				list.add(commandNode);
			}
		}
	}

	public String toString() {
		return list.toString();
	}
}
```

#### 4-7. PrimitiveCommandNodeクラス
<primitive command>に対応するクラスです。

```java:PrimitiveCommandNode.java
// <primitive command> ::= go | right | left
public class PrimitiveCommandNode extends Node {

	private String name;

	public void parse(Context context) throws Exception {
		name = context.currentToken();
		context.skipToken(name);
		if (!name.equals("go") && !name.equals("right") && !name.equals("left")) {
			throw new Exception(name + " is undefined");
		}
	}

	public String toString() {
		return name;
	}
}
```

#### 4-8. 構文解析対象テキスト
構文解析対象となるテキストです。

```java:program.txt
program end
program go end
program go right go right go right go right end
program repeat 4 go right end end
program repeat 4 repeat 3 go right go left end right end end
```

#### 4-9. Mainクラス
メイン処理を行うクラスです。

```java:Main.java
import java.io.BufferedReader;
import java.io.FileReader;

public class Main {
	public static void main(String[] args) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("program.txt"));
			String text;
			while ((text = reader.readLine()) != null) {
				System.out.println("text = \"" + text + "\"");
				Node node = new ProgramNode();
				node.parse(new Context(text));
				System.out.println("node = " + node);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
```

#### 4-10. 実行結果
```
text = "program end"
node = [program []]
text = "program go end"
node = [program [go]]
text = "program go right go right go right go right end"
node = [program [go, right, go, right, go, right, go, right]]
text = "program repeat 4 go right end end"
node = [program [[repeat 4 [go, right]]]]
text = "program repeat 4 repeat 3 go right go left end right end end"
node = [program [[repeat 4 [[repeat 3 [go, right, go, left]], right]]]]
```

## 5. メリット
Interpreterパターンを利用することで、規則の追加や変更が容易になります。
Interpreterパターンの特徴の1つに、「1つの規則を1つのクラスで表す」というものが挙げられます。つまり、新しい規則を追加する場合はNodeクラスのサブクラスを追加するだけで良くなります。
また、規則を修正する場合も、Nodeクラスのサブクラスを修正するだけになります。

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
