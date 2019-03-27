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
