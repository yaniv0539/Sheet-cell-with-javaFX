package expression.impl;

import expression.api.Data;
import expression.api.Expression;
import operations.Operation;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

public class main
{

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();

        Operation oper = Operation.valueOf(str);
        Expression exp = oper.create(new RawString("hello"), new RawString(" world"));

        Operation mathOperation = Operation.valueOf("SUB");
        Expression exp1 = mathOperation.create(exp, new Number(0), new Number(4));
        Data data = exp1.evaluate();

        System.out.println(data.getType());
        System.out.println(data.getValue());


    }
}
