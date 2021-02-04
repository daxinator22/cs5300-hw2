import javax.sound.sampled.FloatControl;
import java.io.IOException;

public class Infix {

    public static void main(String[] args) {
        String str = "+++12-835"; // (((1+2)+(8-3))+5)
        //String str = "";
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

    public Infix(String terminals) throws Exception{
        lookahead = 0;
        string = terminals;
        list();

        //If the parser gets to this point and the lookahead is not at the end of the string,
        //then there is an error because there is no production rule that starts with list.
        if(lookahead < string.length() - 1){
            throw new Exception("Syntax Error on Infix()");
        }
    }

    //Evaluates the values of the list
    private void list() throws Exception{

        if (string.charAt(lookahead) == '+') {
            match(lookahead);
            System.out.print('(');
            list();
            System.out.print('+');
            list();
            System.out.print(')');
        } else if (string.charAt(lookahead) == '-') {
            match(lookahead);
            System.out.print('(');
            list();
            System.out.print('-');
            list();
            System.out.print(')');
        } else {
            digit();
        }

    }


    //Evaluates the values of the digits
    private void digit() throws Exception{
        if(Character.isDigit(string.charAt(lookahead))){
            System.out.print(string.charAt(lookahead));
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
            throw new Exception("Syntax Error on match()");
        }
    }

}
