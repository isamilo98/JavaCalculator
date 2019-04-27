public class Stack {

public listNode top;

//add elements in the stack
	public void push (String newStr) {
		
	listNode nn = new listNode(newStr);

//the stack isn't empty	
	if(top != null) {
		
		top.next=nn;
		nn.previous=top;
		
	}
		top=nn; //when the stack is empty
		
		}

	//remove elements from the stack
	public String pop() {
		
		if(top==null) {//if the stack is empty
			
			return null;
		}
		
		String result_str = top.key_val;	
		
		if(top.previous==null) {
			
			top=null;
		}
		else {
		listNode pre_top=top.previous;
		pre_top.next=null;
		top=pre_top;
		
		}
		return result_str;
	}
	
}