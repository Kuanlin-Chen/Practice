import java.util.*;

class Postfix
{
	static String input;
	static String postfix;
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		System.out.print("Please Enter Formula:");
		input = sc.next(); //next() exclude empty character, space and tab
		while(ParentheseWrong())
		{
			//Scanner sc = new Scanner(System.in);
			System.out.print("Please Enter Formula Again:");
			input = sc.next();
		}
		System.out.println("Origin:"+input);
		
		postfix = toPostfix();
		System.out.println("Postfix:"+postfix);

		int answer = evaluate();
		System.out.println("Answer:"+answer);
	}

	private static boolean ParentheseWrong()
	{
		Stack<Character> inputStack = new Stack<Character>();
		for(int i = 0; i < input.length(); i++)
		{
			if ( input.charAt(i) == '(' )
			{
			    inputStack.push(input.charAt(i));	
			}
			else if ( input.charAt(i) == ')' && !(inputStack.empty()) )
			{
				inputStack.pop();
			}
			else if ( input.charAt(i) == ')' && (inputStack.empty()) )
			{
				//missing '('
		        inputStack.push(input.charAt(i));	
			}
		}
		if (inputStack.empty()) return false;
		else return true;
	}

	private static String toPostfix()
	{
		Stack<Character> operatorStack = new Stack<Character>();
		StringBuilder postfixBuilder = new StringBuilder();
		//StringBuilder operandBuilder = new StringBuilder();

		for (int i = 0; i < input.length(); i++) 
		{
			int prior = priority(input.charAt(i));
		    switch (prior)
		    {
		    	case 0:
		    	  postfixBuilder.append(String.valueOf(input.charAt(i)));
		    	  break;
		    	case 1:
		    	  //if stack contains top priority of data, pop out all operand first
		    	  //if( (!operatorStack.empty()) && ( operatorStack.peek() == '*' || operatorStack.peek() == '/' ) )
		    	  if( (!operatorStack.empty()) )
		    	  {
		    	  	int size = operatorStack.size();
		    	  	for ( int j = 0; j < size; j++ ) 
		    	  	{
		    	  		postfixBuilder.append(String.valueOf(operatorStack.pop()));	
		    	  	}
		    	  }
		    	  operatorStack.push(input.charAt(i));
		    	  break;
		    	case 2:
		    	  operatorStack.push(input.charAt(i));
		    	  break;
		    }
		}

        //pop out remaining data
		int size = operatorStack.size();
		for ( int k = 0; k < size; k++ ) 
		{
			postfixBuilder.append(String.valueOf(operatorStack.pop()));	
		}

        //return postfix
		return postfixBuilder.toString();
	}

    private static int evaluate()
    {
    	Stack<String> numericStack = new Stack<String>();

    	for(int i = 0; i < postfix.length(); i++)
    	{
    		int prior = priority(postfix.charAt(i));
    		if(prior!=0)
    		{
    			int a = Integer.valueOf(numericStack.pop());
    			int b = Integer.valueOf(numericStack.pop());
    			int temp = calculate(a, b, Character.toString(postfix.charAt(i)));
    			numericStack.push( String.valueOf(temp) ); //convert integer into String
    		}
    		else
    		{
    			numericStack.push(Character.toString(postfix.charAt(i)));
    		}
    	}

    	return Integer.valueOf(numericStack.pop());
    }

    private static int calculate(int a, int b, String operator)
    {
    	int temp = 0;
    	switch(operator)
    	{
    		case "+":
              temp = b+a;
              break;
            case "-":
              temp = b-a;
              break;
            case "*":
              temp = b*a;
              break;
            case "/":
              temp = b/a;
              break;
    	}
    	return temp;
    }

    private static int priority(char value)
	{
		return value == ')' ? 3 : value == '*' || value == '/' ? 2 : value == '+' || value == '-' || value == '(' ? 1 : 0 ;
	}

}