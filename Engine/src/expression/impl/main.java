package expression.impl;

import engine.api.Engine;
import engine.impl.EngineImpl;
import expression.api.Data;
import expression.api.Expression;
import expression.parser.CellValueParser;
import operations.Operation;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

public class main
{

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();

        Expression e = new RawString("hello");
        Expression e1 = new Number(4);
        Expression e2 = new Number(5);

        Operation operation = Operation.valueOf(str);
        Expression exp = operation.create(e,e1, e2);

        Expression e3 = CellValueParser.toExpression("{DIVIDE, {PLUS, {PLUS, 5, 7}, {PLUS, 5, 7}}, {PLUS, {PLUS, 5, 7}, {PLUS, -5, -7}}}");
       // Operation mathOperation = Operation.valueOf("SUB");
       // Expression exp1 = mathOperation.create(exp, new Number(0), new Number(4));

        System.out.println(e3.evaluate().getType());
        System.out.println(e3.evaluate().getValue());

//        Engine engine = EngineImpl.CreateEngine();
//        engine.ReadXMLInitFile("Engine/src/engine/jaxb/resources/basic.xml");

    }
}
