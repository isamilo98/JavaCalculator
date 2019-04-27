/* Name : Ziouti Ismail 
 * 		ID : 260807219
 * 
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import acm.gui.TableLayout;
import acm.program.Program;

public class JCalcGUI extends Program implements ChangeListener, ActionListener{

	String c,d;
	Queue intQ = new Queue();
	Queue outQ = new Queue();
	Queue outQ2 = new Queue();
	Stack stack = new Stack();
	Stack Bstack = new Stack();//I create a second stack to push the number and evaluate the postfix
	int comparator;
	double result = 0;
	double nub1, nub2;
	
	String prefix = "";
	String infix = "";
	JTextField input = new JTextField(""); // input text field is empty
	JTextField output = new JTextField(""); // output text field is empty
	JTextField prec_tf = new JTextField(""); // precision text field is empty for now
	JSlider prec_slider;
	
	public void init()
	{
		
		setSize(500, 400);//dimension of the applet
		setLayout(new TableLayout(8, 4));
		
		output.setEditable(false);
		output.setBackground(Color.WHITE);
		
		// To add we need the object and we need to give some constraint (String)
		add(input,"gridwidth = 4 height = 25");
		add(output,"gridwidth = 4 height = 25");
		
		String BUTTON_SIZE = "60";//the size of the buttons
		String button_label[]= {"C","+/-","%","/","7","8","9","x","4","5","6","-","1","2","3","+","0",".","^","="};
		
		String constraint = "width=" + BUTTON_SIZE + " height=" + BUTTON_SIZE;
		
		//Create the buttons
		for (int i = 0; i < button_label.length; i++) {
			
			JButton cur_button = new JButton(button_label[i]);
			cur_button.setFont(new Font("Times new Roman", Font.PLAIN, 15));
			cur_button.addActionListener(this);
			
			add(cur_button, constraint);
		}

	//Label
		add(new JLabel("Precision"));
		
	//Slider for the precision
		int default_val = 6;
		String default_val_str = default_val + "";
		prec_slider = new JSlider(0,16,default_val);
		
	//prec_slider
		prec_slider.addChangeListener(this);
		add(prec_slider,"gridwidth = 2");
		
	//Precision text field
		prec_tf.setText(default_val_str);
		prec_tf.setEditable(false);
		prec_tf.setBackground(Color.WHITE);
		add(prec_tf);
		
	}
	
	@Override
	public void stateChanged(ChangeEvent arg0) {
		// TODO Auto-generated method stub
		
	
		int prec_slider_value = prec_slider.getValue();
		prec_tf.setText(prec_slider_value+"");
		
	}
	
	//If we click on a button
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String cur_command = e.getActionCommand();
		
		if(cur_command.equals("C"))//The clear button
		{
			infix = "";
			input.setText("");
			output.setText("");
				
			}
		
		else if(cur_command.equals("+/-")) {
			
			infix += "-";
			input.setText(infix);
		}
		
		//Evaluate the expression and show the result in the output
		else if(cur_command.equals("=")) {
			
			
			String express = input.getText();

			StringTokenizer st = new StringTokenizer(express,"+-x/",true);
				
			while (st.hasMoreTokens()) {
				intQ.Enqueue(st.nextToken());
			}
			
			while ((c=intQ.Dequeue()) != null) {
				 
				//Check if it's a number			 
				if(isNumber(c)) {
								
				outQ.Enqueue(c);//Enqueue the number in the output queue.
								 
				}
				 
							
			//When the dequeue element from the input is a operator
				else {
							
				while((d=stack.pop())!= null) {
									
		   //Use the function OperatorCompare to check the precedence.
					comparator = OperatorCompare(c, d);
									
			//when the dequeue operator is lower or equal than the top operator in the stack
				if(comparator == 0) {
										
			//Enqueue the top operator who is in the stack
					outQ.Enqueue(d);
				}
									
			//When the dequeue operator is higher than the top operator in the stack			
				else {
											
					stack.push(d);
						break;
						}	
		}
								
				stack.push(c);
				}
						
		}
						 
		//pop all elements left in the stack and enqueue them in the output queue
			while((d=stack.pop()) != null) {
							 
				outQ.Enqueue(d);
			 }
			
			 //Evaluate my postfix
			
			while ((c=outQ.Dequeue()) != null) {
				
		//When it's a number, I push it in a stack
				if(isNumber(c)) {
								 
				 Bstack.push(c);
				 }
				
		/*When I meet an operator, I pop two numbers and I evaluate them with the correct operator. After, I push 
		  the result in the stack. I do the same thing until there is no operator left in the output queue.
		  Each time, I need to convert the string into a double to be able to evaluate the expression and after, I
		  reconvert the result into a string to be able to push it in the stack*/
				
				else if(c.equals("+")) {//if the operator is +
					 nub1 = Double.parseDouble(Bstack.pop());
					 nub2 = Double.parseDouble(Bstack.pop());
								 
					 result = nub1 + nub2;
					 Bstack.push(String.valueOf(result));
				
								 
				 	}
				 else if(c.equals("-")) {//if the operator is -
					nub2 = Double.parseDouble(Bstack.pop());
					nub1 = Double.parseDouble(Bstack.pop());
								 
					result = nub1 - nub2;
					Bstack.push(String.valueOf(result));
				
				 }
				 else if(c.equals("x")) {//if the operator is x
					 nub1 = Double.parseDouble(Bstack.pop());
					 nub2 = Double.parseDouble(Bstack.pop());
								 
					 result = nub1*nub2;
					 Bstack.push(String.valueOf(result));
				
								 
				 }
				 else if(c.equals("/")) {//if the operator is /
					nub2 = Double.parseDouble(Bstack.pop());
					nub1 = Double.parseDouble(Bstack.pop());
								 
					result = nub1 / nub2;
					Bstack.push(String.valueOf(result));
				
								 
					 }
		 }
			
			//controls the number of decimal places(bonus)
			double z = Double.parseDouble(Bstack.pop());
			String nwinp = express + "=";
			input.setText(nwinp);
			
			if(prec_tf.getText().equals("6")) {
				
				String value = String.format("%f", z);
				output.setText(value);
			}
			
			if(prec_tf.getText().equals("0")) {
				
				String value = String.format("%.0f", z);
				output.setText(value);
			}
			
			if(prec_tf.getText().equals("1")) {
				
				String value = String.format("%.1f", z);
				output.setText(value);
			}
			
			if(prec_tf.getText().equals("2")) {
				
				String value = String.format("%.2f", z);
				output.setText(value);
			}
			
			if(prec_tf.getText().equals("3")) {
				
				String value = String.format("%.3f", z);
				output.setText(value);
			}
			
			if(prec_tf.getText().equals("4")) {
				
				String value = String.format("%.4f", z);
				output.setText(value);
			}
			
			if(prec_tf.getText().equals("5")) {
				
				String value = String.format("%.5f", z);
				output.setText(value);
			}
			
			if(prec_tf.getText().equals("7")) {
				
				String value = String.format("%.7f", z);
				output.setText(value);
			}
			
			if(prec_tf.getText().equals("8")) {
				
				String value = String.format("%.8f", z);
				output.setText(value);
			}
			
			if(prec_tf.getText().equals("9")) {
	
				String value = String.format("%.9f", z);
				output.setText(value);
			}
			
			if(prec_tf.getText().equals("10")) {
				
				String value = String.format("%.10f", z);
				output.setText(value);
			}
			
			if(prec_tf.getText().equals("11")) {
				
				String value = String.format("%.11f", z);
				output.setText(value);
			}
			
			if(prec_tf.getText().equals("12")) {
				
				String value = String.format("%.12f", z);
				output.setText(value);
			}

			if(prec_tf.getText().equals("13")) {
				
				String value = String.format("%.13f", z);
				output.setText(value);
			}
			
			if(prec_tf.getText().equals("14")) {
				
				String value = String.format("%.14f", z);
				output.setText(value);
			}
			
			if(prec_tf.getText().equals("15")) {
				
				String value = String.format("%.15f", z);
				output.setText(value);
			}
			
			if(prec_tf.getText().equals("16")) {
				
				String value = String.format("%.16f", z);
				output.setText(value);
			}

		}
		
		//to write the expression
		else {
			infix += cur_command;
			input.setText(infix);
			}
		
	}
	
	
	//To check if it's a number. create a boolean
		public static Boolean isNumber(String c) {
					
		//When it's not a number	
			if (c.equals("+") || c.equals("-") || c.equals("x") || c.equals("/") ) {
			
				return false;
						}
			else { //when it's a number
				return true;
						}
			
	}

		public static void main(String[] args) {
				 	 
	}	
			
		//To check the precedence of the operators.
		public static int OperatorCompare(String QueueStr,String stackStr) {
				int intstack;
				int intqueue;
				
		//Attribute a value to + and - who are in the input queue
				if (QueueStr.equals("+") || QueueStr.equals("-")) {
					intqueue = 1;
				}
				
		//Attribute a value to * and / who are in the input queue(a value higher than + and -)
				else {
					intqueue = 2;
				}
				
			//Attribute a value to + and - who are in the stack
				if (stackStr.equals("+") || stackStr.equals("-")) {
					intstack = 1;
				}
				
			//Attribute a value to * and / who are in the stack(a value higher than + and -)
				else {
					intstack = 2;
				}
			
			//when the dequeue operator is lower or equal than the top operator in the stack
				if (intqueue <= intstack) {
					return 0;
				}
			
			//when the dequeue operator is higher than the top operator in the stack
				else return 1;
				
			}

	
}
