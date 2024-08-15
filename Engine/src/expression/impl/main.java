package expression.impl;


import expression.api.Expression;

public class main
{
    public static void main(String[] args) {


        Expression exp = new Plus(new Number(4), new Number(3));
        System.out.println(exp.evaluate().getValue());
        System.out.println(exp.evaluate().getType());
        exp = new Minus(new Number(4), new Number(3));
        System.out.println(exp.evaluate().getValue());
        System.out.println(exp.evaluate().getType());
        exp = new Divide(new Number(4), new Number(3));
        System.out.println(exp.evaluate().getValue());
        System.out.println(exp.evaluate().getType());
        exp = new Mod(new Number(4), new Number(3));
        System.out.println(exp.evaluate().getValue());
        System.out.println(exp.evaluate().getType());

        exp = new Abs(new Minus(new Number(-4), new Number(3)));
        System.out.println(exp.evaluate().getValue());
        System.out.println(exp.evaluate().getType());

        String str = "hello";
        String str2 = " world";
        String str3 = " good";

        exp = new Concat(new Concat(new RawString(str), new RawString(str3)), new RawString(str2));
        System.out.println(exp.evaluate().getValue());
        System.out.println(exp.evaluate().getType());


    }
}
