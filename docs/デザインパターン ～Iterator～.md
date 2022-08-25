## 1. はじめに

GoFのデザインパターンにおける、**Iteratorパターン**についてまとめます。

## 2. Iteratorパターンとは
- Iterateという英単語は、**何かを繰返す**という意味になります。
- Iteratorパターンは、**集合体の要素に対し、順番にアクセスする処理を行うための方式**です。
- GoFのデザインパターンでは、**振る舞いに関するデザインパターン**に分類されます。

## 3. サンプルクラス図
![Iterator.PNG](https://qiita-image-store.s3.amazonaws.com/0/247638/fd193ad0-f0dc-a8ab-0ec6-4aa9a80d6b5d.png)

## 4. サンプルプログラム
クラス(教室)に生徒を入れ、生徒の名前を順番に表示するプログラムです。

#### 4-1. Iteratorインターフェース
要素を順番にスキャンしていくインターフェースです。

```java:Iterator.java
public interface Iterator {
	public abstract boolean hasNext();
	public abstract Object next();
}
```
#### 4-2. Aggregateインターフェース
Iteratorを作り出すインターフェースです。サンプルでは「集合体」としています。

```java:Aggregate.java
public interface Aggregate {
	public abstract Iterator iterator();
}
```

#### 4-3. Studentクラス
集合体の要素となるクラスです。サンプルでは「生徒」としています。

```java:Student.java
public class Student {

	private String name;

	public Student(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
```

#### 4-4. ClassRoomクラス
Aggregateが定めたインターフェースを実装するクラスです。サンプルでは「教室」としています。

```java:ClassRoom.java
public class ClassRoom implements Aggregate {

	private Student[] students;
	private int last = 0;

	public ClassRoom(int maxsize) {
		this.students = new Student[maxsize];
	}

	public Student getStudentAt(int index) {
		return students[index];
	}

	public void appendStudent(Student student) {
		this.students[last] = student;
		last++;
	}

	public int getLength() {
		return last;
	}

	public Iterator iterator() {
		return new ClassRoomIterator(this);
	}
}
```

#### 4-5. ClassRoomIteratorクラス
Iteratorが定めたインターフェースを実装するクラスです。

```java:ClassRoomIterator.java
public class ClassRoomIterator implements Iterator {

	private ClassRoom classRoom;
	private int index;

	public ClassRoomIterator(ClassRoom classRoom) {
		this.classRoom = classRoom;
		this.index = 0;
	}

	public boolean hasNext() {
		if (index < classRoom.getLength()) {
			return true;
		} else {
			return false;
		}
	}

	public Object next() {
		Student student = classRoom.getStudentAt(index);
		index++;
		return student;
	}
}
```
#### 4-6. Mainクラス
メイン処理を行うクラスです。

```java:Main.java
public class Main {
	public static void main(String[] args) {

		ClassRoom classRoom = new ClassRoom(4);
		classRoom.appendStudent(new Student("田中"));
		classRoom.appendStudent(new Student("山田"));
		classRoom.appendStudent(new Student("鈴木"));
		classRoom.appendStudent(new Student("佐藤"));

		Iterator iterator= classRoom.iterator();

		while (iterator.hasNext()) {
			Student student = (Student)iterator.next();
			System.out.println(student.getName());
		}
	}
}
```

#### 4-7. 実行結果
```
田中
山田
鈴木
佐藤
```

## 5. メリット
Iteratorパターンのメリットとしては、**実装とは切り離して、数え上げを行うことができること**にあります。
デザインパターンはクラスを部品として使えるようにし、再利用性を促進するものなのです。
サンプルプログラムの**Mainクラス**で使われているIteratorメソッドは、**hasNext()、next()**のみになります。つまり、**ClassRoomクラス**に依存しない実装となっており、配列のサイズ等を気にしなくてよくなります。

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
