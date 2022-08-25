## 1. はじめに

GoFのデザインパターンにおける、**Commandパターン**についてまとめます。

## 2. Commandパターンとは
- Commandという英単語は、**命令**という意味になります。
- Commandパターンは、**命令を表すクラスのインスタンスを、1つのものとして表現する方式**です。
- 命令の履歴を管理したいときは、そのインスタンスの集まりを管理すればいいことになります。命令の集まりを保存しておけば、同じ命令を実行したり、複数の命令をまとめて新しい命令として再利用したりできます。
- GoFのデザインパターンでは、**振る舞いに関するデザインパターン**に分類されます。

## 3. サンプルクラス図
![Command.PNG](https://qiita-image-store.s3.amazonaws.com/0/247638/85964ae8-8d10-9de6-1615-2e380e75162f.png)

## 4. サンプルプログラム
簡単なお絵かきプログラムです。

#### 4-1. Commandインターフェース
命令を表現するインターフェースです。

```java:Command.java
package command;

public interface Command {
	public abstract void execute();
}
```

#### 4-2. MacroCommandクラス
「複数の命令をまとめた命令」を表現するクラスです。

```java:MacroCommand.java
package command;

import java.util.Iterator;
import java.util.Stack;

public class MacroCommand implements Command {

	// 命令の集合
	private Stack commands = new Stack();

	public void execute() {
		Iterator it = commands.iterator();
		while (it.hasNext()) {
			((Command) it.next()).execute();
		}
	}

	// 追加
	public void append(Command cmd) {
		if (cmd != this) {
			commands.push(cmd);
		}
	}

	// 最後の命令を削除
	public void undo() {
		if (!commands.empty()) {
			commands.pop();
		}
	}

	// 全部削除
	public void clear() {
		commands.clear();
	}
}
```

#### 4-3. Drawableインターフェース
「描画対象」を表現するインターフェースです。

```java:Drawable.java
package drawer;

public interface Drawable {
	public abstract void draw(int x, int y);
}
```

#### 4-4. DrawCanvasクラス
「描画対象」を実装したクラスです。

```java:DrawCanvas.java
package drawer;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

import command.MacroCommand;

public class DrawCanvas extends Canvas implements Drawable {

	// 描画色
	private Color color = Color.red;
	// 描画する点の半径
	private int radius = 6;
	// 履歴
	private MacroCommand history;

	public DrawCanvas(int width, int height, MacroCommand history) {
		setSize(width, height);
		setBackground(Color.white);
		this.history = history;
	}

	// 履歴全体を再描画
	public void paint(Graphics g) {
		history.execute();
	}

	// 描画
	public void draw(int x, int y) {
		Graphics g = getGraphics();
		g.setColor(color);
		g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
	}
}
```

#### 4-5. DrawCommandクラス
「点の描画命令」を表現するクラスです。

```java:DrawCommand.java
package drawer;

import java.awt.Point;

import command.Command;

public class DrawCommand implements Command {

	// 描画対象
	protected Drawable drawable;
	// 描画位置
	private Point position;

	public DrawCommand(Drawable drawable, Point position) {
		this.drawable = drawable;
		this.position = position;
	}

	public void execute() {
		drawable.draw(position.x, position.y);
	}
}
```

#### 4-6. Mainクラス
メイン処理を行うクラスです。

```java:Main.java
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

import command.Command;
import command.MacroCommand;
import drawer.DrawCanvas;
import drawer.DrawCommand;

public class Main extends JFrame implements ActionListener, MouseMotionListener, WindowListener {

	// 描画履歴
	private MacroCommand history = new MacroCommand();
	// 描画領域
	private DrawCanvas canvas = new DrawCanvas(400, 400, history);
	// アンドゥボタン
	private JButton undoButton = new JButton("undo");
	// 消去ボタン
	private JButton clearButton = new JButton("clear");

	public Main(String title) {
		super(title);

		this.addWindowListener(this);
		canvas.addMouseMotionListener(this);
		undoButton.addActionListener(this);
		clearButton.addActionListener(this);

		Box buttonBox = new Box(BoxLayout.X_AXIS);
		buttonBox.add(undoButton);
		buttonBox.add(clearButton);
		Box mainBox = new Box(BoxLayout.Y_AXIS);
		mainBox.add(buttonBox);
		mainBox.add(canvas);
		getContentPane().add(mainBox);

		pack();
		show();
	}

	// ActionListener用
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == undoButton) {
			history.undo();
			canvas.repaint();
		} else if (source == clearButton) {
			history.clear();
			canvas.repaint();
		}
	}

	// MouseMotionListener用
	public void mouseMoved(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
		Command cmd = new DrawCommand(canvas, e.getPoint());
		history.append(cmd);
		cmd.execute();
	}

	// WindowListener用
	public void windowClosing(WindowEvent e) {
	}

	public void windowActivated(WindowEvent e) {
	}

	public void windowClosed(WindowEvent e) {
	}

	public void windowDeactivated(WindowEvent e) {
	}

	public void windowDeiconified(WindowEvent e) {
	}

	public void windowIconified(WindowEvent e) {
	}

	public void windowOpened(WindowEvent e) {
	}

	public static void main(String[] args) {
		new Main("Command Pattern Sample");
	}
}
```

#### 4-7. 実行結果
![e.png](https://qiita-image-store.s3.amazonaws.com/0/247638/c718fb45-7dc9-6866-afb3-88fffd400bf1.png)

## 5. メリット
「命令」をオブジェクトとして表現することで、命令の履歴をとったり、命令の再実行を行ったりすることができます。
また、新しい「命令」を追加したい場合は、Commandインターフェースを実装したクラスを作成すればよいだけなので、機能拡張が行いやすくなります。

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
