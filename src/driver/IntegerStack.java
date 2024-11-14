package driver;

public class IntegerStack extends DoublyLinkedList<String> implements iStack<String> {

	@Override
	public void Push(Node<String> node) {
		this.InsertAfter(this.Tail, node);
	}

	@Override
	public Node<String> Pop() {
		if (this.IsEmpty()) {
			return null;
		}
		Node<String> ret = this.Tail;
		this.Remove(ret);
		return ret;
	}

	@Override
	public Node<String> Peek() {
		return this.Tail;
	}

}
