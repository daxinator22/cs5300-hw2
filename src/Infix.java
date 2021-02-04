import javax.sound.sampled.FloatControl;
import java.io.IOException;

public class Infix {

    public static void main(String[] args) {
        //String str = "+++12-835"; // (((1+2)+(8-3))+5)
        String str = "+7";
        if (args.length > 0) {
            str = args[0];
        }
        try {
            Infix parser = new Infix(str);
        } catch (Exception e) {
            System.out.println("error");
        }
    }

    /*

    Production Rules with Semantic Actions:
        list -> + {print('(') list {print('+')} list {print(')')}
        list -> - {print('(') list {print('-')} list {print(')')}
        list -> digit
       digit -> 0 {print('0')} | 1 {print('1')} | ... | 9 {print('9')}

     */

    static int lookahead;
    static String string;
    static String test_result;

    public Infix(String terminals) throws Exception{
        lookahead = 0;
        string = terminals;
        test_result = "";
        while(lookahead < string.length()){
            list();
        }
    }

    //Evaluates the values of the list
    private void list() throws Exception{

        if (string.charAt(lookahead) == '+') {
            match(lookahead);
            System.out.print('(');
            test_result += '(';
            list();
            System.out.print('+');
            test_result += '+';
            list();
            System.out.print(')');
            test_result += ')';
        } else if (string.charAt(lookahead) == '-') {
            match(lookahead);
            System.out.print('(');
            test_result += '(';
            list();
            System.out.print('-');
            test_result += '-';
            list();
            System.out.print(')');
            test_result += ')';
        } else {
            digit();
        }

    }


    //Evaluates the values of the digits
    private void digit() throws Exception{
        if(Character.isDigit(string.charAt(lookahead))){
            System.out.print(string.charAt(lookahead));
            test_result += string.charAt(lookahead);
            match(lookahead);
        }
        else{
            throw new Exception("Syntax Error on digit()");
        }
    }

    //Increments the lookahead
    private void match(int t) throws Exception{
        if(lookahead == t){
            lookahead++;
        }
        else{
            throw new Exception();
        }
    }

}
