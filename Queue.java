public class Queue {
	
	public listNode front;
	public listNode back;
	
//add elements in the queue
public void Enqueue (String newStr){
	
	listNode nn = new listNode(newStr);
	
	if (back == null) {//when the queue is empty 
		
		front=nn; 
		back=nn;//one element which will be the front and the back
	}
else {//when there are already elements in the queue
	nn.next=back;
	back.previous=nn;
	back=nn;
	}
}

//Remove elements from the queue
public String Dequeue () {
	
	if(front==null) {//when the queue is empty, there are nothing to remove
	return null;
	}
	
	String result_str = front.key_val;
	
	if(front.previous==null) {
		front=null;
		back=null;
	}
	else {
		listNode pre_front=front.previous;
		pre_front.next=null;
		front= pre_front;
		
		}
	
	return result_str;
}

}
