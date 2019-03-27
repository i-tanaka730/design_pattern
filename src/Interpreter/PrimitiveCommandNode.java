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
