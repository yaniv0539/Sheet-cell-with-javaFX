package expression.impl;

import engine.api.Engine;
import engine.impl.EngineImpl;

public class main
{

    public static void main(String[] args) {

        Engine e = EngineImpl.CreateEngine();
        e.ReadXMLInitFile("Engine/src/engine/jaxb/resources/basic.xml");

//        Scanner sc = new Scanner(System.in);
//        String str = sc.nextLine();
//        Expression e = new RawString("hello");
//
//        Operation operation = Operation.valueOf(str);
//        Expression exp = operation.create(e);
//
//
//       // Operation mathOperation = Operation.valueOf("SUB");
//       // Expression exp1 = mathOperation.create(exp, new Number(0), new Number(4));
//        Data data = exp.evaluate();
//
//        System.out.println(data.getType());
//        System.out.println(data.getValue());


    }
}
