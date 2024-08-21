package expression.impl;

import expression.api.Expression;
import expression.parser.OrignalValueUtilis;
import sheet.api.Sheet;
import sheet.coordinate.impl.CoordinateImpl;
import sheet.impl.SheetImpl;
import sheet.layout.api.Layout;
import sheet.layout.impl.LayoutImpl;
import sheet.layout.size.api.Size;
import sheet.layout.size.impl.SizeImpl;

public class main
{
    public static void main(String[] args) {

        int width = 5;
        int height = 5;

        Size size = SizeImpl.create(width, height);

        int column = 5;
        int row = 5;

        Layout layout = LayoutImpl.create(size, column, row);
        String name = "Yaniv";

        Sheet sh =SheetImpl.create(name, layout);
        sh.setCell(CoordinateImpl.toCoordinate("A1"),"5");
        sh.setCell(CoordinateImpl.toCoordinate("A2"),"5");
        sh.setCell(CoordinateImpl.toCoordinate("A3"),"{PLUS,{REF, A2},{REF,A1}}");

        Expression expOfA3 = OrignalValueUtilis.toExpression("{PLUS,{REF, A2},{REF,    A1}}");

       // Cell c = sh.getCell(2, 0);

        System.out.println(expOfA3.evaluate().getType());
        System.out.println(expOfA3.evaluate().getValue());
        System.out.println("after change:");

        sh.setCell(CoordinateImpl.toCoordinate("A1"),"10");
        sh.setCell(CoordinateImpl.toCoordinate("A5"),"hello");
        System.out.println(expOfA3.evaluate().getType());
        System.out.println(expOfA3.evaluate().getValue());
        System.out.println(expOfA3.evaluate().getValue());


        String str = "{PLUS,{REF, A2},{REF,    A1}}";

        OrignalValueUtilis.findInfluenceFrom(str);

        //System.out.println(c.getEffectiveValue().getValue());
//        sh.setCell(1, 4,"{PLUS,{REF, B1},{REF,A1}" );

       // Expression e3 = CellValueParser.toExpression("{DIVIDE, {PLUS, {PLUS, 5, 7}, {PLUS, 5, 7}}, {PLUS, {PLUS, 5, 7}, {PLUS, -5, -7}}}");
       // Operation mathOperation = Operation.valueOf("SUB");
       // Expression exp1 = mathOperation.create(exp, new Number(0), new Number(4));

//        Engine engine = EngineImpl.CreateEngine();
//        engine.ReadXMLInitFile("Engine/src/engine/jaxb/resources/basic.xml");



    }
}
