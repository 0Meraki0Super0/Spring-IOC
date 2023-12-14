package spring.Annotation;

public class Text {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException{
        myApplicationContext ioc = new myApplicationContext(SpringConfig.class);
        System.out.println("jhh");
        Object userAction = ioc.getBean("UserAction");
        Object wr = ioc.getBean("wr");
        System.out.println(userAction);
        System.out.println(wr);
    }
}
