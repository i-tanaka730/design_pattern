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
