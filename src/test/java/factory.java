import org.testng.annotations.Factory;

public class factory {
    @Factory
    public Object[] factoryMethod(){
        return new Object[]{
                new prueba_mailchimp(),
                new prueba_mailchimp()
        };
    }
}
