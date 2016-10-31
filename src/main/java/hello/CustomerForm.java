package hello;

/**
 * Created by benoi on 29/10/2016.
 */
public class CustomerForm {
    private CustomerService service = CustomerService.getInstance();
    private Customer customer;
    private MyUI myUI;

    public CustomerForm(MyUI myUI) {
        this.myUI = myUI;
    }
}
