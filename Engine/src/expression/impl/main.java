package expression.impl;

import expression.api.Expression;
import expression.parser.OrignalValueUtilis;
import sheet.api.Sheet;
import sheet.cell.api.Cell;
import sheet.coordinate.impl.CoordinateFactory;
import sheet.coordinate.impl.CoordinateImpl;
import sheet.impl.SheetImpl;
import sheet.layout.api.Layout;
import sheet.layout.impl.LayoutImpl;
import sheet.layout.size.api.Size;
import sheet.layout.size.impl.SizeImpl;

import java.util.Map;

public class main
{
    public static Integer  f = 1;

    public static void print(Sheet sh)
    {

        System.out.println(f.toString() +  ":\n");
        f++;
        sh.getActiveCells().forEach((coordinate, cell) ->
                System.out.println(cell.getCoordinate().toString() + ": "
                        + "\"" + cell.getOriginalValue() + "\""+ " = "
                        + cell.getEffectiveValue().getValue()));

        System.out.println("\n");
    }

    public static void main(String[] args) {

        int width = 5;
        int height = 5;

        Size size = SizeImpl.create(width, height);

        int column = 5;
        int row = 5;

        Layout layout = LayoutImpl.create(size, column, row);
        String name = "Yaniv";
        Sheet sh =SheetImpl.create(name, layout);

        try
        {
            //init
            sh.setCell(CoordinateImpl.toCoordinate("A1"),"{POW,2,4}");
           sh.setCell(CoordinateImpl.toCoordinate("A2"),"{REF,A1}");
            sh.setCell(CoordinateImpl.toCoordinate("A4"),"world");
            sh.setCell(CoordinateImpl.toCoordinate("A5"),"hello ");
            sh.setCell(CoordinateImpl.toCoordinate("A3"),"{CONCAT,{REF,A5},{REF,A4}}");
            sh.setCell(CoordinateImpl.toCoordinate("B3"),"{REF,A3}");

            print(sh);
            sh.setCell(CoordinateImpl.toCoordinate("A5"),"{MINUS,3,{REF,A1}}");
             print(sh);


        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            print(sh);
        }

            try{
                sh.setCell(CoordinateImpl.toCoordinate("A1"),"hello");
                print(sh);

            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
                print(sh);
            }
        try{

            sh.setCell(CoordinateImpl.toCoordinate("A3"),"{MINUS,{REF,A1},{PLUS,{REF,A2},{REF,A1}}}");
            print(sh);

        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            print(sh);
        }
        try{
            sh.setCell(CoordinateImpl.toCoordinate("A1"),"{REF,A2}");
            print(sh);

        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            print(sh);
        }
        try{
            sh.setCell(CoordinateImpl.toCoordinate("A4"),"hello world");
            print(sh);

        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            print(sh);
        }
        try{
            sh.setCell(CoordinateImpl.toCoordinate("A5"),"{CONCAT,Hello,{SUB,{REF,A4},{REF,A2},{REF,A1}}}");
            print(sh);
            sh.setCell(CoordinateImpl.toCoordinate("B6"),"hello world");
            print(sh);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            print(sh);
        }


    }
}
