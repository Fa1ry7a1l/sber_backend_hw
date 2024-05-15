public class Main {
    public static void main(String[] args) {
        Child c1 = new Child();
        Child c2 = new Child("Ivan");
    }
}

/*Parent:static 1
Parent:static 2
Child:static 1
Child:static 2
Parent:instance 1
Parent:instance 2
Parent:constructor
Child:instance 1
Child:instance 2
Child:constructor
Parent:instance 1
Parent:instance 2
Parent:name-constructor
Child:instance 1
Child:instance 2
Child:name-constructor*/

/*Статические блоки срабатывают при запуске, при этом блоки родителей срабатывают раньше детей.*/
/*нестатические блоки детей срабатывают после родительского конструктора, но до собственного.*/

class Child extends Parent
{
    static{
        System.out.println("Child:static 1");
    }
    {
        System.out.println("Child:instance 1");
    }

    static{
        System.out.println("Child:static 2");
    }
    Child()
    {
        System.out.println("Child:constructor");
    }
    Child(String name)
    {
        super(name);
        System.out.println("Child:name-constructor");
    }
    {
        System.out.println("Child:instance 2");
    }
}
abstract class Parent{
    private String name;
    static{
        System.out.println("Parent:static 1");
    }
    {
        System.out.println("Parent:instance 1");
    }

    static{
        System.out.println("Parent:static 2");
    }
    Parent()
    {
        System.out.println("Parent:constructor");
    }
    {
        System.out.println("Parent:instance 2");
    }
    Parent(String name)
    {
        System.out.println("Parent:name-constructor");
        this.name = name;
    }

}

