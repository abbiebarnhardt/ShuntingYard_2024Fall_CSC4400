package driver;

public class ShuntngYard 
{
	public static String Operators = "+-*/^()";
	public static String Numbers = "1234567890";
	
	public static boolean IsNumber(String input) 
	{
		for(char c: input.toCharArray()) 
		{
			if (Numbers.indexOf(c) == -1) 
			{
				return false;
			}
		}
		return true;
	}
	
	public static int getPrecedence(String input) 
	{
		char op = input.charAt(0);
		switch (op) 
		{
			case '(':
				return 1;
			case '+':
			case '-':
				return 2;
			case '*':
			case '/':
				return 3;
			case '^':
				return 4;
			case ')':
				return 5;
			default:
				return 0;
		}			
	}

	//parse a math expression into a linked list
    //input: the math expression as a string
    //parsed result will stored in Token list
	public static TokenList ParseFromExp(String exp) 
	{
		TokenList lst = new TokenList();

		for(int i = 0; i < exp.length(); i++)
		{
			String temp = exp.substring(i, i+1);
			int counter = 0;
			while((i+counter+1) < exp.length() & getPrecedence(exp.substring(i+counter, i+counter+1)) == 0)
			{
				//System.out.println(temp);
				temp = temp + exp.substring(i+counter+1, i+counter+2);
				counter++;
				//System.out.println(temp);
			}
			if(temp.length() >1)
			{
				temp = temp.substring(0, temp.length()-1);
				counter--;
			}

			
			//will end up in reversed order
			Node node = new Node(temp);
			lst.Append(node);
			i = i+ counter;
		}
		


		return lst;
	}
	
	//take the tokens from Tokens queue, and stored the reversed polish expression in ReversePolish queue
	public static TokenList BuildFromTokens(TokenList tokenList)
	{
        TokenList outputQueue = new TokenList();
        IntegerStack operatorStack = new IntegerStack();
        Node currentNode = tokenList.Head;

        // Loop through each token in the token list
        while (currentNode != null) {
            String tokenValue = currentNode.Payload.toString();
            int precedence = getPrecedence(tokenValue);

            // If the token is a number, add it to the output queue
            if (precedence == 0) {
                outputQueue.Append(currentNode);
            }
            // If the token is an operator
            else if (precedence > 1 && precedence < 5) {
                while (!operatorStack.IsEmpty() &&
                       getPrecedence(operatorStack.Peek().Payload.toString()) >= precedence) {
                    outputQueue.Append(operatorStack.Pop());
                }
                operatorStack.Push(currentNode);
            }
            // If the token is a left parenthesis, push it onto the stack
            else if (tokenValue.equals("(")) {
                operatorStack.Push(currentNode);
            }
            // If the token is a right parenthesis
            else if (tokenValue.equals(")")) {
                while (!operatorStack.IsEmpty() && !operatorStack.Peek().Payload.toString().equals("(")) {
                    outputQueue.Append(operatorStack.Pop());
                }
                // Discard the left parenthesis
                if (!operatorStack.IsEmpty() && operatorStack.Peek().Payload.toString().equals("(")) {
                    operatorStack.Pop();
                }
            }

            // Move to the next token
            currentNode = currentNode.NextNode;
        }

        // Pop any remaining operators from the stack to the output queue
        while (!operatorStack.IsEmpty()) {
            outputQueue.Append(operatorStack.Pop());
        }

        return outputQueue;
	}
	
	//process use the reverse polish format of expression to process the math result
	//output: the math result of the expression
	public static String Process(TokenList queue) {
	    IntegerStack resultStack = new IntegerStack();
	    Node<String> currentNode = queue.Head;

	    while (currentNode != null) {
	        String tokenValue = currentNode.Payload;

	        if (IsNumber(tokenValue)) {
	            // If it's a number, push it to the result stack
	            resultStack.Push(new Node<>(tokenValue));
	        } else {
	            // Pop two operands from the stack for the operation
	            int b = Integer.parseInt(resultStack.Pop().Payload);
	            int a = Integer.parseInt(resultStack.Pop().Payload);

	            // Perform the operation based on the tokenValue
	            int result = 0;
	            switch (tokenValue) {
	                case "+":
	                    result = a + b;
	                    break;
	                case "-":
	                    result = a - b;
	                    break;
	                case "*":
	                    result = a * b;
	                    break;
	                case "/":
	                    result = a / b;
	                    break;
	                case "^":
	                    result = (int) Math.pow(a, b);
	                    break;
	            }
	            // Push the result back as a string
	            resultStack.Push(new Node<>(String.valueOf(result)));
	        }
	        currentNode = currentNode.NextNode;
	    }

	    // The final result should be the only element left in the stack
	    return resultStack.Pop().Payload;
	}
}
